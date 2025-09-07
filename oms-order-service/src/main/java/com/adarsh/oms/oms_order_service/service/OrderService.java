package com.adarsh.oms.oms_order_service.service;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_order_service.dto.OrderRequestDto;
import com.adarsh.oms.oms_order_service.dto.OrderResponseDto;
import com.adarsh.oms.oms_order_service.enums.OrderStatus;
import com.adarsh.oms.oms_order_service.enums.OrderType;
import com.adarsh.oms.oms_order_service.exception.OmsServiceException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


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



        // Enrich order
        Instant timestamp = Instant.now();
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
                .setTimestamp(timestamp.toEpochMilli())
                .build();

        kafkaTemplate.send("orders-avro", event);


        // Construct response
        return OrderResponseDto.builder()
                .orderId(orderId)
                .clientOrderId(requestDto.getClientOrderId())
                .symbol(requestDto.getSymbol())
                .side(requestDto.getSide())
                .quantity(requestDto.getQuantity())
                .price(requestDto.getPrice())
                .status(status)
                .message("Order accepted and pending validation")
                .timestamp(timestamp)
                .build();
    }
}
