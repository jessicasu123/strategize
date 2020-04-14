package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OthelloGamePieceTest {
    //PIECES FOR HORIZONTAL LEFT
    OthelloGamePiece horizLeft = new OthelloGamePiece(0, new Coordinate(2,3));
    //horizontal neighbors
    OthelloGamePiece emptyHoriz_HL = new OthelloGamePiece(0, new Coordinate(2,0));
    OthelloGamePiece playerHoriz_HL = new OthelloGamePiece(1, new Coordinate(2,1));
    OthelloGamePiece oppHoriz_HL = new OthelloGamePiece(2, new Coordinate(2,2));
    //vertical neighbors
    OthelloGamePiece empty1Vert_HL = new OthelloGamePiece(0, new Coordinate(0,3));
    OthelloGamePiece empty2Vert_HL = new OthelloGamePiece(0, new Coordinate(1,3));
    OthelloGamePiece empty3Vert_HL = new OthelloGamePiece(0, new Coordinate(3,3));
    //diagonal neighbors
    OthelloGamePiece empty1Diag_HL = new OthelloGamePiece(0, new Coordinate(0,1));
    OthelloGamePiece playerDiag_HL = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece empty2Diag_HL = new OthelloGamePiece(0, new Coordinate(3,2));


    //test for horizontal left move
    @Test
    void testHorizontalLeftMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_HL, playerHoriz_HL, oppHoriz_HL,
                empty1Vert_HL, empty2Vert_HL, empty3Vert_HL,
                empty1Diag_HL, playerDiag_HL, empty2Diag_HL);
        List<Coordinate> possibleMoves = horizLeft.calculateAllPossibleMoves(neighbors);
        assertEquals(horizLeft.getPosition(), possibleMoves.get(0));

        horizLeft.makeMove(new Coordinate(2,3), neighbors, 1);
        //empty state should become player 1
        assertEquals(1, horizLeft.getState());
        //opponent piece to the left in the same row should also become 1
        assertEquals(1,oppHoriz_HL.getState());
    }


    //FOR HORIZONTAL RIGHT
    OthelloGamePiece horizRight = new OthelloGamePiece(0, new Coordinate(1,0));
    //horizontal row
    OthelloGamePiece opponent1Horiz = new OthelloGamePiece(2, new Coordinate(1,1));
    OthelloGamePiece myPiece1Horiz = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece emptySpotHoriz = new OthelloGamePiece(0, new Coordinate(1,3));
    //vertical row
    OthelloGamePiece empty1Vert = new OthelloGamePiece(0, new Coordinate(0,0));
    OthelloGamePiece empty2Vert = new OthelloGamePiece(0, new Coordinate(2,0));
    OthelloGamePiece empty3Vert = new OthelloGamePiece(0, new Coordinate(3,0));
    //diagonals
    OthelloGamePiece myPiece1Diag = new OthelloGamePiece(1, new Coordinate(2,1));
    OthelloGamePiece empty1Diag = new OthelloGamePiece(0, new Coordinate(3,2));
    OthelloGamePiece empty2Diag = new OthelloGamePiece(0, new Coordinate(0,1));


    //test for horizontal right move
    @Test
    void testHorizontalRightMove() {
        List<GamePiece> neighbors = List.of(opponent1Horiz, myPiece1Horiz, emptySpotHoriz,
                empty1Vert, empty2Vert, empty3Vert,
                myPiece1Diag, empty1Diag, empty2Diag);
        List<Coordinate> possibleMoves = horizRight.calculateAllPossibleMoves(neighbors);
        //this empty spot should be valid because there is an adjacent opponent piece between the empty spot
        //and the player piece in the same row
        assertEquals(new Coordinate(1,0),possibleMoves.get(0));

        horizRight.makeMove(new Coordinate(1,0),neighbors, 1);
        assertEquals(1, horizRight.getState());
        assertEquals(1, opponent1Horiz.getState());
    }

    //PIECES FOR VERTICAL TOP
    OthelloGamePiece vertTop = new OthelloGamePiece(0,new Coordinate(3,2));
    //horizontal neighbors
    OthelloGamePiece empty1Horiz_VT = new OthelloGamePiece(0, new Coordinate(3,0));
    OthelloGamePiece empty2Horiz_VT = new OthelloGamePiece(0, new Coordinate(3,1));
    OthelloGamePiece empty3Horiz_VT = new OthelloGamePiece(0, new Coordinate(3,3));
    //vertical neighbors
    OthelloGamePiece empty1Vert_VT = new OthelloGamePiece(0, new Coordinate(0,2));
    OthelloGamePiece player1Vert_VT = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece opponentVert_VT = new OthelloGamePiece(2, new Coordinate(2,2));
    //diagonal neighbors
    OthelloGamePiece empty1Diag_VT = new OthelloGamePiece(0, new Coordinate(1,0));
    OthelloGamePiece player1Diag_VT = new OthelloGamePiece(1, new Coordinate(2,2));
    OthelloGamePiece empty2Diag_VT = new OthelloGamePiece(0, new Coordinate(2,3));

    //test for vertical top move
    @Test
    void testVerticalTopMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VT, empty2Horiz_VT, empty3Horiz_VT,
                empty1Vert_VT, player1Vert_VT, opponentVert_VT,
                empty1Diag_VT, player1Diag_VT, empty2Diag_VT);
        List<Coordinate> verticalTopPossibleMoves = vertTop.calculateAllPossibleMoves(neighbors);
        assertEquals(vertTop.getPosition(), verticalTopPossibleMoves.get(0));

        vertTop.makeMove(new Coordinate(3,2), neighbors, 1);
        assertEquals(1, vertTop.getState());
        assertEquals(1, opponentVert_VT.getState());
    }

    //PIECES FOR VERTICAL BOTTOM
    OthelloGamePiece vertBottom = new OthelloGamePiece (0, new Coordinate(0,1));
    //horizontal neighbors
    OthelloGamePiece empty1Horiz_VB = new OthelloGamePiece(0, new Coordinate(0,0));
    OthelloGamePiece empty2Horiz_VB = new OthelloGamePiece(0, new Coordinate(0,2));
    OthelloGamePiece empty3Horiz_VB = new OthelloGamePiece(0, new Coordinate(0,3));
    //vertical neighbors
    OthelloGamePiece opponentVert_VB = new OthelloGamePiece(2, new Coordinate(1,1));
    OthelloGamePiece playerVert_VB = new OthelloGamePiece(1, new Coordinate(2,1));
    OthelloGamePiece emptyVert_VB = new OthelloGamePiece(0, new Coordinate(3,1));
    //diagonal neighbors
    OthelloGamePiece playerDiag_VB = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece empty1Diag_VB = new OthelloGamePiece(0, new Coordinate(2,3));
    OthelloGamePiece empty2Diag_VB = new OthelloGamePiece(0, new Coordinate(1,0));

    //test for vertical bottom move
    @Test
    void testVerticalBottomMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VB, empty2Horiz_VB, empty3Horiz_VB,
                opponentVert_VB, playerVert_VB, emptyVert_VB,
                playerDiag_VB, empty1Diag_VB, empty2Diag_VB);
        List<Coordinate> possibleMovesVertBottom = vertBottom.calculateAllPossibleMoves(neighbors);
        assertEquals(vertBottom.getPosition(), possibleMovesVertBottom.get(0));
        vertBottom.makeMove(new Coordinate(0,1), neighbors, 1);
        assertEquals(1, vertBottom.getState());
        assertEquals(1, opponentVert_VB.getState());
    }


    //PIECES FOR RIGHT DIAG PREV
    OthelloGamePiece rightDiagPrev = new OthelloGamePiece(0, new Coordinate(2,3));
    //horizontal neighbors
    OthelloGamePiece emptyHoriz_RDP = new OthelloGamePiece(0, new Coordinate(2,0));
    OthelloGamePiece playerHoriz_RDP = new OthelloGamePiece(1, new Coordinate(2,1));
    OthelloGamePiece oppHoriz_RDP = new OthelloGamePiece(2, new Coordinate(2,2));
    //vertical neighbors
    OthelloGamePiece empty1Vert_RDP = new OthelloGamePiece(0, new Coordinate(0,3));
    OthelloGamePiece empty2Vert_RDP = new OthelloGamePiece(0, new Coordinate(1,3));
    OthelloGamePiece empty3Vert_RDP = new OthelloGamePiece(0, new Coordinate(3,3));
    //diagonal neighbors
    OthelloGamePiece playerDiag_RDP = new OthelloGamePiece(1, new Coordinate(0,1));
    OthelloGamePiece oppDiag_RDP = new OthelloGamePiece(2, new Coordinate(1,2));
    OthelloGamePiece emptyDiag_RDP = new OthelloGamePiece(0, new Coordinate(3,2));

    @Test
    void testRightDiagPrevMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_RDP, playerHoriz_RDP, oppHoriz_RDP,
                empty1Vert_RDP, empty2Vert_RDP, empty3Vert_RDP,
                playerDiag_RDP, oppDiag_RDP, emptyDiag_RDP);
        List<Coordinate> possibleMovesRDP = rightDiagPrev.calculateAllPossibleMoves(neighbors);
        assertEquals(rightDiagPrev.getPosition(), possibleMovesRDP.get(0));
        rightDiagPrev.makeMove(new Coordinate(2,3), neighbors, 1);
        assertEquals(1, rightDiagPrev.getState());
        //the adjacent opponent in the same ROW should also be turned to player piece
        assertEquals(1, oppHoriz_RDP.getState());
        //adjacent opponent on diagonal should become player piece
        assertEquals(1, oppDiag_RDP.getState());
    }


    //PIECES FOR RIGHT DIAG NEXT
    OthelloGamePiece rightDiagNext = new OthelloGamePiece(0, new Coordinate(3,2));
    //horizontal neighbors
    OthelloGamePiece empty1Horiz_RDN = new OthelloGamePiece(0, new Coordinate(3,0));
    OthelloGamePiece empty2Horiz_RDN = new OthelloGamePiece(0, new Coordinate(3,1));
    OthelloGamePiece empty3Horiz_RDN = new OthelloGamePiece(0, new Coordinate(3,3));
    //vertical neighbors
    OthelloGamePiece emptyVert_RDN = new OthelloGamePiece(0, new Coordinate(0,2));
    OthelloGamePiece playerVert_RDN = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece oppVert_RDN = new OthelloGamePiece(2, new Coordinate(2,2));
    //diagonal neighbors
    OthelloGamePiece playerDiag_RDN = new OthelloGamePiece(1, new Coordinate(1,0));
    OthelloGamePiece oppDiag_RDN = new OthelloGamePiece(2, new Coordinate(2,1));
    OthelloGamePiece emptyDiag_RDN = new OthelloGamePiece(0, new Coordinate(2,3));

    //test for right diagonal next move - also tests that BOTH DIAGONAL AND VERTICAL TURN OPPONENTS
    @Test
    void testRightDiagNextMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_RDN, empty2Horiz_RDN, empty3Horiz_RDN,
                emptyVert_RDN, playerVert_RDN, oppVert_RDN,
                playerDiag_RDN, oppDiag_RDN, emptyDiag_RDN);
        List<Coordinate> possibleMovesRDN = rightDiagNext.calculateAllPossibleMoves(neighbors);
        assertEquals(rightDiagNext.getPosition(), possibleMovesRDN.get(0));

        rightDiagNext.makeMove(new Coordinate(3,2), neighbors, 1);
        assertEquals(1, rightDiagNext.getState());
        assertEquals(1, oppDiag_RDN.getState());
        assertEquals(1, oppVert_RDN.getState());
    }

    //PIECES FOR LEFT DIAG NEXT
    OthelloGamePiece leftDiagNext = new OthelloGamePiece(0, new Coordinate(0,3));
    //horizontal neighbors
    OthelloGamePiece emptyHoriz_LDN = new OthelloGamePiece(0, new Coordinate(0,0));
    OthelloGamePiece playerHoriz_LDN = new OthelloGamePiece(1, new Coordinate(0,1));
    OthelloGamePiece oppHoriz_LDN = new OthelloGamePiece(2, new Coordinate(0,2));
    //vertical neighbors
    OthelloGamePiece empty1Vert_LDN = new OthelloGamePiece(0, new Coordinate(1,3));
    OthelloGamePiece empty2Vert_LDN = new OthelloGamePiece(0, new Coordinate(2,3));
    OthelloGamePiece empty3Vert_LDN = new OthelloGamePiece(0, new Coordinate(3,3));
    //diagonal neighbors
    OthelloGamePiece oppDiag_LDN = new OthelloGamePiece(2, new Coordinate(1,2));
    OthelloGamePiece playerDiag_LDN = new OthelloGamePiece(1, new Coordinate(2,1));
    OthelloGamePiece emptyDiag_LDN = new OthelloGamePiece(0, new Coordinate(3,0));
    @Test
    void testLeftDiagNextMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_LDN, playerHoriz_LDN, oppHoriz_LDN,
                empty1Vert_LDN, empty2Vert_LDN, empty3Vert_LDN,
                oppDiag_LDN, playerDiag_LDN, emptyDiag_LDN);
        List<Coordinate> possibleMovesLDN = leftDiagNext.calculateAllPossibleMoves(neighbors);
        assertEquals(leftDiagNext.getPosition(), possibleMovesLDN.get(0));

        leftDiagNext.makeMove(new Coordinate(0,3), neighbors, 1);
        assertEquals(1, leftDiagNext.getState());
        assertEquals(1, oppHoriz_LDN.getState());
        assertEquals(1, oppDiag_LDN.getState());
    }

    //PIECES FOR LEFT DIAGONAL PREV MOVE
    OthelloGamePiece leftDiagPrev = new OthelloGamePiece(0, new Coordinate(3,0));
    //horizontal neighbors
    OthelloGamePiece empty1Horiz_LDP = new OthelloGamePiece(0, new Coordinate(3,1));
    OthelloGamePiece empty2Horiz_LDP = new OthelloGamePiece(0, new Coordinate(3,2));
    OthelloGamePiece empty3Horiz_LDP = new OthelloGamePiece(0, new Coordinate(3,3));
    //vertical neighbors
    OthelloGamePiece emptyVert_LDP = new OthelloGamePiece(0, new Coordinate(0,0));
    OthelloGamePiece playerVert_LDP = new OthelloGamePiece(1, new Coordinate(1,0));
    OthelloGamePiece oppVert_LDP = new OthelloGamePiece(2, new Coordinate(2,0));
    //diagonal neighbors
    OthelloGamePiece emptyDiag_LDP = new OthelloGamePiece(0, new Coordinate(0,3));
    OthelloGamePiece playerDiag_LDP = new OthelloGamePiece(1, new Coordinate(1,2));
    OthelloGamePiece oppDiag_LDP = new OthelloGamePiece(2, new Coordinate(2,1));

    //test for left diagonal prev move - also that both vertical and diagonal turn opponents
    @Test
    void testLeftDiagPrevMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_LDP, empty2Horiz_LDP, empty3Horiz_LDP,
                emptyVert_LDP, playerVert_LDP, oppVert_LDP,
                emptyDiag_LDP, playerDiag_LDP, oppDiag_LDP);
        List<Coordinate> possibleMovesLDP = leftDiagPrev.calculateAllPossibleMoves(neighbors);
        assertEquals(leftDiagPrev.getPosition(), possibleMovesLDP.get(0));

        leftDiagPrev.makeMove(new Coordinate(3,0), neighbors, 1);
        assertEquals(1, leftDiagPrev.getState());
        assertEquals(1, oppVert_LDP.getState());
        assertEquals(1, oppDiag_LDP.getState());

    }
}
