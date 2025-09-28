package com.adarsh.oms.oms_matching_engine.consumer;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_matching_engine.utils.OrderStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedConsumer {
    private final OrderStore orderStore;
    private final RiskApprovedConsumer riskApprovedConsumer; // so you can call back

    @KafkaListener(topics = "orders-avro", groupId = "oms-matching-engine")
    public void consume(OrderPlacedEvent event) {
        log.info("📥 Received OrderPlacedEvent: {}", event);
        orderStore.save(event);
        log.info("🗂️ Cached orderId={} symbol={} side={}", event.getOrderId(), event.getSymbol(), event.getSide());
        // If approval already arrived earlier
        riskApprovedConsumer.processPendingApproval(event);
    }

}
