package com.adarsh.oms.oms_order_service.dto;

import com.adarsh.oms.oms_order_service.enums.OrderSide;
import com.adarsh.oms.oms_order_service.enums.OrderType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Symbol must not be blank")
    private String symbol;

    @NotNull(message = "Side is required")
    private OrderSide side;

    @NotNull(message = "Type is required")
    private OrderType orderType;

    @NotNull(message = "Quantity must be at least 1")
    private Integer quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    private String clientOrderId;
}
