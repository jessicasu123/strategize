package ooga.model.exceptions;

/**
 * This exception is for when the specified evaluation function type does NOT
 * match one of the supported evaluation functions.
 */
public class InvalidEvaluationFunctionException extends Exception{

    public InvalidEvaluationFunctionException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
