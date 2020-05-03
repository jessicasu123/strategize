package ooga.controller;

import ooga.model.engine.BoardConfiguration;
import ooga.model.exceptions.InvalidEvaluationFunctionException;
import ooga.model.exceptions.InvalidMoveException;
import ooga.model.exceptions.InvalidNeighborhoodException;
import ooga.model.exceptions.InvalidWinTypeException;

import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  - communicates information from the view to the model
 *  - also provides information from the model to the view
 *  - controls both of the modules of the model — the engine module and the data module
 *      - this allows for their logic to be separated but they still get the information they need
 */
public interface ControllerFramework {

    /**
     * METHOD PURPOSE:
     *  -creates a new game and board when the "Restart" button is clicked
     */
    void restartGame() throws InvalidNeighborhoodException, InvalidEvaluationFunctionException, InvalidWinTypeException;

    /**
     * METHOD PURPOSE:
     *  -sends the needed properties and the filename to the file handler to be saved
     * @param fileName - the String the user indicates they want the file to be saved as
     */
    void saveANewFile(String fileName, Map<String, String> startingProperties);

    /**
     * METHOD PURPOSE:
     *  -sends the needed properties from the file handler to the view/model
     * @return a map of the key,value pairs from the file
     */
    Map<String, String> getStartingProperties();

    /**
     * METHOD PURPOSE:
     *  - stores the piece selected that the user wants to move in an instance variable
     *  - this keeps track of the information from the user's action on the view
     * @param row - row coordinate of piece selected
     * @param col - column coordinate of piece selected
     */
    void pieceSelected(int row, int col);

    /**
     * METHOD PURPOSE:
     *  -stores the square selected that the user wants to move the piece to in an instance variable
     *  - this keeps track of the information from the user's action on the view
     *  @param row - row coordinate of square selected
     *  @param col - column coordinate of square selected
     */
    void squareSelected(int row, int col);

    /**
     * METHOD PURPOSE:
     *  - sends the move information stored by pieceSelected and squareSelected to the back-end to validate
     *  and act upon
     *  - if no piece is selected will repeat the square selected information
     */
    void playMove() throws InvalidMoveException;

    /*
     * @deprecated void haveAgentMove() throws InvalidMoveException;
     */

    /**
     * METHOD PURPOSE:
     *  - gives the visual info from the back-end to the front-end to display
     * @return list of list of integers to use to display the game state visually
     */
    BoardConfiguration getGameVisualInfo();

    BoardConfiguration getNumPiecesVisualInfo();

    /**
     * METHOD PURPOSE:
     * -returns the name of the JSON config file for the game currently running on a view.
     * -enables a certain view to go back to the game set up options screen to
     * restart a game.
     * @return the name of the file
     */
    String getGameFileName();

    /*
     * @deprecated int getUserNumber();
     */

    /**
     * METHOD PURPOSE:
     *  - lets the view know if the game is over based on the logic from the backend
     * @return  if the game is over
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

    /**
     * METHOD PURPOSE:
     * - tells the view all the locations that are possible moves
     * @return List of List of integers, where a 1 represents a possible move and a 0
     * represents everything else
     */
    BoardConfiguration getPossibleMovesForView();

    /**
     * METHOD PURPOSE:
     * - gets the number of rows that should be populated with pieces in the view.
     * - relevant for cells with multiple pieces
     * @return number of rows that each board cell should have
     */
    int getVisualRowsPerSquare();

    /**
     * METHOD PURPOSE:
     * - tells the view what the maximum number of pieces for each square should be.
     * - relevant for cells with multiple pieces
     * @return max number of pieces a square can hold
     */
    int getMaxPiecesPerSquare();

    /**
     * METHOD PURPOSE:
     * - lets the view know if it's the user turn or not
     * so it can update the board appearance or not
     * @return true if it's the user's turn, false if not
     */
    boolean userTurn();

    /**
     * METHOD PURPOSE:
     * - tells the view if the pieces move. the view will have
     * extra conditions to update cells if the piece moves (requires a click on a piece AND a square)
     * or doesn't (user only clicks on a square).
     * @return true if the pieces move, false if they don't
     */
    boolean doPiecesMove();

    /**
     * METHOD PURPOSE:
     * - string representing which player passed because they did not have moves
     * @return "user" if user passed, "agent" if agent passed, "" if neither passed
     */
    String playerPass();

    /**
     * METHOD PURPOSE:
     * - gets a mapping of the states to the images that the state
     * will be visually represented by
     * @return map with states as keys and values as images for those states
     */
    Map<Integer, String> getStateImageMapping();

    /**
     * METHOD PURPOSE:
     * gets the state information for the user player.
     * @return - list of integers corresponding to the user's states
     */
    List<Integer> getUserStateInfo();

    /**
     * METHOD PURPOSE:
     * gets the state information for the agent player.
     * @return - list of integers corresponding to the agent's states
     */
    List<Integer> getAgentStateInfo();

    /**
     * METHOD PURPOSE:
     * - gets the types of squares that can be clicked on in the view
     * and considered a "move".
     * - useful to differentiate between games where only empty cells
     * can be clicked vs. games where player cells can be clicked
     * @return List of all the types of squares in the view that the user can click on
     */
    List<String> getSquareClickTypes();

    /**
     * METHOD PURPOSE:
     * Allows the view to differentiate whether a piece can contain multiple
     * objects to be displayed by the view
     */
    boolean hasMultiplePiecesPerSquare();

    /**
     * METHOD PURPOSE:
     * Gets a mapping of the special states to the colors that represent them.
     * @return map with keys as special states and values as colors
     */
    Map<Integer,String> getSpecialStateColorMapping();
}
