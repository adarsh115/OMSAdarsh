package com.adarsh.oms.oms_core.kafka.consumers;

import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketDataConsumer {

    @KafkaListener(
            topics = "market-data-avro",
            containerFactory = "marketDataKafkaListenerContainerFactory",
            groupId = "oms-core"
    )
    public void consume(ConsumerRecord<String, MarketTickEvent> record) {
        MarketTickEvent event = record.value();
        log.info("✅ Received MarketTickEvent: {}", event);
        log.info("🧪 Class identity: {}", event.getClass().getCanonicalName());
    }
}