package com.adarsh.oms.oms_core.util.order;

import com.adarsh.oms.oms_core.dto.order.OrderRequestDto;
import com.adarsh.oms.oms_core.dto.order.OrderResponseDto;
import com.adarsh.oms.oms_core.entity.Order;
import com.adarsh.oms.oms_core.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapperImpl implements OrderMapper {

    public Order toEntity(OrderRequestDto dto) {
        Order order = new Order();
        order.setSymbol(dto.getSymbol());
        order.setSide(dto.getSide());
        order.setType(dto.getType());
        order.setPrice(dto.getPrice());
        order.setQuantity(dto.getQuantity());
        order.setUserId(dto.getUserId());
        order.setStatus(OrderStatus.NEW); // default status
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return order;
    }

    public OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setSymbol(order.getSymbol());
        dto.setSide(order.getSide());
        dto.setType(order.getType());
        dto.setPrice(order.getPrice());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUserId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        return dto;
    }
}
