package com.adarsh.oms.oms_matching_engine.producer;

import com.adarsh.oms.oms_events.avro.TradeExecutedEvent;
import com.adarsh.oms.oms_matching_engine.model.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class TradePublisher {

    private final KafkaTemplate<String, TradeExecutedEvent> tradeKafkaTemplate;

    public void publish(Trade trade) {
        log.debug("🛠 Building TradeExecutedEvent from Trade {}", trade.getTradeId());

        TradeExecutedEvent event = TradeExecutedEvent.newBuilder()
                .setTradeId(trade.getTradeId())
                .setBuyOrderId(trade.getBuyOrderId())
                .setSellOrderId(trade.getSellOrderId())
                .setSymbol(trade.getSymbol())
                .setPrice(trade.getPrice().doubleValue())
                .setQuantity(trade.getQuantity())
                .setTimestamp(trade.getTimestamp().toEpochMilli())
                .build();

        log.info("📤 Publishing TradeExecutedEvent: {}", event);

        tradeKafkaTemplate.send("trade_executed", event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("❌ Failed to publish TradeExecutedEvent {} due to {}", event.getTradeId(), ex.getMessage(), ex);
                    } else {
                        log.debug("✅ Successfully sent TradeExecutedEvent {} to partition {} with offset {}",
                                event.getTradeId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}