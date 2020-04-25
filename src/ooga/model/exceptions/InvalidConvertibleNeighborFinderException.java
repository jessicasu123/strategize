package ooga.model.exceptions;

/**
 * Exception for when the specified ConvertibleNeighborhoodFinder is not a supported type.
 */
public class InvalidConvertibleNeighborFinderException extends Exception {
    public InvalidConvertibleNeighborFinderException(String message, Object ... values) {
        super(String.format(message, values));}
}
