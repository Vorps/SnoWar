package me.vorps.snowar.Exceptions;


/**
 * Project Hub Created by Vorps on 24/02/2016 at 03:34.
 */
public class SqlException extends Exception {
    public SqlException(){
        super();
    }

    public SqlException(String message) {
        super(message);
    }
    public SqlException(Throwable cause) {
        super(cause);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
