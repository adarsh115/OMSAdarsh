package com.adarsh.oms.oms_core.util.trade;

import com.adarsh.oms.oms_core.dto.trade.TradeRequestDto;
import com.adarsh.oms.oms_core.dto.trade.TradeResponseDto;
import com.adarsh.oms.oms_core.entity.Trade;

public interface TradeMapper {
    Trade toEntity(TradeRequestDto dto);
    TradeResponseDto toResponseDto(Trade trade);
}
