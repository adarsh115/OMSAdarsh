package com.adarsh.oms.oms_risk_service.utility;

public class RiskRejectionReasons {

    // ✅ Approval
    public static final String ORDER_APPROVED =
            "Order approved";

    // ❌ Rejection Reasons
    public static final String HIGH_QUANTITY_MARKET_ORDER =
            "Rejected: quantity exceeds threshold for market order";

    public static final String INVALID_PRICE_RANGE =
            "Rejected: price is outside acceptable range";

    public static final String INVALID_SYMBOL =
            "Rejected: symbol not recognized or unsupported";

    public static final String RISK_ENGINE_TIMEOUT =
            "Rejected: risk evaluation timed out";

    public static final String DUPLICATE_ORDER =
            "Rejected: duplicate clientOrderId detected";

    public static final String INSUFFICIENT_FUNDS =
            "Rejected: user has insufficient balance for order";

    // 🧪 Add more rules as needed
}