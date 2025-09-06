package com.adarsh.oms.oms_core.entity;

import com.adarsh.oms.oms_core.enums.OrderSide;
import com.adarsh.oms.oms_core.enums.OrderStatus;
import com.adarsh.oms.oms_core.enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol; // e.g., "AAPL", "BTC-USD"

    @Enumerated(EnumType.STRING)
    private OrderSide side; // BUY or SELL

    @Enumerated(EnumType.STRING)
    private OrderType type; // MARKET, LIMIT

    private Double price; // null for market orders

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // NEW, VALIDATED, EXECUTED, CANCELLED, REJECTED

    private Long userId; // optional: for multi-user support

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
