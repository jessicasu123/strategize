package ooga.model.engine.pieces;

import ooga.model.exceptions.InvalidMoveTypeException;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

/**
 * Creates a move type based on the information it is given
 */
public class MoveTypeFactory {
    public static final int SPECIAL_STATE_INDEX = 1;

    /**
     * Creates a move type
     * @param moveTypeName - the name of the move type
     * @param converter - the convertible neighbor finder this move type will use
     * @param emptyState - the empty state
     * @param convertToEmptyState - whether when converting pieces it should convert them to the empty state (as opposed
     *                            to the players state)
     * @param promotionRow - the row once it reaches will enable a promotion
     * @param playerStates - all of the states of the player whose move type this is
     * @return a move type based on the parameters
     * @throws InvalidMoveTypeException - if this program doesn't support this move type then it throws an exception
     */
    public MoveType createMoveType(String moveTypeName, ConvertibleNeighborFinder converter, int emptyState, boolean convertToEmptyState,
                                   int promotionRow, List<Integer> playerStates, boolean onlyChangeOpponent, int neighborNumObjectsToCompare) throws InvalidMoveTypeException {
        switch(moveTypeName){
            case "PositionMove":
                return new PositionMove();
            case "PromotionMove":
                return new PromotionMove(promotionRow,playerStates.get(SPECIAL_STATE_INDEX));
            case "ForceMoveAgainMove":
                return new ForceMoveAgainMove(playerStates, converter);
            case "ClearObjectsMove":
                return new ClearObjectsMove();
            case "SpecialCaptureMove":
                return new SpecialCaptureMove(playerStates, converter);
            case "ChangeNeighborObjectsMove":
                return new ChangeNeighborObjectsMove(converter, onlyChangeOpponent);
            case "ChangeOpponentPiecesMove":
                return new ChangeOpponentPiecesMove(converter, convertToEmptyState, emptyState);
            case "ChangeToNewStateMove":
                return new ChangeToNewStateMove();
            case "PieceAtMaxObjectsMove":
                return new PieceAtMaxObjectsMove(neighborNumObjectsToCompare, emptyState, converter);
            case "SplitObjectsMove":
                return new SplitObjectsMove(converter, emptyState);
            default:
                throw new InvalidMoveTypeException(moveTypeName + " is not a valid move type.");
        }
    }
}
