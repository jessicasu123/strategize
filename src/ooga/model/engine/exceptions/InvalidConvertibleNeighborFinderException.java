package ooga.model.engine.exceptions;

/**
 * Exception for when the specified ConvertibleNeighborhoodFinder is not a supported type.
 */
public class InvalidConvertibleNeighborFinderException extends RuntimeException {
    public InvalidConvertibleNeighborFinderException(String message, Object ... values) {
        super(String.format(message, values));}
}
