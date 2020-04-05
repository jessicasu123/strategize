package ooga.model;

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
}
