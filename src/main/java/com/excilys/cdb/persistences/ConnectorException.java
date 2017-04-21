package com.excilys.cdb.persistences;

public class ConnectorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConnectorException(String arg0) {
        super(arg0);
    }

    public ConnectorException(Throwable arg0) {
        super(arg0);
    }

    public ConnectorException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ConnectorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
