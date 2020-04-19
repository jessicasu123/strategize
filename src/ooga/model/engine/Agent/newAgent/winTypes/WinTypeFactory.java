package ooga.model.engine.Agent.newAgent.winTypes;


import ooga.model.engine.exceptions.InvalidWinTypeException;


public class WinTypeFactory {

    public WinType createNeighborhood(String winType, int emptyState) {
        switch (winType) {
            case "NoPiecesForOpponent":
                return new NoPiecesForOpponent(emptyState);
            default:
                throw new InvalidWinTypeException(winType + " is not a valid neighborhood type.");
        }
    }
}
