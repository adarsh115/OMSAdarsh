package com.adarsh.oms.oms_risk_service.consumer;

import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class MarketDataConsumer {


    private static final Logger log = LoggerFactory.getLogger(MarketDataConsumer.class);
    // Thread-safe map to store latest prices
    private final ConcurrentMap<String, Double> latestPrices = new ConcurrentHashMap<>();
    private final Set<String> fallbackInstruments;

    public MarketDataConsumer(@Value("${risk.known-instruments}") String fallbackInstrumentsCsv) {
        this.fallbackInstruments = Arrays.stream(fallbackInstrumentsCsv.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        log.info("📄 Loaded fallback instrument list: {}", this.fallbackInstruments);
    }

    @KafkaListener(
            topics = "market-data-avro",
            groupId = "oms-risk-service-market-data",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleMarketTick(MarketTickEvent tick) {
        latestPrices.put(tick.getSymbol().toString(), tick.getPrice());
//        log.info("📊 Updated market price: {} = {}", tick.getSymbol(), tick.getPrice());
    }

    public boolean isKnownInstrument(String symbol) {
        return latestPrices.containsKey(symbol) || fallbackInstruments.contains(symbol);
    }

    public Double getLatestPrice(String symbol) {
        return latestPrices.get(symbol);
    }

    public boolean isUsingFallbackOnly() {
        return latestPrices.isEmpty();
    }


}
