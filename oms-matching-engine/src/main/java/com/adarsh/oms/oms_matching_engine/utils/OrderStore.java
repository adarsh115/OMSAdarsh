package com.adarsh.oms.oms_matching_engine.utils;

import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import org.springframework.context.annotation.Bean;

import java.util.Optional;


public interface OrderStore {
    void save(OrderPlacedEvent order);
    Optional<OrderPlacedEvent> find(long orderId);
    void remove(long orderId);
}
