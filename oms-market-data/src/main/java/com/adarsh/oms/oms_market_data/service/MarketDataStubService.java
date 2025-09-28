package com.adarsh.oms.oms_market_data.service;

import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import com.adarsh.oms.oms_events.avro.TickType;
import com.adarsh.oms.oms_market_data.publisher.MarketDataPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
public class MarketDataStubService {

    private static final List<String> SYMBOLS = List.of("AAPL", "GOOG", "MSFT", "TSLA", "AMZN");
    private static final Logger log = LoggerFactory.getLogger(MarketDataStubService.class);
    private final MarketDataPublisher publisher;
    private final Random random = new Random();

    public MarketDataStubService(MarketDataPublisher publisher) {
        this.publisher = publisher;
    }


    @Scheduled(fixedRate = 5000)
    public void emitTicks() {
        for(String sym: SYMBOLS){
            emitTick(sym);
        }
    }

    public void emitTick(String symbol) {

        double price = generatePrice(symbol);

        MarketTickEvent tick = MarketTickEvent.newBuilder()
                .setSymbol(symbol)
                .setPrice(price)
                .setTimestamp(Instant.now().toString())
                .setSource("SIMULATED")
                .setType(TickType.LAST_TRADE)
                .build();


        log.info("📈 Emitting tick: symbol={}, price={}, timestamp={}",
                tick.getSymbol(), tick.getPrice(), tick.getTimestamp());

        publisher.publish(tick);
    }

    private double generatePrice(String symbol) {
        return switch (symbol) {
            case "AAPL" -> 185 + Math.random() * 5;
            case "GOOG" -> 2700 + Math.random() * 50;
            case "MSFT" -> 300 + Math.random() * 10;
            case "TSLA" -> 700 + Math.random() * 100;
            case "AMZN" -> 3300 + Math.random() * 100;
            default -> 100 + Math.random() * 10;
        };
    }

}
