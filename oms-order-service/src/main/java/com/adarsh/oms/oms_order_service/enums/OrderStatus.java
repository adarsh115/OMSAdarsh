package com.adarsh.oms.oms_order_service.enums;

public enum OrderStatus {
    PENDING,       // Just placed, awaiting risk check
    APPROVED,      // Passed risk validation
    REJECTED,      // Failed risk validation
    FILLED,        // Fully matched and traded
    PARTIALLY_FILLED, // Some quantity matched
    CANCELLED      // Manually cancelled
}