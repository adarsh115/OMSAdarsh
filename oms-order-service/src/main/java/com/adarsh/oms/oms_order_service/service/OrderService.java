package com.adarsh.oms.oms_order_service.service;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_order_service.dto.OrderRequestDto;
import com.adarsh.oms.oms_order_service.dto.OrderResponseDto;
import com.adarsh.oms.oms_order_service.enums.OrderStatus;
import com.adarsh.oms.oms_order_service.enums.OrderType;
import com.adarsh.oms.oms_order_service.enums.RiskStatus;
import com.adarsh.oms.oms_order_service.exception.OmsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;


import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrderService {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public OrderResponseDto placeOrder(OrderRequestDto requestDto) {
        // Business rule: LIMIT orders must have a price
        if (requestDto.getOrderType() == OrderType.LIMIT && requestDto.getPrice() == null) {
            throw new OmsServiceException("Limit orders must specify a price", "LIMIT_ORDER_MISSING_PRICE");
        }
        // Basic validation
        if (requestDto.getQuantity() <= 0 || requestDto.getQuantity() > 1000000) {
            throw new OmsServiceException("Invalid quantity", "INVALID_QUANTITY");
        }
        if (requestDto.getPrice() != null && requestDto.getPrice() <= 0) {
            throw new OmsServiceException("Invalid price", "INVALID_PRICE");
        }




        // Enrich order
        Instant timestamp = Instant.now();
        long epochMillis = timestamp.toEpochMilli();
        OrderStatus status = OrderStatus.PENDING;


        // Generate order ID (mock for now)
        Long orderId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;


        OrderPlacedEvent event = OrderPlacedEvent.newBuilder()
                .setOrderId(orderId)
                .setUserId(requestDto.getUserId())
                .setSymbol(requestDto.getSymbol())
                .setOrderType(requestDto.getOrderType().name())
                .setSide(requestDto.getSide().name())
                .setQuantity(requestDto.getQuantity())
                .setPrice(requestDto.getPrice())
                .setClientOrderId(requestDto.getClientOrderId())
                .setTimestamp(epochMillis)
                .build();

        CompletableFuture<SendResult<String, OrderPlacedEvent>> future =
                kafkaTemplate.send("orders-avro", event);
//        kafkaTemplate.send("orders-avro", event)

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("OrderPlacedEvent sent successfully: {}", event);
            } else {
                log.error("Failed to send OrderPlacedEvent: {}", event, ex);
            }
        });



        // Construct response
        return OrderResponseDto.builder()
                .orderId(orderId)
                .clientOrderId(requestDto.getClientOrderId())
                .orderType(requestDto.getOrderType())
                .symbol(requestDto.getSymbol())
                .side(requestDto.getSide())
                .quantity(requestDto.getQuantity())
                .price(requestDto.getPrice())
                .status(status) // e.g., "PENDING"
                .riskStatus(RiskStatus.PENDING)
                .message("Order accepted and awaiting risk validation")
                .timestamp(timestamp)
                .build();



    }
}
