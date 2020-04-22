package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGamePieceTest {
    Coordinate emptyCoord = new Coordinate(0,1);
    Coordinate xCoord = new Coordinate(0,0);
    Coordinate oCoord = new Coordinate(0,2);
    GamePiece emptyPiece = new TicTacToeGamePiece(0, emptyCoord,1);
    GamePiece xPiece = new TicTacToeGamePiece(1, xCoord,1);
    GamePiece oPiece = new ConnectFourGamePiece(2, oCoord,1);
    List<GamePiece> dummyNeighbors = List.of(xPiece, oPiece);

    @Test
    void testCalculateAllPossibleMoves() {
        // possible moves for empty cell
        List<Coordinate> possibleMoves = emptyPiece.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(emptyCoord), possibleMoves);

        // possible moves for occupied cell
        List<Coordinate> noPossibleMoves = xPiece.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(), noPossibleMoves);
    }

    @Test
    void testMakeMove() {
        Coordinate endCoordinate = new Coordinate(1,1);
        List<GamePiece> dummyNeighbors = new ArrayList<>();
        int newState = 1;
        emptyPiece.makeMove(endCoordinate, dummyNeighbors, newState);
        assertEquals(endCoordinate, emptyPiece.getPosition());
        assertEquals(newState, emptyPiece.getState());
    }


    @Test
    void testGetState() {
        assertEquals(0, emptyPiece.getState());
        assertEquals(1, xPiece.getState());
        assertEquals(2, oPiece.getState());
    }

    @Test
    void testGetPosition() {
        assertEquals(emptyCoord, emptyPiece.getPosition());
        assertEquals(xCoord, xPiece.getPosition());
        assertEquals(oCoord, oPiece.getPosition());
    }
}