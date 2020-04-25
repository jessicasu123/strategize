package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

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

    /**
     *
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether or not this move given by the parameters is satisfied according to the logic of each move check
     */
    boolean isConditionMet(Coordinate startingLocation,GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions);
}
