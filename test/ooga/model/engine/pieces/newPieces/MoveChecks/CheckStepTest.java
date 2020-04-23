package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class CheckStepTest {
    CheckStep step = new CheckStep();
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    List<Integer> negDirection = new ArrayList<>(List.of(-1));
    List<Integer> bothDirections = new ArrayList<>(List.of(-1,1));
    Coordinate coord00 = new Coordinate(0,0);
    Coordinate coord01 = new Coordinate(0,1);
    Coordinate coord11 = new Coordinate(1,1);
    Coordinate coord02 = new Coordinate(0,2);
    Coordinate coord13 = new Coordinate(1,3);
    Coordinate coord22 = new Coordinate(2,2);
    Coordinate coord20 = new Coordinate(2,0);
    Coordinate coord21 = new Coordinate(2,1);

    GamePiece piece00 = new GamePiece(0,coord00,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece11 = new GamePiece(0,coord11,1,negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece02 = new GamePiece(0,coord02,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece13 = new GamePiece(0,coord13,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece22 = new GamePiece(0,coord22,1,negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece20 = new GamePiece(0,coord20,1,negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece01 = new GamePiece(0,coord01,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece piece21 = new GamePiece(0,coord21,1,negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    List<GamePiece> all = new ArrayList<>(List.of(piece00, piece11, piece02, piece13,piece22, piece20, piece01,piece21));

    @Test
    void isConditionMetForwardStep() {
        assertTrue(step.isConditionMet(coord00,piece11,all,0,posDirection));
        assertTrue(step.isConditionMet(coord02,piece11,all,0,posDirection));
        assertTrue(step.isConditionMet(coord02,piece13,all,0,posDirection));

        assertFalse(step.isConditionMet(coord11,piece22,all,0,negDirection));
        assertFalse(step.isConditionMet(coord00,piece01,all,0,posDirection));
    }

    @Test
    void isConditionMetBackwardsStep() {
        assertTrue(step.isConditionMet(coord11,piece00,all,0,negDirection));
        assertTrue(step.isConditionMet(coord11,piece02,all,0,negDirection));

        assertFalse(step.isConditionMet(coord13,piece02,all,0,posDirection));
        assertFalse(step.isConditionMet(coord11,piece21,all,0,negDirection));
    }

    @Test
    void isConditionMetBothDirections() {
        piece11 = new GamePiece(0,coord11,1,bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        all = new ArrayList<>(List.of(piece00, piece11, piece02, piece13,piece22, piece20, piece01,piece21));

        assertTrue(step.isConditionMet(coord11,piece00,all,0,bothDirections));
        assertTrue(step.isConditionMet(coord11,piece02,all,0,bothDirections));
        assertTrue(step.isConditionMet(coord11,piece20,all,0,bothDirections));
        assertTrue(step.isConditionMet(coord11,piece22,all,0,bothDirections));

        assertFalse(step.isConditionMet(coord11,piece21,all,0,bothDirections));

    }
}