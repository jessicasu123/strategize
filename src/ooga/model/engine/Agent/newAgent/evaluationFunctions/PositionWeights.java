package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import java.util.List;

public class PositionWeights implements EvaluationFunction {
    private List<List<Integer>> myBoardPositionWeights;
    private List<Integer> myStates;
    private List<Integer> myOpponentStates;
    private int myDirection;
    private int myOpponentDirection;
    private boolean considersDirection;

    public PositionWeights(List<List<Integer>> boardPositionWeights, List<Integer> maxStates, List<Integer> minStates, int maxDirection, int minDirection) {
        myBoardPositionWeights = boardPositionWeights;
        myStates = maxStates;
        myOpponentStates = minStates;
        myDirection = maxDirection;
        myOpponentDirection = minDirection;
        considersDirection = myDirection!=myOpponentDirection;
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        return calculateWeightsPerPlayer(boardStateInfo, myStates, myDirection) -
                calculateWeightsPerPlayer(boardStateInfo, myOpponentStates, myOpponentDirection);
    }

    private int calculateWeightsPerPlayer(List<List<Integer>> boardStateInfo, List<Integer> playerStates, int playerDirection) {
        int playerPositionsWeight = 0;
        for (int r = 0; r < myBoardPositionWeights.size();r++) {
            for (int c = 0; c < myBoardPositionWeights.get(0).size();c++) {
                if (playerStates.contains(boardStateInfo.get(r).get(c))) {
                    if (considersDirection) {
                        if (playerDirection > 0 && r  * playerDirection > playerDirection * (boardStateInfo.size() - 1)/2) {
                            playerPositionsWeight += myBoardPositionWeights.get(r).get(c);
                        } else if (playerDirection < 0 && r  * playerDirection >= playerDirection * (boardStateInfo.size() - 1)/2){
                            playerPositionsWeight += myBoardPositionWeights.get(r).get(c);
                        }
                    } else {
                        playerPositionsWeight += myBoardPositionWeights.get(r).get(c);
                    }
                }
            }
        }
        return playerPositionsWeight;
    }


}
