package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.exceptions.InvalidWinTypeException;

import java.util.List;

public class WinTypeFactory {

    public WinType createWinType(String winType, int emptyState, int stateIndex, int winValue, int playerRow,
                                 boolean checkCurrConfig, List<List<Integer>> initialConfig) {
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
