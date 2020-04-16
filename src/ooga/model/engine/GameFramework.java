package ooga.model.engine;

import java.util.List;

/**
 * PURPOSE OF INTERFACE:
 *  - keeps track of the player/turn information
 *  - holds the board and agent which allows and can enact these objects
 *      - updates the board based on moves made by the user or agent (depending on whose turn it is)
 *      - holds information on the game status (if it is over/who won)
 *  - acts to communicate between the controller (getting information from the user on the front-end)
 *  and the game elements (players/agent and the board)
 */
public interface GameFramework {
    /**
     * METHOD PURPOSE:
     *  - makes the move of a user player on the board
     * @param moveCoordinates - list of the coordinates the controller has collected from the view for the move
     */
    //void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException;
    /**
     * METHOD PURPOSE:
     *  - makes the agent's move on the board
     */
    //void makeAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - gets the status of the game to know if the game is over, and if so who won
     * @return - if the game is still continuing will return 0
     *          - if the game is over:
     *                  -if player1 has won will return 1
     *                  -if player2 has won will return 2
     *                  -if no one won (no moves left) will return 3
     */
    int getEndGameStatus();

    public boolean isUserTurn();

    public void makeGameMove(List<Integer> moveCoordinates) throws InvalidMoveException;
    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info from the board so the view can access it
     */
    List<List<Integer>> getVisualInfo();

    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info for the possible moves
     *  - represented as a list of lists of integers, where a position is marked as 1
     *  if it is a possible move, 0 otherwise.
     */
    List<List<Integer>> possibleMovesForView();
}
