package ooga.model.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONObject;


/**
 * This class is responsible for parsing JSON files and
 * retrieving initial configurations and View properties
 * as well as saving a view configuration to a JSON file
 */

public class JSONFileReader implements FileHandler {

    private String fileNameGame;// = "tic-tac-toe.json";
    public static Map<String, String> gameProperties;
    private JSONArray fileArray;
    private JSONObject JO = new JSONObject();
    private Object obj;
    private List<List<Integer>> configuration;
    private Iterator it;
    private Iterator<Map.Entry> itrl;
    private int counter = 0;
    private String configAsString;
    private JSONObject GameType;
    private JSONObject neighborhood;
    private JSONObject board2;
    private JSONObject board;
    private JSONObject player1_1;
    private JSONObject player2_1;
    private JSONObject player1;
    private JSONObject player2;
    public static final String DEFAULT_RESOURCES = "src/resources/";
    public static final String DATAFILE = DEFAULT_RESOURCES+ "tic-tac-toe.json";
    private org.json.JSONObject gameData;
    private String neighbString;
    List<String> neighborhoodlist;

    public JSONFileReader(String file) throws FileNotFoundException {
        fileNameGame = file;
        gameProperties = new HashMap<>();
    }

    /**
     * parses the configuration string and adds to the configuration 2-D List
     * @param config - the String configuration from the JSON File
     */
    private void parseJSONConfiguration(String config){
        configuration = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        for(int i = 0;i<config.length();i++){
            if(!config.substring(i,i+1).equals(",") && !config.substring(i,i+1).equals(";")){
                row.add(Integer.parseInt(config.substring(i,i+1)));

            }
            if(config.substring(i,i+1).equals(";") || i == config.length()-1){
                configuration.add(row);
                row = new ArrayList<>();
            }
        }
    }


    /**
     * creates a JSONArray and iterator from the JSON File
     */
    private void createJSONArray() throws IOException {
        FileReader br = new FileReader(DEFAULT_RESOURCES + fileNameGame);
        JSONTokener token = new JSONTokener(br);
        gameData = new org.json.JSONObject(token);
    }

    /**
     * adds mapped pairs of properties from the JSON File to the gameproperties hashmap
     */
    private void getGamePropertyNested() throws IOException {
        gameProperties.put("State1",gameData.getJSONObject("Player1").getString("State"));
        gameProperties.put("Color1",gameData.getJSONObject("Player1").getString("Color"));
        gameProperties.put("Image1",gameData.getJSONObject("Player1").getString("Image"));


        gameProperties.put("State2",gameData.getJSONObject("Player2").getString("State"));
        gameProperties.put("Color2",gameData.getJSONObject("Player2").getString("Color"));
        gameProperties.put("Image2",gameData.getJSONObject("Player2").getString("Image"));

        gameProperties.put("Width", gameData.getJSONObject("Board").getString("Width"));
        gameProperties.put("Height", gameData.getJSONObject("Board").getString("Height"));


    }

    /**
     * @param config - 2-D list of game configuration
     * @return configuration in string form
     */
    private String configAsString(List<List<Integer>> config){
        for(int i = 0;i<config.size();i++){
            for(int j = 0;j<config.get(0).size();j++){
                configAsString += config.get(i).get(j) +",";
            }
            configAsString = configAsString.substring(0,configAsString.length()-1);
            configAsString +=";";
        }
        configAsString = configAsString.substring(0,configAsString.length()-1);
        return configAsString;
    }

    /**
     * @return gametype in string form
     */

    public String getGameType() throws IOException {
        return loadFileProperties().get("Gametype");

    }

    /**
     * @return neighborhood in string form
     */

    public List<String> getNeighborhood() throws IOException {
        createJSONArray();
        neighborhoodlist = new ArrayList<>();
        neighbString = gameData.getString("Neighborhood");
        if (neighbString.length()==0) return neighborhoodlist;
        int start = 0;
        for(int i = 0;i<=neighbString.length();i++){
            if(i == neighbString.length() || neighbString.substring(i,i+1).equals(",")){
                neighborhoodlist.add(neighbString.substring(start,i));
                start = i+1;
            }
        }
        return neighborhoodlist;
    }

    @Override
    public String getPlayerImage(int playerID) {
        return null;
    }


    /**
     * @return - a 2-D arraylist of integers representing the game configuration
     */
    @Override
    public List<List<Integer>> loadFileConfiguration() throws IOException {
        createJSONArray();
        JSONObject config = gameData.getJSONObject("Board");
        parseJSONConfiguration(config.getString("InitialConfig"));
        return configuration;
    }

    /**
     * @return - a Hashmap that maps Game properties names
     */
    @Override
    public Map<String, String> loadFileProperties() throws IOException {
        createJSONArray();
        gameProperties.put("Gametype", gameData.getString("Gametype"));
        gameProperties.put("Neighborhood", gameData.getString("Neighborhood"));
        getGamePropertyNested();
        return gameProperties;
    }

    /**
     * saves current configuration to a JSON File
     * @param fileName - the name of the JSON File
     * @param configurationInfo - a 2-D list of the current game view configuration
     */
    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {
        fileArray = new JSONArray();
        GameType = new JSONObject();
        neighborhood = new JSONObject();
        board2 = new JSONObject();
        board = new JSONObject();
        player1_1 = new JSONObject();
        player2_1 = new JSONObject();
        player1 = new JSONObject();
        player2 = new JSONObject();

        GameType.put("GameType", properties.get("GameType"));

        neighborhood.put("Neighborhood",properties.get("Neighborhood"));

        board2.put("Width", properties.get("Width"));
        board2.put("Height", properties.get("Height"));
        board2.put("config", configAsString(configurationInfo));
        board.put("Board", board2);

        player1_1.put("State", properties.get("State"));
        player1_1.put("Color", properties.get("Color"));
        player1_1.put("Image", properties.get("Image"));
        player1.put("Player1", player1_1);

        player2_1.put("State", properties.get("State"));
        player2_1.put("Color", properties.get("Color"));
        player2_1.put("Image", properties.get("Image"));
        player2.put("Player2", player2_1);

        fileArray.put(GameType);
        fileArray.put(neighborhood);
        fileArray.put(board);
        fileArray.put(player1);
        fileArray.put(player2);

        try (FileWriter file = new FileWriter("src/resources")) {

            file.write(fileArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
