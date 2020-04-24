package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.exceptions.InvalidMoveCheckException;

import java.util.List;

public class MoveCheckFactory {

    public MoveCheck createMoveCheck(String moveCheckType, int emptyState, List<Integer> playerStates, int numObjectsToCompare) throws Exception {
        switch(moveCheckType){
            case "CheckStep":
                return new CheckStep(emptyState);
            case "CheckJump":
                return new CheckJump(emptyState, playerStates);
            default:
                throw new InvalidMoveCheckException(moveCheckType + " is not a valid move check type.");
        }
    }
}
