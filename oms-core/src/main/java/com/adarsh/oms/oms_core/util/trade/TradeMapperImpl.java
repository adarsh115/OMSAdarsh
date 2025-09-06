package com.adarsh.oms.oms_core.util.trade;

import com.adarsh.oms.oms_core.dto.trade.TradeRequestDto;
import com.adarsh.oms.oms_core.dto.trade.TradeResponseDto;
import com.adarsh.oms.oms_core.entity.Trade;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TradeMapperImpl implements TradeMapper{

    public Trade toEntity(TradeRequestDto dto) {
        Trade trade = new Trade();

        trade.setOrderId(dto.getOrderId());
        trade.setSymbol(dto.getSymbol());
        trade.setPrice(dto.getPrice());
        trade.setQuantity(dto.getQuantity());
        trade.setStatus(dto.getStatus());
        trade.setExecutionTime(LocalDateTime.now());

        return trade;
    }

    public TradeResponseDto toResponseDto(Trade trade) {
        TradeResponseDto dto = new TradeResponseDto();
        if(dto == null) return null;

        dto.setId(trade.getId());
        dto.setOrderId(trade.getOrderId());
        dto.setSymbol(trade.getSymbol());
        dto.setPrice(trade.getPrice());
        dto.setQuantity(trade.getQuantity());
        dto.setExecutionTime(trade.getExecutionTime());
        dto.setStatus(trade.getStatus());

        return dto;
    }
}
