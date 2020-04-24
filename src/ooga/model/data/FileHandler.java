package ooga.model.data;

import ooga.model.engine.exceptions.InvalidFileFormatException;
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
    void saveToFile(String fileName, Map<String, String> properties,List<List<Integer>> configurationInfo);

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

    int getSpecialPieceIndex();
    List<String> getEvaluationFunctions();
    String getWinType();
    int getWinValue();
    List<List<Integer>> getBoardWeights();
    int getEmptyState();
    int player1Direction();
    int player2Direction();
    boolean doPiecesMove();
    String getGameType();
    List<Integer> getStatesToIgnoreForPlayer(int i);
    String getConverterType();
    List<String> getSelfMoveChecks();
    List<String> getNeighborMoveChecks();
    List<String> getMoveTypes();
    List<Integer> getDirectionForPlayer(int i);
    int getNeighborNumObjectsToCompare();
    int getSelfNumObjectsToCompare();
    boolean convertToEmptyState();

    boolean shouldCheckCurrConfig();

    int getPromotionRowForPlayer(int player);


}
