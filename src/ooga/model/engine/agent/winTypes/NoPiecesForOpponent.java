package ooga.model.engine.agent.winTypes;

import java.util.List;

/**
 * Given the empty state, this class determines that a win for the player
 * whose states are given if the board only contains their pieces and the empty state
 * (their opponent has no pieces)
 * @author Holly Ansel
 */

public class NoPiecesForOpponent implements WinType {
    private final int myEmptyState;

    /**
     * @param emptyState - the empty state, used to see if states other than the players and
     *                   the empty state are on the board
     */
    public NoPiecesForOpponent(int emptyState){
        myEmptyState = emptyState;
    }

    /**
     * @param playerStates - a list of the states corresponding to the player
     * @param boardStateInfo - the current state of the board
     * @param noMovesLeft - true if there are no moves left, false if there are
     * @return whether opponent states (states other than the players and the empty state) are on the board
     */
    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
        int numOfOpponentsPieces = 0;
        for(List<Integer> row: boardStateInfo){
            for(int state : row){
                if(!playerStates.contains(state) && state != myEmptyState){
                    numOfOpponentsPieces++;
                }
            }
        }
        return numOfOpponentsPieces == 0;
    }
}
