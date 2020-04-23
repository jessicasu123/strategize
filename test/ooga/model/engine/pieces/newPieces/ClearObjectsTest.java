package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClearObjectsTest {
    List<Integer> posDir = new ArrayList<>(List.of(1));
    List<Integer> negDir = new ArrayList<>(List.of(-1));
    Coordinate coord01 = new Coordinate(0,1);
    Coordinate coord12 = new Coordinate(1,2);
    GamePiece piece1 = new GamePiece(3,coord01, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece2 = new GamePiece(1,coord12, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    List<GamePiece> all = new ArrayList<>(List.of(piece1,piece2));
    ClearObjects clear = new ClearObjects();
    @Test
    void completeMoveType() {
        clear.completeMoveType(piece1, coord01,all,3,-1);
        assertEquals(0, piece1.getNumObjects());

        clear.completeMoveType(piece2, coord12,all,1,1);
        assertEquals(0, piece2.getNumObjects());
    }
}