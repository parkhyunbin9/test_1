package com.reco.prob.exception;

public class NoSuchHistoryException extends RuntimeException {
    public NoSuchHistoryException() {
        super();
    }

    public NoSuchHistoryException(String message) {
        super(message);
    }

    public NoSuchHistoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchHistoryException(Throwable cause) {
        super(cause);
    }
}
