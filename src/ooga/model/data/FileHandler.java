package ooga.model.data;

import ooga.model.exceptions.InvalidFileFormatException;
import java.util.List;
import java.util.Map;

/**
 * This interface is responsible for reading values from a file
 * that will be relevant to constructing the back-end and front-end components of a game.
 *
 * FileHandler will provide all these values to other classes (ex. Controller)
 * that will need them to know how to instantiate the Game/Board/View/etc.
 */
public interface FileHandler {

    /**
     * METHOD PURPOSE:
     *  - gives the game piece configuration from the file in order to build the board
     *      - will be passed to the view from the controller (getting the information from the game and board)
     * @return list of list of integers to represent the state configuration specified in the file
     */
    List<List<Integer>> loadFileConfiguration();

    /**
     * METHOD PURPOSE:
     *  -loads all of the properties from the file for the specifications of the game
     * @return a map to represent the key,value pairings from the file
     */
    Map<String, String> loadFileProperties();

    void parseFile() throws InvalidFileFormatException;

    /**
     * METHOD PURPOSE:
     *  - saving a new file based on the information passed from other parts of the program
     * @param fileName - the name of the file it will be saved to
     * @param configurationInfo  - the board configuration to save to the file
     */
    void saveToFile(String fileName, List<List<Integer>> configurationInfo, List<List<Integer>> objectConfigInfo);

    /*@deprecated
    /void saveToFile(String fileName,  Map<String, String> properties, List<List<Integer>> configurationInfo);
    */
    /**
     * METHOD PURPOSE:
     * - gets a list of all the neighborhood types a certain game piece
     * will need to consider.
     * @return - neighbor types to consider
     */
    List<String> getNeighborhood();

    /**
     *
     * @param i - the player whose info are looking for
     * @return list of all of the states for that player
     */
    List<Integer> getPlayerStateInfo(int i);

    /**
     *
     * @param i - the player whose info are looking for
     * @return a map that maps each state to the image to represent it
     */
    Map<Integer, String> getStateImageMapping(int i);

    /**
     * METHOD PURPOSE:
     *  - gets the configuration of number of objects in each row, col position
     *  for the view.
     *  - relevant for games with multi-piece board cells.
     * @return a list of list of integers, where each row,col position represents the number
     * of objects at that location.
     */
    List<List<Integer>> getObjectConfig();


    /**
     * METHOD PURPOSE:
     * - determines whether a board cell will hold multiple pieces or not
     * @return true if the cell should be multi-piece, false if not
     */
    boolean hasMultiplePiecesPerSquare();

    /**
     * METHOD PURPOSE:
     * -gets the number of VISUAL rows per square
     * -relevant for games with multiple pieces per cell
     * @return number of rows that the board cell should use to populate piece images
     */
    int getNumRowsPerSquare();

    /**
     * METHOD PURPOSE:
     * -gets the maximum number of objects in each square
     * @return max number of objects a board cell can hold
     */
    int getMaxObjectsPerSquare();

    /**
     * METHOD PURPOSE:
     * - mapping of the special state to the color that represents that state.
     * - relevant for multi piece board cell games where the game pieces are the
     * same for both players but the special states should be differentiated somehow.
     * @param i - the player whose special state value is being looked for
     * @return map with keys as special states and values as colors representing those states
     */
    Map<Integer,String> getSpecialStateColorMapping(int i);

    /**
     * @return the index of the special piece (a piece with added/different functionality)
     */
    int getSpecialPieceIndex();

    /**
     * @return a list of the names of all of the evaluation functions to use
     */
    List<String> getEvaluationFunctions();

    /**
     * @return the name of the win type that should be used
     */
    String getWinType();

    /**
     * @return the value needed to win (number pieces in a row, number of pieces collected"
     */
    int getWinValue();

    /**
     * @return a nested list of the boardweights
     */
    List<List<Integer>> getBoardWeights();

    /**
     * @return the empty state of the board
     */
    int getEmptyState();

    /**
     * @return whether pieces move positions
     */
    boolean doPiecesMove();

    /**
     * @return gametype in string form
     */
    String getGameType();

    /**
     * @param i - Player ID number
     * @return - List of states for the specific player to ignore
     */
    List<Integer> getStatesToIgnoreForPlayer(int i);

    /**
     * @return Neighborhood Converter Type String
     */
    String getConverterType();

    /**
     * @return List of MoveChecks
     */
    List<String> getSelfMoveChecks();

    /**
     * @return List of Neighbor MoveChecks
     */
    List<String> getNeighborMoveChecks();

    /**
     * @return List of MoveTypes
     */
    List<String> getMoveTypes();

    /**
     * @return List of Directions based on player ID
     */
    List<Integer> getDirectionForPlayer(int i);

    /**
     * @return the number of object to compare when checking a neighbor
     */
    int getNeighborNumObjectsToCompare();

    /**
     * @return the number of object to compare when checking self
     */
    int getSelfNumObjectsToCompare();

    /**
     * @return whether or not when converting the state of a piece it should be converted to the empty state
     */
    boolean convertToEmptyState();

    /**
     * @return if should check the current config
     */
    boolean shouldCheckCurrConfig();

    /**
     * @param player - PlayerID int
     * @return - the row number for promotion for a specific playerID
     */
    int getPromotionRowForPlayer(int player);

    /**
     * @param player - PlayerID int
     * @return - the immovable state for a specific playerID
     */
    int getImmovableStateForPlayer(int player);

    /**
     * @return whether the only state that should be changed on a move is your opponents
     */
    boolean getOnlyChangeOpponent();

    /**
     * @return gets the click type of a square for the front end
     */
    List<String> getSquareClickTypes();
}
