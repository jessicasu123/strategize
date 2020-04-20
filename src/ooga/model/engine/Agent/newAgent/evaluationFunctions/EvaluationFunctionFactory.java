package ooga.model.engine.Agent.newAgent.evaluationFunctions;


import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

public class EvaluationFunctionFactory {
    //TODO: do we want to have weights for each of the evaluation functions
    public EvaluationFunction createEvaluationFunction(String evaluationType, int stateIndex, List<Integer> maxStates,
                                                       List<Integer> minStates, List<List<Integer>> boardWeights,
                                                       int maxDirection, int minDirection, int inARow) {
        switch (evaluationType) {
            case "NumOpenLines":
                return new NumOpenLines(stateIndex, maxStates,minStates, inARow);
            case "SumOfDistancesForSpecialPiece":
                return new SumOfDistancesForSpecialPieceEval(stateIndex, maxStates, minStates);
            case "PositionWeights":
                return new PositionWeights(boardWeights,maxStates,minStates,maxDirection, minDirection);
            default:
                throw new InvalidEvaluationFunctionException(evaluationType + " is not a valid neighborhood type.");
        }
    }
}
