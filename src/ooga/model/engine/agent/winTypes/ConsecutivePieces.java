package ooga.model.engine.agent.winTypes;

import ooga.model.engine.BoardConfiguration;
import ooga.model.engine.neighborhood.DiagonalNeighborhood;
import ooga.model.engine.neighborhood.VerticalNeighborhood;

import java.util.ArrayList;
import java.util.List;

/**
 * This class checks to see
 * how many consecutive pieces are in a row
 * to determine whether a player has won
 * @author Brian Li
 */
public class ConsecutivePieces implements WinType {
    private int myInARow;
    private DiagonalNeighborhood calculateDiagonals;
    private VerticalNeighborhood calculateColumns;
    private List<List<Integer>> stateInfo;

    public ConsecutivePieces(int InARow){
        myInARow = InARow;
    }

    /**
     * @param playerStates - a list of the states corresponding to the player
     * @param boardStateInfo - the current state of the board
     * @param objectInfo
     * @param noMovesLeft - true if there are no moves left, false if there are
     * @return whether player has won
     */
    @Override
    public boolean isWin(List<Integer> playerStates, BoardConfiguration boardStateInfo, BoardConfiguration objectInfo, boolean noMovesLeft) {
        stateInfo = new ArrayList<>();
        for (int r = 0; r < boardStateInfo.getNumRows();r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < boardStateInfo.getNumCols();c++) {
                row.add(boardStateInfo.getValue(r,c));
            }
            stateInfo.add(row);
        }
        List<List<Integer>> rows = stateInfo;
        List<List<Integer>> cols = getCols(boardStateInfo);
        List<List<Integer>> diags = getDiagonals(boardStateInfo);
        return checkWinInGroup(rows, playerStates) || checkWinInGroup(cols,playerStates) || checkWinInGroup(diags, playerStates);
    }

    /**
     * @param spaceChecking - nested list of integers of spacing
     * @param player - nested list of players
     * @return whether a group has a win
     */
    private boolean checkWinInGroup(List<List<Integer>> spaceChecking, List<Integer> player){
        for(List<Integer> allOfGroup: spaceChecking){
            int consecutive = 0;
            for(int state : allOfGroup){
                if(player.contains(state)){
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


    private List<List<Integer>> getDiagonals(BoardConfiguration boardStateInfo){
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.getNumRows(),
                boardStateInfo.getNumCols());
        return calculateDiagonals.getAllDiagonals(stateInfo,
                myInARow);
    }



    private List<List<Integer>> getCols(BoardConfiguration boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.getNumRows(),
                boardStateInfo.getNumCols());
        return calculateColumns.getAllVerticals(stateInfo);
    }
}
