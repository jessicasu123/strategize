package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.exceptions.InvalidWinTypeException;

public class WinTypeFactory {

    public WinType createWinType(String winType, int emptyState, int winValue, int playerRow) {
        switch (winType) {
            case "ConsecutivePieces":
                return new ConsecutivePieces(winValue);
            case "NoPiecesForOpponent":
                return new NoPiecesForOpponent(emptyState);
            case "NoMovesMorePieces":
                return new NoMovesMorePieces(winValue, playerRow);
            default:
                throw new InvalidWinTypeException(winType + " is not a valid neighborhood type.");
        }
    }
}
