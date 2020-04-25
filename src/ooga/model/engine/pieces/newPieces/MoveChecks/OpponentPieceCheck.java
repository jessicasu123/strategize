package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * MoveCheck to determine whether the piece belongs to the opponent
 * @author Sanya Kochhar
 */
public class OpponentPieceCheck implements MoveCheck{

    private int myEmptyState;
    private List<Integer> myStates;

    public OpponentPieceCheck(int emptyState, List<Integer> playerStates){
        myEmptyState = emptyState;
        myStates = playerStates;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        for (int myState : myStates) {
            if (checking.getState() == myState) {
                return false;
            }
        }
        return checking.getState() != myEmptyState;
    }
}
