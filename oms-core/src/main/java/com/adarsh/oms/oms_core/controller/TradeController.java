package com.adarsh.oms.oms_core.controller;

import com.adarsh.oms.oms_core.dto.trade.TradeResponseDto;
import com.adarsh.oms.oms_core.service.TradeService;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
@Validated
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService){
        this.tradeService = tradeService;
    }

    @GetMapping("/{id}")
    public TradeResponseDto getTradeById(@PathVariable("id") @Min(1) Long id) {
        return tradeService.getTradeById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<TradeResponseDto> getTradesByOrderId(@PathVariable("orderId") @Min(1) Long orderId) {
        return tradeService.getTradesByOrderId(orderId);
    }

    @GetMapping("/symbol/{symbol}")
    public List<TradeResponseDto> getTradesBySymbol(@PathVariable("symbol") String symbol) {
        return tradeService.getTradesBySymbol(symbol);
    }

    @GetMapping("/recent")
    public List<TradeResponseDto> getRecentTrades(@RequestParam(name="limit", defaultValue = "10") @Min(1) int limit) {
        return tradeService.getRecentTrades(limit);
    }

    @PostMapping("/cancel/{id}")
    public TradeResponseDto cancelTrade(@PathVariable("id") @Min(1) Long id) {
        return tradeService.cancelTradeAndReturn(id);
    }

}
