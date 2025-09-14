package com.adarsh.oms.oms_risk_service.service;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_risk_service.utility.RiskDecision;
import com.adarsh.oms.oms_risk_service.utility.RiskRejectionReasons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskEvaluatorService {

    private static final Logger log = LoggerFactory.getLogger(RiskEvaluatorService.class);

    public RiskDecision evaluateRisk(OrderPlacedEvent event) {
        String orderType = event.getOrderType().toString();

        log.info("Evaluating risk for orderId={}, symbol={}, quantity={}, price={}",
                event.getOrderId(), event.getSymbol(), event.getQuantity(), event.getPrice());

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