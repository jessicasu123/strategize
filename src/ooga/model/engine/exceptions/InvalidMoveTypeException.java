package ooga.model.engine.exceptions;

/**
 * This exception is thrown when the MoveType specified in the config file
 * is NOT one of the supported MoveTypes.
 */
public class InvalidMoveTypeException extends RuntimeException {
    public InvalidMoveTypeException(String message, Object ... values) {
        super(String.format(message, values));}
}
