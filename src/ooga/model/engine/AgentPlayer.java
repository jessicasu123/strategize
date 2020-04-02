package ooga.model.engine;

import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface AgentPlayer {


    /**
     * METHOD PURPOSE:
     *  -
     */
    Map.Entry<Coordinate, Coordinate> calculateMove(BoardFramework boardCopy) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    int findWinner(BoardFramework boardCopy);
}
