package com.adarsh.oms.oms_risk_service.consumer;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_events.avro.RiskApprovedEvent;
import com.adarsh.oms.oms_events.avro.RiskStatus;
import com.adarsh.oms.oms_risk_service.service.RiskEvaluatorService;
import com.adarsh.oms.oms_risk_service.utility.RiskDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderPlacedConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderPlacedConsumer.class);

    private final RiskEvaluatorService riskEvaluatorService;
    private final KafkaTemplate<String, RiskApprovedEvent> kafkaTemplate;

    public OrderPlacedConsumer(RiskEvaluatorService riskEvaluatorService,
                               KafkaTemplate<String, RiskApprovedEvent> kafkaTemplate) {
        this.riskEvaluatorService = riskEvaluatorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders-avro", groupId = "oms-risk-service", containerFactory = "kafkaListenerContainerFactory")
    public void handleOrderPlaced(OrderPlacedEvent event) {

        log.info("🟢 Kafka listener triggered for OrderPlacedEvent");
        log.info("📥 Received OrderPlacedEvent: orderId={}, symbol={}, quantity={}, price={}, orderType={}, side={}",
                event.getOrderId(), event.getSymbol(), event.getQuantity(), event.getPrice(),
                event.getOrderType(), event.getSide());


        try {
            log.info("🔍 Evaluating risk for orderId={}", event.getOrderId());

            RiskDecision decision = riskEvaluatorService.evaluateRisk(event);

            log.info("✅ Risk decision for orderId={}: {}", event.getOrderId(),
                    decision.isApproved() ? "APPROVED" : "REJECTED");

            RiskApprovedEvent response = RiskApprovedEvent.newBuilder()
                    .setOrderId(event.getOrderId())
                    .setClientOrderId(event.getClientOrderId())
                    .setApproved(decision.isApproved())
                    .setReason(decision.getReason())
                    .setRiskStatus(decision.isApproved() ? RiskStatus.APPROVED : RiskStatus.REJECTED)
                    .setTimestamp(Instant.now().toEpochMilli())
                    .build();

            kafkaTemplate.send("risk_approved", response);

            log.info("📤 Published RiskApprovedEvent: orderId={}, clientOrderId={}, approved={}, riskStatus={}, reason={}",
                    response.getOrderId(), response.getClientOrderId(), response.getApproved(), response.getRiskStatus(), response.getReason());

        } catch (Exception e) {
            log.error("❌ Failed to evaluate risk or publish event for orderId={}", event.getOrderId(), e);

        }
    }
}