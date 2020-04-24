package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Game;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.NeighborAtEndCoordinateFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SplitObjectsMoveTest {
    GamePiece selfPiece1;
    GamePiece selfPiece2;
    GamePiece opponentPiece1;
    GamePiece opponentPiece2;
    List<GamePiece> neighbors;

    ConvertibleNeighborFinder neighborFinder = new NeighborAtEndCoordinateFinder();
    SplitObjectsMove splitObjectsMove = new SplitObjectsMove(neighborFinder, 0);
    Coordinate c1 = new Coordinate(0,0);
    Coordinate c2 = new Coordinate(0,1);
    Coordinate c3 = new Coordinate(1,0);
    Coordinate c4 = new Coordinate(1,1);
    List<Integer> directions = new ArrayList<>(List.of(1));

    @BeforeEach
    void setUp() {
        selfPiece1 = new GamePiece(1, c1,3, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        selfPiece2 = new GamePiece(1, c2,4, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        opponentPiece1 = new GamePiece(2, c3,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        opponentPiece2 = new GamePiece(2, c4,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        neighbors = new ArrayList<>(List.of(selfPiece1, selfPiece2, opponentPiece2, opponentPiece2));
    }



    @Test
    void testFailedSplit() {
        splitObjectsMove.completeMoveType(selfPiece1, c4, neighbors, 1, 1);
        assertEquals(1, opponentPiece2.getNumObjects());
        assertEquals(3, selfPiece1.getNumObjects());
        assertEquals(1, selfPiece1.getState());
    }

    @Test
    void testEvenSplit() {
        splitObjectsMove.completeMoveType(selfPiece2, c1, neighbors, 1, 1);
        assertEquals(2, selfPiece2.getNumObjects());
        assertEquals(5, selfPiece1.getNumObjects());
        assertEquals(1, selfPiece2.getState());
    }

    @Test
    void testOddSplit() {
        splitObjectsMove.completeMoveType(selfPiece1, c2, neighbors, 1, 1);
        assertEquals(2, selfPiece1.getNumObjects());
        assertEquals(5, selfPiece2.getNumObjects());
        assertEquals(1, selfPiece1.getState());

        splitObjectsMove.completeMoveType(opponentPiece1, c4, neighbors, 1, 1);
        assertEquals(2, opponentPiece2.getNumObjects());
        assertEquals(0, opponentPiece1.getNumObjects());
        assertEquals(0, opponentPiece1.getState());
    }

}