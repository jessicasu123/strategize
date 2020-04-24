package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnPieceCheckTest {
    List<Integer> directions = new ArrayList<>(List.of(1));
    Coordinate c1 = new Coordinate(1,2);
    Coordinate c2 = new Coordinate(1,1);

    GamePiece player1Piece = new GamePiece(1, c1,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece player1AltStatePiece = new GamePiece(2, c1,1, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece player2Piece = new GamePiece(3, c2,2, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    GamePiece emptyPiece = new GamePiece(0, c2,2, directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    List<GamePiece> neighbors = new ArrayList<>(List.of(player1Piece, player2Piece));

    List<Integer> player1States = new ArrayList<>(List.of(1,2));
    List<Integer> player2States = new ArrayList<>(List.of(3,4));

    OwnPieceCheck player1OwnCheck = new OwnPieceCheck(player1States);
    OwnPieceCheck player2OwnCheck = new OwnPieceCheck(player2States);

    @Test
    void conditionMetTest() {
        assertTrue(player1OwnCheck.isConditionMet(c1, player1Piece, neighbors, 1, directions));
        assertTrue(player1OwnCheck.isConditionMet(c1, player1AltStatePiece, neighbors, 2, directions));
        assertTrue(player2OwnCheck.isConditionMet(c1, player2Piece, neighbors, 3, directions));
    }

    @Test
    void conditionNotMetTest() {
        assertFalse(player1OwnCheck.isConditionMet(c1, player2Piece, neighbors, 1, directions));
        assertFalse(player1OwnCheck.isConditionMet(c1, player1Piece, neighbors, 1000, directions));
        assertFalse(player2OwnCheck.isConditionMet(c1, player1Piece, neighbors, 3, directions));
        assertFalse(player2OwnCheck.isConditionMet(c1, emptyPiece, neighbors, 3, directions));
    }
}