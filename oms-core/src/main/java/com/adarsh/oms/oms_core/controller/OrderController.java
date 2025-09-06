package com.adarsh.oms.oms_core.controller;

import com.adarsh.oms.oms_core.dto.order.OrderRequestDto;
import com.adarsh.oms.oms_core.dto.order.OrderResponseDto;
import com.adarsh.oms.oms_core.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDto placeOrder(@Valid  @RequestBody OrderRequestDto requestDto){
        return orderService.placeOrder(requestDto);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") @Min(1) Long id){
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDto> getOrderByUser(@PathVariable("userId") Long userId){
        return orderService.getOrdersByUser(userId);

    }

    @GetMapping("/symbol/{symbol}")
    public List<OrderResponseDto> getOrdersBySymbol(@PathVariable("symbol") String symbol) {
        return orderService.getOrdersBySymbol(symbol);
    }

    @GetMapping("/recent")
    public List<OrderResponseDto> getRecentOrder(@RequestParam(defaultValue = "10") @Min(1) int limit){
        return orderService.getRecentOrder(limit);
    }
}
