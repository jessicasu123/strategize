package ooga.model.engine.Agent.evaluationFunctions;

import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for evaluating the positions
 * of players on the board according to integer weights given to each position.
 * Positive weights are more favorable positions, while negative weights are
 * unfavorable positions.
 *
 * @author Jessica Su
 */
public class PositionWeights implements EvaluationFunction {
    private List<List<Integer>> myBoardPositionWeights;
    private List<Integer> myStates;
    private List<Integer> myOpponentStates;
    private int myDirection;
    private int myOpponentDirection;
    private boolean considersDirection;

    /**
     * @param boardPositionWeights - the weights of ALL the positions on the board
     * @param maxStates - the states of the max (agent) player
     * @param minStates - the states of the min (user) player
     * @param maxDirection - the direction of the max player
     * @param minDirection - the direction of the min player
     */
    public PositionWeights(List<List<Integer>> boardPositionWeights, List<Integer> maxStates, List<Integer> minStates, int maxDirection, int minDirection) {
        myBoardPositionWeights = boardPositionWeights;
        myStates = maxStates;
        myOpponentStates = minStates;
        myDirection = maxDirection;
        myOpponentDirection = minDirection;
        considersDirection = myDirection != myOpponentDirection;
    }

    /**
     * Evaluates whether the max (agent's) player's positions are better than
     * the min (user's) player's positions.
     *
     * The quality of each player's positions
     * are based on predefined board weights for each potential position on the board.
     *
     * @param boardStateInfo - the current state configuration of the board
     * @param noMovesLeft - boolean that represents if there are moves left
     * @return positive number if the max player's positions are better, negative
     * number if the min player's positions are better
     */
    @Override
    public int evaluate(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
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
