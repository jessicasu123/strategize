package ooga.model.engine;

import java.util.List;

/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface GameFramework {

    /**
     * METHOD PURPOSE:
     *  -
     */
    void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    void makeAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    int getUserGameStatus();


    /**
     * METHOD PURPOSE:
     *  -
     */
    List<List<Integer>> getVisualInfo();

}
