package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindNeighborsUntilNoObjects;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCaptureTest {
    List<Integer> posDir = new ArrayList<>(List.of(1));
    List<Integer> negDir = new ArrayList<>(List.of(-1));
    List<Integer> noDir = new ArrayList<>(List.of(0));

    Coordinate coord00 = new Coordinate(0,0);
    Coordinate coord01 = new Coordinate(0,1);
    Coordinate coord02 = new Coordinate(0,2);
    Coordinate coord03 = new Coordinate(0,3);
    Coordinate coord04 = new Coordinate(0,4);

    Coordinate coord10 = new Coordinate(1,0);
    Coordinate coord11 = new Coordinate(1,1);
    Coordinate coord12 = new Coordinate(1,2);
    Coordinate coord13 = new Coordinate(1,3);
    Coordinate coord14 = new Coordinate(1,4);

    GamePiece goal1 = new GamePiece(4,coord00, 0,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece goal2 = new GamePiece(2, coord14, 0,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece empty1= new GamePiece(0,coord04,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2 = new GamePiece(0,coord10,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece topSquare1 = new GamePiece(3,coord01, 0,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare2 = new GamePiece(3,coord02, 1,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare3 = new GamePiece(3,coord03, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece bottomSquare1 = new GamePiece(1,coord11, 2,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare2 = new GamePiece(1,coord12, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare3 = new GamePiece(1,coord13,0, posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> allPiece = new ArrayList<>(List.of(goal1,goal2,empty1,empty2,topSquare1,topSquare2,topSquare3,
            bottomSquare1,bottomSquare2,bottomSquare3));
    List<Integer> player = new ArrayList<>(List.of(1,2));
    List<Integer> player2 = new ArrayList<>(List.of(3,4));
    List<Integer> toIgnore = new ArrayList<>(List.of(0,4));
    List<Integer> toIgnore2 = new ArrayList<>(List.of(0,2));
    ConvertableNeighborFinder finder1 = new FindNeighborsUntilNoObjects(toIgnore);
    ConvertableNeighborFinder finder2 = new FindNeighborsUntilNoObjects(toIgnore2);
    SpecialCapture capture1 = new SpecialCapture(player,finder1);
    SpecialCapture capture2 = new SpecialCapture(player2,finder2);

    @Test
    void completeMoveType() {
        capture1.completeMoveType(bottomSquare1,coord11,allPiece,1,1);
        assertEquals(5, goal2.getNumObjects());
        assertEquals(0, bottomSquare3.getNumObjects());
        assertEquals(0, topSquare3.getNumObjects());

        bottomSquare1 = new GamePiece(1,coord11, 4,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        allPiece = new ArrayList<>(List.of(goal1,goal2,empty1,empty2,topSquare1,topSquare2,topSquare3,
                bottomSquare1,bottomSquare2,bottomSquare3));

        capture2.completeMoveType(topSquare2,coord02,allPiece,3,-1);
        assertEquals(5, goal1.getNumObjects());
        assertEquals(0, topSquare1.getNumObjects());
        assertEquals(0, bottomSquare1.getNumObjects());
    }
}