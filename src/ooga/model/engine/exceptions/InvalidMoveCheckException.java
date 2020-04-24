package ooga.model.engine.exceptions;

public class InvalidMoveCheckException extends RuntimeException {
    public InvalidMoveCheckException(String message, Object ... values) {
        super(String.format(message, values));}
}
