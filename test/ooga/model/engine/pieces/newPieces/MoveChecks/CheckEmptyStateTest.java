package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CheckEmptyStateTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    CheckEmptyState checkemptystate1 = new CheckEmptyState(0);
    CheckEmptyState checkemptystate2 = new CheckEmptyState(3);

    Coordinate coord1 = new Coordinate(1,2);
    GamePiece gp1 = new GamePiece(1,coord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord2 = new Coordinate(1,2);
    GamePiece gp2 = new GamePiece(0,coord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord3 = new Coordinate(1,2);
    GamePiece gp3 = new GamePiece(3,coord3,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate coord4 = new Coordinate(1,2);
    GamePiece gp4 = new GamePiece(1000,coord4,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());


    @Test
    void TestisConditionMetEmptyStateFalse() {
        List<GamePiece> neighbors = new ArrayList<>(List.of());
        assertFalse(checkemptystate1.isConditionMet(coord1,gp1,neighbors,1,posDirection));
        assertFalse(checkemptystate2.isConditionMet(coord1,gp1,neighbors,1,posDirection));
        assertFalse(checkemptystate1.isConditionMet(coord4,gp4,neighbors,1000,posDirection));
        assertFalse(checkemptystate2.isConditionMet(coord4,gp4,neighbors,1000,posDirection));
    }

    @Test
    void TestisConditionMetEmptyStateTrue() {
        List<GamePiece> neighbors = new ArrayList<>(List.of());
        assertTrue(checkemptystate1.isConditionMet(coord2,gp2,neighbors,0,posDirection));
        assertTrue(checkemptystate2.isConditionMet(coord3,gp3,neighbors,3,posDirection));
    }
}