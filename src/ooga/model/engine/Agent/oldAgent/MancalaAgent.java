package ooga.model.engine.Agent.oldAgent;

import ooga.model.engine.Coordinate;

import java.util.List;

public class MancalaAgent extends Agent {
    private Coordinate maximizingPlayerGoalPos;
    private Coordinate minimizingPlayerGoalPos;

    /**
     * Creates a Mancala AI agent
     * @param maximizingPlayerID - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerID - the ID of the player who the agent will try to minimize its moves for
     */
    public MancalaAgent(int maximizingPlayerID, int minimizingPlayerID, Coordinate maximizingGoalPosition, Coordinate minimizingGoalPosition) {
        super(maximizingPlayerID, minimizingPlayerID);
        maximizingPlayerGoalPos = maximizingGoalPosition;
        minimizingPlayerGoalPos = minimizingGoalPosition;
    }

    /**
     * @param boardStateInfo - all of the current state information from the board
     * @return an integer representing the evaluation function's evaluation of the current game state
     */
    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        if (this.isWin(this.getMaxPlayer(), boardStateInfo, noMovesLeft)) {
            return Integer.MAX_VALUE;
        } else if(this.isWin(this.getMinPlayer(), boardStateInfo, noMovesLeft)) {
            return Integer.MIN_VALUE;
        }
        int maxPlayerNumMarbles = 0;
        int minPlayerNumMarbles = 0;
        int numCols = boardStateInfo.get(0).size();
        for (int row = 0; row < boardStateInfo.size(); row++) {
            for (int col = 0; col < numCols; col ++) {
                if (row == maximizingPlayerGoalPos.getYCoord() && col == maximizingPlayerGoalPos.getXCoord()) {
                    maxPlayerNumMarbles = boardStateInfo.get(row).get(col);
                }
                else if (row == minimizingPlayerGoalPos.getYCoord() && col == minimizingPlayerGoalPos.getXCoord()) {
                    minPlayerNumMarbles = boardStateInfo.get(row).get(col);
                }
            }
        }
        return maxPlayerNumMarbles - minPlayerNumMarbles;
    }
    /**
     * WIN BY:
     *      - either player has no pieces/moves left
     *      - and playerID has more marbles in your mancala than the other
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return if that player has won or not
     */
    @Override
    protected boolean isWin(int playerID, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        List<Integer> maxPlayerRow = boardStateInfo.get(maximizingPlayerGoalPos.getYCoord());
        List<Integer> minPlayerRow = boardStateInfo.get(minimizingPlayerGoalPos.getYCoord());
        int maxPlayerMarbles = boardStateInfo.get(maximizingPlayerGoalPos.getYCoord()).get(maximizingPlayerGoalPos.getXCoord());
        int minPlayerMarbles = boardStateInfo.get(minimizingPlayerGoalPos.getYCoord()).get(minimizingPlayerGoalPos.getXCoord());
        boolean moreMarbles;
        if (getMaxPlayer() == playerID) {
            moreMarbles = maxPlayerMarbles > minPlayerMarbles;
        } else {
            moreMarbles = maxPlayerMarbles < minPlayerMarbles;
        }
        return moreMarbles && (checkIfRowEmpty(maxPlayerRow, maxPlayerMarbles) || checkIfRowEmpty(minPlayerRow, minPlayerMarbles));
    }

    private boolean checkIfRowEmpty(List<Integer> rowToCheck, int numGoalMarbles) {
        int numMarbles = 0;
        for (int col : rowToCheck) {
            numMarbles += col;
        }
        return numMarbles == numGoalMarbles;
    }
}
