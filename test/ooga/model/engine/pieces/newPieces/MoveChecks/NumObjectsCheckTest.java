package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumObjectsCheckTest {
    List<Integer> directions = new ArrayList<>(List.of(1));
    NumObjectsCheck numObjectsCheck0 = new NumObjectsCheck(0);
    NumObjectsCheck numObjectsCheck1 = new NumObjectsCheck(1);
    NumObjectsCheck numObjectsCheck5 = new NumObjectsCheck(5);
    Coordinate c = new Coordinate(1,2);

    GamePiece pieceObj0 = new GamePiece(1, c,0, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj1 = new GamePiece(1, c,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj2 = new GamePiece(1, c,2, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj5 = new GamePiece(1, c,5, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj6 = new GamePiece(1, c,6, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    List<GamePiece> neighbors = new ArrayList<>(List.of(pieceObj1, pieceObj2));

    @Test
    void conditionMetTest() {
        assertTrue(numObjectsCheck0.isConditionMet(c, pieceObj1, neighbors, 1, directions));
        assertTrue(numObjectsCheck1.isConditionMet(c, pieceObj2, neighbors, 1, directions));
        assertTrue(numObjectsCheck5.isConditionMet(c, pieceObj6, neighbors, 1, directions));
    }

    @Test
    void conditionNotMetTest() {
        assertFalse(numObjectsCheck0.isConditionMet(c, pieceObj0, neighbors, 1, directions));
        assertFalse(numObjectsCheck5.isConditionMet(c, pieceObj5, neighbors, 1, directions));
        assertFalse(numObjectsCheck1.isConditionMet(c, pieceObj0, neighbors, 1, directions));
        assertFalse(numObjectsCheck5.isConditionMet(c, pieceObj2, neighbors, 1, directions));
    }
}