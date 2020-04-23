package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Player.Player;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.FindAllFlippableNeighbors;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckAllFlippableDirections;
import ooga.model.engine.pieces.newPieces.MoveChecks.CheckEmptyState;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OthelloIntegrationTest {

    int numObjects = 1;
    int playerID = 1;
    int dir = 1;
    List<Integer> direction = new ArrayList<>(List.of(1));
    MoveCheck checkAllFlippableDirections = new CheckAllFlippableDirections();
    MoveCheck checkEmptyState = new CheckEmptyState(0);
    List<MoveCheck> moveChecks = List.of(checkEmptyState, checkAllFlippableDirections);

    MoveType changeToNewState = new ChangeToNewState();
    FindAllFlippableNeighbors allFlippableNeighbors = new FindAllFlippableNeighbors();
    ChangeOpponentPieces changeOpponentPieces = new ChangeOpponentPieces(allFlippableNeighbors, false,0);
    List<MoveType> moveTypes = List.of(changeToNewState, changeOpponentPieces);


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


}
