package ooga.model.engine.exceptions;
/**
 * The purpose of this exception is to let the user know when they make a move
 * that is not valid according to the game rules
 */

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
