package com.adarsh.oms.oms_core.service;

import com.adarsh.oms.oms_core.dto.trade.TradeResponseDto;
import com.adarsh.oms.oms_core.entity.Order;
import com.adarsh.oms.oms_core.entity.Trade;
import com.adarsh.oms.oms_core.enums.OrderType;
import com.adarsh.oms.oms_core.enums.TradeStatus;
import com.adarsh.oms.oms_core.exception.OmsCoreException;
import com.adarsh.oms.oms_core.repository.trade.TradeRepository;

import com.adarsh.oms.oms_core.util.trade.TradeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper){
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    private Double fetchMarketPrice(String symbol) {
        // TODO: Integrate with price service or mock it
        return 100.0; // placeholder value
    }

    public TradeResponseDto executeTrade(Order order){
        Trade trade = new Trade();

        trade.setOrderId(order.getId());
        trade.setSymbol(order.getSymbol());
        trade.setQuantity(order.getQuantity());
        trade.setExecutionTime(LocalDateTime.now());
        trade.setStatus(TradeStatus.FILLED);

        if(order.getType() == OrderType.MARKET){
            trade.setPrice(fetchMarketPrice(order.getSymbol()));
        }
        else{
            trade.setPrice(order.getPrice());
        }

        Trade savedTrade = tradeRepository.save(trade);
        return tradeMapper.toResponseDto(savedTrade);
    }

    public TradeResponseDto getTradeById(Long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new OmsCoreException("Trade not found with ID: " + id));
        return tradeMapper.toResponseDto(trade);
    }

    public List<TradeResponseDto> getTradesByOrderId(Long orderId) {
        List<Trade> trades = tradeRepository.findByOrderId(orderId);
        return trades.stream()
                .map(tradeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TradeResponseDto> getTradesBySymbol(String symbol) {
        return tradeRepository.findBySymbol(symbol)
                .stream()
                .map(tradeMapper::toResponseDto)
                .collect(Collectors.toList());

    }

    public List<TradeResponseDto> getRecentTrades(int limit) {
        // 1. Create pagination and sorting request
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "executionTime"));

        // 2. Execute paginated query
        List<Trade> trades = tradeRepository.findAll(pageable).getContent();

        // 3. Convert entities to DTOs
        return trades.stream()
                .map(tradeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public TradeResponseDto cancelTradeAndReturn(Long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new OmsCoreException("Trade not found"));
        if (trade.getStatus() == TradeStatus.FILLED) {
            throw new OmsCoreException("Cannot cancel a filled trade");
        }
        if (trade.getStatus() == TradeStatus.REJECTED) {
            throw new OmsCoreException("Trade is already cancelled");
        }
        trade.setStatus(TradeStatus.REJECTED);
        Trade updated = tradeRepository.save(trade);

        return tradeMapper.toResponseDto(updated);
    }


}
