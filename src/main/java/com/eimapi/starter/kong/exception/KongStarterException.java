package com.eimapi.starter.kong.exception;

public class KongStarterException extends Exception {
   	private static final long serialVersionUID = 1097767474317189806L;

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
