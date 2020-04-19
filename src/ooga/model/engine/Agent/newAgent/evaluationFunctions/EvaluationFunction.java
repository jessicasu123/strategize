package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import java.util.List;

public interface EvaluationFunction {

    int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft, int weight);
}
