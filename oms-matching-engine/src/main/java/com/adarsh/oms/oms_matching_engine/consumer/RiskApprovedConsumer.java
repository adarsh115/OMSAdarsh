package com.adarsh.oms.oms_matching_engine.consumer;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_events.avro.RiskApprovedEvent;
import com.adarsh.oms.oms_matching_engine.engine.MatchingEngineService;
import com.adarsh.oms.oms_matching_engine.enums.OrderSide;
import com.adarsh.oms.oms_matching_engine.enums.OrderType;
import com.adarsh.oms.oms_matching_engine.model.InternalOrder;
import com.adarsh.oms.oms_matching_engine.model.Trade;
import com.adarsh.oms.oms_matching_engine.producer.TradePublisher;
import com.adarsh.oms.oms_matching_engine.utils.OrderStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiskApprovedConsumer {

    private final MatchingEngineService matchingEngineService;
    private final TradePublisher tradePublisher;

    private final OrderStore orderStore; // in-memory for now
    private final Map<Long, RiskApprovedEvent> pendingApprovals = new ConcurrentHashMap<>();


    @KafkaListener(topics = "risk_approved", groupId = "oms-matching-engine")
    public void consume(RiskApprovedEvent event) {
        log.info("📥 Received RiskApprovedEvent: {}", event);

        if(!event.getApproved()){
            log.warn("❌ Order {} rejected by risk engine: {}", event.getOrderId(), event.getReason());
            return;
        }
        orderStore.find(event.getOrderId())
                .ifPresentOrElse(
                        order -> processApproval(event, order),
                        () -> {
                            log.warn("⚠️ Approval arrived before order, stashing orderId={}", event.getOrderId());
                            pendingApprovals.put(event.getOrderId(), event);
                        }
                );
    }

    // Called by OrderPlacedConsumer when an order arrives
    public void processPendingApproval(OrderPlacedEvent order) {
        RiskApprovedEvent approval = pendingApprovals.remove(order.getOrderId());
        if (approval != null) {
            log.info("🔁 Found pending approval for orderId={}, processing now", order.getOrderId());
            processApproval(approval, order);
        }
    }

    private void processApproval(RiskApprovedEvent approval, OrderPlacedEvent order) {
        InternalOrder internal = convertToInternalOrder(order);
        List<Trade> trades = matchingEngineService.processNewOrder(internal);

        log.info("📊 MatchingEngineService returned {} trades for orderId={}", trades.size(), order.getOrderId());

        if (trades.isEmpty()) {
            log.info("ℹ️ No trades executed for orderId={} (order added to book)", order.getOrderId());
        } else {
            trades.forEach(trade -> {
                log.debug("➡️ Publishing trade: {}", trade);
                tradePublisher.publish(trade);
            });
        }
    }

    private InternalOrder convertToInternalOrder(OrderPlacedEvent order) {
        return InternalOrder.builder()
                .orderId(String.valueOf(order.getOrderId()))
                .clientOrderId(order.getClientOrderId().toString())
                .symbol(order.getSymbol().toString())
                .side(OrderSide.valueOf(order.getSide().toString()))
                .type(OrderType.valueOf(order.getOrderType().toString()))
                .price(BigDecimal.valueOf(order.getPrice()))
                .quantity(order.getQuantity())
                .timestamp(Instant.ofEpochMilli(order.getTimestamp()))
                .build();
    }
}