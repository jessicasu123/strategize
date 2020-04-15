package ooga.model.engine.Agent;

import ooga.model.engine.InvalidGameTypeException;


public class AgentFactory {
    public Agent createAgent(String gameType, int maxPlayer, int minPlayer) throws InvalidGameTypeException {
        switch (gameType) {
            case "Tic-Tac-Toe":
                //TODO get to read inARow from datafile
                return new TicTacToeAgent(maxPlayer, minPlayer, 3);
            case "Checkers":
                //TODO get to read from data file
                return new CheckersAgent(maxPlayer,minPlayer,2,4,0,1);
            case "Othello":
                return new OthelloAgent(maxPlayer, minPlayer);
            default:
                //TODO: figure out whether to throw exception here or in Board (createBoardFromStartingConfig)
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }

    }
}
