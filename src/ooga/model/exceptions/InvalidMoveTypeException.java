package ooga.model.exceptions;

/**
 * This exception is thrown when the MoveType specified in the config file
 * is NOT one of the supported MoveTypes.
 */
public class InvalidMoveTypeException extends Exception {
    public InvalidMoveTypeException(String message, Object ... values) {
        super(String.format(message, values));}
}
