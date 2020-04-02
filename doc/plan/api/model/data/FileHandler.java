package ooga.model.data;


import java.util.List;
import java.util.Map;

/**
 * PURPOSE OF INTERFACE:
 *  - controls the data
 *      - this class will read and parse the configuration file used to create the program
 *          - and the information will be passed to the other parts of the program that need it
 *      - this class also controls saving information to a new configuration file
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


    /**
     * METHOD PURPOSE:
     *  - saving a new file based on the information passed from other parts of the program
     * @param fileName - the name of the file it will be saved to
     * @param  properties - the properties to save to the file
     * @param configurationInfo  - the board configuration to save to the file
     */
    void saveToFile(String fileName,  Map<String, String> properties, List<List<Integer>> configurationInfo);
}
