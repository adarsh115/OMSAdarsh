package com.adarsh.oms.oms_market_data.publisher;
//import com.adarsh.oms.events.MarketTickEvent;
import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import com.adarsh.oms.oms_market_data.exception.MarketDataException;
import org.springframework.kafka.core.KafkaTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import org.springframework.stereotype.Component;

@Component
public class MarketDataPublisher {
    private static final Logger log = LoggerFactory.getLogger(MarketDataPublisher.class);
    private final KafkaTemplate<String, MarketTickEvent> kafkaTemplate;

    public MarketDataPublisher(KafkaTemplate<String, MarketTickEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(MarketTickEvent event) {
        try {
            kafkaTemplate.send("market-data-avro", event.getSymbol().toString(), event);
            log.info("✅ Published tick: symbol={}, price={}, timestamp={}",
                    event.getSymbol(), event.getPrice(), event.getTimestamp());
        } catch (Exception e) {
            log.error("❌ Failed to publish MarketTickEvent for symbol: {}", event.getSymbol(), e);
            throw new MarketDataException("Failed to publish MarketTickEvent for symbol: " + event.getSymbol(), e);
        }

    }

}
