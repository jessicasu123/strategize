package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;

import java.util.List;

public class MoveTypeFactory {
    public MoveType createMoveType(String moveTypeName, ConvertableNeighborFinder converter, int emptyState, boolean convertToEmptyState,
                                   List<Integer> statesToIgnore, int promotionRow, List<Integer> playerStates) throws Exception {
        switch(moveTypeName){
            default:
                throw new Exception();
        }
    }
}
