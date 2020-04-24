package ooga.model.engine.pieces.newPieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.ConnectFourGamePiece;
import ooga.model.engine.pieces.newPieces.ChangeToNewStateMove;
import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.MoveChecks.BelowCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.EmptyStateCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class Connect4IntegrationTest {

    List<Integer> posDirection = new ArrayList<>(List.of(0));
    MoveCheck checkEmptyState = new EmptyStateCheck(0);
    MoveCheck checkBelow = new BelowCheck();
    List<MoveCheck> moveChecks = List.of(checkEmptyState, checkBelow);

    MoveType changeToNewState = new ChangeToNewStateMove();
    List<MoveType> moveTypes = List.of(changeToNewState);


    Coordinate opponentcoord1 = new Coordinate(5,2);
    Coordinate opponentcoord2 = new Coordinate(5,2);

    Coordinate usercoord1 = new Coordinate(5,3);
    Coordinate usercoord2 = new Coordinate(4,3);

    GamePiece userPiece1 = new GamePiece(1, usercoord1,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece userPiece2 = new GamePiece(1, usercoord2,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);

    GamePiece opponent1 = new GamePiece(2,opponentcoord1,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece opponent2 = new GamePiece(2,opponentcoord2,1,posDirection,moveChecks,new ArrayList<>(), moveTypes);

    GamePiece neighbor = new GamePiece(1,new Coordinate(0,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece neighbor2 = new GamePiece(1,new Coordinate(1,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece neighbor3 = new GamePiece(1,new Coordinate(2,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece neighbor4 = new GamePiece(1,new Coordinate(3,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece neighbor5 = new GamePiece(1,new Coordinate(4,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    GamePiece neighbor6 = new GamePiece(1,new Coordinate(5,3),1,posDirection,moveChecks,new ArrayList<>(), moveTypes);
    List<GamePiece> dummyNeighbors = List.of(neighbor, neighbor2, neighbor3, neighbor4, neighbor5, neighbor6);

    @Test
    void testAllPossibleMoves(){

        List<Coordinate> coords = new ArrayList<>();
        assertEquals(coords, userPiece1.calculateAllPossibleMoves(dummyNeighbors,1));

        dummyNeighbors = List.of(neighbor6);
        List<Coordinate> possibleMoves = userPiece2.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(coords, possibleMoves);
    }

    @Test
    void testMakeMove() {
        int newState = 3;
        dummyNeighbors = List.of();
        userPiece1.makeMove(new Coordinate(5,1), dummyNeighbors, newState);
        assertEquals(newState, userPiece1.getState());
        userPiece2.makeMove(new Coordinate(5,3),dummyNeighbors,newState);
        assertEquals(newState,userPiece2.getState());
    }

    @Test
    void testgetState() {
        assertEquals(1, userPiece1.getState());
    }

    @Test
    void testGetPosition() {
        assertEquals(usercoord1, userPiece1.getPosition());
    }

}
