package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckNumObjectsTest {
    List<Integer> directions = new ArrayList<>(List.of(1));
    CheckNumObjects checkNumObjects0 = new CheckNumObjects(0);
    CheckNumObjects checkNumObjects1 = new CheckNumObjects(1);
    CheckNumObjects checkNumObjects5 = new CheckNumObjects(5);
    Coordinate c = new Coordinate(1,2);

    GamePiece pieceObj0 = new GamePiece(1, c,0, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj1 = new GamePiece(1, c,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj2 = new GamePiece(1, c,2, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj5 = new GamePiece(1, c,5, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece pieceObj6 = new GamePiece(1, c,6, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    List<GamePiece> neighbors = new ArrayList<>(List.of(pieceObj1, pieceObj2));

    @Test
    void conditionMetTest() {
        assertTrue(checkNumObjects0.isConditionMet(c, pieceObj1, neighbors, 1, directions));
        assertTrue(checkNumObjects1.isConditionMet(c, pieceObj2, neighbors, 1, directions));
        assertTrue(checkNumObjects5.isConditionMet(c, pieceObj6, neighbors, 1, directions));
    }

    @Test
    void conditionNotMetTest() {
        assertFalse(checkNumObjects0.isConditionMet(c, pieceObj0, neighbors, 1, directions));
        assertFalse(checkNumObjects5.isConditionMet(c, pieceObj5, neighbors, 1, directions));
        assertFalse(checkNumObjects1.isConditionMet(c, pieceObj0, neighbors, 1, directions));
        assertFalse(checkNumObjects5.isConditionMet(c, pieceObj2, neighbors, 1, directions));
    }
}