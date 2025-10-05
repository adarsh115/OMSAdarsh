package com.adarsh.oms.oms_core.kafka.consumers;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderPlacedConsumer {
    @KafkaListener(
            topics = "orders-avro",
            groupId = "oms-core",
            containerFactory = "orderPlacedKafkaListenerContainerFactory"
    )
    public void consume(OrderPlacedEvent event){
        log.info("🏢 Received OrderPlacedEvent: {}", event);
    }
}
