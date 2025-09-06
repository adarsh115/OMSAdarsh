package com.adarsh.oms.oms_core.repository.trade;

import com.adarsh.oms.oms_core.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByOrderId(Long orderId);

    List<Trade> findBySymbol(String symbol);

}
