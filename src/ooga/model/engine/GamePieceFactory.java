package ooga.model.engine;

public class GamePieceFactory {

    public GamePiece createGamePiece(String gameType, int status, Coordinate position) {
        switch (gameType) {
            case "Tic-Tac-Toe":
                return new TicTacToeGamePiece(status, position);
        }
        return null;
    }

}
