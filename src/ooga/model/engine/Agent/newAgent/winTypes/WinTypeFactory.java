package ooga.model.engine.Agent.newAgent.winTypes;

import ooga.model.engine.exceptions.InvalidWinTypeException;

import java.util.List;

public class WinTypeFactory {

    public WinType createWinType(String winType, int emptyState, int winValue, int playerRow, int myPlayer, int playerStateIndex,
                                 List<List<Integer>> initialConfig, Boolean checkCurrConfig) {
        switch (winType) {
            case "ConsecutivePieces":
                return new ConsecutivePieces(winValue, myPlayer);
            case "NoPiecesForOpponent":
                return new NoPiecesForOpponent(emptyState);
            case "NoMovesMorePieces":
                return new NoMovesMorePieces(winValue, playerStateIndex, initialConfig, checkCurrConfig);
            default:
                throw new InvalidWinTypeException(winType + " is not a valid neighborhood type.");
        }
    }
}
