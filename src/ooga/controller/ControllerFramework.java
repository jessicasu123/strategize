package ooga.controller;

import ooga.model.engine.InvalidMoveException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
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
     *  -sends the needed properties and the filename to the file handler to be saved
     * @param fileName - the String the user indicates they want the file to be saved as
     * @param properties - the properties the user specified/changed to be stored
     */
    void saveANewFile(String fileName, Map<String, String> properties);

    /**
     * METHOD PURPOSE:
     *  -sends the needed properties from the file handler to the view/model
     * @return a map of the key,value pairs from the file
     */
    Map<String, String> getStartingProperties() throws IOException, ParseException;

    /**
     * METHOD PURPOSE:
     *  - stores the piece selected that the user wants to move in an instance variable
     *  - this keeps track of the information from the user's action on the view
     * @param x - x coordinate of piece selected
     * @param y - y coordinate of piece selected
     */
    void pieceSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  -stores the square selected that the user wants to move the piece to in an instance variable
     *  - this keeps track of the information from the user's action on the view
     *  @param x - x coordinate of square selected
     *  @param y - y coordinate of square selected
     */
    void squareSelected(int x, int y);

    /**
     * METHOD PURPOSE:
     *  - sends the move information stored by pieceSelected and squareSelected to the back-end to validate
     *  and act upon
     *  - if no piece is selected will repeat the square selected information
     */
    void playMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - tells the backend to do the logic for having an agent play its turn
     */
    void haveAgentMove() throws InvalidMoveException;

    /**
     * METHOD PURPOSE:
     *  - gives the visual info from the back-end to the front-end to display
     * @return list of list of integers to use to display the game state visually
     */
    List<List<Integer>> getGameVisualInfo();

    /**
     * METHOD PURPOSE:
     * -returns the name of the JSON config file for the game currently running on a view.
     * -enables a certain view to go back to the game set up options screen to
     * restart a game.
     * @return
     */
    String getGameFileName();

    /**
     * METHOD PURPOSE:
     *  - indicated which state number represents the user
     * @return the state number that represents the user
     */
    int getUserNumber();

    /**
     * METHOD PURPOSE:
     *  - indicated which state number represents the agent
     * @return the state number that represents the agent
     */
    int getAgentNumber();

    /**
     * METHOD PURPOSE:
     *  - lets the view know if the game is over based on the logic from the backend
     * @boolean if the game is over
     */
    boolean isGameOver();

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
