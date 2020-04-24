package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * This interface is responsible for checking the validity of a move,
 * which can be determined by a variety of different conditions.
 *
 * Different types of move checks (specified in the game configuration file)
 * will be used by the GamePiece to determine if a move is valid.
 *
 * This interface provides the flexibility to potentially change/add/remove conditions
 * that are required for a move to be "valid" in a game.
 */
public interface MoveCheck {

    boolean isConditionMet(Coordinate startingLocation,GamePiece checking, List<GamePiece> neighbors, int state, List<Integer> directions);
}
