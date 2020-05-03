package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.BoardConfiguration;

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
    private BoardConfiguration myBoardPositionWeights;
    private List<Integer> myStates;
    private List<Integer> myOpponentStates;
    private int myDirection;
    private int myOpponentDirection;
    private boolean considersDirection;
    private int numRows;
    private int numCols;

    /**
     * @param boardPositionWeights - the weights of ALL the positions on the board
     * @param maxStates - the states of the max (agent) player
     * @param minStates - the states of the min (user) player
     * @param maxDirection - the direction of the max player
     * @param minDirection - the direction of the min player
     */
    public PositionWeights(BoardConfiguration boardPositionWeights, List<Integer> maxStates, List<Integer> minStates, int maxDirection, int minDirection) {
        myBoardPositionWeights = boardPositionWeights;
        numRows = myBoardPositionWeights.getNumRows();
        numCols = myBoardPositionWeights.getNumCols();
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
    public int evaluate(BoardConfiguration boardStateInfo, BoardConfiguration objectInfo, boolean noMovesLeft) {
        return calculateWeightsPerPlayer(boardStateInfo, myStates, myDirection) -
                calculateWeightsPerPlayer(boardStateInfo, myOpponentStates, myOpponentDirection);
    }

    private int calculateWeightsPerPlayer(BoardConfiguration boardStateInfo, List<Integer> playerStates, int playerDirection) {
        int playerPositionsWeight = 0;
        for (int r = 0; r < myBoardPositionWeights.getNumRows();r++) {
            for (int c = 0; c < myBoardPositionWeights.getNumCols();c++) {
                if (playerStates.contains(boardStateInfo.getValue(r,c))) {
                    BoardConfiguration weightsToEvaluate = myBoardPositionWeights;
                    if (considersDirection) {
                        weightsToEvaluate = adjustWeightsForDirection(playerDirection);
                    }
                    playerPositionsWeight += weightsToEvaluate.getValue(r,c);
                }
            }
        }
        return playerPositionsWeight;
    }

    private BoardConfiguration adjustWeightsForDirection(int playerDirection){

        BoardConfiguration adjustedConfig = new BoardConfiguration(numRows, numCols);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                adjustedConfig.setValue(r,c, myBoardPositionWeights.getValue(r,c)*playerDirection);
            }
        }

        return adjustedConfig;
    }


}
