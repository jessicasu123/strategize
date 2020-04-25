package ooga.model.engine;

import ooga.model.exceptions.InvalidMoveException;

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
     *  - gets the status of the game to know if the game is over, and if so who won
     * @return - if the game is still continuing will return 0
     *          - if the game is over:
     *                  -if player1 has won will return 1
     *                  -if player2 has won will return 2
     *                  -if no one won (no moves left) will return 3
     */
    int getEndGameStatus();

    /**
     * METHOD PURPOSE:
     *  - returns whether it's the user's turn or not
     *  - true if it's the user's turn, false if it's the agent's turn
     *  - used by the view to update the board appearance accordingly 
     */
    boolean isUserTurn();

    /**
     * METHOD PURPOSE:
     *  - passes along the information about which player passed (either user or agent)
     *  - returns "user" if used passed, "agent" if agent passed, and "" if neither passed
     */
    String whichPlayerPassed();

    /**
     * METHOD PURPOSE:
     *  - executes both the user's move IF it's the user's turn AND the agent's
     *  move IF it's the agent's turn. Has logic to check if either player has no moves
     *  and must pass
     * @param moveCoordinates - the start and end coordinates of the user's move
     */
    void makeGameMove(List<Integer> moveCoordinates) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info about which PLAYER is in which position
     *  from the board so the view can access it
     */
    List<List<Integer>> getVisualInfo();

    /**
     * METHOD PURPOSE:
     *  - passes along the info from the board about how many OBJECTS are in each position
     *  so the view can access it
     */
    List<List<Integer>> getObjectInfo();

    /**
     * METHOD PURPOSE:
     *  - passes along the visual state info for the possible moves
     *  - represented as a list of lists of integers, where a position is marked as 1
     *  if it is a possible move, 0 otherwise.
     */
    List<List<Integer>> possibleMovesForView();
}
