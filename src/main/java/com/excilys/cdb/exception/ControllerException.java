package com.excilys.cdb.exception;

public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

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
}