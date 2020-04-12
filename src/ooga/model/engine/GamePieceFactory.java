package ooga.model.engine;

import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.OthelloGamePiece;
import ooga.model.engine.pieces.TicTacToeGamePiece;

/**
 * The purpose of this class is to generate different types
 * of GamePieces based on the game type. This class follows
 * the factory method design pattern.
 */
public class GamePieceFactory {

    /**
     * Creates a different subclass of GamePiece depending on the game type.
     * The Piece will be initialized with a status and position.
     * @param gameType - the type of game (ex. Tic-Tac-Toe, Checkers, Connect4, etc.)
     * @param status - the status that the generated piece will have (0,1,2).
     *               - specified by the starting configuration
     * @param position - the Coordinate position that the generated piece will have.
     *                 - determined by the current position (row, col) in starting config
     * @return - new type of GamePiece
     * @throws InvalidGameTypeException - if the gameType parameter is NOT one of the supported game types
     */
    public GamePiece createGamePiece(String gameType, int status, Coordinate position) throws InvalidGameTypeException {
        switch (gameType) {
            case "Tic-Tac-Toe":
                return new TicTacToeGamePiece(status, position);
            case "Othello":
                return new OthelloGamePiece(status,position);
            default:
                //TODO: figure out whether to throw exception here or in Board (createBoardFromStartingConfig)
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }

    }

}
