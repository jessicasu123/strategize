package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.exceptions.InvalidMoveCheckException;

import java.util.List;

/**
 * This factory is responsible for creating a MoveCheck depending on the
 * MoveCheckType specified in the game configuration file.
 */
public class MoveCheckFactory {
    public MoveCheck createMoveCheck(String moveCheckType, int emptyState, List<Integer> playerStates, int numObjectsToCompare,
                                     int immovableState) throws InvalidMoveCheckException {
        switch(moveCheckType){
            case "CheckStep":
                return new CheckStep(emptyState);
            case "CheckJump":
                return new CheckJump(emptyState, playerStates);
            case "CheckAllFlippableDirections":
                return new CheckAllFlippableDirections();
            case "CheckBelow":
                return new CheckBelow();
            case "CheckEmptyState":
                return new CheckEmptyState(emptyState);
            case "CheckNotImmovable":
                return new CheckNotImmovable(immovableState);
            default:
                throw new InvalidMoveCheckException(moveCheckType + " is not a valid move check type.");
        }
    }
}
