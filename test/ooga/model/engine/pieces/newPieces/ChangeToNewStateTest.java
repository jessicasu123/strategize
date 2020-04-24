package ooga.model.engine.pieces.newPieces;


import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ChangeToNewStateTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));

    Coordinate gamecoord1 = new Coordinate(5,2);
    GamePiece gameplayer = new GamePiece(1,gamecoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate gamecoord2 = new Coordinate(4,2);
    GamePiece gameopponent = new GamePiece(2,gamecoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate gamecoord3 = new Coordinate(5,4);
    GamePiece gamempty = new GamePiece(0,gamecoord3,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    Coordinate gamecoord4 = new Coordinate(5,2);
    GamePiece gamespecial = new GamePiece(123123,gamecoord4,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    ChangeToNewState change = new ChangeToNewState();

    @Test
    void completeMoveTypeTest() {
        List<GamePiece> neighbors = new ArrayList<>(List.of());
        change.completeMoveType(gameplayer,gamecoord1,neighbors,2,0);
        change.completeMoveType(gameopponent,gamecoord2,neighbors,1,0);
        change.completeMoveType(gamempty,gamecoord3,neighbors,1,0);
        change.completeMoveType(gamespecial,gamecoord4,neighbors,5,0);

        assertEquals(2,gameplayer.getState());
        assertEquals(1,gameopponent.getState());
        assertEquals(1,gamempty.getState());
        assertEquals(5,gamespecial.getState());

    }
}