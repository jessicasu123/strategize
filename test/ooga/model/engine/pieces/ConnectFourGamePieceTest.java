package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourGamePieceTest {
    Coordinate c = new Coordinate(5,3);
    GamePiece piece = new ConnectFourGamePiece(0,c,1);
    Coordinate c1 = new Coordinate(5,3);
    GamePiece neighbor = new ConnectFourGamePiece(2,c1,1);

    @Test
    void testCalculateAllPossibleMoves() {
        List<Coordinate> coords = new ArrayList<>();
        coords.add(c1);
        List<GamePiece> dummyNieghbors = new ArrayList<>();
        dummyNieghbors.add(neighbor);
        assertEquals(coords, piece.calculateAllPossibleMoves(dummyNieghbors,1));
    }

    @Test
    void testMakeMove() {
        Coordinate endCoordinate = new Coordinate(5,2);
        List<GamePiece> dummyNieghbors = new ArrayList<>();
        int newState = 1;
        piece.makeMove(endCoordinate,dummyNieghbors,newState);
        assertEquals(endCoordinate, piece.getPosition());
        assertEquals(newState,piece.getState());
    }

    @Test
    void testgetState() {
        assertEquals(0, piece.getState());
    }

    @Test
    void testGetPosition() {
        assertEquals(c, piece.getPosition());
    }
}