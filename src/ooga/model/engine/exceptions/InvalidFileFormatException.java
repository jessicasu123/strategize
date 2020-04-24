package ooga.model.engine.exceptions;

/**
 * Exception for when the file format is invalid. Some things that could cause a file to be invalid include:
 *  - the number of rows and columns for the initial configuration/ object configuration/board weight configuration
 *   do not match the specified dimensions
 *  - the number of states for the two players don't match
 *  - the number of states per player and number of corresponding images don't match
 */
public class InvalidFileFormatException extends RuntimeException{
    public InvalidFileFormatException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
