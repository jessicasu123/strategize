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
     *  - makes the move of a user player on the board
     * @param moveCoordinates - list of the coordinates the controller has collected from the view for the move
     */
    void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - makes the agent's move on the board
     */
    void makeAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - gets the status of the game to know if the game is over, and if so who won
     * @return - if the game is still continuing will return 0
     *          - if the game is over(no moves left):
     *                  -if player1 has won will return 1
     *                  -if player2 has won will return 2
     *                  -if no one one will return 3
     */
    int getEndGameStatus();


    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info from the board so the view can access it
     */
    List<List<Integer>> getVisualInfo();

}
