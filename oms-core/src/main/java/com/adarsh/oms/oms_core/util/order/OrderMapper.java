package com.adarsh.oms.oms_core.util.order;

import com.adarsh.oms.oms_core.dto.order.OrderRequestDto;
import com.adarsh.oms.oms_core.dto.order.OrderResponseDto;
import com.adarsh.oms.oms_core.entity.Order;

//@Mapper
public interface OrderMapper {
    Order toEntity(OrderRequestDto dto);
    OrderResponseDto toResponseDto(Order order);
}
