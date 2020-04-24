package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.exceptions.InvalidMoveTypeException;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

public class MoveTypeFactory {
    public static final int SPECIAL_STATE_INDEX = 1;
    public MoveType createMoveType(String moveTypeName, ConvertibleNeighborFinder converter, int emptyState, boolean convertToEmptyState,
                                   int promotionRow, List<Integer> playerStates) throws InvalidMoveTypeException {
        switch(moveTypeName){
            case "PositionMove":
                return new PositionMove();
            case "Promotion":
                return new Promotion(promotionRow,playerStates.get(SPECIAL_STATE_INDEX));
            case "ForceMoveAgain":
                return new ForceMoveAgain(playerStates, converter);
            case "ClearObjects":
                return new ClearObjects();
            case "SpecialCapture":
                return new SpecialCapture(playerStates, converter);
            default:
                throw new InvalidMoveTypeException(moveTypeName + " is not a valid move type.");
        }
    }
}
