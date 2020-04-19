package ooga.model.data;

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
 * @author Brian Li
 */

public class JSONFileReader implements FileHandler {

    private String gameFileName;
    public static Map<String, String> gameProperties;
    private JSONArray fileArray;
    private List<List<Integer>> configuration;
    private String configAsString;
    private JSONObject GameType;
    private JSONObject neighborhood;
    private JSONObject board2;
    private JSONObject board;
    private JSONObject player1_1;
    private JSONObject player2_1;
    private JSONObject player1;
    private JSONObject player2;
    private JSONObject emptystate;
    private JSONObject pieceMove;
    private JSONObject player1PosDirection;
    private JSONObject possibleMove;
    private JSONObject Rules;
    public static final String DEFAULT_RESOURCES = "src/resources/gameFiles/";
    private org.json.JSONObject gameData;
    private String neighbString;
    List<String> neighborhoodlist;
    String boardDimensions;

    public JSONFileReader(String file, String dimensions) throws IOException {
        gameFileName = file;
        boardDimensions = dimensions;
        gameProperties = new HashMap<>();
        createJSONArray();
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
        FileReader br = new FileReader(DEFAULT_RESOURCES + gameFileName);
        JSONTokener token = new JSONTokener(br);
        gameData = new org.json.JSONObject(token);
    }

    /**
     * @param i - the player whose info are looking for
     * @return the states of that player
     */
    public List<Integer> getPlayerStateInfo(int i){
        JSONObject player = gameData.getJSONObject("Player" + i);
        JSONArray stateInfo = player.getJSONArray("States");
        List<Integer> playerStateInfo = new ArrayList<>();
        for(int j = 0; j < stateInfo.length(); j++){
            playerStateInfo.add(stateInfo.getInt(j));
        }
        return Collections.unmodifiableList(playerStateInfo);
    }

    /**
     *
     * @param i - the player whose info are looking for
     * @return a map for the player where each state is mapped to the file name for the image
     */
    public Map<Integer, String> getStateImageMapping(int i){
        List<Integer> states = getPlayerStateInfo(i);
        Map<Integer, String> stateImageMapping = new HashMap<>();
        JSONArray imageInfo = gameData.getJSONObject("Player" + i).getJSONArray("Images");
        for(int j = 0; j < imageInfo.length(); j++){
            stateImageMapping.put(states.get(j), imageInfo.getString(j));
        }
        return stateImageMapping;
    }
    /**
     * adds mapped pairs of properties from the JSON File to the gameproperties hashmap
     */
    private void getGamePropertyNested() {
        gameProperties.put("Color1",gameData.getJSONObject("Player1").getString("Color"));
        gameProperties.put("Color2",gameData.getJSONObject("Player2").getString("Color"));
        JSONObject boardDetails = gameData.getJSONObject("Board").getJSONObject("DimensionDetails").getJSONObject(boardDimensions);
        gameProperties.put("Width", boardDetails.getString("Width"));
        gameProperties.put("Height", boardDetails.getString("Height"));
        gameProperties.put("Player1Direction", gameData.getString("Player1PosDirection"));
        gameProperties.put("EmptyState", gameData.getString("EmptyState"));
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

    public List<String> getNeighborhood(){
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


    /**
     * @return - a 2-D arraylist of integers representing the game configuration
     */
    @Override
    public List<List<Integer>> loadFileConfiguration() throws IOException {
        createJSONArray();
        JSONObject config = gameData.getJSONObject("Board").getJSONObject("DimensionDetails").getJSONObject(boardDimensions);
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
        gameProperties.put("EmptyState",gameData.getString("EmptyState"));
        gameProperties.put("PiecesMove",gameData.getString("PiecesMove"));
        gameProperties.put("Player1PosDirection",gameData.getString("Player1PosDirection"));
        gameProperties.put("PiecesMove", gameData.getString("PiecesMove"));
        gameProperties.put("possibleMove", gameData.getString("possibleMove"));
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
        emptystate = new JSONObject();
        pieceMove = new JSONObject();
        player1PosDirection = new JSONObject();
        board2 = new JSONObject();
        board = new JSONObject();
        player1_1 = new JSONObject();
        player2_1 = new JSONObject();
        player1 = new JSONObject();
        player2 = new JSONObject();
        possibleMove = new JSONObject();
        Rules = new JSONObject();

        GameType.put("GameType", properties.get("GameType"));

        neighborhood.put("Neighborhood",properties.get("Neighborhood"));

        emptystate.put("EmptyState",properties.get("EmptyState"));

        pieceMove.put("PiecesMove",properties.get("PiecesMove"));

        player1PosDirection.put("Player1PosDirection",properties.get("Player1PosDirection"));

        board2.put("Width", properties.get("Width"));
        board2.put("Height", properties.get("Height"));
        board2.put("config", configAsString(configurationInfo));
        board.put("Board", board2);

        player1_1.put("State", properties.get("State"));
        player1_1.put("Image", properties.get("Image"));
        player1_1.put("StartingPieces",properties.get("StartingPieces"));
        player1_1.put("Color", properties.get("Color"));
        player1.put("Player1", player1_1);

        player2_1.put("State", properties.get("State"));
        player2_1.put("Image", properties.get("Image"));
        player2_1.put("StartingPieces",properties.get("StartingPieces"));
        player2_1.put("Color", properties.get("Color"));
        player2.put("Player2", player2_1);

        possibleMove.put("possibleMove", properties.get("possibleMove"));

        fileArray.put(GameType);
        fileArray.put(neighborhood);
        fileArray.put(board);
        fileArray.put(player1);
        fileArray.put(player2);
        fileArray.put(emptystate);
        fileArray.put(pieceMove);
        fileArray.put(player1PosDirection);
        fileArray.put(possibleMove);

        try (FileWriter file = new FileWriter(fileName)) {

            file.write(fileArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
