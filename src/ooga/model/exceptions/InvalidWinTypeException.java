package ooga.model.exceptions;

/**
 * This exception is for when the specified win type does NOT
 * match one of the supported win types.
 */

public class InvalidWinTypeException extends Exception{
    public InvalidWinTypeException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
