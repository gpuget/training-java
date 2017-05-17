package com.excilys.cdb.exception;

public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ControllerException() {
        super();
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     * @param cause cause
     * @param enableSuppression suppression
     * @param writableStackTrace stack trace
     */
    public ControllerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     * @param cause cause
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Superclass constructor.
     *
     * @param cause cause
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }
}