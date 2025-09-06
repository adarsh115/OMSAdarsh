package com.adarsh.oms.oms_core.repository.trade;

import com.adarsh.oms.oms_core.entity.Trade;

import java.util.List;

public interface TradeRepositoryCustom {
    List<Trade> findRecentTrades(int limit);

}
