package ooga.model.exceptions;

/**
 * This exception is for when the specified neighborhood type does NOT
 * match one of the supported neighborhoods.
 */
public class InvalidNeighborhoodException extends Exception{
    public InvalidNeighborhoodException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
