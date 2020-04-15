package ooga.model.engine.Agent;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourAgent extends Agent{
    private final int myInARow;
    /**
     * Creates an AI agent
     *
     * @param maximizingPlayerID - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerID - the ID of the player who the agent will try to minimize its moves for
     */
    public ConnectFourAgent(int maximizingPlayerID, int minimizingPlayerID, int inARow) {
        super(maximizingPlayerID, minimizingPlayerID);
        myInARow = inARow;
    }


    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo) {
        if(isWin(this.getMaxPlayer(), boardStateInfo)){
            return Integer.MAX_VALUE;
        }else if(isWin(this.getMinPlayer(), boardStateInfo)){
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


    //TODO: Fix diagonal function
    private List<List<Integer>> getDiagonals(List<List<Integer>> boardStateInfo){
        List<List<Integer>> leftDiag = new ArrayList<List<Integer>>();
        List<List<Integer>> rightDiag = new ArrayList<List<Integer>>();

        //change this later
        return boardStateInfo;
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
     *      - getting three of your game pieces consecutively in a row, column, or diagonal
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return if that player has won or not
     */
    protected boolean isWin(int playerID, List<List<Integer>> boardStateInfo) {
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


