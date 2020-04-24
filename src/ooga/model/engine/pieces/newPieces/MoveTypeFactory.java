package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.exceptions.InvalidMoveTypeException;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

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
                                   int promotionRow, List<Integer> playerStates, boolean onlyChangeOpponent) throws InvalidMoveTypeException {
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
            case "ChangeNeighborObjects":
                return new ChangeNeighborObjects(converter, onlyChangeOpponent);
            case "ChangeOpponentPieces":
                return new ChangeOpponentPieces(converter, convertToEmptyState, emptyState);
            case "ChangeToNewState":
                return new ChangeToNewState();
            default:
                throw new InvalidMoveTypeException(moveTypeName + " is not a valid move type.");
        }
    }
}
