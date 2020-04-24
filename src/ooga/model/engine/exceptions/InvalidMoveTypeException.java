package ooga.model.engine.exceptions;

public class InvalidMoveTypeException extends RuntimeException {
    public InvalidMoveTypeException(String message, Object ... values) {
        super(String.format(message, values));}
}
