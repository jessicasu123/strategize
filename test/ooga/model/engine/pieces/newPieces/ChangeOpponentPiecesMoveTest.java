package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FlippableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.NeighborsBetweenCoordinatesFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeOpponentPiecesMoveTest {


    //TEST FOR CHANGING OPPONENTS TO PLAYER STATES IN MULTIPLE DIRECTIONS (EX. OTHELLO)
    @Test
    void testChangeOpponentPiecesInMultipleDirections() {
        int playerID = 1;
        int numObjects = 1;
        int dir = 1;
        List<Integer> direction = List.of(1);

        //test for changing opponent pieces with a flippable neighbors from multiple directions
        FlippableNeighborFinder allFlippableNeighbors = new FlippableNeighborFinder();
        ChangeOpponentPiecesMove changeOpponentPiecesMove = new ChangeOpponentPiecesMove(allFlippableNeighbors, false,0);

        GamePiece rightDiagNext = new GamePiece(0, new Coordinate(3,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        //horizontal neighbors
        GamePiece empty1Horiz_RDN = new GamePiece(0, new Coordinate(3,0),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece empty2Horiz_RDN = new GamePiece(0, new Coordinate(3,1),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece empty3Horiz_RDN = new GamePiece(0, new Coordinate(3,3),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        //vertical neighbors
        GamePiece emptyVert_RDN = new GamePiece(0, new Coordinate(0,2),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece playerVert_RDN = new GamePiece(1, new Coordinate(1,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece oppVert_RDN = new GamePiece(2, new Coordinate(2,2),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        //diagonal neighbors
        GamePiece playerDiag_RDN = new GamePiece(1, new Coordinate(1,0),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece oppDiag_RDN = new GamePiece(2, new Coordinate(2,1),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        GamePiece emptyDiag_RDN = new GamePiece(0, new Coordinate(2,3),1,direction, new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

        Coordinate endCoord = new Coordinate(3,2);

        List<GamePiece> neighbors = List.of(empty1Horiz_RDN, empty2Horiz_RDN, empty3Horiz_RDN,
                emptyVert_RDN, playerVert_RDN, oppVert_RDN,
                playerDiag_RDN, oppDiag_RDN, emptyDiag_RDN);
        //should change neighbor to current player state in the diagonal direction (right diagonal next) AND the adjacent opponent in same column
        changeOpponentPiecesMove.completeMoveType(rightDiagNext, endCoord, neighbors, playerID, dir);
        assertEquals(1, oppDiag_RDN.getState());
        assertEquals(1, oppVert_RDN.getState());
    }

    //TEST FOR TURNING OPPONENTS TO EMPTY (EX. CHECKERS)
    NeighborsBetweenCoordinatesFinder neighborsBetweenCoordinatesFinder = new NeighborsBetweenCoordinatesFinder();
    ChangeOpponentPiecesMove changeOpponentPiecesMove = new ChangeOpponentPiecesMove(neighborsBetweenCoordinatesFinder, true, 0);

    GamePiece checker1Red = new GamePiece(1, new Coordinate(0,0), 1,List.of(1), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker2Black = new GamePiece(3,new Coordinate(1,1),1,List.of(-1),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    GamePiece checker3Empty = new GamePiece(0, new Coordinate(2,2), 1,List.of(0), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker4Red = new GamePiece(1, new Coordinate(3,3), 1,List.of(1),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker12Empty = new GamePiece(0, new Coordinate(1,3),1, List.of(0), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker4Black = new GamePiece(3, new Coordinate(3,3), 1,List.of(-1),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker5Empty = new GamePiece(0, new Coordinate(4,4), 1,List.of(0), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    @Test
    void testChangeToEmptyForPlayerPiece() {
        int playerID = 1;
        int dir = 1;
        Coordinate endCoord = new Coordinate(2,2);
        GamePiece currPiece = checker1Red;

        //jump - the neighbors in the middle should become empty
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Red, checker12Empty));
        changeOpponentPiecesMove.completeMoveType(currPiece, endCoord, neighbors, playerID, dir);
        assertEquals(0, checker2Black.getState());

        //double jump
        endCoord = new Coordinate(4,4);
        neighbors = new ArrayList<>(List.of(checker2Black, checker3Empty, checker4Black, checker5Empty));
        changeOpponentPiecesMove.completeMoveType(currPiece, endCoord, neighbors, playerID, dir);
        assertEquals(0, checker2Black.getState());
        assertEquals(0, checker4Black.getState());
    }

    GamePiece checker5Black = new GamePiece(3, new Coordinate(4,4),1, List.of(-1),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker2Empty = new GamePiece(0, new Coordinate(1,1),1, List.of(0),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker1Black = new GamePiece(3, new Coordinate(0,0), 1,List.of(-1), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker1Empty = new GamePiece(0, new Coordinate(0,0), 1,List.of(0),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece checker2Red = new GamePiece(1, new Coordinate(1,1),1, List.of(1),  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    @Test
    void testChangeToEmptyForOpponentPiece() {
        int playerID = 3;
        int dir = -1;
        Coordinate endCoord = new Coordinate(2,2);
        GamePiece currPiece = checker5Black;

        //jump
        List<GamePiece> neighbors = new ArrayList<>(List.of(checker2Empty, checker3Empty, checker4Red, checker1Black));
        changeOpponentPiecesMove.completeMoveType(currPiece, endCoord, neighbors, playerID, dir);
        assertEquals(0, checker4Red.getState());

        //double jump
        endCoord = new Coordinate(0,0);
        neighbors = new ArrayList<>(List.of(checker1Empty, checker2Red, checker3Empty, checker4Red, checker1Black));
        changeOpponentPiecesMove.completeMoveType(currPiece, endCoord, neighbors, playerID, dir);
        assertEquals(0, checker4Red.getState());
        assertEquals(0, checker2Red.getState());
    }

}
