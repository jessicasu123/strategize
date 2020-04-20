package ooga.model.engine.Agent.oldAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * This an AI agent for the Connect4 game
 * It uses the rules about winning the game (getting a certain number of pieces in a row) to determine if the game
 * has been won and if so by whom
 * This agent also has an evaluation function for evaluating the current game state for how "good" or "bad" it is
 * @author Brian Li
 */

public class ConnectFourAgent extends Agent{
    private final int myInARow;
    private int ROWS = 6;
    private int COLS = 7;

    /**
     * Creates a Connect4 AI agent
     *
     * @param maximizingPlayerID - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerID - the ID of the player who the agent will try to minimize its moves for
     * @param inARow - the number of pieces needed in a row to win
     */
    public ConnectFourAgent(int maximizingPlayerID, int minimizingPlayerID, int inARow) {
        super(maximizingPlayerID, minimizingPlayerID);
        myInARow = inARow;
    }


    /**
     * Returns 0 when no moves are possible
     *  EVALUATION FUNCTION:
     *      (number of rows, columns, and diagonals open for max) - (number of rows, columns, and diagonals open for min)
     * @param boardStateInfo - all of the current state information from the board
     * @return an integer representing the evaluation function's evaluation of the current game state
     */

    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        if(isWin(this.getMaxPlayer(), boardStateInfo, noMovesLeft)){
            return Integer.MAX_VALUE;
        }else if(isWin(this.getMinPlayer(), boardStateInfo, noMovesLeft)){
            return Integer.MIN_VALUE;
        }
        int rowEvaluation = evaluateMaxOpenMinusMinOpen((boardStateInfo));
        int colEvaluation = evaluateMaxOpenMinusMinOpen(getCols(boardStateInfo));
        int diagEvaluation = evaluateMaxOpenMinusMinOpen(getDiagonals(boardStateInfo));
        return rowEvaluation + colEvaluation + diagEvaluation;
    }

    private int evaluateMaxOpenMinusMinOpen(List<List<Integer>> neighborhood){
        int numOpenMax = 0;
        int numOpenMin = 0;
        for(List<Integer> group : neighborhood){
            if(checkNeighborhoodOpen(group, this.getMaxPlayer())){
                numOpenMax++;
            }
            if(checkNeighborhoodOpen(group, this.getMinPlayer())){
                numOpenMin++;
            }
        }
        return numOpenMax - numOpenMin;
    }

    //TODO: know empty state
    private boolean checkNeighborhoodOpen(List<Integer> check, int playerOpenFor){
        int consecutiveUnblockedSpots = 0;
        for(int state: check){
            if(state == playerOpenFor || (state != this.getMaxPlayer() && state != this.getMinPlayer())){
                consecutiveUnblockedSpots++;
            }else{
                consecutiveUnblockedSpots = 0;
            }
        }
        return consecutiveUnblockedSpots >= myInARow;
    }

    private List<List<Integer>> getDiagonals(List<List<Integer>> boardStateInfo){
        List<List<Integer>> alldiag = new ArrayList<List<Integer>>();
        for(int row = ROWS - 4; row>=0;row--) {
            for(int col = COLS - 4; col>=0;col--){
                List<Integer> leftDiag = new ArrayList<Integer>();
                List<Integer> rightDiag = new ArrayList<Integer>();
                for(int i = 0;i<4;i++){
                    rightDiag.add(boardStateInfo.get(row+i).get(col-i+3));
                    leftDiag.add(boardStateInfo.get(row+i).get(col+i));
                }
                alldiag.add(leftDiag);
                alldiag.add(rightDiag);
            }
        }
        return alldiag;
    }

    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        List<List<Integer>> allCols = new ArrayList<>();
        for(int i = 0; i < boardStateInfo.get(0).size(); i++) {
            List<Integer> col = new ArrayList<>();
            for (List<Integer> row : boardStateInfo) {
                col.add(row.get(i));
            }
            allCols.add(col);
        }
        return allCols;
    }

    /**
     * WIN BY:
     *      - getting 4 of your game pieces consecutively in a row, column, or diagonal
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return if that player has won or not
     */
    protected boolean isWin(int playerID, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        List<List<Integer>> rows = boardStateInfo;
        List<List<Integer>> cols = getCols(boardStateInfo);
        List<List<Integer>> diags = getDiagonals(boardStateInfo);

        return checkWinInGroup(rows, playerID) || checkWinInGroup(cols, playerID) || checkWinInGroup(diags, playerID);
    }

    private boolean checkWinInGroup(List<List<Integer>> spaceChecking, int player){
        for(List<Integer> allOfGroup: spaceChecking){
            int consecutive = 0;
            for(int state : allOfGroup){
                if(state == player){
                    consecutive++;
                }else{
                    consecutive = 0;
                }
                if(consecutive >= myInARow){
                    return true;
                }
            }
        }
        return false;
    }
}

