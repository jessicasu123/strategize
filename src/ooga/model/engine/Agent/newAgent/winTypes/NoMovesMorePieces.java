package ooga.model.engine.Agent.newAgent.winTypes;

import java.util.List;

/**
 * Given a winValue and index for the player's row of pieces,
 * returns whether that player has sufficient pieces in its row
 * meet the win condition
 */

public class NoMovesMorePieces implements WinType {
    private final int myWinValue;
    private final int myPlayerRow;

    public NoMovesMorePieces(int winValue, int playerRow){
        myWinValue = winValue;
        myPlayerRow = playerRow;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int numPlayerPieces = boardStateInfo.get(myPlayerRow).stream().mapToInt(n -> n).sum();
        return noMovesLeft && numPlayerPieces > myWinValue;
    }

}
