package ooga.model.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import ooga.model.engine.exceptions.InvalidFileFormatException;
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

    public static final int POS_DIRECTION = 1;
    public static final String ERROR_MESSAGE = "file not formatted properly";
    private String gameFileName;
    public static Map<String, String> gameStringProperties;
    public static Map<String, Integer> gameIntProperties;
    public static Map<String, Boolean> gameBoolProperties;
    public static Map<String, JSONArray> gameArrayProperties;
    private String configAsString;
    public static final String DEFAULT_RESOURCES = "src/resources/gameFiles/";
    public static final String JSON_ENDING = ".json";
    private JSONObject gameData;
    private String boardDimensions;

    public JSONFileReader(String file, String dimensions){
        gameFileName = file;
        boardDimensions = dimensions;
        gameStringProperties = new HashMap<>();
        gameIntProperties = new HashMap<>();
        gameBoolProperties = new HashMap<>();
        gameArrayProperties = new HashMap<>();
    }

    public void parseFile()throws InvalidFileFormatException{
        try {
            FileReader br = new FileReader(DEFAULT_RESOURCES + gameFileName);
            JSONTokener token = new JSONTokener(br);
            gameData = new JSONObject(token);
            parseData();
        }catch(Exception e){
            throw new InvalidFileFormatException(ERROR_MESSAGE);
        }
    }

    @Override
    public int getMaxObjectsPerSquare() {
        return gameIntProperties.get("MaxNumObjectsPerSquare");
    }

    @Override
    public List<Integer> getStatesToIgnoreForPlayer(int i) {
        return convertJSONArrayToIntegerList(gameArrayProperties.get("Player" + i + "StatesToIgnore"));
    }

    private List<String> convertJSONArrayToStringList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length();i++) {
            stringList.add(jsonArray.getString(i));
        }
        return stringList;
    }

    private List<Integer> convertJSONArrayToIntegerList(JSONArray jsonArray) {
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length();i++) {
            intList.add(jsonArray.getInt(i));
        }
        return intList;
    }

    @Override
    public String getConverterType() {
        return gameStringProperties.get("NeighborConverterType");
    }

    @Override
    public List<String> getSelfMoveChecks() {
        return convertJSONArrayToStringList(gameArrayProperties.get("SelfMoveChecks"));
    }

    @Override
    public List<String> getNeighborMoveChecks() {
        return convertJSONArrayToStringList(gameArrayProperties.get("NeighborMoveChecks"));
    }

    @Override
    public List<String> getMoveTypes() {
        return convertJSONArrayToStringList(gameArrayProperties.get("MoveTypes"));
    }

    @Override
    public List<Integer> getDirectionForPlayer(int i) {
        return convertJSONArrayToIntegerList(gameArrayProperties.get("Player"+i+"Direction"));
    }

    @Override
    public int getNeighborNumObjectsToCompare() {
        return gameIntProperties.get("NeighborNumObjectsToCompare");
    }

    @Override
    public int getSelfNumObjectsToCompare() {
        return gameIntProperties.get("SelfNumObjectsToCompare");
    }

    @Override
    public boolean convertToEmptyState() {
        return gameBoolProperties.get("ConvertToEmptyState");
    }

    @Override
    public int getPromotionRowForPlayer(int player) {
        boolean player1PromotionIsLastRow = gameBoolProperties.get("Player1PromotionIsLastRow");
        int lastRow = Integer.parseInt(gameStringProperties.get("Height"));
        if((player == 1 && player1PromotionIsLastRow) || (player == 2 && !player1PromotionIsLastRow)){
            return lastRow;
        }
        return 0;
    }

    @Override
    public int getNumRowsPerSquare() {
        return gameIntProperties.get("NumVisualRowsPerSquare");
    }


    /**
     * parses the configuration string and adds to the configuration 2-D List
     * @param config - the String configuration from the JSON File
     */
    private List<List<Integer>> parseJSONConfiguration(String config){
        List<List<Integer>> configuration = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        String[] rows = config.split(";");
        for(String rowConfig: rows){
            int[] rowTemp = Arrays.stream(rowConfig.split(",")).mapToInt(Integer::parseInt).toArray();
            for(int i : rowTemp){
                row.add(i);
            }
            configuration.add(row);
            row = new ArrayList<>();
        }
        return configuration;
    }

    /**
     * @param i - the player whose info are looking for
     * @return the states of that player
     */
    public List<Integer> getPlayerStateInfo(int i){
        JSONArray stateInfo = gameArrayProperties.get("Player" + i + "States");
        List<Integer> playerStateInfo = new ArrayList<>();
        for (int j = 0; j < stateInfo.length(); j++) {
            playerStateInfo.add(stateInfo.getInt(j));
        }
        return Collections.unmodifiableList(playerStateInfo);

    }

    public List<List<Integer>> getBoardWeights(){
        String boardWeightStr = gameStringProperties.get("BoardWeights");
        return parseJSONConfiguration(boardWeightStr);
    }

    public List<List<Integer>> getObjectConfig(){
        String boardObjectStr = gameStringProperties.get("ObjectConfig");
        return parseJSONConfiguration(boardObjectStr);
    }
    public int getWinValue(){
        return gameIntProperties.get("WinValue");
    }

    public String getWinType(){
        return gameStringProperties.get("WinType");
    }

    public List<String> getEvaluationFunctions(){
        return convertJSONArrayToStringList(gameArrayProperties.get("EvaluationFunctions"));
    }

    public int getSpecialPieceIndex(){
        return gameIntProperties.get("SpecialPieceIndex");
    }

    public int getEmptyState(){
        return gameIntProperties.get("EmptyState");
    }

    public int player1Direction(){
       boolean player1PosDirection = gameBoolProperties.get("Player1PosDirection");
       if(player1PosDirection){
           return POS_DIRECTION;
       }else{
           return -POS_DIRECTION;
       }
    }

    public int player2Direction(){
        boolean player1PosDirection = gameBoolProperties.get("Player1PosDirection");
        if(player1PosDirection){
            return -POS_DIRECTION;
        }else{
            return POS_DIRECTION;
        }
    }
    public boolean doPiecesMove(){
        return gameBoolProperties.get("PiecesMove");
    }
    /**
     *
     * @param i - the player whose info are looking for
     * @return a map for the player where each state is mapped to the file name for the image
     */
    public Map<Integer, String> getStateImageMapping(int i){
        List<Integer> states = getPlayerStateInfo(i);
        Map<Integer, String> stateImageMapping = new HashMap<>();
        JSONArray imageInfo = gameArrayProperties.get("Player" + i + "Images");
        for(int j = 0; j < imageInfo.length(); j++){
            stateImageMapping.put(states.get(j), imageInfo.getString(j));
        }
        return stateImageMapping;
    }

    public Map<Integer, String> getSpecialStateColorMapping(int i) {
        List<Integer> states = getPlayerStateInfo(i);
        Map<Integer,String> specialStateColorMapping = new HashMap<>();
        if (states.size()>1) {
            JSONArray colorInfo = gameArrayProperties.get("Player" + i + "Colors");
            specialStateColorMapping.put(states.get(states.size()-1), colorInfo.getString(colorInfo.length()-1));
        }
        return specialStateColorMapping;
    }

    /**
     * @param config - 2-D list of game configuration
     * @return configuration in string form
     */
    private String configAsString(List<List<Integer>> config){
        configAsString = "";
        for (List<Integer> integers : config) {
            for (int j = 0; j < config.get(0).size(); j++) {
                configAsString += Integer.toString(integers.get(j)) + ",";
            }
            configAsString = configAsString.substring(0, configAsString.length() - 1);
            configAsString += ";";
        }
        configAsString = configAsString.substring(0,configAsString.length()- 1);
        return configAsString;
    }

    public boolean hasMultiplePiecesPerSquare() {return gameBoolProperties.get("MultiplePiecesPerSquare");}

    /**
     * @return gametype in string form
     */
    public String getGameType(){
        return gameStringProperties.get("Gametype");
    }

    public boolean shouldCheckCurrConfig(){
        return gameBoolProperties.get("CheckCurrConfig");
    }

    /**
     * @return neighborhood in string form
     */

    public List<String> getNeighborhood(){
        List<String> neighborhoodlist = new ArrayList<>();
        String neighbString = gameStringProperties.get("Neighborhood");
        if (neighbString.length()==0) return neighborhoodlist;
        int start = 0;
        for(int i = 0; i<= neighbString.length(); i++){
            if(i == neighbString.length() || neighbString.substring(i,i+ 1).equals(",")){
                neighborhoodlist.add(neighbString.substring(start,i));
                start = i+ 1;
            }
        }
        return neighborhoodlist;
    }

    /**
     * @return - a 2-D arraylist of integers representing the game configuration
     */
    @Override
    public List<List<Integer>> loadFileConfiguration(){
        return parseJSONConfiguration(gameStringProperties.get("InitialConfig"));
    }

    private void parseData(){
        for (String key: gameData.keySet()) {
            Object value = gameData.get(key);
            if (value.getClass().equals(JSONObject.class)) {
                JSONObject nestedObject = gameData.getJSONObject(key);
                parseNestedData(key, nestedObject);
            } else {
                getBasicValues(gameData, key, key, value);
            }
        }
        validateData();
    }

    private void parseNestedData(String key, JSONObject nestedObject) {
        for (String nestedKey: nestedObject.keySet()) {
            Object value = nestedObject.get(nestedKey);
            String newKey = nestedKey;
            if (key.contains("Player") || key.equals(boardDimensions)) { //TODO: don't harcode?
                if (key.contains("Player")){
                    newKey = key+nestedKey;
                }
                getBasicValues(nestedObject, nestedKey, newKey, value);
            }
        }
    }

    private void getBasicValues(JSONObject data, String key, String mapName, Object value) {
        if (value.getClass().equals(String.class)) {
            gameStringProperties.put(mapName, data.getString(key));
        } else if (value.getClass().equals(Boolean.class)) {
            gameBoolProperties.put(mapName, data.getBoolean(key));
        } else if (value.getClass().equals(Integer.class)) {
            gameIntProperties.put(mapName, data.getInt(key));
        } else if (value.getClass().equals(JSONArray.class)) {
            gameArrayProperties.put(mapName, data.getJSONArray(key));
        }
    }

    private void validateData(){
        if(checkBoardDimensions() || checkBoardWeightDimensions() || checkImageLengths() ||
                checkPlayerAndImageStatesSameLength() || checkPlayerStatesSameLength() || checkerBoardObjectDimensions()){
            throw new InvalidFileFormatException(ERROR_MESSAGE);
        }
    }

    private boolean checkerBoardObjectDimensions(){
        return Integer.parseInt(gameStringProperties.get("Width")) != getObjectConfig().get(0).size() ||
                Integer.parseInt(gameStringProperties.get("Height")) != getObjectConfig().size();
    }
    private boolean checkPlayerStatesSameLength() {
        return gameArrayProperties.get("Player1States").length() != gameArrayProperties.get("Player2States").length();
    }

    private boolean checkPlayerAndImageStatesSameLength() {
        return gameArrayProperties.get("Player1Images").length() != gameArrayProperties.get("Player1States").length() &&
                gameArrayProperties.get("Player2Images").length() != gameArrayProperties.get("Player2States").length() ;
    }

    private boolean checkImageLengths() {
        return gameArrayProperties.get("Player1Images").length() != gameArrayProperties.get("Player2Images").length();
    }

    private boolean checkBoardWeightDimensions() {
        return Integer.parseInt(gameStringProperties.get("Width")) != getBoardWeights().get(0).size() ||
                Integer.parseInt(gameStringProperties.get("Height")) != getBoardWeights().size();
    }

    private boolean checkBoardDimensions() {
        return Integer.parseInt(gameStringProperties.get("Width")) != loadFileConfiguration().get(0).size() ||
        Integer.parseInt(gameStringProperties.get("Height")) != loadFileConfiguration().size();
    }

    /**
     * @return - a Hashmap that maps Game properties names
     */
    @Override
    public Map<String, String> loadFileProperties()  {
        return gameStringProperties;
    }

    private void writeBasicValues(String searchKey, JSONObject writeTo, String key, List<List<Integer>> currentConfig) {
        if (gameStringProperties.keySet().contains(searchKey)) {
            if (key.equals("InitialConfig")) {
                writeTo.put(key,configAsString(currentConfig));
            } else if (key.equals("Default") && (!boardDimensions.equals(gameStringProperties.get(key)))) {
                    writeTo.put(key, boardDimensions);
            } else {
                writeTo.put(key, gameStringProperties.get(searchKey));
            }
        }
        else if (gameBoolProperties.keySet().contains(searchKey)) {
            writeTo.put(key, gameBoolProperties.get(searchKey));
        } else if (gameArrayProperties.keySet().contains(searchKey)) {
            writeTo.put(key, gameArrayProperties.get(searchKey));
        } else if (gameIntProperties.keySet().contains(searchKey)) {
            writeTo.put(key, gameIntProperties.get(searchKey));
        }
    }

    private JSONObject writeNestedObject(String key, String valuesList, List<List<Integer>> config) {
        JSONObject nestedObject = new JSONObject();
        JSONArray values = gameArrayProperties.get(valuesList);
        for (int val = 0; val < values.length(); val++) {
            String nestedKey = (String) values.get(val);
            String searchName = nestedKey;
            if (key.contains("Player")) {
                searchName = key + nestedKey;
            }
            writeBasicValues(searchName, nestedObject, nestedKey, config);
        }
        return nestedObject;
    }
    /**
     * saves current configuration to a JSON File
     * @param fileName - the name of the JSON File
     * @param configurationInfo - a 2-D list of the current game view configuration
     */
    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {
        JSONObject jsonFile = new JSONObject();
        JSONArray allKeys = gameArrayProperties.get("Keys");
        for (int i = 0; i < allKeys.length();i++) {
            String key = (String) allKeys.get(i);
            if (key.equals(boardDimensions)) { //TODO: fix so that there's a mapping from nested keys to their inner values
                JSONObject nestedObject = writeNestedObject(key,"BoardKeys", configurationInfo);
                jsonFile.put(key, nestedObject);
            }
            else if (key.equals("Player1") || key.equals("Player2")) {
                JSONObject nestedPlayerObj = writeNestedObject(key, "PlayerKeys", configurationInfo);
                jsonFile.put(key, nestedPlayerObj);
            } else {
                writeBasicValues(key, jsonFile, key, configurationInfo);
            }
        }
        try (FileWriter file = new FileWriter(DEFAULT_RESOURCES + fileName + JSON_ENDING)) {
            file.write(jsonFile.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
