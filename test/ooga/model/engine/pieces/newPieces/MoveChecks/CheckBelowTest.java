package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckBelowTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    Coordinate belowcoord1 = new Coordinate(5,2);
    GamePiece opponentBelow1 = new GamePiece(2,belowcoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate belowcoord2 = new Coordinate(4,2);
    GamePiece opponentBelow2 = new GamePiece(2,belowcoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord1 = new Coordinate(4,2);
    GamePiece myPiece1 = new GamePiece(1, startcoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord2 = new Coordinate(3,2);
    GamePiece myPiece2 = new GamePiece(1, startcoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord3 = new Coordinate(2,2);
    GamePiece myPiece3 = new GamePiece(1, startcoord3,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord4 = new Coordinate(5,2);
    GamePiece myPiece4 = new GamePiece(1, startcoord4,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate empty_52 = new Coordinate(5,2);
    GamePiece emptypiece_52 = new GamePiece(0, empty_52,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate empty_54 = new Coordinate(5,2);
    GamePiece emptypiece_54 = new GamePiece(0, empty_54,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    CheckBelow checkbelow = new CheckBelow(6);

    @Test
    void isConditionMetAbovePiece() {
        List<GamePiece> verticalneighbors = new ArrayList<>(List.of(opponentBelow1));
        assertTrue(checkbelow.isConditionMet(startcoord1,myPiece1,verticalneighbors,1,posDirection));
        assertFalse(checkbelow.isConditionMet(startcoord2,myPiece2,verticalneighbors,1,posDirection));

        List<GamePiece> verticalneighbors2 = new ArrayList<>(List.of(opponentBelow2));
        assertTrue(checkbelow.isConditionMet(startcoord2,myPiece2,verticalneighbors2,1,posDirection));
        assertFalse(checkbelow.isConditionMet(startcoord3,myPiece3,verticalneighbors2,1,posDirection));
    }

    @Test
    void isConditionMetAtBottomRow() {
        List<GamePiece> neighbors = new ArrayList<>(List.of());
        assertTrue(checkbelow.isConditionMet(startcoord4,myPiece4,neighbors,1,posDirection));
        assertFalse(checkbelow.isConditionMet(startcoord3,myPiece3,neighbors,1,posDirection));
    }
}