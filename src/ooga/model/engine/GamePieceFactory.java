package ooga.model.engine;

public class GamePieceFactory {

    public GamePiece createGamePiece(String gameType, int status, Coordinate position) throws InvalidGameTypeException {
        switch (gameType) {
            case "Tic-Tac-Toe":
                return new TicTacToeGamePiece(status, position);
            default:
                throw new InvalidGameTypeException(gameType + " is not a supported game type.");
        }

    }

}
