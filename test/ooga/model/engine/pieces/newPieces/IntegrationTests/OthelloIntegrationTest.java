package ooga.model.engine.pieces.newPieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ChangeOpponentPiecesMove;
import ooga.model.engine.pieces.newPieces.ChangeToNewStateMove;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FlippableNeighborFinder;
import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.MoveChecks.AllFlippableDirectionsCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.EmptyStateCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OthelloIntegrationTest {

    int numObjects = 1;
    int playerID = 1;
    int dir = 1;
    List<Integer> direction = new ArrayList<>(List.of(1));
    MoveCheck checkAllFlippableDirections = new AllFlippableDirectionsCheck();
    MoveCheck checkEmptyState = new EmptyStateCheck(0);
    List<MoveCheck> moveChecks = List.of(checkEmptyState, checkAllFlippableDirections);

    MoveType changeToNewState = new ChangeToNewStateMove();
    FlippableNeighborFinder allFlippableNeighbors = new FlippableNeighborFinder();
    ChangeOpponentPiecesMove changeOpponentPiecesMove = new ChangeOpponentPiecesMove(allFlippableNeighbors, false,0);
    List<MoveType> moveTypes = List.of(changeToNewState, changeOpponentPiecesMove);


    //PIECES FOR HORIZONTAL LEFT
    GamePiece horizLeft = new GamePiece(0, new Coordinate(2,3),1,direction,moveChecks,new ArrayList<>(), moveTypes);
    //horizontal neighbors
    GamePiece emptyHoriz_HL = new GamePiece(0, new Coordinate(2,0), 1,direction,moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerHoriz_HL = new GamePiece(1, new Coordinate(2,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppHoriz_HL = new GamePiece(2, new Coordinate(2,2), 1,direction, moveChecks, new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece empty1Vert_HL = new GamePiece(0, new Coordinate(0,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Vert_HL = new GamePiece(0, new Coordinate(1,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Vert_HL = new GamePiece(0, new Coordinate(3,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece empty1Diag_HL = new GamePiece(0, new Coordinate(0,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerDiag_HL = new GamePiece(1, new Coordinate(1,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Diag_HL = new GamePiece(0, new Coordinate(3,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);

    //test for horizontal left move
    @Test
    void testHorizontalLeftMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_HL, playerHoriz_HL, oppHoriz_HL,
                empty1Vert_HL, empty2Vert_HL, empty3Vert_HL,
                empty1Diag_HL, playerDiag_HL, empty2Diag_HL);
        List<Coordinate> possibleMoves = horizLeft.calculateAllPossibleMoves(neighbors,1);
        assertEquals(horizLeft.getPosition(), possibleMoves.get(0));

        horizLeft.makeMove(new Coordinate(2,3), neighbors, 1);
        //empty state should become player 1
        assertEquals(1, horizLeft.getState());
        //opponent piece to the left in the same row should also become 1
        assertEquals(1,oppHoriz_HL.getState());
    }

    //FOR HORIZONTAL RIGHT
    GamePiece horizRight = new GamePiece(0, new Coordinate(1,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //horizontal row
    GamePiece opponent1Horiz = new GamePiece(2, new Coordinate(1,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece myPiece1Horiz = new GamePiece(1, new Coordinate(1,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece emptySpotHoriz = new GamePiece(0, new Coordinate(1,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //vertical row
    GamePiece empty1Vert = new GamePiece(0, new Coordinate(0,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Vert = new GamePiece(0, new Coordinate(2,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Vert = new GamePiece(0, new Coordinate(3,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //diagonals
    GamePiece myPiece1Diag = new GamePiece(1, new Coordinate(2,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty1Diag = new GamePiece(0, new Coordinate(3,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Diag = new GamePiece(0, new Coordinate(0,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);

    //test for horizontal right move
    @Test
    void testHorizontalRightMove() {
        List<GamePiece> neighbors = List.of(opponent1Horiz, myPiece1Horiz, emptySpotHoriz,
                empty1Vert, empty2Vert, empty3Vert,
                myPiece1Diag, empty1Diag, empty2Diag);
        List<Coordinate> possibleMoves = horizRight.calculateAllPossibleMoves(neighbors,1);
        //this empty spot should be valid because there is an adjacent opponent piece between the empty spot
        //and the player piece in the same row
        assertEquals(new Coordinate(1,0),possibleMoves.get(0));

        horizRight.makeMove(new Coordinate(1,0),neighbors, 1);
        assertEquals(1, horizRight.getState());
        assertEquals(1, opponent1Horiz.getState());
    }

    //PIECES FOR VERTICAL TOP
    GamePiece vertTop = new GamePiece(0,new Coordinate(3,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece empty1Horiz_VT = new GamePiece(0, new Coordinate(3,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Horiz_VT = new GamePiece(0, new Coordinate(3,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Horiz_VT = new GamePiece(0, new Coordinate(3,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece empty1Vert_VT = new GamePiece(0, new Coordinate(0,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece player1Vert_VT = new GamePiece(1, new Coordinate(1,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece opponentVert_VT = new GamePiece(2, new Coordinate(2,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece empty1Diag_VT = new GamePiece(0, new Coordinate(1,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece player1Diag_VT = new GamePiece(1, new Coordinate(2,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Diag_VT = new GamePiece(0, new Coordinate(2,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);

    //test for vertical top move
    @Test
    void testVerticalTopMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VT, empty2Horiz_VT, empty3Horiz_VT,
                empty1Vert_VT, player1Vert_VT, opponentVert_VT,
                empty1Diag_VT, player1Diag_VT, empty2Diag_VT);
        List<Coordinate> verticalTopPossibleMoves = vertTop.calculateAllPossibleMoves(neighbors,1);
        assertEquals(vertTop.getPosition(), verticalTopPossibleMoves.get(0));

        vertTop.makeMove(new Coordinate(3,2), neighbors, 1);
        assertEquals(1, vertTop.getState());
        assertEquals(1, opponentVert_VT.getState());
    }

    //PIECES FOR VERTICAL BOTTOM
    GamePiece vertBottom = new GamePiece (0, new Coordinate(0,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece empty1Horiz_VB = new GamePiece(0, new Coordinate(0,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Horiz_VB = new GamePiece(0, new Coordinate(0,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Horiz_VB = new GamePiece(0, new Coordinate(0,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece opponentVert_VB = new GamePiece(2, new Coordinate(1,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerVert_VB = new GamePiece(1, new Coordinate(2,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece emptyVert_VB = new GamePiece(0, new Coordinate(3,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece playerDiag_VB = new GamePiece(1, new Coordinate(1,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty1Diag_VB = new GamePiece(0, new Coordinate(2,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Diag_VB = new GamePiece(0, new Coordinate(1,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);

    //test for vertical bottom move
    @Test
    void testVerticalBottomMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VB, empty2Horiz_VB, empty3Horiz_VB,
                opponentVert_VB, playerVert_VB, emptyVert_VB,
                playerDiag_VB, empty1Diag_VB, empty2Diag_VB);
        List<Coordinate> possibleMovesVertBottom = vertBottom.calculateAllPossibleMoves(neighbors,1);
        assertEquals(vertBottom.getPosition(), possibleMovesVertBottom.get(0));
        vertBottom.makeMove(new Coordinate(0,1), neighbors, 1);
        assertEquals(1, vertBottom.getState());
        assertEquals(1, opponentVert_VB.getState());
    }

    //PIECES FOR RIGHT DIAG PREV
    GamePiece rightDiagPrev = new GamePiece(0, new Coordinate(2,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece emptyHoriz_RDP = new GamePiece(0, new Coordinate(2,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerHoriz_RDP = new GamePiece(1, new Coordinate(2,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppHoriz_RDP = new GamePiece(2, new Coordinate(2,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece empty1Vert_RDP = new GamePiece(0, new Coordinate(0,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Vert_RDP = new GamePiece(0, new Coordinate(1,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Vert_RDP = new GamePiece(0, new Coordinate(3,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece playerDiag_RDP = new GamePiece(1, new Coordinate(0,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppDiag_RDP = new GamePiece(2, new Coordinate(1,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece emptyDiag_RDP = new GamePiece(0, new Coordinate(3,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);


    @Test
    void testRightDiagPrevMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_RDP, playerHoriz_RDP, oppHoriz_RDP,
                empty1Vert_RDP, empty2Vert_RDP, empty3Vert_RDP,
                playerDiag_RDP, oppDiag_RDP, emptyDiag_RDP);
        List<Coordinate> possibleMovesRDP = rightDiagPrev.calculateAllPossibleMoves(neighbors,1);
        assertEquals(rightDiagPrev.getPosition(), possibleMovesRDP.get(0));
        rightDiagPrev.makeMove(new Coordinate(2,3), neighbors, 1);
        assertEquals(1, rightDiagPrev.getState());
        //the adjacent opponent in the same ROW should also be turned to player piece
        assertEquals(1, oppHoriz_RDP.getState());
        //adjacent opponent on diagonal should become player piece
        assertEquals(1, oppDiag_RDP.getState());
    }

    //PIECES FOR RIGHT DIAG NEXT
    GamePiece rightDiagNext = new GamePiece(0, new Coordinate(3,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece empty1Horiz_RDN = new GamePiece(0, new Coordinate(3,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Horiz_RDN = new GamePiece(0, new Coordinate(3,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Horiz_RDN = new GamePiece(0, new Coordinate(3,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece emptyVert_RDN = new GamePiece(0, new Coordinate(0,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerVert_RDN = new GamePiece(1, new Coordinate(1,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppVert_RDN = new GamePiece(2, new Coordinate(2,2),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece playerDiag_RDN = new GamePiece(1, new Coordinate(1,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppDiag_RDN = new GamePiece(2, new Coordinate(2,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece emptyDiag_RDN = new GamePiece(0, new Coordinate(2,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);

    @Test
    void testRightDiagNextMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_RDN, empty2Horiz_RDN, empty3Horiz_RDN,
                emptyVert_RDN, playerVert_RDN, oppVert_RDN,
                playerDiag_RDN, oppDiag_RDN, emptyDiag_RDN);
        List<Coordinate> possibleMovesRDN = rightDiagNext.calculateAllPossibleMoves(neighbors,1);
        assertEquals(rightDiagNext.getPosition(), possibleMovesRDN.get(0));

        rightDiagNext.makeMove(new Coordinate(3,2), neighbors, 1);
        assertEquals(1, rightDiagNext.getState());
        assertEquals(1, oppDiag_RDN.getState());
        assertEquals(1, oppVert_RDN.getState());
    }

    //PIECES FOR LEFT DIAG NEXT
    GamePiece leftDiagNext = new GamePiece(0, new Coordinate(0,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece emptyHoriz_LDN = new GamePiece(0, new Coordinate(0,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerHoriz_LDN = new GamePiece(1, new Coordinate(0,1),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppHoriz_LDN = new GamePiece(2, new Coordinate(0,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece empty1Vert_LDN = new GamePiece(0, new Coordinate(1,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Vert_LDN = new GamePiece(0, new Coordinate(2,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Vert_LDN = new GamePiece(0, new Coordinate(3,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece oppDiag_LDN = new GamePiece(2, new Coordinate(1,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerDiag_LDN = new GamePiece(1, new Coordinate(2,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece emptyDiag_LDN = new GamePiece(0, new Coordinate(3,0),1,direction,  moveChecks,new ArrayList<>(),moveTypes);

    @Test
    void testLeftDiagNextMove() {
        List<GamePiece> neighbors = List.of(emptyHoriz_LDN, playerHoriz_LDN, oppHoriz_LDN,
                empty1Vert_LDN, empty2Vert_LDN, empty3Vert_LDN,
                oppDiag_LDN, playerDiag_LDN, emptyDiag_LDN);
        List<Coordinate> possibleMovesLDN = leftDiagNext.calculateAllPossibleMoves(neighbors,1);
        assertEquals(leftDiagNext.getPosition(), possibleMovesLDN.get(0));

        leftDiagNext.makeMove(new Coordinate(0,3), neighbors, 1);
        assertEquals(1, leftDiagNext.getState());
        assertEquals(1, oppHoriz_LDN.getState());
        assertEquals(1, oppDiag_LDN.getState());
    }

    //PIECES FOR LEFT DIAGONAL PREV MOVE
    GamePiece leftDiagPrev = new GamePiece(0, new Coordinate(3,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //horizontal neighbors
    GamePiece empty1Horiz_LDP = new GamePiece(0, new Coordinate(3,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty2Horiz_LDP = new GamePiece(0, new Coordinate(3,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece empty3Horiz_LDP = new GamePiece(0, new Coordinate(3,3),1,direction,  moveChecks,new ArrayList<>(),moveTypes);
    //vertical neighbors
    GamePiece emptyVert_LDP = new GamePiece(0, new Coordinate(0,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerVert_LDP = new GamePiece(1, new Coordinate(1,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppVert_LDP = new GamePiece(2, new Coordinate(2,0),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    //diagonal neighbors
    GamePiece emptyDiag_LDP = new GamePiece(0, new Coordinate(0,3),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece playerDiag_LDP = new GamePiece(1, new Coordinate(1,2),1,direction, moveChecks,new ArrayList<>(),moveTypes);
    GamePiece oppDiag_LDP = new GamePiece(2, new Coordinate(2,1),1,direction, moveChecks,new ArrayList<>(),moveTypes);

    @Test
    void testLeftDiagPrevMove() {
        List<GamePiece> neighbors = List.of(empty1Horiz_LDP, empty2Horiz_LDP, empty3Horiz_LDP,
                emptyVert_LDP, playerVert_LDP, oppVert_LDP,
                emptyDiag_LDP, playerDiag_LDP, oppDiag_LDP);
        List<Coordinate> possibleMovesLDP = leftDiagPrev.calculateAllPossibleMoves(neighbors,1);
        assertEquals(leftDiagPrev.getPosition(), possibleMovesLDP.get(0));

        leftDiagPrev.makeMove(new Coordinate(3,0), neighbors, 1);
        assertEquals(1, leftDiagPrev.getState());
        assertEquals(1, oppVert_LDP.getState());
        assertEquals(1, oppDiag_LDP.getState());

    }













}
