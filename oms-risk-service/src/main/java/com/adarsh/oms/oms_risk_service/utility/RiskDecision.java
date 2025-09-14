package com.adarsh.oms.oms_risk_service.utility;

public class RiskDecision {
    private final boolean approved;
    private final String reason;

    public RiskDecision(boolean approved, String reason) {
        this.approved = approved;
        this.reason = reason;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "RiskDecision{" +
                "approved=" + approved +
                ", reason='" + reason + '\'' +
                '}';
    }

}
