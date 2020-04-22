package ooga.model.engine.exceptions;

public class InvalidFileFormatException extends RuntimeException{
    public InvalidFileFormatException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
