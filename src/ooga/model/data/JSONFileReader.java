package ooga.model.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class is responsible for parsing JSON files and
 * retrieving initial configurations and View properties
 * as well as saving a view configuration to a JSON file
 */

public class JSONFileReader implements FileHandler {

    private String fileNameGame = "";
    private String fileNameView = "";
    public static Map<String, String> gameProperties;
    private JSONObject JO = new JSONObject();
    private Object obj;
    private List<List<Integer>> configuration;
    private JSONArray ja;
    private Iterator it;
    private Iterator<Map.Entry> itrl;
    private int counter = 0;

    public JSONFileReader(String JSONFile){
        fileNameGame = JSONFile;
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
    private void getGameProperty(String object) throws IOException, ParseException {
        createJSONArray(fileNameView, object);
        while(it.hasNext()){
            itrl = ((Map) it.next()).entrySet().iterator();
            while(itrl.hasNext()){
                Map.Entry pair = itrl.next();
                gameProperties.put((String) pair.getKey(), (String) pair.getValue());
            }
        }
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
            }
        }
        return configuration;
    }

    /**
     * @return - a Hashmap that maps Game View property names
     */
    @Override
    public Map<String, String> loadFileProperties() throws IOException, ParseException {
        getGameProperty("Text");
        getGameProperty("Icons");
        getGameProperty("GamePieceImage");
        getGameProperty("GameColor");
        return gameProperties;
    }

    /**
     * saves current configuration to a JSON File
     * @param fileName - the name of the JSON File
     * @param properties - a map of the Game View properties
     * @param configurationInfo - a 2-D list of the current game view configuration
     */
    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {

    }

}
