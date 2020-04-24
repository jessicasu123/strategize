package ooga.model.engine.pieces.newPieces.IntegrationTests;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.ConnectFourGamePiece;
import ooga.model.engine.pieces.newPieces.GamePiece;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class Connect4IntegrationTest {

    List<Integer> posDirection = new ArrayList<>(List.of(1));
    Coordinate opponentcoord1 = new Coordinate(5,2);
    Coordinate opponentcoord2 = new Coordinate(5,2);

    Coordinate usercoord1 = new Coordinate(5,3);
    Coordinate usercoord2 = new Coordinate(4,3);

    GamePiece userPiece1 = new GamePiece(1, usercoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece userPiece2 = new GamePiece(1, usercoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece opponent1 = new GamePiece(2,opponentcoord1,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece opponent2 = new GamePiece(2,opponentcoord2,1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    GamePiece neighbor = new GamePiece(1,new Coordinate(0,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece neighbor2 = new GamePiece(1,new Coordinate(1,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece neighbor3 = new GamePiece(1,new Coordinate(2,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece neighbor4 = new GamePiece(1,new Coordinate(3,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    GamePiece neighbor5 = new GamePiece(1,new Coordinate(4,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
    List<GamePiece> dummyNeighbors = List.of(neighbor, neighbor2, neighbor3, neighbor4, neighbor5);
    GamePiece neighbor6 = new GamePiece(1,new Coordinate(5,3),1,posDirection,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testAllPossibleMoves(){
        List<Coordinate> coords = new ArrayList<>();
        coords.add(usercoord1);
        assertEquals(coords, userPiece1.calculateAllPossibleMoves(dummyNeighbors,1));

        dummyNeighbors = List.of(neighbor, neighbor2, neighbor3, neighbor4, neighbor6);
        List<Coordinate> possibleMoves = userPiece2.calculateAllPossibleMoves(dummyNeighbors, 1);
        assertEquals(List.of(usercoord2), possibleMoves);
    }

    @Test
    void testMakeMove() {
        Coordinate endCoordinate = new Coordinate(5,3);
        int newState = 1;
        userPiece1.makeMove(endCoordinate,dummyNeighbors,newState);
        assertEquals(endCoordinate, userPiece1.getPosition());
        assertEquals(newState,userPiece1.getState());
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
