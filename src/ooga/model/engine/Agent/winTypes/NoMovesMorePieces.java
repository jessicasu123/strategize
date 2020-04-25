package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the win condition is met based on the checking player having
 * more pieces (or objects, for object-based games) than the other player
 * when there are no moves left
 * For games such as Mancala where the winning condition relies on the number
 * of objects each player has, not pieces, checks number of objects based on
 * positions found from the initialStateConfig
 *
 * @author Sanya Kochhar
 */

public class NoMovesMorePieces implements WinType {
    private final int myStateIndex;
    private final List<List<Integer>> myInitialConfig;
    private List<List<Integer>> myCurrConfig;
    private final Boolean checkCurrConfig;
    private int myPlayerState;
    private int myEmptyState;

    /**
     *
     * @param stateIndex - index of specific state to be considered
     * @param initialConfig - initial configuration of states on the board
     * @param checkCurrConfig - determines whether to check curr board config or use object config instead
     * @param emptyState - state of the empty piece for the game
     */
    public NoMovesMorePieces(int stateIndex, List<List<Integer>> initialConfig, Boolean checkCurrConfig, int emptyState){
        myStateIndex = stateIndex;
        myInitialConfig = initialConfig;
        this.checkCurrConfig = checkCurrConfig;
        myEmptyState = emptyState;
    }

    /**
     * Checks number of pieces based on the current board configuration or object configuration
     * to determine whether the player has more pieces in a no remaining move situation
     * @param playerStates - a list of the states corresponding to the player
     * @param boardStateInfo - the current state of the board
     * @param objectInfo - the current layout of objects on the board
     * @param noMovesLeft - true if there are no moves left, false if there are
     * @return - whether the player in question has won
     */
    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
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
            numPlayerPieces = countObjectOccurrences(myStateCoords, objectInfo);
        }
        return noMovesLeft && numPlayerPieces > countAllPieces()/2;
    }

    /**
     * Counts all the pieces on the board. Used to determine whether the player
     * has a simple majority of the pieces or objects present
     * @return number of pieces or objects on the board
     */
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

    /**
     * Gets coordinates corresponding to a specific states
     */
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

    /**
     * Counts occurrences of objects residing in given coordinates
     * @param stateCoords - coordinates matching the specific state
     * @param objectInfo - current config of objects on the board
     * @return - number of object occurrences
     */
    private int countObjectOccurrences(ArrayList<Coordinate> stateCoords, List<List<Integer>> objectInfo) {
        int total = 0;
        for (Coordinate pos : stateCoords) {
            total += objectInfo.get(pos.getRow()).get(pos.getCol());
        }
        return total;
    }
}
