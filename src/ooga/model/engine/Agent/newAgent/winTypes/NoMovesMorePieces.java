package ooga.model.engine.Agent.newAgent.winTypes;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a winValue and index for the player's row of pieces,
 * returns whether that player has sufficient pieces in its row
 * meet the win condition
 */

public class NoMovesMorePieces implements WinType {
    private final int myWinValue;
    private final int myStateIndex;
    private final List<List<Integer>> myInitialConfig;
    private final Boolean checkCurrConfig;
    private int myPlayerState;

    public NoMovesMorePieces(int winValue, int stateIndex, List<List<Integer>> initialConfig, Boolean checkCurrConfig){
        myWinValue = winValue;
        myStateIndex = stateIndex;
        myInitialConfig = initialConfig;
        this.checkCurrConfig = checkCurrConfig;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        myPlayerState = playerStates.get(myStateIndex);
        List<List<Integer>> boardToCheck;
        if (checkCurrConfig) {
            boardToCheck = boardStateInfo;
        } else {
            boardToCheck = myInitialConfig;
        }
        ArrayList<Coordinate> myStateCoords = getStateCoords(boardToCheck);
        int numPlayerPieces;
        if (checkCurrConfig) {
            numPlayerPieces = myStateCoords.size();
        } else {
            numPlayerPieces = countStateOccurrences(myStateCoords, boardStateInfo);
        }
        return noMovesLeft && numPlayerPieces > myWinValue;
    }

    private ArrayList<Coordinate> getStateCoords(List<List<Integer>> boardToCheck) {
        ArrayList<Coordinate> stateCoords = new ArrayList<>();
        for (int r = 0; r < boardToCheck.size(); r++) {
            for (int c = 0; c < boardToCheck.get(0).size(); c++) {
                if (boardToCheck.get(r).get(c) == myPlayerState) {
                    stateCoords.add(new Coordinate(r, c));
                }
            }
        }
        return stateCoords;
    }

    private int countStateOccurrences(ArrayList<Coordinate> stateCoords, List<List<Integer>> boardStateInfo) {
        int total = 0;
        for (Coordinate pos : stateCoords) {
            total += boardStateInfo.get(pos.getRow()).get(pos.getCol());
        }
        return total;
    }
}
