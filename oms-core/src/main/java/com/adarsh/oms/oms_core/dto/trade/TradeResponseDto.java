package com.adarsh.oms.oms_core.dto.trade;

import com.adarsh.oms.oms_core.enums.TradeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TradeResponseDto {
    private Long id;
    private Long orderId;
    private String symbol;
    private Double price;
    private Integer quantity;
    private LocalDateTime executionTime;
    private TradeStatus status;

}
