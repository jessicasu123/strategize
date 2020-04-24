package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.exceptions.InvalidWinTypeException;

import java.util.List;

/**
 * This factory is responsible for creating a WinType object
 * based on the WinTypes specified in game configuration file.
 */
public class WinTypeFactory {
    /**
     * @param winType - the type of win specified
     * @param emptyState - the integer representing the empty state
     * @param stateIndex - the index with which to access the state of the player
     * @param winValue - the value that determines a win
     * @param checkCurrConfig - a boolean of whether to check the current game configuration
     * @param initialConfig - the board's initial configuration
     * @return WinType object matching the specified string identifier from a config file
     */
    public WinType createWinType(String winType, int emptyState, int stateIndex, int winValue, boolean checkCurrConfig,
                                 List<List<Integer>> initialConfig) {
        switch (winType) {
            case "ConsecutivePieces":
                return new ConsecutivePieces(winValue);
            case "NoPiecesForOpponent":
                return new NoPiecesForOpponent(emptyState);
            case "NoMovesMorePieces":
                return new NoMovesMorePieces(winValue, stateIndex, initialConfig, checkCurrConfig);
            default:
                throw new InvalidWinTypeException(winType + " is not a valid neighborhood type.");
        }
    }
}
