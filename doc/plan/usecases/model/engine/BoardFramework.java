package ooga.model.engine;

import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  - the board holds all of the game pieces
 *  - it can gather information on all of the game pieces as a whole
 *  - tells the game pieces when to act
 *
 */

public interface BoardFramework {
    /**
     * METHOD PURPOSE:
     *  - gets all the legal moves of each of the pieces of the player indicated by the parameter
     *  - this will be used by the Agent to determine the best move
     * @param player - the player whose moves you are looking for
     * @return a map which maps the start coordinates of a piece to all of the possible end coordinates that piece
     * can legally move to
     */
    Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player);

    /**
     * METHOD PURPOSE:
     *  - moves a piece on the board and updates the state accordingly
     *  - calls on the Game pieces to do this
     *  - verifies the move
     * @param startCoordinate - the coordinate you are moving from
     * @param endCoordinate - the coordinate you are moving to (may be the same as start coordinate if no movement
     *                      is happening)
     */
    void makeMove(Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - gets the info for all of the current states of the game pieces for the front-end to use
     * @return list of list of the integers used to represent the state at each location
     */
    List<List<Integer>> getStateInfo();

    /**
     * METHOD PURPOSE:
     *  - makes a copy of the board so the agent can try out moves without affecting the actual game state
     * @return a copy of the board
     */
    BoardFramework copyBoard();

}
