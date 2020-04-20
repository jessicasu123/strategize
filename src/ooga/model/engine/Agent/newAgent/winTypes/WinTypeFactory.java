package ooga.model.engine.Agent.newAgent.winTypes;


import ooga.model.engine.exceptions.InvalidWinTypeException;


public class WinTypeFactory {

    public WinType createWinType(String winType, int emptyState, int inARow) {
        switch (winType) {
            case "ConsecutivePieces":
                return new ConsecutivePieces(inARow);
            case "NoPiecesForOpponent":
                return new NoPiecesForOpponent(emptyState);
            default:
                throw new InvalidWinTypeException(winType + " is not a valid neighborhood type.");
        }
    }
}
