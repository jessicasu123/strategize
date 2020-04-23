package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FindAllFlippableNeighborsTest {
    FindAllFlippableNeighbors flippableNeighbors = new FindAllFlippableNeighbors();

    int numObjects = 1;
    int playerID = 1;
    int dir = 1;
    List<Integer> direction = new ArrayList<>(List.of(1));

    //PIECES FOR HORIZONTAL LEFT
    GamePiece horizLeft = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece emptyHoriz_HL = new GamePiece(0, new Coordinate(2,0), 1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerHoriz_HL = new GamePiece(1, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppHoriz_HL = new GamePiece(2, new Coordinate(2,2), 1,direction, new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
    //vertical neighbors
    GamePiece empty1Vert_HL = new GamePiece(0, new Coordinate(0,3),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Vert_HL = new GamePiece(0, new Coordinate(1,3),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Vert_HL = new GamePiece(0, new Coordinate(3,3),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece empty1Diag_HL = new GamePiece(0, new Coordinate(0,1),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerDiag_HL = new GamePiece(1, new Coordinate(1,2),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Diag_HL = new GamePiece(0, new Coordinate(3,2),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testHorizontalLeftFlippable() {
        Coordinate currCoordinate = new Coordinate(2,3);
        Coordinate endCoordinate = new Coordinate(2,3);

        List<GamePiece> neighbors = List.of(emptyHoriz_HL, playerHoriz_HL, oppHoriz_HL,
                empty1Vert_HL, empty2Vert_HL, empty3Vert_HL,
                empty1Diag_HL, playerDiag_HL, empty2Diag_HL);
        assertEquals(oppHoriz_HL,
                flippableNeighbors.findNeighborsToConvert(currCoordinate, endCoordinate, numObjects, playerID, dir, neighbors).get(0));
    }

    //FOR HORIZONTAL RIGHT
    GamePiece horizRight = new GamePiece(0, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal row
    GamePiece opponent1Horiz = new GamePiece(2, new Coordinate(1,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece myPiece1Horiz = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece emptySpotHoriz = new GamePiece(0, new Coordinate(1,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical row
    GamePiece empty1Vert = new GamePiece(0, new Coordinate(0,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Vert = new GamePiece(0, new Coordinate(2,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Vert = new GamePiece(0, new Coordinate(3,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonals
    GamePiece myPiece1Diag = new GamePiece(1, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty1Diag = new GamePiece(0, new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Diag = new GamePiece(0, new Coordinate(0,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());


    @Test
    void testHorizontalRightFlippable() {
        Coordinate currCoordinate = new Coordinate(1,0);
        Coordinate endCoord = currCoordinate;
        List<GamePiece> neighbors = List.of(opponent1Horiz, myPiece1Horiz, emptySpotHoriz,
                empty1Vert, empty2Vert, empty3Vert,
                myPiece1Diag, empty1Diag, empty2Diag);
        List<GamePiece> neighborsToFlip = flippableNeighbors.findNeighborsToConvert(currCoordinate, endCoord, numObjects, playerID, dir, neighbors);
        assertEquals(opponent1Horiz, neighborsToFlip.get(0));
    }

    //PIECES FOR VERTICAL TOP
    GamePiece vertTop = new GamePiece(0,new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece empty1Horiz_VT = new GamePiece(0, new Coordinate(3,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Horiz_VT = new GamePiece(0, new Coordinate(3,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Horiz_VT = new GamePiece(0, new Coordinate(3,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece empty1Vert_VT = new GamePiece(0, new Coordinate(0,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player1Vert_VT = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece opponentVert_VT = new GamePiece(2, new Coordinate(2,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece empty1Diag_VT = new GamePiece(0, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece player1Diag_VT = new GamePiece(1, new Coordinate(2,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Diag_VT = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testVerticalTopFlippable() {
        Coordinate currCoord = new Coordinate(3,2);
        Coordinate endCoord = currCoord;

        List<GamePiece> neighbors = List.of(empty1Horiz_VT, empty2Horiz_VT, empty3Horiz_VT,
                empty1Vert_VT, player1Vert_VT, opponentVert_VT,
                empty1Diag_VT, player1Diag_VT, empty2Diag_VT);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord, numObjects, playerID, dir,
                neighbors);
        assertEquals(opponentVert_VT, neighborsToConvert.get(0));
    }

    //PIECES FOR VERTICAL BOTTOM
    GamePiece vertBottom = new GamePiece (0, new Coordinate(0,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece empty1Horiz_VB = new GamePiece(0, new Coordinate(0,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Horiz_VB = new GamePiece(0, new Coordinate(0,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Horiz_VB = new GamePiece(0, new Coordinate(0,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece opponentVert_VB = new GamePiece(2, new Coordinate(1,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerVert_VB = new GamePiece(1, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece emptyVert_VB = new GamePiece(0, new Coordinate(3,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece playerDiag_VB = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty1Diag_VB = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Diag_VB = new GamePiece(0, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testVertBottomFlippable() {
        Coordinate currCoord = new Coordinate(0,1);
        Coordinate endCoord = currCoord;
        List<GamePiece> neighbors = List.of(empty1Horiz_VB, empty2Horiz_VB, empty3Horiz_VB,
                opponentVert_VB, playerVert_VB, emptyVert_VB,
                playerDiag_VB, empty1Diag_VB, empty2Diag_VB);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord, numObjects, playerID, dir, neighbors);
        assertEquals(opponentVert_VB, neighborsToConvert.get(0));
    }

    //PIECES FOR RIGHT DIAG PREV
    GamePiece rightDiagPrev = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece emptyHoriz_RDP = new GamePiece(0, new Coordinate(2,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerHoriz_RDP = new GamePiece(1, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppHoriz_RDP = new GamePiece(2, new Coordinate(2,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece empty1Vert_RDP = new GamePiece(0, new Coordinate(0,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Vert_RDP = new GamePiece(0, new Coordinate(1,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Vert_RDP = new GamePiece(0, new Coordinate(3,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece playerDiag_RDP = new GamePiece(1, new Coordinate(0,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppDiag_RDP = new GamePiece(2, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece emptyDiag_RDP = new GamePiece(0, new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testRightDiagPrevFlippable() {
        Coordinate currCoord = new Coordinate(2,3);
        Coordinate endCoord = currCoord;
        List<GamePiece> neighbors = List.of(emptyHoriz_RDP, playerHoriz_RDP, oppHoriz_RDP,
                empty1Vert_RDP, empty2Vert_RDP, empty3Vert_RDP,
                playerDiag_RDP, oppDiag_RDP, emptyDiag_RDP);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord,
                numObjects, playerID, dir, neighbors);
        //the opponent in same ROW should be converted
        assertTrue(neighborsToConvert.contains(oppHoriz_RDP));
        //adjacent opponent on diagonal should also be converted
        assertTrue(neighborsToConvert.contains(oppDiag_RDP));
    }

    //PIECES FOR RIGHT DIAG NEXT
    GamePiece rightDiagNext = new GamePiece(0, new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece empty1Horiz_RDN = new GamePiece(0, new Coordinate(3,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Horiz_RDN = new GamePiece(0, new Coordinate(3,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Horiz_RDN = new GamePiece(0, new Coordinate(3,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece emptyVert_RDN = new GamePiece(0, new Coordinate(0,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerVert_RDN = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppVert_RDN = new GamePiece(2, new Coordinate(2,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece playerDiag_RDN = new GamePiece(1, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppDiag_RDN = new GamePiece(2, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece emptyDiag_RDN = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testRightDiagNextFlippable() {
        Coordinate currCoord = new Coordinate(3,2);
        Coordinate endCoord = currCoord;
        List<GamePiece> neighbors = List.of(empty1Horiz_RDN, empty2Horiz_RDN, empty3Horiz_RDN,
                emptyVert_RDN, playerVert_RDN, oppVert_RDN,
                playerDiag_RDN, oppDiag_RDN, emptyDiag_RDN);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord,
                numObjects, playerID, dir, neighbors);
        //should have the adjacent opponent in right diag
        assertTrue(neighborsToConvert.contains(oppDiag_RDN));
        //should also have the adjacent opponent in the same column
        assertTrue(neighborsToConvert.contains(oppVert_RDN));
    }

    //PIECES FOR LEFT DIAG NEXT
    GamePiece leftDiagNext = new GamePiece(0, new Coordinate(0,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece emptyHoriz_LDN = new GamePiece(0, new Coordinate(0,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerHoriz_LDN = new GamePiece(1, new Coordinate(0,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppHoriz_LDN = new GamePiece(2, new Coordinate(0,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece empty1Vert_LDN = new GamePiece(0, new Coordinate(1,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Vert_LDN = new GamePiece(0, new Coordinate(2,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Vert_LDN = new GamePiece(0, new Coordinate(3,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece oppDiag_LDN = new GamePiece(2, new Coordinate(1,2),1,direction,  new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
    GamePiece playerDiag_LDN = new GamePiece(1, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece emptyDiag_LDN = new GamePiece(0, new Coordinate(3,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testLeftDiagNextFlippable() {
        Coordinate currCoord = new Coordinate(0,3);
        Coordinate endCoord = currCoord;
        List<GamePiece> neighbors = List.of(emptyHoriz_LDN, playerHoriz_LDN, oppHoriz_LDN,
                empty1Vert_LDN, empty2Vert_LDN, empty3Vert_LDN,
                oppDiag_LDN, playerDiag_LDN, emptyDiag_LDN);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord,
                numObjects, playerID, dir, neighbors);
        //should have both horizontal and diagonal neighbors to convert
        assertTrue(neighborsToConvert.contains(oppHoriz_LDN));
        assertTrue(neighborsToConvert.contains(oppDiag_LDN));
    }

    //PIECES FOR LEFT DIAGONAL PREV MOVE
    GamePiece leftDiagPrev = new GamePiece(0, new Coordinate(3,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //horizontal neighbors
    GamePiece empty1Horiz_LDP = new GamePiece(0, new Coordinate(3,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty2Horiz_LDP = new GamePiece(0, new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece empty3Horiz_LDP = new GamePiece(0, new Coordinate(3,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //vertical neighbors
    GamePiece emptyVert_LDP = new GamePiece(0, new Coordinate(0,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerVert_LDP = new GamePiece(1, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppVert_LDP = new GamePiece(2, new Coordinate(2,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    //diagonal neighbors
    GamePiece emptyDiag_LDP = new GamePiece(0, new Coordinate(0,3),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece playerDiag_LDP = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece oppDiag_LDP = new GamePiece(2, new Coordinate(2,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testLeftDiagPrevFlippable() {
        Coordinate currCoord = new Coordinate(3,0);
        Coordinate endCoord = currCoord;
        List<GamePiece> neighbors = List.of(empty1Horiz_LDP, empty2Horiz_LDP, empty3Horiz_LDP,
                emptyVert_LDP, playerVert_LDP, oppVert_LDP,
                emptyDiag_LDP, playerDiag_LDP, oppDiag_LDP);
        List<GamePiece> neighborsToConvert = flippableNeighbors.findNeighborsToConvert(currCoord, endCoord,
                numObjects, playerID, dir, neighbors);

        //should have both the vertical and diagonal neighbors
        assertTrue(neighborsToConvert.contains(oppVert_LDP));
        assertTrue(neighborsToConvert.contains(oppDiag_LDP));

    }









}
