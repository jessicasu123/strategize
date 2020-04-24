package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class JumpCheckTest {
    List<Integer> posDirection = new ArrayList<>(List.of(1));
    List<Integer> negDirection = new ArrayList<>(List.of(-1));
    List<Integer> bothDirections = new ArrayList<>(List.of(-1,1));
    List<Integer> player1 = new ArrayList<>(List.of(1,3));
    List<Integer> player2 = new ArrayList<>(List.of(2,4));
    Coordinate coord00 = new Coordinate(0,0);
    GamePiece empty00 = new GamePiece(0,coord00,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord11 = new Coordinate(1,1);
    GamePiece empty11 = new GamePiece(0,coord11,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player1_11_pawn = new GamePiece(1,coord11,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord22 = new Coordinate(2,2);
    GamePiece player1_22_pawn = new GamePiece(1,coord22,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player2_22_pawn = new GamePiece(2,coord22,1, negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player2_22_king = new GamePiece(4,coord22,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty22 = new GamePiece(0,coord22,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord33 = new Coordinate(3,3);
    GamePiece empty33 = new GamePiece(0,coord33,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player1_33_pawn = new GamePiece(1,coord33,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player1_33_king = new GamePiece(3,coord33,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player2_33_king = new GamePiece(4,coord33,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord44 = new Coordinate(4,4);
    GamePiece player1_44_king = new GamePiece(3,coord44,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player2_44_pawn = new GamePiece(2,coord44,1, negDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player2_44_king = new GamePiece(4,coord44,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty44 = new GamePiece(0,coord44,1, bothDirections,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    Coordinate coord55 = new Coordinate(5,5);
    GamePiece empty55 = new GamePiece(0,coord55,1, posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    JumpCheck jump1 = new JumpCheck(0,player1);
    JumpCheck jump2 = new JumpCheck(0,player2);
    @Test
    void isConditionMetForwardJump() {
        //test jumps
        List<GamePiece> neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,player2_22_pawn,empty33,player2_44_king,empty55));
        assertTrue(jump1.isConditionMet(coord11,empty33,neighbors,1,posDirection));
        assertTrue(jump1.isConditionMet(coord11,empty55,neighbors,1,posDirection));
        assertFalse(jump1.isConditionMet(coord11,player2_44_king,neighbors,1,posDirection));

        //test cant jump over own piece
        neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,player1_22_pawn,empty33,player2_44_king,empty55));
        assertFalse(jump1.isConditionMet(coord11,empty33,neighbors,1,posDirection));
        assertFalse(jump1.isConditionMet(coord11,empty55,neighbors,1,posDirection));

        //test jump needs to follow pattern and needs to land on empty square
        neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,player2_22_pawn,player1_33_king,player2_44_king,empty55));
        assertFalse(jump1.isConditionMet(coord11,player1_33_king,neighbors,1,posDirection));

        //test player with negative direction can't do forward jump
        neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,player2_22_pawn,player1_33_king,empty44,empty55));
        assertFalse(jump2.isConditionMet(coord22,empty44,neighbors,2,negDirection));
    }

    @Test
    void isConditionMetBackwardJump() {
        //test jumps
        List<GamePiece> neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,empty22,player1_33_king,player2_44_pawn,empty55));
        assertTrue(jump2.isConditionMet(coord44,empty22,neighbors,2,negDirection));
        assertTrue(jump2.isConditionMet(coord44,empty00,neighbors,2,negDirection));
        assertFalse(jump2.isConditionMet(coord44,player1_11_pawn,neighbors,2,negDirection));

        //test cant jump over own piece
        neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,empty22,player2_33_king,player2_44_pawn,empty55));
        assertFalse(jump2.isConditionMet(coord44,empty22,neighbors,2,negDirection));
        assertFalse(jump2.isConditionMet(coord44,empty00,neighbors,2,negDirection));

        //test jump needs to follow pattern and needs to land on empty square
        neighbors = new ArrayList<>(List.of(empty00,player1_11_pawn,player2_22_pawn,player1_33_king,player2_44_pawn,empty55));
        assertFalse(jump2.isConditionMet(coord44,player2_22_pawn,neighbors,2,negDirection));

        //test player with negative direction can't do backward jump
        neighbors = new ArrayList<>(List.of(empty00,empty11,player2_22_pawn,player1_33_pawn,empty44,empty55));
        assertFalse(jump1.isConditionMet(coord33,empty11,neighbors,1,posDirection));

    }

    @Test
    void isConditionMetBothDirectionJump() {
        //test player 1 both directions
        List<GamePiece> neighbors = new ArrayList<>(List.of(empty00,empty11,player2_22_pawn,player1_33_king,player2_44_pawn,empty55));
        assertTrue(jump1.isConditionMet(coord33,empty11,neighbors,1,bothDirections));
        assertTrue(jump1.isConditionMet(coord33,empty55,neighbors,1,bothDirections));

        //test player 2 both direction
        neighbors = new ArrayList<>(List.of(empty00,empty11,player1_22_pawn,player2_33_king,player1_44_king,empty55));
        assertTrue(jump2.isConditionMet(coord33,empty11,neighbors,2,bothDirections));
        assertTrue(jump2.isConditionMet(coord33,empty55,neighbors,2,bothDirections));
    }
}