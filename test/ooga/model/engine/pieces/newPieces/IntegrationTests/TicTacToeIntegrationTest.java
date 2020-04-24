package ooga.model.engine.pieces.newPieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Game;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;


import java.util.List;

public class TicTacToeIntegrationTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    Coordinate emptyCoord = new Coordinate(1,1);
    Coordinate xCoord = new Coordinate(0,0);
    Coordinate oCoord = new Coordinate(0,2);
    GamePiece emptyPiece = new GamePiece(0, emptyCoord,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece xPiece = new GamePiece(1, xCoord,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oPiece = new GamePiece(2, oCoord,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
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
        List<GamePiece> dummyNeighbors = new ArrayList<>();
        int newState = 1;
        emptyPiece.makeMove(new Coordinate(1,1), dummyNeighbors, newState);
        assertEquals(new Coordinate(1,1), emptyPiece.getPosition());
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
