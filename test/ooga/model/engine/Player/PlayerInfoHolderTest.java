package ooga.model.engine.Player;

import ooga.model.engine.pieces.newPieces.ChangeOpponentPiecesMove;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.NeighborsBetweenCoordinatesFinder;
import ooga.model.engine.pieces.newPieces.MoveChecks.JumpCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.OwnPieceCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.StepCheck;
import ooga.model.engine.pieces.newPieces.MoveType;
import ooga.model.engine.pieces.newPieces.PositionMove;
import ooga.model.engine.pieces.newPieces.PromotionMove;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInfoHolderTest {
    List<Integer> player1 = new ArrayList<>(List.of(1,3));
    List<Integer> player1Direction = new ArrayList<>(List.of(1));
    List<Integer> player2 = new ArrayList<>(List.of(2,4));
    List<Integer> player2Direction = new ArrayList<>(List.of(-1));
    int emptyState = 0;
    ConvertibleNeighborFinder myFinder = new NeighborsBetweenCoordinatesFinder();
    MoveCheck ownPiecePlayer1 = new OwnPieceCheck(player1);
    MoveCheck ownPiecePlayer2 = new OwnPieceCheck(player2);
    MoveCheck step = new StepCheck(emptyState);
    MoveCheck jumpPlayer1 = new JumpCheck(emptyState, player1);
    MoveCheck jumpPlayer2 = new JumpCheck(emptyState, player2);
    MoveType changeOpponent = new ChangeOpponentPiecesMove(myFinder,true,emptyState);
    MoveType positon = new PositionMove();
    MoveType promotionPlayer1 = new PromotionMove(0,3);
    MoveType promotionPlayer2 = new PromotionMove(8,4);

    List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1));
    List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2));
    List<MoveCheck> neighborMoveCheckPlayer1 = new ArrayList<>(List.of(step, jumpPlayer1));
    List<MoveCheck> neighborMoveCheckPlayer2 = new ArrayList<>(List.of(step, jumpPlayer2));
    List<MoveType> moveTypesPlayer1 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer1));
    List<MoveType> moveTypesPlayer2 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer2));


    PlayerInfoHolder player1Info = new PlayerInfoHolder(player1,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1,moveTypesPlayer1,true);
    PlayerInfoHolder player2Info = new PlayerInfoHolder(player2,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2,moveTypesPlayer2,false);
    @Test
    void getPlayerStates() {
        assertEquals(player1, player1Info.getPlayerStates());
        assertEquals(player2, player2Info.getPlayerStates());
    }

    @Test
    void getDirections() {
        assertEquals(player1Direction, player1Info.getDirections());
        assertEquals(player2Direction, player2Info.getDirections());
    }

    @Test
    void getMoveChecks() {
        assertEquals(selfMoveCheckPlayer1, player1Info.getMoveChecks());
        assertEquals(selfMoveCheckPlayer2, player2Info.getMoveChecks());
    }

    @Test
    void getNeighborMoveChecks() {
        assertEquals(neighborMoveCheckPlayer1, player1Info.getNeighborMoveChecks());
        assertEquals(neighborMoveCheckPlayer2, player2Info.getNeighborMoveChecks());
    }

    @Test
    void getMoveTypes() {
        assertEquals(moveTypesPlayer1, player1Info.getMoveTypes());
        assertEquals(moveTypesPlayer2, player2Info.getMoveTypes());
    }

    @Test
    void isPlayer1() {
        assertTrue(player1Info.isPlayer1());
        assertFalse(player2Info.isPlayer1());
    }
}