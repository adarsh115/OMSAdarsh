package com.adarsh.oms.oms_core.dto.trade;

import com.adarsh.oms.oms_core.enums.TradeStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TradeRequestDto {
    @NotNull(message = "Order id required")
    private Long orderId;

    @NotBlank(message = "Symbol must not be blank")
    private String symbol;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Status is required")
    private TradeStatus status;
}
