package com.adarsh.oms.oms_order_service.controller;

import com.adarsh.oms.oms_order_service.dto.OrderRequestDto;
import com.adarsh.oms.oms_order_service.dto.OrderResponseDto;
import com.adarsh.oms.oms_order_service.exception.OmsServiceException;
import com.adarsh.oms.oms_order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Endpoints for placing and managing orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Place a new order",
            description = "Accepts market or limit orders and returns order status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        try {
            OrderResponseDto response = orderService.placeOrder(requestDto);
            return ResponseEntity.ok(response);
        } catch (OmsServiceException ex) {
            // Directly throw — no global handler
            throw ex;
        } catch (Exception ex) {
            throw new OmsServiceException("Unexpected error while placing order", "UNEXPECTED_ERROR");
        }
    }
}