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
     * @param player - the player to be moved (1 or 2)
     * @param startCoordinate - the coordinate you are moving from
     * @param endCoordinate - the coordinate you are moving to (may be the same as start coordinate if no movement
     *                      is happening)
     */
    void makeMove(int player, Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException;

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

    /**
     * METHOD PURPOSE:
     *  - checks if there are no possible moves by either opponent
     * @return true if there are moves left, false if there are not
     */
    boolean checkNoMovesLeft(int userID, int agentID);

    /**
     * METHOD PURPOSE:
     *  - returns a visual representation of the possible moves.
     * @return list of list of integers with the same row/col dimensions of a board.
     *  - 1 indicates that a position is a possible move
     *  - 0 indicated that a position is NOT a possible move
     */
    List<List<Integer>> possibleMovesVisualInfo(int playerID);

    boolean changeTurns();



}
