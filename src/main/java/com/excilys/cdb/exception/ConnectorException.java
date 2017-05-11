package com.excilys.cdb.exception;

public class ConnectorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ConnectorException() {
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
    public ConnectorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     * @param cause cause
     */
    public ConnectorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     */
    public ConnectorException(String message) {
        super(message);
    }

    /**
     * Superclass constructor.
     *
     * @param cause cause
     */
    public ConnectorException(Throwable cause) {
        super(cause);
    }
}
