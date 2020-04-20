package ooga.model.engine.Agent.evaluationFunctions;
import ooga.model.engine.exceptions.InvalidEvaluationFunctionException;

import java.util.List;

public class EvaluationFunctionFactory {
    public EvaluationFunction createEvaluationFunction(String evaluationType, int stateIndex, List<Integer> maxStates,
                                                       List<Integer> minStates, List<List<Integer>> boardWeights,
                                                       boolean userPosDirection, int winValue, boolean checkCurrConfig,
                                                       List<List<Integer>> initialConfig) {
        int maxDirection;
        int minDirection;
        if(userPosDirection){
            minDirection = 1;
        }else{
           minDirection = -1;
        }
        maxDirection = minDirection * -1;
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
