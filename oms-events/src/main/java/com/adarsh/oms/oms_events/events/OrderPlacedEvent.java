package com.adarsh.oms.oms_events.events;

import com.adarsh.oms.oms_events.enums.OrderSide;
import com.adarsh.oms.oms_events.enums.OrderType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private String symbol;
    private OrderSide side;
    private OrderType orderType;
    private Integer quantity;
    private Double price;
    private String clientOrderId;
    private Instant timestamp;
}