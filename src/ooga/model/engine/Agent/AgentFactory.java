package ooga.model.engine.Agent;

import ooga.model.engine.InvalidGameTypeException;


public class AgentFactory {
    public Agent createAgent(String gameType, int maxPlayer, int minPlayer) throws InvalidGameTypeException {
        switch (gameType) {
            case "Tic-Tac-Toe":
                //TODO get to read inARow from datafile
                return new TicTacToeAgent(maxPlayer, minPlayer, 3);
            default:
                //TODO: figure out whether to throw exception here or in Board (createBoardFromStartingConfig)
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }

    }
}
