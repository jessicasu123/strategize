package ooga.model.engine;

import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.TicTacToeGamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GamePieceFactoryTest {
    GamePieceFactory gamePieceFactory = new GamePieceFactory();

    @Test
    void testCreatePieceWithValidGameType() throws InvalidGameTypeException {
        Coordinate coord = new Coordinate(0,0);
        GamePiece gp = gamePieceFactory.createGamePiece("Tic-Tac-Toe", 0, coord);
        //testing that it's a tic tac goe game piece
        assertTrue(gp instanceof TicTacToeGamePiece);
        //testing status
        assertEquals(gp.getState(), 0);
        //testing position
        assertEquals(gp.getPosition(), coord);
    }

    @Test
    void testInvalidGameTypeException() throws InvalidGameTypeException {
        Coordinate c = new Coordinate(1,1);
        String gameType = "Chess";
        assertThrows(InvalidGameTypeException.class, () -> gamePieceFactory.createGamePiece(gameType, 0, c));
    }

}
