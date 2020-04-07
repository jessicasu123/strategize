package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGamePieceTest {
    Coordinate c = new Coordinate(0,1);
    GamePiece piece = new TicTacToeGamePiece(0, c);

    @Test
    void testCalculateAllPossibleMoves() {
        // TODO: update this depending on whether we decide to return just that piece's position or all positions
    }

    @Test
    void testMakeMove() {
        Coordinate endCoordinate = new Coordinate(1,1);
        List<GamePiece> dummyNeighbors = new ArrayList<>();
        int newState = 1;
        piece.makeMove(endCoordinate, dummyNeighbors, newState);
        assertEquals(endCoordinate, piece.getPosition());
        assertEquals(newState, piece.getState());
    }

    @Test
    void testGetState() {
        assertEquals(0, piece.getState());
    }

    @Test
    void testGetPosition() {
        assertEquals(c, piece.getPosition());
    }
}