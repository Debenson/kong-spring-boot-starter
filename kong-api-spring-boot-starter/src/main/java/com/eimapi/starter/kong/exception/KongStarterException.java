package com.eimapi.starter.kong.exception;

public class KongStarterException extends Exception {
    public KongStarterException() {
    }

    public KongStarterException(String message) {
        super(message);
    }

    public KongStarterException(String message, Throwable cause) {
        super(message, cause);
    }

    public KongStarterException(Throwable cause) {
        super(cause);
    }

    public KongStarterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
