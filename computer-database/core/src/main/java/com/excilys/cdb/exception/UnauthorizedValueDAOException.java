package com.excilys.cdb.exception;

public class UnauthorizedValueDAOException extends DAOException {
    private static final long serialVersionUID = 1L;

    /**
     * Superclass constructor.
     *
     * @param message message
     * @param cause cause
     */
    public UnauthorizedValueDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Superclass constructor.
     *
     * @param message message
     */
    public UnauthorizedValueDAOException(String message) {
        super(message);
    }
}
