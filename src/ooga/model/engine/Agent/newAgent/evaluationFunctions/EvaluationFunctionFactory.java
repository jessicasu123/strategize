package ooga.model.engine.Agent.newAgent.evaluationFunctions;


import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

public class EvaluationFunctionFactory {

    public EvaluationFunction createNeighborhood(String evaluationType, int maxState, int minState,
                                                 List<Integer> maxStates, List<Integer> minStates) {
        switch (evaluationType) {
            case "SumOfDistancesForSpecialPiece":
                return new SumOfDistancesForSpecialPieceEval(maxState, minState, maxStates, minStates);
            default:
                throw new InvalidEvaluationFunctionException(evaluationType + " is not a valid neighborhood type.");
        }
    }
}
