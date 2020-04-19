package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import java.util.List;

public class PositionWeights implements EvaluationFunction {
    private List<List<Integer>> myBoardPositionWeights;
    private List<Integer> myUserStates;
    private List<Integer> myAgentStates;

    public PositionWeights(List<List<Integer>> boardPositionWeights, List<Integer> userStates, List<Integer> agentStates) {
        myBoardPositionWeights = boardPositionWeights;
        myUserStates = userStates;
        myAgentStates = agentStates;
    }
    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        return calculateWeightsPerPlayer(boardStateInfo, myAgentStates) -
                calculateWeightsPerPlayer(boardStateInfo, myUserStates);
    }

    private int calculateWeightsPerPlayer(List<List<Integer>> boardStateInfo, List<Integer> playerStates) {
        int playerPositionsWeight = 0;
        for (int r = 0; r < myBoardPositionWeights.size();r++) {
            for (int c = 0; c < myBoardPositionWeights.get(0).size();c++) {
                if (playerStates.contains(boardStateInfo.get(r).get(c))) {
                    playerPositionsWeight += myBoardPositionWeights.get(r).get(c);
                }
            }
        }
        return playerPositionsWeight;
    }
}
