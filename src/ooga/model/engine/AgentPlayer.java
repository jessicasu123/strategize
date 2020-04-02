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
     *  - finds the move that the AI agent will make
     *  - uses the board copy to see all of its legal moves and try out moves
     *  - this will implement the mini-max function using alpha-beta pruning
     * @param boardCopy - a copy of the current board
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
