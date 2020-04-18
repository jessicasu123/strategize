package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.InvalidGameTypeException;

import java.util.List;

public class GameFactory {
    //TODO special number? for 3 in a row for tictactoe, 4 in a row for checkers, starting marbles number in mancala
    public GameTypeFactory createGameType(String gameType, List<Integer> userStates, List<Integer> agentStates,
                                          boolean userMovesPosDirection, int emptyState) throws InvalidGameTypeException {
        switch (gameType) {
            case "Connect4":
                return new ConnectFourFactory(userStates, agentStates);
            case "Tic-Tac-Toe":
                return new TicTacToeFactory(userStates, agentStates);
            case "Othello":
                return new OthelloFactory(userStates, agentStates);
            case "Checkers":
                return new CheckersFactory(userStates, agentStates, userMovesPosDirection, emptyState);
            case "Mancala":
                //TODO: what to do about num marbles start with?
                return new MancalaFactory(userStates, agentStates, userMovesPosDirection,emptyState,4);
            default:
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }
    }
}
