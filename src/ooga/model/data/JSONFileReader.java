package ooga.model.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * This class is responsible for parsing JSON files and
 * retrieving initial configurations and View properties
 * as well as saving a view configuration to a JSON file
 */

public class JSONFileReader implements FileHandler {

    private String fileNameGame = "tic-tac-toe.json";
    public static Map<String, String> gameProperties;
    private JSONObject JO = new JSONObject();
    private Object obj;
    private List<List<Integer>> configuration;
    private JSONArray ja;
    private Iterator it;
    private Iterator<Map.Entry> itrl;
    private int counter = 0;
    private String configAsString;
    private JSONArray fileArray;
    private JSONObject GameType;
    private JSONObject neighborhood;
    private  JSONObject board2;
    private  JSONObject board;
    private JSONObject player1_1;
    private  JSONObject player2_1;
    private  JSONObject player1;
    private JSONObject player2;

    public JSONFileReader(){

    }

    /**
     * parses the configuration string and adds to the configuration 2-D List
     * @param config - the String configuration from the JSON File
     */
    private void parseJSONConfiguration(String config){
        for(int i = 0;i<config.length();i++){
            if(config.substring(i,i+1).equals(";")){
                counter++;
            }
            else if(!config.substring(i,i+1).equals(",")){
                configuration.get(counter).add(Integer.parseInt(config.substring(i,i+1)));
            }
        }
    }

    /**
     * creates a JSONArray and iterator from the JSON File
     * @param filename - the file name of the JSON File
     * @param object - the particular object of the JSON File
     */
    private void createJSONArray(String filename, String object) throws IOException, ParseException {
        obj = new JSONParser().parse(new FileReader(filename));
        JO = (JSONObject) obj;
        ja = (JSONArray) JO.get(object);
        it = ja.iterator();
    }

    /**
     * adds mapped pairs of properties from the JSON File to the gameproperties hashmap
     * @param object - the particular object of the JSON File
     */
    //debug
    private void getGameProperty(String object) throws IOException, ParseException {
        createJSONArray(fileNameGame, object);
            itrl = ((Map) it.next()).entrySet().iterator();
            while (itrl.hasNext()) {
                Map.Entry pair = itrl.next();
                gameProperties.put((String) pair.getKey(), (String) pair.getValue());
            }
    }

    /**
     * adds mapped pairs of properties from the JSON File to the gameproperties hashmap
     * @param object - the particular object of the JSON File
     */
    private void getGamePropertyNested(String object) throws IOException, ParseException {
        createJSONArray(fileNameGame, object);
        while(it.hasNext()){
            itrl = ((Map) it.next()).entrySet().iterator();
            while(itrl.hasNext()){
                Map.Entry pair = itrl.next();
                gameProperties.put((String) pair.getKey(), (String) pair.getValue());
            }
        }
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
    public String getGameType(){
        return gameProperties.get("GameType");
    }

    /**
     * @return neighborhood in string form
     */
    public String getNeighborhood(){
        return gameProperties.get("Neighborhood");
    }
    
    /**
     * @return - a 2-D arraylist of integers representing the game configuration
     */
    @Override
    public List<List<Integer>> loadFileConfiguration() throws IOException, ParseException {
        createJSONArray(fileNameGame, "Board");
        while(it.hasNext()){
            itrl = ((Map) it.next()).entrySet().iterator();
            while(itrl.hasNext()){
                Map.Entry pair = itrl.next();
                if(pair.getKey().equals("InitialConfig")) {
                    parseJSONConfiguration((String) pair.getValue());
                    break;
                }
                else if(pair.getKey().equals("Height") || pair.getKey().equals("Width")){
                    gameProperties.put((String) pair.getKey(), (String) pair.getValue());
                }
            }
        }
        return configuration;
    }

    /**
     * @return - a Hashmap that maps Game properties names
     */
    @Override
    public Map<String, String> loadFileProperties() throws IOException, ParseException {
        getGameProperty("GameType");
        getGameProperty("Neighborhood");
        getGamePropertyNested("Player1");
        getGamePropertyNested("Player2");
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

        try (FileWriter file = new FileWriter(fileName)) {

            file.write(fileArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
