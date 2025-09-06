package com.adarsh.oms.oms_core.repository.order;

import com.adarsh.oms.oms_core.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findRecentOrders(int limit);
    List<Order> findOrdersWithStatus(String status);

}
