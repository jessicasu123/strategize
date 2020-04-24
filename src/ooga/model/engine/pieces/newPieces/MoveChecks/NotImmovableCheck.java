package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * This class is responsible for checking whether the game piece being checked to move is not an immovable state
 *
 * @author Holly Ansel
 */
public class NotImmovableCheck implements MoveCheck {
    private int myNonMovingState;

    /**
     * @param nonMovingState - a state defined as being unable to move
     */
    public NotImmovableCheck(int nonMovingState){
        myNonMovingState = nonMovingState;
    }

    /**
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether the piece being moved's state is not the immovable state
     */
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checking.getState() != myNonMovingState;
    }
}
