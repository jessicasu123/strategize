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

import java.io.InputStream;

public class JSONFileReader implements FileHandler {

    private String fileName = "";
    public static List<List<Integer>> gameConfiguration;
    public static Map<String, String> gameProperties;
    private JSONObject JO = new JSONObject();
    private Object obj;
    private List<List<Integer>> configuration;
    private JSONArray ja;
    private Iterator it;
    private Iterator<Map.Entry> itrl;
    private int counter = 0;

    public JSONFileReader(String JSONFile){
        fileName = JSONFile;
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

    @Override
    public List<List<Integer>> loadFileConfiguration() throws IOException, ParseException {
        obj = new JSONParser().parse(new FileReader(fileName));
        JO = (JSONObject) obj;
        ja = (JSONArray) JO.get("Board");
        it = ja.iterator();

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
    public Map<String, String> loadFileProperties() {
        return null;
    }

    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {

    }

}
