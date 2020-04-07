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

    private void createJSONArray(String filename, String header) throws IOException, ParseException {
        obj = new JSONParser().parse(new FileReader(filename));
        JO = (JSONObject) obj;
        ja = (JSONArray) JO.get(header);
        it = ja.iterator();
    }

    private void getGameProperty(String header) throws IOException, ParseException {
        createJSONArray(fileNameView, header);
        while(it.hasNext()){
            itrl = ((Map) it.next()).entrySet().iterator();
            while(itrl.hasNext()){
                Map.Entry pair = itrl.next();
                gameProperties.put((String) pair.getKey(), (String) pair.getValue());
            }
        }
    }

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

    @Override
    public Map<String, String> loadFileProperties() throws IOException, ParseException {
        getGameProperty("Text");
        getGameProperty("Icons");
        getGameProperty("GamePieceImage");
        getGameProperty("GameColor");
        return gameProperties;
    }


    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {
        
    }

}
