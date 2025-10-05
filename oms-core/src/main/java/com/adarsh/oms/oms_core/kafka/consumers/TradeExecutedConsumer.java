package com.adarsh.oms.oms_core.kafka.consumers;

import com.adarsh.oms.oms_events.avro.TradeExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeExecutedConsumer {
    @KafkaListener(
            topics = "trade_executed",
            groupId = "oms-core",
            containerFactory = "orderPlacedKafkaListenerContainerFactory"
    )
    public void consume(TradeExecutedEvent event) {
        log.info("💰 Received TradeExecutedEvent: {}", event);
    }

}
