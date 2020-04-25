package ooga.model.engine.pieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.moveChecks.MoveCheck;
import ooga.model.engine.pieces.ChangeToNewStateMove;
import ooga.model.engine.pieces.moveChecks.EmptyStateCheck;
import ooga.model.engine.pieces.MoveType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeIntegrationTest {

    List<Integer> posDirection = new ArrayList<>(List.of(0));
    MoveCheck checkEmptyState = new EmptyStateCheck(0);
    List<MoveCheck> moveChecks = List.of(checkEmptyState);

    MoveType changeToNewState = new ChangeToNewStateMove();
    List<MoveType> moveTypes = List.of(changeToNewState);

    Coordinate emptyCoord = new Coordinate(1,1);
    Coordinate emptyCoord2 = new Coordinate(2,2);
    Coordinate xCoord = new Coordinate(0,0);
    Coordinate oCoord = new Coordinate(0,2);
    GamePiece emptyPiece1 = new GamePiece(0, emptyCoord,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece emptyPiece2 = new GamePiece(0, emptyCoord2,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);

    GamePiece xPiece = new GamePiece(1, xCoord,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece oPiece = new GamePiece(2, oCoord,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    List<GamePiece> dummyNeighbors = List.of(xPiece, oPiece);

    @Test
    void testCalculateAllPossibleMoves() {
        // possible moves for empty cell
        List<Coordinate> possibleMoves = emptyPiece1.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(emptyCoord), possibleMoves);

        // possible moves for occupied cell
        List<Coordinate> noPossibleMoves = xPiece.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(), noPossibleMoves);
    }

    @Test
    void testMakeMove() {
        List<GamePiece> dummyNeighbors = new ArrayList<>();
        int newState = 1;
        emptyPiece1.makeMove(new Coordinate(1,1), dummyNeighbors, newState);
        assertEquals(new Coordinate(1,1), emptyPiece1.getPosition());
        assertEquals(newState, emptyPiece1.getState());
        emptyPiece2.makeMove(new Coordinate(2,2), dummyNeighbors, newState);
        assertEquals(new Coordinate(2,2), emptyPiece2.getPosition());
        assertEquals(newState, emptyPiece2.getState());
    }


    @Test
    void testGetState() {
        assertEquals(0, emptyPiece1.getState());
        assertEquals(1, xPiece.getState());
        assertEquals(2, oPiece.getState());
    }

    @Test
    void testGetPosition() {
        assertEquals(emptyCoord, emptyPiece1.getPosition());
        assertEquals(xCoord, xPiece.getPosition());
        assertEquals(oCoord, oPiece.getPosition());
    }
}
