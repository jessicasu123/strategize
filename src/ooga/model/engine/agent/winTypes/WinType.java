package ooga.model.engine.agent.winTypes;

import java.util.List;

/**
 * This interface is responsible for determining if a current board configuration
 * represents a win or not.
 *
 * Wins can be defined by different conditions (for example, having all pieces
 * in a consecutive order, having more pieces when there are no more moves left,
 * or making your opponent lose all their pieces).
 *
 * These conditions will be specified in the game configuration file. This provides
 * the flexibility to potentially customize what determines a win
 * by simply changing the WinType parameter in the config.
 */
public interface WinType {
    /**
     * Determine if the game in its current state is a win.
     * @param playerStates - a list of the states corresponding to the player
     * @param boardStateInfo - the current state of the board
     * @param noMovesLeft - true if there are no moves left, false if there are
     * @return true if a player has won the game, false if the game is continuing/tie
     */
    boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft);
}
