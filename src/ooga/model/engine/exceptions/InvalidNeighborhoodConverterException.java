package ooga.model.engine.exceptions;

public class InvalidNeighborhoodConverterException extends RuntimeException {
    public InvalidNeighborhoodConverterException(String message, Object ... values) {
        super(String.format(message, values));}
}
