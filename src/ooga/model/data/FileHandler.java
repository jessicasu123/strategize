package ooga.model.data;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileHandler {


    /**
     * METHOD PURPOSE:
     *  - gives the game piece configuration from the file in order to build the board
     *      - will be passed to the view from the controller (getting the information from the game and board)
     * @return list of list of integers to represent the state configuration specified in the file
     */
    List<List<Integer>> loadFileConfiguration() throws IOException, ParseException;

    /**
     * METHOD PURPOSE:
     *  -loads all of the properties from the file for the specifications of the game
     * @return a map to represent the key,value pairings from the file
     */
    Map<String, String> loadFileProperties() throws IOException, ParseException;


    /**
     * METHOD PURPOSE:
     *  - saving a new file based on the information passed from other parts of the program
     * @param fileName - the name of the file it will be saved to
     * @param configurationInfo  - the board configuration to save to the file
     */
    void saveToFile(String fileName, Map<String, String> properties,List<List<Integer>> configurationInfo);

    public String getGameType() throws IOException;
    public List<String> getNeighborhood() throws IOException;

    /**
     *
     * @param i - the player whose info are looking for
     * @return list of all of the states for that player
     */
    public List<Integer> getPlayerStateInfo(int i);

    /**
     *
     * @param i - the player whose info are looking for
     * @return a map that maps each state to the image to represent it
     */
    public Map<Integer, String> getStateImageMapping(int i);

}
