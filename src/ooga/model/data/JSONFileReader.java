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
    private JSONObject gameData;
    private JSONObject boardDetails;
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
            gameData = new org.json.JSONObject(token);
            boardDetails = gameData.getJSONObject("Board").getJSONObject("DimensionDetails").getJSONObject(boardDimensions);
            parseData();
        }catch(Exception e){
            throw new InvalidFileFormatException(ERROR_MESSAGE);
        }
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

    public int getWinValue(){
        return gameIntProperties.get("WinValue");
    }

    public String getWinType(){
        return gameStringProperties.get("WinType");
    }

    public List<String> getEvaluationFunctions(){
        List<String> evalFunctions = new ArrayList<>();
        JSONArray allEvals = gameArrayProperties.get("EvaluationFunctions");
        for(int i = 0; i < allEvals.length(); i++){
            evalFunctions.add(allEvals.getString(i));
        }
        return evalFunctions;
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

    /**
     * @param config - 2-D list of game configuration
     * @return configuration in string form
     */
    private String configAsString(List<List<Integer>> config){
        for (List<Integer> integers : config) {
            for (int j = 0; j < config.get(0).size(); j++) {
                configAsString += integers.get(j) + ",";
            }
            configAsString = configAsString.substring(0, configAsString.length() - 1);
            configAsString += ";";
        }
        configAsString = configAsString.substring(0,configAsString.length()- 1);
        return configAsString;
    }

    /**
     * @return gametype in string form
     */

    public String getGameType(){
        return gameStringProperties.get("Gametype");
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
        parseStringData();
        parseBooleanData();
        parseIntegerData();
        parseArrayData();
        validateData();
    }

    private void validateData(){
        if(checkBoardDimensions() || checkBoardWeightDimensions() || checkImageLengths() ||
                checkPlayerAndImageStatesSameLength() || checkPlayerStatesSameLength()){
            throw new InvalidFileFormatException(ERROR_MESSAGE);
        }
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

    private void parseArrayData() {
        gameArrayProperties.put("Player1States", gameData.getJSONObject("Player1").getJSONArray("States"));
        gameArrayProperties.put("Player2States", gameData.getJSONObject("Player2").getJSONArray("States"));
        gameArrayProperties.put("EvaluationFunctions", gameData.getJSONArray("EvaluationFunctions"));
        gameArrayProperties.put("Player1Images", gameData.getJSONObject("Player1").getJSONArray("Images"));
        gameArrayProperties.put("Player2Images", gameData.getJSONObject("Player2").getJSONArray("Images"));
    }

    private void parseIntegerData() {
        gameIntProperties.put("WinValue", boardDetails.getInt("WinValue"));
        gameIntProperties.put("SpecialPieceIndex", gameData.getInt("SpecialPieceIndex"));
        gameIntProperties.put("EmptyState", gameData.getInt("EmptyState"));
    }

    private void parseBooleanData() {
        gameBoolProperties.put("PiecesMove", gameData.getBoolean("PiecesMove"));
        gameBoolProperties.put("Player1PosDirection", gameData.getBoolean("Player1PosDirection"));
    }

    private void parseStringData() {
        gameStringProperties.put("Gametype", gameData.getString("Gametype"));
        gameStringProperties.put("Neighborhood", gameData.getString("Neighborhood"));
        gameStringProperties.put("possibleMove", gameData.getString("possibleMove"));
        gameStringProperties.put("Color1",gameData.getJSONObject("Player1").getString("Color"));
        gameStringProperties.put("Color2",gameData.getJSONObject("Player2").getString("Color"));
        gameStringProperties.put("Width", boardDetails.getString("Width"));
        gameStringProperties.put("Height", boardDetails.getString("Height"));
        gameStringProperties.put("InitialConfig", boardDetails.getString("InitialConfig"));
        gameStringProperties.put("WinType",  gameData.getString("WinType"));
        gameStringProperties.put("BoardWeights",  boardDetails.getString("BoardWeights"));
    }

    /**
     * @return - a Hashmap that maps Game properties names
     */
    @Override
    public Map<String, String> loadFileProperties()  {
        return gameStringProperties;
    }

    /**
     * saves current configuration to a JSON File
     * @param fileName - the name of the JSON File
     * @param configurationInfo - a 2-D list of the current game view configuration
     */

    @Override
    public void saveToFile(String fileName, Map<String, String> properties, List<List<Integer>> configurationInfo) {
        JSONArray fileArray = new JSONArray();
        JSONObject gameType = new JSONObject();
        JSONObject neighborhood = new JSONObject();
        JSONObject emptystate = new JSONObject();
        JSONObject pieceMove = new JSONObject();
        JSONObject player1PosDirection = new JSONObject();
        JSONObject board2 = new JSONObject();
        JSONObject board = new JSONObject();
        JSONObject player1_1 = new JSONObject();
        JSONObject player2_1 = new JSONObject();
        JSONObject player1 = new JSONObject();
        JSONObject player2 = new JSONObject();
        JSONObject possibleMove = new JSONObject();
        JSONObject rules = new JSONObject();

        gameType.put("GameType", properties.get("GameType"));

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

        fileArray.put(gameType);
        fileArray.put(neighborhood);
        fileArray.put(board);
        fileArray.put(player1);
        fileArray.put(player2);
        fileArray.put(emptystate);
        fileArray.put(pieceMove);
        fileArray.put(player1PosDirection);
        fileArray.put(possibleMove);

        try (FileWriter file = new FileWriter("src/resources/gameFiles/" + fileName)) {
            file.write(fileArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
