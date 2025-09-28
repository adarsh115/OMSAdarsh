package com.adarsh.oms.oms_risk_service.service;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_risk_service.consumer.MarketDataConsumer;
import com.adarsh.oms.oms_risk_service.utility.RiskDecision;
import com.adarsh.oms.oms_risk_service.utility.RiskRejectionReasons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskEvaluatorService {

    private static final Logger log = LoggerFactory.getLogger(RiskEvaluatorService.class);
    private final MarketDataConsumer marketDataConsumer;

    public RiskEvaluatorService(MarketDataConsumer marketDataConsumer) {
        this.marketDataConsumer = marketDataConsumer;
    }


    public RiskDecision evaluateRisk(OrderPlacedEvent event) {
        String orderType = event.getOrderType().toString();
        String symbol = event.getSymbol().toString();

        //Check if live market data is available
        if (marketDataConsumer.isUsingFallbackOnly()) {
            log.warn("⚠️ Risk service is in FALLBACK MODE — using static instrument list until market data arrives");
        } else {
            log.info("✅ Risk service has live market data");
        }


        // 🆕 Rule 0: Validate instrument exists in market data
        if (!marketDataConsumer.isKnownInstrument(symbol)) {
            log.warn("Risk decision: UNKNOWN_INSTRUMENT");
            return new RiskDecision(false, "UNKNOWN_INSTRUMENT");
        }

        // Get live price if available, else fall back to order price
        double referencePrice = marketDataConsumer.getLatestPrice(symbol) != null
                ? marketDataConsumer.getLatestPrice(symbol)
                : event.getPrice();


        log.info("Evaluating risk for orderId={}, symbol={}, quantity={}, price={}",
                event.getOrderId(), event.getSymbol(), event.getQuantity(), event.getPrice());

        // Example: reject if order value > $1M
        double orderValue = event.getQuantity() * referencePrice;
        if (orderValue > 1_000_000) {
            log.warn("Risk decision: ORDER_VALUE_TOO_HIGH");
            return new RiskDecision(false, "ORDER_VALUE_TOO_HIGH");
        }

        // 🟡 Rule 1: Reject market orders over 10,000 quantity
        if ("MARKET".equalsIgnoreCase(orderType) && event.getQuantity() > 10000) {
            log.warn("Risk decision: {}", RiskRejectionReasons.HIGH_QUANTITY_MARKET_ORDER);
            return new RiskDecision(false, RiskRejectionReasons.HIGH_QUANTITY_MARKET_ORDER);
        }

        // 🟡 Rule 2: Reject orders with unrealistic price
        if (event.getPrice() < 0.01 || event.getPrice() > 1000000) {
            log.warn("Risk decision: {}", RiskRejectionReasons.INVALID_PRICE_RANGE);
            return new RiskDecision(false, RiskRejectionReasons.INVALID_PRICE_RANGE);
        }

        // ✅ Default: Approve
        log.info("Risk decision: {}", RiskRejectionReasons.ORDER_APPROVED);
        return new RiskDecision(true, RiskRejectionReasons.ORDER_APPROVED);
    }
}