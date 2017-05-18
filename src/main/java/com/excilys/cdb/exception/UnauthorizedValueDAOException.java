package com.excilys.cdb.exception;

public class UnauthorizedValueDAOException extends DAOException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedValueDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedValueDAOException(String message) {
        super(message);
    }

}
