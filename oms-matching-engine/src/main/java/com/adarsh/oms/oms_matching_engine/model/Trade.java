package com.adarsh.oms.oms_matching_engine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    private String tradeId;
    private String buyOrderId;
    private String sellOrderId;
    private String symbol;
    private BigDecimal price;
    private int quantity;
    private Instant timestamp;
}
