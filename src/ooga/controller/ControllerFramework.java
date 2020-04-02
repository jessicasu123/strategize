package ooga.controller;

import ooga.model.engine.InvalidMoveException;

import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface ControllerFramework {

    /**
     * METHOD PURPOSE:
     *  -
     */
    List<List<Integer>> getStartingStateConfiguaration();

    /**
     * METHOD PURPOSE:
     *  -
     */
    Map<String, String> getStartingProperties();

    /**
     * METHOD PURPOSE:
     *  -
     */
    void pieceSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  -
     */
    void squareSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  -
     */
    void playMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    void haveAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  -
     */
    List<List<Integer>> getGameVisualInfo();

    /**
     * METHOD PURPOSE:
     *  -
     */
    int getUserNumber();

    /**
     * METHOD PURPOSE:
     *  -
     */
    boolean gameOver();

    /**
     * METHOD PURPOSE:
     *  -
     */
    int gameWinner();
}
