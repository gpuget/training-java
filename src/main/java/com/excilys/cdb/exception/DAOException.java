package com.excilys.cdb.exception;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

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
}