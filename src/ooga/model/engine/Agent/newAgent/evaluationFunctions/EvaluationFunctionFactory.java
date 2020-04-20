package ooga.model.engine.Agent.newAgent.evaluationFunctions;


import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

public class EvaluationFunctionFactory {
    //TODO: do we want to have weights for each of the evaluation functions
    public EvaluationFunction createEvaluationFunction(String evaluationType, int stateIndex, List<Integer> maxStates,
                                                       List<Integer> minStates, List<List<Integer>> boardWeights,
                                                       int maxDirection, int minDirection, int winValue,
                                                       List<List<Integer>> initialConfig, Boolean checkCurrConfig) {
        switch (evaluationType) {
            case "MorePieces":
                return new MorePieces(stateIndex, maxStates, minStates, initialConfig, checkCurrConfig);
            case "NumOpenLines":
                return new NumOpenLines(stateIndex, maxStates,minStates, winValue);
            case "PositionWeights":
                return new PositionWeights(boardWeights,maxStates,minStates,maxDirection, minDirection);
            case "SumOfDistancesForSpecialPiece":
                return new SumOfDistancesForSpecialPieceEval(stateIndex, maxStates, minStates);
            default:
                throw new InvalidEvaluationFunctionException(evaluationType + " is not a valid neighborhood type.");
        }
    }
}
