package me.vorps.snowar.Exceptions;


/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
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
