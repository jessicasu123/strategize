package ooga.model.engine.exceptions;

public class InvalidConvertableNeighborFinderException extends RuntimeException {
    public InvalidConvertableNeighborFinderException(String message, Object ... values) {
        super(String.format(message, values));}
}
