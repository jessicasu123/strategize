package ooga.model.engine.agent.winTypes;

import ooga.model.engine.ImmutableGrid;
import java.util.List;

/**
 * Purpose:
 * - Given the empty state, this class determines that a win for the player
 * whose states are given if the board only contains their pieces and the empty state
 * (their opponent has no pieces)
 * - Uses this condition to see if the player evaluating has a winning game state
 *
 * This is an example of a win type that would be composed within the Agent
 *
 * Why well designed:
 * - follows single responsibility principle while also active
 *     - this class only has the purpose of checking if an opponent has pieces on the board or not
 *     - however this is still active as it is providing useful information and using logic to get that information
 *     rather than being based on getters and setters
 * - utilizes the win type interface
 *      - this allows this class to be referenced as an abstraction so that in other parts of the code whatever
 *      type of Win Type it is is irrelevant
 * - flexible to handle any game type
 *      - there are no game specific assumptions so this win type can be used for any game that needs this criteria
 *      to determine a win
 * @author Holly Ansel
 */

public class NoPiecesForOpponent implements WinType {
    public static final int NO_PIECES = 0;
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
    public boolean isWin(List<Integer> playerStates, ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft) {
        int numOfOpponentsPieces = NO_PIECES;
        for(int r = 0; r < boardStateInfo.numRows(); r++){
            for(int c = 0; c < boardStateInfo.numCols(); c++){
                int state = boardStateInfo.getVal(r,c);
                if(!playerStates.contains(state) && state != myEmptyState){
                    numOfOpponentsPieces++;
                }
            }
        }
        return numOfOpponentsPieces == NO_PIECES;
    }
}
