package com.adarsh.oms.oms_core.exception;


public class OmsCoreException extends RuntimeException {

    public OmsCoreException(String message) {
        super(message);
    }

    public OmsCoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
