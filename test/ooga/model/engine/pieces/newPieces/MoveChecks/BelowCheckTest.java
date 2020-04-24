package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class BelowCheckTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    Coordinate belowcoord1 = new Coordinate(5,2);
    GamePiece opponentBelow1 = new GamePiece(2,belowcoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate belowcoord2 = new Coordinate(4,2);
    GamePiece opponentBelow2 = new GamePiece(2,belowcoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord1 = new Coordinate(4,2);
    GamePiece myPiece1 = new GamePiece(0, startcoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord2 = new Coordinate(3,2);
    GamePiece myPiece2 = new GamePiece(0, startcoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord3 = new Coordinate(2,2);
    GamePiece myPiece3 = new GamePiece(1, startcoord3,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord4 = new Coordinate(5,2);
    GamePiece myPiece4 = new GamePiece(1, startcoord4,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate empty_52 = new Coordinate(5,2);
    GamePiece emptypiece_52 = new GamePiece(0, empty_52,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate empty_54 = new Coordinate(5,2);
    GamePiece emptypiece_54 = new GamePiece(0, empty_54,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    BelowCheck checkbelow = new BelowCheck();

    Coordinate startcoord = new Coordinate(0,2);
    GamePiece myPiece = new GamePiece(1, startcoord, 1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate startcoord0 = new Coordinate(1,2);
    GamePiece myPiece0 =  new GamePiece(1, startcoord0,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void isConditionMetAbovePiece() {
        List<GamePiece> verticalneighbors = new ArrayList<>(List.of(myPiece, myPiece0, myPiece3, myPiece2, opponentBelow1));
        assertTrue(checkbelow.isConditionMet(startcoord1,myPiece1,verticalneighbors,1,posDirection));
        verticalneighbors = List.of(myPiece, myPiece0, myPiece3, myPiece1, opponentBelow1);
        assertFalse(checkbelow.isConditionMet(startcoord2,myPiece2,verticalneighbors,1,posDirection));

        List<GamePiece> verticalneighbors2 = new ArrayList<>(List.of(myPiece, myPiece0, myPiece3, opponentBelow2, opponentBelow1));
        assertTrue(checkbelow.isConditionMet(startcoord2,myPiece2,verticalneighbors2,1,posDirection));
        verticalneighbors2 = List.of(myPiece, myPiece0, myPiece2, myPiece1, opponentBelow1);
        assertFalse(checkbelow.isConditionMet(startcoord3,myPiece3,verticalneighbors2,1,posDirection));
    }

    @Test
    void isConditionMetAtBottomRow() {
        List<GamePiece> neighbors = new ArrayList<>(List.of(myPiece, myPiece0, myPiece1, myPiece2, myPiece3));
        assertTrue(checkbelow.isConditionMet(startcoord4,myPiece4,neighbors,1,posDirection));
        List<GamePiece> neighbors2 = List.of(myPiece, myPiece0, myPiece2, myPiece1, myPiece4);
        assertFalse(checkbelow.isConditionMet(startcoord3,myPiece3,neighbors2,1,posDirection));
    }
}