package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AllFlippableDirectionsCheckTest {

    int state = 1;
    List<Integer> direction = List.of(1);

    AllFlippableDirectionsCheck allFlippableDirectionsCheck = new AllFlippableDirectionsCheck();
    /**
     * A lot of the checking opponent flippability for all eight directions
     * is tested in the FindAllFlippableDirections. This will validate that
     * the AllFlippableDirectionsCheck object can distinguish when at least
     * one direction is flippable and when there are NO flippable directions.
     */

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


    //There should be a possible move in the vertical top direction because there is an opponent piece directly above
    //and a player piece directly above that one.
    @Test
    void testPossibleFlippableDirection() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VT, empty2Horiz_VT, empty3Horiz_VT,
                empty1Vert_VT, player1Vert_VT, opponentVert_VT,
                empty1Diag_VT, player1Diag_VT, empty2Diag_VT);
        assertEquals(true,
                allFlippableDirectionsCheck.isConditionMet(new Coordinate(3,2),
                        vertTop, neighbors, state, direction));
    }


    //now if the opponent in the vertical top direction is changed to a player, there is no longer a possible move line.
    //there is no adjacent opponent between an empty spot and another player - its just two players in a row.
    GamePiece player2Vert_VT = new GamePiece(1, new Coordinate(2,2),1,direction,  new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

    @Test
    void testNoPossibleFlippableDirections() {
        List<GamePiece> neighbors = List.of(empty1Horiz_VT, empty2Horiz_VT, empty3Horiz_VT,
                empty1Vert_VT, player1Vert_VT, player2Vert_VT,
                empty1Diag_VT, player1Diag_VT, empty2Diag_VT);
        assertEquals(false,
                allFlippableDirectionsCheck.isConditionMet(new Coordinate(3,2),
                        vertTop, neighbors, state, direction));

    }

}
