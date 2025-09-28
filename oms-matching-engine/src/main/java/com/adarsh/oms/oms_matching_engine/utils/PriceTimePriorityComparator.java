package com.adarsh.oms.oms_matching_engine.utils;

import com.adarsh.oms.oms_matching_engine.enums.OrderSide;
import com.adarsh.oms.oms_matching_engine.model.InternalOrder;

import java.util.Comparator;

public class PriceTimePriorityComparator implements Comparator<InternalOrder> {

    private final OrderSide side;

    public PriceTimePriorityComparator(OrderSide side) {
        this.side = side;
    }

    @Override
    public int compare(InternalOrder o1, InternalOrder o2) {
        int priceComparison;

        if (side == OrderSide.BUY) {
            priceComparison = o2.getPrice().compareTo(o1.getPrice()); // Higher price first(max-heap)
        } else {
            priceComparison = o1.getPrice().compareTo(o2.getPrice()); // Lower price first(min-heap)
        }

        if (priceComparison != 0) {
            return priceComparison;
        }

        return o1.getTimestamp().compareTo(o2.getTimestamp()); // FIFO(min-heap)
    }
}
