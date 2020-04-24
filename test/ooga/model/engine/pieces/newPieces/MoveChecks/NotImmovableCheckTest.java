package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotImmovableCheckTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    NotImmovableCheck check1 = new NotImmovableCheck(0);
    NotImmovableCheck check2 = new NotImmovableCheck(3);

    Coordinate coord1 = new Coordinate(1,2);
    GamePiece gp1 = new GamePiece(1,coord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord2 = new Coordinate(1,2);
    GamePiece gp2 = new GamePiece(0,coord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord3 = new Coordinate(1,2);
    GamePiece gp3 = new GamePiece(3,coord3,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord4 = new Coordinate(1,2);
    GamePiece gp4 = new GamePiece(1000,coord4,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> all = new ArrayList<>(List.of(gp1,gp2,gp3,gp4));
    @Test
    void isConditionMetFalse() {
        assertFalse(check1.isConditionMet(coord2,gp2,all,0,posDirection));
        assertFalse(check2.isConditionMet(coord3,gp3,all,3,posDirection));
    }

    @Test
    void isConditionMetTrue() {
        assertTrue(check2.isConditionMet(coord2,gp2,all,0,posDirection));
        assertTrue(check1.isConditionMet(coord3,gp3,all,3,posDirection));

        assertTrue(check2.isConditionMet(coord1,gp1,all,1,posDirection));
        assertTrue(check2.isConditionMet(coord4,gp4,all,1000,posDirection));
        assertTrue(check1.isConditionMet(coord1,gp1,all,1,posDirection));
        assertTrue(check2.isConditionMet(coord4,gp4,all,1,posDirection));

    }
}