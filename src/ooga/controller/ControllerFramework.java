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
     *  -sends the needed properties from the file handler to the view
     */
    Map<String, String> getStartingProperties();

    /**
     * METHOD PURPOSE:
     *  - stores the piece selected that the user wants to move
     */
    void pieceSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  -stores the square selected that the user wants to move the piece to
     */
    void squareSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  -plays the moves based on what is stored from the piece and square selected
     */
    void playMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - tells the backend to do the logic for having an agent play its turn
     */
    void haveAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - gives the visual info from the back-end to the front-end
     */
    List<List<Integer>> getGameVisualInfo();

    /**
     * METHOD PURPOSE:
     *  - indicated which state number represents the user
     */
    int getUserNumber();

    /**
     * METHOD PURPOSE:
     *  - lets the view know if the game is over based on the logic from the backend
     */
    boolean gameOver();

    /**
     * METHOD PURPOSE:
     *  - tells the view who won the game
     * @return - if the game is over:
     *             -if player1 has won will return 1
     *             -if player2 has won will return 2
     *             -if no one won (no moves left) will return 3
     */
    int gameWinner();
}
