package com.adarsh.oms.oms_risk_service.exception;



public class OmsRiskException extends RuntimeException {

    public OmsRiskException(String message) {
        super(message);
    }

    public OmsRiskException(String message, Throwable cause) {
        super(message, cause);
    }

    public OmsRiskException(Throwable cause) {
        super(cause);
    }
}
