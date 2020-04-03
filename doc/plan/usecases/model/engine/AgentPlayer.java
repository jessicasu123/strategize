package ooga.model.engine;

import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 * - contains the game rules pertaining to the end of the game (how the game is won)
 *  - smart acting agent to evaluate game state and acts as an opponent
 *      - it acts as a player and evaluates the current game state to see which move it should make
 *      - in doing so, this class evaluates the current game state to see if the game has been won/is over based
 *      on the game rules
 *  - this will later be implemented as an inheritance hierarchy where each Agent is specific to the game type
 *  so that is can act smartly
 */
public interface AgentPlayer {


    /**
     * METHOD PURPOSE:
     *  - finds the move that the AI agent will make
     *  - uses the board copy to see all of its legal moves and try out moves
     *  - this will implement the mini-max function using alpha-beta pruning
     *      - agent players will evaluate each state to see how favorable it is
     * @param boardCopy - a copy of the current board to represent the current game state
     * @return returns a map entry mapping the start coordinate of the move selected to the end coordinate
     */
    Map.Entry<Coordinate, Coordinate> calculateMove(BoardFramework boardCopy) throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - will find the winner of the game/game status
     * @return - if the game is still continuing will return 0
     *          - if the game is over:
     *                  -if player1 has won will return 1
     *                  -if player2 has won will return 2
     *                  -if no one won (no moves left) will return 3
     */
    int findWinner(BoardFramework boardCopy);
}
