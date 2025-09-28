package com.adarsh.oms.oms_matching_engine.engine;


import com.adarsh.oms.oms_matching_engine.enums.OrderSide;
import com.adarsh.oms.oms_matching_engine.enums.OrderType;
import com.adarsh.oms.oms_matching_engine.model.InternalOrder;
import com.adarsh.oms.oms_matching_engine.model.Trade;
import com.adarsh.oms.oms_matching_engine.utils.PriceTimePriorityComparator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

public class OrderBook {

    private final String symbol;
    private final PriorityQueue<InternalOrder> bids;
    private final PriorityQueue<InternalOrder> asks;

    public OrderBook(String symbol) {
        this.symbol = symbol;
        this.bids = new PriorityQueue<>(new PriceTimePriorityComparator(OrderSide.BUY));
        this.asks = new PriorityQueue<>(new PriceTimePriorityComparator(OrderSide.SELL));
    }

    public List<Trade> matchOrder(InternalOrder incomingOrder) {
        List<Trade> trades = new ArrayList<>();

        PriorityQueue<InternalOrder> oppositeQueue = incomingOrder.getSide() == OrderSide.BUY ? asks : bids;

        while (!oppositeQueue.isEmpty() && incomingOrder.getQuantity() > 0) {
            InternalOrder restingOrder = oppositeQueue.peek();

            boolean priceMatch = isPriceMatch(incomingOrder, restingOrder);
            if (!priceMatch) break;

            int executedQty = Math.min(incomingOrder.getQuantity(), restingOrder.getQuantity());
            BigDecimal executionPrice = restingOrder.getPrice(); // resting order sets the price

            trades.add(Trade.builder()
                    .tradeId(UUID.randomUUID().toString())
                    .buyOrderId(incomingOrder.getSide() == OrderSide.BUY ? incomingOrder.getOrderId() : restingOrder.getOrderId())
                    .sellOrderId(incomingOrder.getSide() == OrderSide.SELL ? incomingOrder.getOrderId() : restingOrder.getOrderId())
                    .symbol(symbol)
                    .price(executionPrice)
                    .quantity(executedQty)
                    .timestamp(Instant.now())
                    .build());

            incomingOrder.reduceQuantity(executedQty);
            restingOrder.reduceQuantity(executedQty);

            if (restingOrder.isFilled()) {
                oppositeQueue.poll(); // remove fully filled order
            }
        }

        if (incomingOrder.getQuantity() > 0 && incomingOrder.getType() == OrderType.LIMIT) {
            getQueue(incomingOrder.getSide()).add(incomingOrder); // add remaining to book
        }

        return trades;
    }

    private boolean isPriceMatch(InternalOrder incoming, InternalOrder resting) {
        if (incoming.getType() == OrderType.MARKET) return true;

        if (incoming.getSide() == OrderSide.BUY) {
            return incoming.getPrice().compareTo(resting.getPrice()) >= 0;
        } else {
            return incoming.getPrice().compareTo(resting.getPrice()) <= 0;
        }
    }

    private PriorityQueue<InternalOrder> getQueue(OrderSide side) {
        return side == OrderSide.BUY ? bids : asks;
    }
}


/*
What the OrderBook Does

It’s a mini stock exchange matching engine for one trading symbol.

Keeps two priority queues:

Bids (Buy orders) → sorted by highest price first, then earliest order.

Asks (Sell orders) → sorted by lowest price first, then earliest order.

When a new order arrives:

If it’s a BUY, try to match it against the lowest SELL.

If it’s a SELL, try to match it against the highest BUY.

Matching process:

Compare prices → if they are compatible (bid ≥ ask), a trade happens.

Trade quantity = min(buyer’s qty, seller’s qty).

        Trade price = resting order’s price (the one already in the book).

Reduce quantities → remove any fully filled order.

If incoming order not fully matched:

If it’s a LIMIT order → put leftover quantity into the order book.

If it’s a MARKET order → discard leftover (never goes into the book).

Returns a list of trades that were executed.

        🎯 In one line:

This code matches incoming buy/sell orders against existing ones, executes trades if prices align, and maintains the order book with unmatched orders.

 */