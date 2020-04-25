package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.NeighborsUntilNoObjectsFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForceMoveAgainMoveTest {
    List<Integer> posDir = new ArrayList<>(List.of(1));
    List<Integer> negDir = new ArrayList<>(List.of(-1));
    List<Integer> noDir = new ArrayList<>(List.of(0));

    Coordinate coord00 = new Coordinate(0,0);
    Coordinate coord01 = new Coordinate(0,1);
    Coordinate coord02 = new Coordinate(0,2);
    Coordinate coord03 = new Coordinate(0,3);
    Coordinate coord04 = new Coordinate(0,4);
    Coordinate coord05 = new Coordinate(0,5);
    Coordinate coord06 = new Coordinate(0,6);
    Coordinate coord07 = new Coordinate(0,7);

    Coordinate coord10 = new Coordinate(1,0);
    Coordinate coord11 = new Coordinate(1,1);
    Coordinate coord12 = new Coordinate(1,2);
    Coordinate coord13 = new Coordinate(1,3);
    Coordinate coord14 = new Coordinate(1,4);
    Coordinate coord15 = new Coordinate(1,5);
    Coordinate coord16 = new Coordinate(1,6);
    Coordinate coord17 = new Coordinate(1,7);

    GamePiece goal1 = new GamePiece(4,coord00, 0,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece goal2 = new GamePiece(2, coord17, 0,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece empty1= new GamePiece(0,coord07,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2 = new GamePiece(0,coord10,0, noDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece topSquare1 = new GamePiece(3,coord01, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare2 = new GamePiece(3,coord02, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare3 = new GamePiece(3,coord03, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare4 = new GamePiece(3,coord04, 4,negDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare5 = new GamePiece(3,coord05, 4,negDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece topSquare6 = new GamePiece(3,coord06, 4,negDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());


    GamePiece bottomSquare1 = new GamePiece(1,coord11, 4,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare2 = new GamePiece(1,coord12, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare3 = new GamePiece(1,coord13,4, posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare4 = new GamePiece(1,coord14, 4,posDir,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare5 = new GamePiece(1,coord15, 4,posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece bottomSquare6 = new GamePiece(1,coord16,4, posDir, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> allPiece = new ArrayList<>(List.of(goal1,goal2,empty1,empty2,topSquare1,topSquare2,topSquare3,
            topSquare4,topSquare5,topSquare6,bottomSquare1,bottomSquare2,bottomSquare3,bottomSquare4,bottomSquare5,bottomSquare6));
    List<Integer> player = new ArrayList<>(List.of(1,2));
    List<Integer> player2 = new ArrayList<>(List.of(3,4));
    List<Integer> toIgnore = new ArrayList<>(List.of(0,4));
    List<Integer> toIgnore2 = new ArrayList<>(List.of(0,2));
    ConvertibleNeighborFinder finder1 = new NeighborsUntilNoObjectsFinder(toIgnore);
    ForceMoveAgainMove forceMove1 = new ForceMoveAgainMove(player,finder1);
    ConvertibleNeighborFinder finder2 = new NeighborsUntilNoObjectsFinder(toIgnore2);
    ForceMoveAgainMove forceMove2 = new ForceMoveAgainMove(player2,finder2);

    @Test
    void completeMoveTypeEndsInGoal() {
        forceMove1.completeMoveType(bottomSquare3,coord13,allPiece,1,1);
        assertFalse(bottomSquare3.changeTurnAfterMove());

        forceMove2.completeMoveType(topSquare4,coord04,allPiece,3,-1);
        assertFalse(topSquare4.changeTurnAfterMove());
    }

    @Test
    void completeMoveTypeEndsInNormalPocket() {
        forceMove1.completeMoveType(bottomSquare1,coord11,allPiece,1,1);
        assertTrue(bottomSquare1.changeTurnAfterMove());

        forceMove2.completeMoveType(topSquare6,coord06,allPiece,3,-1);
        assertTrue(topSquare6.changeTurnAfterMove());
    }
    @Test
    void completeMoveTypeEndsInOpponentSquare() {
        forceMove1.completeMoveType(bottomSquare6,coord16,allPiece,1,1);
        assertTrue(bottomSquare6.changeTurnAfterMove());

        forceMove2.completeMoveType(topSquare1,coord01,allPiece,3,-1);
        assertTrue(topSquare1.changeTurnAfterMove());
    }

}