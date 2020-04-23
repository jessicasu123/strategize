package ooga.model.engine.pieces.newPieces.MoveChecks;

import java.util.List;

public class MoveCheckFactory {

    public MoveCheck createMoveCheck(String moveCheckType, int emptyState, List<Integer> playerStates, int numObjectsToCompare) throws Exception {
        switch(moveCheckType){
            default:
                throw new Exception();
        }
    }
}
