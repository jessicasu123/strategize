package ooga.model.engine.pieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.*;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.NeighborAtEndCoordinateFinder;
import ooga.model.engine.pieces.moveChecks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChopsticksIntegrationTest {
    GamePiece topSquare1;
    GamePiece topSquare2;
    GamePiece bottomSquare1;
    GamePiece bottomSquare2;
    List<GamePiece> allPieces;

    Coordinate top1Coord = new Coordinate(0,0);
    Coordinate top2Coord = new Coordinate(0,1);
    Coordinate bottom1Coord = new Coordinate(1,0);
    Coordinate bottom2Coord = new Coordinate(1,1);

    List<Integer> player1States = new ArrayList<>(List.of(1));
    List<Integer> player1Direction = new ArrayList<>(List.of(-1));
    List<Integer> player2States = new ArrayList<>(List.of(2));
    List<Integer> player2Direction = new ArrayList<>(List.of(1));
    ConvertibleNeighborFinder neighborFinder = new NeighborAtEndCoordinateFinder();

    MoveCheck ownPiecePlayer1 = new OwnPieceCheck(player1States);
    MoveCheck ownPiecePlayer2 = new OwnPieceCheck(player2States);
    MoveCheck ownNumObjects = new NumObjectsCheck(0);
    MoveCheck checkOpponentPiecePlayer1 = new OpponentPieceCheck(0, player1States);
    MoveCheck checkOpponentPiecePlayer2 = new OpponentPieceCheck(0, player2States);

    MoveType splitMove = new SplitObjectsMove(neighborFinder, 0);
    MoveType changeNeighborObjectsMove = new ChangeNeighborObjectsMove(neighborFinder, true);
    MoveType pieceAtMaxObjectsMove = new PieceAtMaxObjectsMove(4, 0, neighborFinder);

    List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1, ownNumObjects));
    List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2, ownNumObjects));
    List<MoveCheck> neighborMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1, checkOpponentPiecePlayer1));
    List<MoveCheck> neighborMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2, checkOpponentPiecePlayer2));
    List<MoveType> moveTypes = new ArrayList<>(List.of(splitMove, changeNeighborObjectsMove, pieceAtMaxObjectsMove));


    @BeforeEach
    void setUp() {
        topSquare1 = new GamePiece(2,top1Coord,1,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypes);
        topSquare2 = new GamePiece(2,top2Coord,1,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2, moveTypes);
        bottomSquare1 = new GamePiece(1,bottom1Coord,1,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypes);
        bottomSquare2 = new GamePiece(1,bottom2Coord,1,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1, moveTypes);
        allPieces = new ArrayList<>(List.of(topSquare1, topSquare2, bottomSquare1, bottomSquare2));
    }

    @Test
    void calculateAllPossibleMovesTop() {
        //simple test
        List<Coordinate> expectedMoves = new ArrayList<>(List.of(top2Coord, bottom1Coord, bottom2Coord));
        allPieces.remove(topSquare1);
        assertEquals(expectedMoves, topSquare1.calculateAllPossibleMoves(allPieces,2));

        //test empty state not added
        GamePiece dummyPiece = new GamePiece(0, top1Coord, 0, player1Direction, selfMoveCheckPlayer1, neighborMoveCheckPlayer1, moveTypes);
        expectedMoves = new ArrayList<>(List.of(bottom1Coord, bottom2Coord));
        allPieces.add(dummyPiece);
        allPieces.remove(topSquare2);
        assertEquals(expectedMoves, topSquare2.calculateAllPossibleMoves(allPieces,2));
        allPieces.remove(dummyPiece);
        allPieces.add(topSquare1);
        allPieces.add(topSquare2);

        //test for not own piece, no objects
        expectedMoves = new ArrayList<>(List.of());
        assertEquals(expectedMoves, topSquare1.calculateAllPossibleMoves(allPieces, 1));
        assertEquals(expectedMoves, dummyPiece.calculateAllPossibleMoves(allPieces, 0));
    }


    @Test
    void calculateAllPossibleMovesBottom() {
        //simple test
        List<Coordinate> expectedMoves = new ArrayList<>(List.of(top1Coord, top2Coord, bottom2Coord));
        allPieces.remove(bottomSquare1);
        assertEquals(expectedMoves, bottomSquare1.calculateAllPossibleMoves(allPieces,1));

        //test for not own piece
        expectedMoves = new ArrayList<>(List.of());
        assertEquals(expectedMoves, bottomSquare1.calculateAllPossibleMoves(allPieces, 2));
    }

    @Test
    void makeSplitMove() {

    }

    @Test
    void makeMoveTop() {
        //test split to 0 objects, empty state
        allPieces.remove(topSquare1);
        topSquare1.makeMove(top2Coord, allPieces,2);
        assertEquals(0, topSquare1.getState());
        assertEquals(0, topSquare1.getNumObjects());
        assertEquals(2, topSquare2.getNumObjects());

        //test opponent attack move
        allPieces.add(topSquare1);
        topSquare2.makeMove(bottom1Coord, allPieces, 2);
        assertEquals(3, bottomSquare1.getNumObjects());
        assertEquals(2, topSquare2.getNumObjects());
    }

    @Test
    void makeMoveBottom() {
        allPieces.remove(bottomSquare1);
        bottomSquare1.makeMove(top2Coord, allPieces,1);
        assertEquals(2, topSquare2.getNumObjects());
        assertEquals(1, bottomSquare1.getNumObjects());
        bottomSquare1.makeMove(bottom2Coord, allPieces, 1);
        assertEquals(2, bottomSquare2.getNumObjects());
        assertEquals(0, bottomSquare1.getNumObjects());
        assertEquals(0, bottomSquare1.getState());
    }

}
