package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionMoveTest {
    MoveType pos = new PositionMove();
    List<Integer> direction = new ArrayList<>(List.of(1));
    GamePiece piece1 = new GamePiece(1,new Coordinate(0,0),direction,1,
            new ArrayList<>(),new ArrayList<>(), new ArrayList<>(List.of(pos)));
    GamePiece piece2 = new GamePiece(1,new Coordinate(1,1),direction,1,
            new ArrayList<>(), new ArrayList<>(),new ArrayList<>(List.of(pos)));
    GamePiece piece3 = new GamePiece(1,new Coordinate(2,2),direction,1,
            new ArrayList<>(),new ArrayList<>(), new ArrayList<>(List.of(pos)));

    @Test
    void completeMoveType() {
        Coordinate moveTo = new Coordinate (1,1);
        Coordinate oldPos = piece1.getPosition();
        piece1.makeMove(moveTo, new ArrayList<>(List.of(piece2, piece3)),1);
        assertEquals(moveTo, piece1.getPosition());
        assertEquals(oldPos, piece2.getPosition());

    }
}