package ooga.model.engine;

import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */

public interface BoardFramework {
    /**
     * METHOD PURPOSE:
     *  -
     */
    Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player);

    /**
     * METHOD PURPOSE:
     *  -
     */
    void makeMove(Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    List<List<Integer>> getStateInfo();

    /**
     * METHOD PURPOSE:
     *  -
     */
    BoardFramework copyBoard();

}
