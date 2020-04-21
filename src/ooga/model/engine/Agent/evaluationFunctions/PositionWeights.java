package ooga.model.engine.Agent.evaluationFunctions;

import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;

import java.util.ArrayList;
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
        considersDirection = myDirection != myOpponentDirection;
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
                    List<List<Integer>> weightsToEvaluate = myBoardPositionWeights;
                    if (considersDirection) {
                        weightsToEvaluate = adjustWeightsForDirection(playerDirection);
                    }
                    playerPositionsWeight += weightsToEvaluate.get(r).get(c);
                }
            }
        }
        return playerPositionsWeight;
    }

    private List<List<Integer>> adjustWeightsForDirection(int playerDirection){
        List<List<Integer>> adjusted = new ArrayList<>();
        for(List<Integer> row: myBoardPositionWeights){
            List<Integer> adjustedRow = new ArrayList<>();
            for(int state : row){
                adjustedRow.add(state * playerDirection);
            }
            adjusted.add(adjustedRow);
        }
        return adjusted;
    }


}
