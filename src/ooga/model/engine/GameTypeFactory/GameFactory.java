package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.InvalidGameTypeException;

public class GameFactory {
    //TODO special number? for 3 in a row for tictactoe, 4 in a row for checkers, starting marbles number in mancala
    public GameTypeFactory createGameType(String gameType, int userPlayerID, int agentPlayerID, int userSpecialID,
                                          int agentSpecialID, boolean userPosDirection, int emptyState) throws InvalidGameTypeException {
        switch (gameType) {
            case "Connect4":
                return new ConnectFourFactory(userPlayerID,agentPlayerID);
            case "Tic-Tac-Toe":
                return new TicTacToeFactory(userPlayerID, agentPlayerID);
            case "Othello":
                return new OthelloFactory(userPlayerID,agentPlayerID);
            case "Checkers":
                return new CheckersFactory(userPlayerID,agentPlayerID,userSpecialID,agentSpecialID, userPosDirection, emptyState);
            case "Mancala":
                //TODO: what to do about num marbles start with?
                return new MancalaFactory(userPlayerID,agentPlayerID,userSpecialID,agentSpecialID,userPosDirection,emptyState,4);
            default:
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }
    }
}
