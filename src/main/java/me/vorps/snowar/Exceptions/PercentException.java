package me.vorps.snowar.Exceptions;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:22.
 */
public class PercentException extends Exception {

    /**
     * Exception when somme percent bonus not 100%
     */
    public PercentException(){
        super();
    }

    public PercentException(String message) {
        super(message);
    }
    public PercentException(Throwable cause) {
        super(cause);
    }

    public PercentException(String message, Throwable cause) {
        super(message, cause);
    }
}
