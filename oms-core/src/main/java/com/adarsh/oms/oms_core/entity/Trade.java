package com.adarsh.oms.oms_core.entity;

import com.adarsh.oms.oms_core.enums.TradeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="trades")
@Getter
@Setter
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String symbol;

    private Double price;

    private Integer quantity;

    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;
}
