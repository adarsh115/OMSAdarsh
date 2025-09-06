package com.adarsh.oms.oms_order_service.exception;

public class OmsServiceException extends RuntimeException {

    private final String errorCode;

    public OmsServiceException(String message) {
        super(message);
        this.errorCode = "OMS_ERROR";
    }

    public OmsServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}