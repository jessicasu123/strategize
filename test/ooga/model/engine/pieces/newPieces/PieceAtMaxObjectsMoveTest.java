package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.NeighborAtEndCoordinateFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieceAtMaxObjectsMoveTest {
    ConvertibleNeighborFinder neighborFinder = new NeighborAtEndCoordinateFinder();
    PieceAtMaxObjectsMove pieceAtMaxObjectsMove = new PieceAtMaxObjectsMove(4, 0, neighborFinder);
    Coordinate c1 = new Coordinate(0,0);
    Coordinate c2 = new Coordinate(0,1);
    Coordinate c3 = new Coordinate(1,1);

    List<Integer> directions = new ArrayList<>(List.of(1));
    GamePiece selfPiece1 = new GamePiece(1, c1,3, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece selfPiece2 = new GamePiece(1, c2,5, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece opponentPiece1 = new GamePiece(2, c3,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    List<GamePiece> neighbors = new ArrayList<>(List.of(selfPiece2, opponentPiece1));

    @Test
    void completeMoveType() {
        // if neighbor piece has max objects, it's state and objects are nullified
        pieceAtMaxObjectsMove.completeMoveType(selfPiece1, c2, neighbors, 1, 1);
        assertEquals(0, selfPiece2.getState());
        assertEquals(0, selfPiece2.getNumObjects());
        assertEquals(1, selfPiece1.getState());
        assertEquals(3, selfPiece1.getNumObjects());

        // else: it is not modified
        pieceAtMaxObjectsMove.completeMoveType(selfPiece1, c3, neighbors, 1, 1);
        assertEquals(1, opponentPiece1.getNumObjects());
        assertEquals(2, opponentPiece1.getState());
    }
}