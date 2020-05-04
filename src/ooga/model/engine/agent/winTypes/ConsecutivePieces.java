package ooga.model.engine.agent.winTypes;

import ooga.model.engine.ImmutableGrid;
import ooga.model.engine.neighborhood.DiagonalNeighborhood;
import ooga.model.engine.neighborhood.VerticalNeighborhood;

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
    public boolean isWin(List<Integer> playerStates, ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft) {
        ImmutableGrid cols = getCols(boardStateInfo);
        ImmutableGrid diags = getDiagonals(boardStateInfo);
        return checkWinInGroup(boardStateInfo, playerStates) || checkWinInGroup(cols,playerStates) || checkWinInGroup(diags, playerStates);
    }

    /**
     * @param spaceChecking - nested list of integers of spacing
     * @param player - nested list of players
     * @return whether a group has a win
     */
    private boolean checkWinInGroup(ImmutableGrid spaceChecking, List<Integer> player){
        for(int i = 0; i < spaceChecking.numRows(); i++){
            int consecutive = 0;
            for(int j = 0; j < spaceChecking.numCols(); j++){
                int state = spaceChecking.getVal(i,j);
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


    private ImmutableGrid getDiagonals(ImmutableGrid boardStateInfo){
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.numRows(),
                boardStateInfo.numCols());
        return calculateDiagonals.getAllDiagonals(boardStateInfo,
                myInARow);
    }



    private ImmutableGrid getCols(ImmutableGrid boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.numRows(),
                boardStateInfo.numCols());
        return calculateColumns.getAllVerticals(boardStateInfo);
    }
}
