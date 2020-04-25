package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;
/**
 * This class checks to see whether a particular state is the empty state
 * @author Brian Li
 */
public class EmptyStateCheck implements MoveCheck{

    private int myEmptyState;

    public EmptyStateCheck(int emptystate){
        myEmptyState = emptystate;
    }

    /**
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether state is equivalent to emptystate
     */
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checking.getState() == myEmptyState;
    }
}
