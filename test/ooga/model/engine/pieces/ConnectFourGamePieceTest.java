package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourGamePieceTest {
    Coordinate c = new Coordinate(5,3);
    GamePiece piece = new ConnectFourGamePiece(0,c,1);
    Coordinate desiredCoord = new Coordinate(5,3);

    GamePiece piece2 = new ConnectFourGamePiece(0, new Coordinate(4,3), 1);
    Coordinate desiredCoord2 = new Coordinate(4,3);

    GamePiece neighbor = new ConnectFourGamePiece(1,new Coordinate(0,3),1);
    GamePiece neighbor2 = new ConnectFourGamePiece(1,new Coordinate(1,3),1);
    GamePiece neighbor3 = new ConnectFourGamePiece(1,new Coordinate(2,3),1);
    GamePiece neighbor4 = new ConnectFourGamePiece(1,new Coordinate(3,3),1);
    GamePiece neighbor5 = new ConnectFourGamePiece(1,new Coordinate(4,3),1);
    List<GamePiece> dummyNeighbors = List.of(neighbor, neighbor2, neighbor3, neighbor4, neighbor5);

    GamePiece neighbor6 = new ConnectFourGamePiece(1,new Coordinate(5,3),1);

    @Test
    void testCalculateAllPossibleMoves() {
        //test when coordinate is at bottom row
        List<Coordinate> coords = new ArrayList<>();
        coords.add(desiredCoord);
        assertEquals(coords, piece.calculateAllPossibleMoves(dummyNeighbors,1));

        //test when coordinate has piece underneath
        dummyNeighbors = List.of(neighbor, neighbor2, neighbor3, neighbor4, neighbor6);
        List<Coordinate> possibleMoves = piece2.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(desiredCoord2), possibleMoves);
    }

    @Test
    void testMakeMove() {
        Coordinate endCoordinate = new Coordinate(5,3);
        int newState = 1;
        piece.makeMove(endCoordinate,dummyNeighbors,newState);
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