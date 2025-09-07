package com.adarsh.oms.oms_events.events;

import com.adarsh.oms.oms_events.enums.OrderSide;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TradeExecutedEvent {
    private Long tradeId;
    private Long orderId;
    private String symbol;
    private OrderSide side;
    private Integer quantity;
    private Double price;
    private Instant timestamp;
}
