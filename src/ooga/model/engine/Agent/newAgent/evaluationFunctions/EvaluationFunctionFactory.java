package ooga.model.engine.Agent.newAgent.evaluationFunctions;


import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

public class EvaluationFunctionFactory {
    //TODO: do we want to have weights for each of the evaluation functions
    public EvaluationFunction createEvaluationFunction(String evaluationType, int maxState, int minState,
                                                 List<Integer> maxStates, List<Integer> minStates, List<List<Integer>> boardWeights) {
        switch (evaluationType) {
            case "SumOfDistancesForSpecialPiece":
                return new SumOfDistancesForSpecialPieceEval(maxState, minState, maxStates, minStates);
            case "PositionWeights":
                return new PositionWeights(boardWeights,maxStates,minStates);
            default:
                throw new InvalidEvaluationFunctionException(evaluationType + " is not a valid neighborhood type.");
        }
    }
}
