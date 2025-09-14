package com.adarsh.oms.oms_order_service.dto;

import com.adarsh.oms.oms_order_service.enums.OrderSide;
import com.adarsh.oms.oms_order_service.enums.OrderStatus;
import com.adarsh.oms.oms_order_service.enums.OrderType;
import com.adarsh.oms.oms_order_service.enums.RiskStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderResponseDto {

    @NotNull(message = "Order ID must not be null")
    private Long orderId;

    private String clientOrderId;

    @NotNull(message = "Order type must not be null")
    private OrderType orderType;

    @NotBlank(message = "Symbol must not be blank")
    private String symbol;

    @NotNull(message = "Order side is required")
    private OrderSide side;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    private RiskStatus riskStatus;

    @NotBlank(message = "Message must not be blank")
    private String message;

    @NotNull(message = "Timestamp must not be null")
    private Instant timestamp;

}
