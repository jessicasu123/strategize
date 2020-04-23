package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {
    List<Integer> direction = new ArrayList<>(List.of(1));
    Coordinate coord00 = new Coordinate(0,0);
    GamePiece piece00 = new GamePiece(0,coord00,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord11 = new Coordinate(1,1);
    GamePiece piece11 = new GamePiece(0,coord11,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord22 = new Coordinate(2,2);
    GamePiece piece22 = new GamePiece(0,coord22,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord33 = new Coordinate(3,3);
    GamePiece piece33 = new GamePiece(0,coord33,1,direction,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    List<GamePiece> all = new ArrayList<>(List.of(piece00, piece11, piece22, piece33));
    MoveType promotion = new Promotion(3,1);
    @Test
    void completeMoveType() {

        promotion.completeMoveType(piece22,coord33,all,0,1);
        assertEquals(1, piece22.getState());
        assertTrue(direction.size() > 1);
        assertTrue(direction.contains(-1));

        promotion.completeMoveType(piece11,coord33,all,0,1);
        assertEquals(1, piece11.getState());

        promotion.completeMoveType(piece00,coord22,all,0,1);
        assertEquals(0, piece00.getState());
    }

}