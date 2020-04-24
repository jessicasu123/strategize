package ooga.model.engine.Agent.evaluationFunctions;
import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

/**
 * This factory is responsible for creating an EvaluationFunction object
 * according to a String identifier. This identifier can be specified
 * by the game configuration file.
 */
public class EvaluationFunctionFactory {
    /**
     * @param evaluationType - the type of evaluation
     * @param stateIndex - the index with which to access a player's state
     * @param maxStates - the states for the max (agent) player
     * @param minStates - the states for the min (user) player
     * @param boardWeights - the weights given to different positions on the board
     * @param maxDirection - the direction of the max (agent) player
     * @param minDirection - the direction of the min (user) player
     * @param winValue - the value used to determine a win
     * @param checkCurrConfig - a boolean that represents whether to check the current configuration or not
     * @param initialConfig - the initial configuration of the board
     * @return EvaluationFunction object, as specified by the evaluationType
     */
    public EvaluationFunction createEvaluationFunction(String evaluationType, int stateIndex, List<Integer> maxStates,
                                                       List<Integer> minStates, List<List<Integer>> boardWeights,
                                                       int maxDirection, int minDirection, int winValue, boolean checkCurrConfig,
                                                       List<List<Integer>> initialConfig) {
        switch (evaluationType) {
            case "MorePieces":
                return new MorePieces(stateIndex, maxStates, minStates, initialConfig, checkCurrConfig);
            case "NumOpenLines":
                return new NumOpenLines(stateIndex, maxStates,minStates, winValue);
            case "SumOfDistances":
                return new SumOfDistances(stateIndex, maxStates, minStates);
            case "PositionWeights":
                return new PositionWeights(boardWeights,maxStates,minStates,maxDirection, minDirection);
            default:
                throw new InvalidEvaluationFunctionException(evaluationType + " is not a valid neighborhood type.");
        }
    }
}
