package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.Agent.winTypes.WinType;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a winValue and index for the player's row of pieces, returns whether
 * that player has sufficient pieces in its row meet the win condition
 * For games such as Mancala where the winning condition relies on the number
 * of objects each player has, not pieces, checks number of objects based on
 * positions found from the initialStateConfig
 */

public class NoMovesMorePieces implements WinType {
    private final int myStateIndex;
    private final List<List<Integer>> myInitialConfig;
    private List<List<Integer>> myCurrConfig;
    private final Boolean checkCurrConfig;
    private int myPlayerState;
    private int myEmptyState;

    public NoMovesMorePieces(int stateIndex, List<List<Integer>> initialConfig, Boolean checkCurrConfig, int emptyState){
        myStateIndex = stateIndex;
        myInitialConfig = initialConfig;
        this.checkCurrConfig = checkCurrConfig;
        myEmptyState = emptyState;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        myPlayerState = playerStates.get(myStateIndex);
        myCurrConfig = boardStateInfo;
        List<List<Integer>> boardToCheck;
        if (checkCurrConfig) {
            boardToCheck = myCurrConfig;
        } else {
            boardToCheck = myInitialConfig;
        }
        ArrayList<Coordinate> myStateCoords = getStateCoords(boardToCheck);
        int numPlayerPieces;
        if (checkCurrConfig) {
            numPlayerPieces = myStateCoords.size();
        } else {
            numPlayerPieces = countStateOccurrences(myStateCoords);
        }
        return noMovesLeft && numPlayerPieces > countAllPieces()/2;
    }

    private int countAllPieces() {
        int allPieceCount = 0;
        for (List<Integer> row: myCurrConfig) {
            for (int colValue: row) {
                if (colValue != myEmptyState && checkCurrConfig) {
                        allPieceCount += 1;
                } else {
                    allPieceCount += colValue;
                }
            }
        }
        return allPieceCount;
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

    private int countStateOccurrences(ArrayList<Coordinate> stateCoords) {
        int total = 0;
        for (Coordinate pos : stateCoords) {
            total += myCurrConfig.get(pos.getRow()).get(pos.getCol());
        }
        return total;
    }
}
