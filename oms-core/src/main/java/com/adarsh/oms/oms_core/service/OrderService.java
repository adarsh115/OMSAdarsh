package com.adarsh.oms.oms_core.service;

import com.adarsh.oms.oms_core.dto.order.OrderRequestDto;
import com.adarsh.oms.oms_core.dto.order.OrderResponseDto;
import com.adarsh.oms.oms_core.dto.trade.TradeResponseDto;
import com.adarsh.oms.oms_core.entity.Order;
import com.adarsh.oms.oms_core.enums.OrderStatus;
import com.adarsh.oms.oms_core.enums.OrderType;
import com.adarsh.oms.oms_core.exception.OmsCoreException;
import com.adarsh.oms.oms_core.repository.order.OrderRepository;
import com.adarsh.oms.oms_core.util.order.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final TradeService tradeService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, TradeService tradeService){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.tradeService = tradeService;
    }

    //function to place order
    public OrderResponseDto placeOrder(OrderRequestDto requestDto){
        if (requestDto.getType() == OrderType.LIMIT && requestDto.getPrice() == null) {
            throw new OmsCoreException("Limit orders must include a price");
        }
        if (requestDto.getType() == OrderType.MARKET && requestDto.getPrice() != null) {
            throw new OmsCoreException("Market orders should not specify a price");
        }

        // Map and persist order
        Order order = orderMapper.toEntity(requestDto);
        order.setStatus(OrderStatus.NEW);
        Order savedOrder = orderRepository.save(order);

        // Execute trade
        TradeResponseDto trade = tradeService.executeTrade(savedOrder);

        // Optionally update order status
        savedOrder.setStatus(OrderStatus.FILLED);
        orderRepository.save(savedOrder);

        return orderMapper.toResponseDto(savedOrder);
    }

    //function to find order by id
    public OrderResponseDto getOrderById(Long id){
        return orderRepository.findById(id)
                .map(orderMapper::toResponseDto)
                .orElseThrow(() -> new OmsCoreException("Order with ID " + id + " not found"));
    }
    //function to get order by user
    public List<OrderResponseDto> getOrdersByUser(Long userId){
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }
    //function to get order by symbol
    public List<OrderResponseDto> getOrdersBySymbol(String symbol){
        return orderRepository.findBySymbol(symbol).stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }
    //function to get recent order
    public List<OrderResponseDto> getRecentOrder(int limit){
        return orderRepository.findRecentOrders(limit).stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }


}
