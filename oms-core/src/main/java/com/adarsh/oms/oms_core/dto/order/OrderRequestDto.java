package com.adarsh.oms.oms_core.dto.order;

import com.adarsh.oms.oms_core.enums.OrderSide;
import com.adarsh.oms.oms_core.enums.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    @NotBlank(message = "Symbol must not be blank")
    private String symbol;

    @NotNull(message = "Side is required")
    private OrderSide side;

    @NotNull(message = "Type is required")
    private OrderType type;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    // Optional for MARKET orders, required for LIMIT
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    @NotNull(message = "User ID is required")
    private Long userId;

}
