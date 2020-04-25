package ooga.model.engine.pieces.moveChecks;

import ooga.model.exceptions.InvalidMoveCheckException;

import java.util.List;

/**
 * This factory is responsible for creating a MoveCheck depending on the
 * MoveCheckType specified in the game configuration file.
 */
public class MoveCheckFactory {
    /**
     * @param moveCheckType - the name of the move check
     * @param emptyState - the empty state
     * @param playerStates - all of the states of the player whose move check this is
     * @param numObjectsToCompare - the minimum number of objects a piece needs to have to move
     * @param immovableState - the state that is not allowed to move
     * @return a move check based on the parameters
     * @throws InvalidMoveCheckException - if this program doesn't support this move check then it throws an exception
     */
    public MoveCheck createMoveCheck(String moveCheckType, int emptyState, List<Integer> playerStates, int numObjectsToCompare,
                                     int immovableState) throws InvalidMoveCheckException {
        switch(moveCheckType){
            case "StepCheck":
                return new StepCheck(emptyState);
            case "JumpCheck":
                return new JumpCheck(emptyState, playerStates);
            case "AllFlippableDirectionsCheck":
                return new AllFlippableDirectionsCheck();
            case "BelowCheck":
                return new BelowCheck();
            case "EmptyStateCheck":
                return new EmptyStateCheck(emptyState);
            case "NotImmovableCheck":
                return new NotImmovableCheck(immovableState);
            case "NumObjectsCheck":
                return new NumObjectsCheck(numObjectsToCompare);
            case "OpponentPieceCheck":
                return new OpponentPieceCheck(emptyState,playerStates);
            case "OwnPieceCheck":
                return new OwnPieceCheck(playerStates);
            default:
                throw new InvalidMoveCheckException(moveCheckType + " is not a valid move check type.");
        }
    }
}
