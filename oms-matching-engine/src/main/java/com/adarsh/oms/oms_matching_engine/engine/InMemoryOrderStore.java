package com.adarsh.oms.oms_matching_engine.engine;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_matching_engine.utils.OrderStore;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryOrderStore implements OrderStore {
    private final Map<Long, OrderPlacedEvent> cache = new ConcurrentHashMap<>();

    @Override
    public void save(OrderPlacedEvent order) {
        cache.put(order.getOrderId(), order);
    }

    @Override
    public Optional<OrderPlacedEvent> find(long orderId) {
        return Optional.ofNullable(cache.get(orderId));
    }

    @Override
    public void remove(long orderId) {
        cache.remove(orderId);
    }

}
