package com.adarsh.oms.oms_core.dto.order;

import com.adarsh.oms.oms_core.enums.OrderSide;
import com.adarsh.oms.oms_core.enums.OrderStatus;
import com.adarsh.oms.oms_core.enums.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String symbol;
    private OrderSide side;
    private OrderType type;
    private Double price;
    private Integer quantity;
    private OrderStatus status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
