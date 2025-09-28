package com.adarsh.oms.oms_matching_engine.engine;


import com.adarsh.oms.oms_matching_engine.model.InternalOrder;
import com.adarsh.oms.oms_matching_engine.model.Trade;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchingEngineService {

    private final Map<String, OrderBook> orderBooks = new HashMap<>();

    public List<Trade> processNewOrder(InternalOrder order) {
        OrderBook book = orderBooks.computeIfAbsent(order.getSymbol(), OrderBook::new);
        return book.matchOrder(order);
    }

    public Map<String, OrderBook> getOrderBooks() {
        return Collections.unmodifiableMap(orderBooks);
    }
}
