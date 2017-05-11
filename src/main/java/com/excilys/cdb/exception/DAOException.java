package com.excilys.cdb.exception;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public DAOException() {
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
    public DAOException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     * @param cause cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Superclass constructor.
     *
     * @param cause cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}