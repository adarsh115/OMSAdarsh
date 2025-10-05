package com.adarsh.oms.oms_core.kafka.consumers;

import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import com.adarsh.oms.oms_events.avro.RiskApprovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RiskApprovedConsumer {
    @KafkaListener(
            topics = "risk_approved",
            groupId = "oms-core",
            containerFactory = "orderPlacedKafkaListenerContainerFactory"
    )
    public void consume(RiskApprovedEvent event){
        log.info("✅ Received RiskApprovedEvent: {}", event);
    }
}
