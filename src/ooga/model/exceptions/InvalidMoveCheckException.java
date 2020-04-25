package ooga.model.exceptions;

/**
 * This exception is thrown when the MoveCheck specified in the config file
 * is NOT one of the supported MoveChecks.
 */
public class InvalidMoveCheckException extends Exception {
    public InvalidMoveCheckException(String message, Object ... values) {
        super(String.format(message, values));}
}
