package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

/**
 * This class is responsible for checking whether from the start coordinate a piece can step to the location
 * of the piece checking.
 * A step is a move of size 1 in both the x and y coordinates
 * A step needs to land on a piece with the empty state
 *
 * @author Holly Ansel
 */
public class StepCheck implements MoveCheck {
    public static final int STEP_SIZE = 1;
    private int myEmptyState;

    /**
     * @param emptyState - the empty state on the board(where steps can land)
     */
    public StepCheck(int emptyState){
        myEmptyState = emptyState;
    }

    /**
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether going from the starting location to the location of the game piece being checked is a legal step
     */
    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checkStepRow(startingLocation, checking, directions) && checkStepCol(startingLocation, checking)
                && checking.getState() == myEmptyState;
    }

    private boolean checkStepRow(Coordinate startingLocation, GamePiece checking, List<Integer> directions){
        for(int direction: directions){
            if(startingLocation.getRow() + direction == checking.getPosition().getRow()){
                return true;
            }
        }
        return false;
    }

    private boolean checkStepCol(Coordinate startingLocation, GamePiece checking){
        return startingLocation.getCol() + STEP_SIZE == checking.getPosition().getCol() ||
                startingLocation.getCol() - STEP_SIZE == checking.getPosition().getCol();
    }
}
