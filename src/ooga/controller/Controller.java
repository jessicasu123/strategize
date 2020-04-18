package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.GameTypeFactory.GameFactory;
import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;


public class Controller implements ControllerFramework {
    private GameFramework myGame;
    private GameTypeFactory gameType;
    private FileHandler myFileHandler;
    private boolean isPieceSelected;
    private int pieceSelectedX;
    private int pieceSelectedY;
    private int squareSelectedX;
    private int squareSelectedY;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;
    private List<Integer> myUserPlayerInformation;
    private List<Integer> myAgentPlayerInformation;
    private Map<Integer, String> myStateToImageMapping;

    public Controller(String fileName, String userID, String opponent) throws IOException, ParseException, InvalidGameTypeException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName);
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        myStateToImageMapping = new HashMap<>();
        isPieceSelected = false;
        setPlayerInformation();
        gameType = createGameTypeFactory();
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(),
                myUserPlayerInformation, myAgentPlayerInformation, userIsPlayer1);
    }

    private GameTypeFactory createGameTypeFactory() throws IOException, ParseException, InvalidGameTypeException {
        String gameType = getStartingProperties().get("Gametype");
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));
        boolean playerPosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        return new GameFactory().createGameType(gameType,myUserPlayerInformation, myAgentPlayerInformation,
                playerPosDirection, emptyState);
    }

    private void setPlayerInformation(){
        if (userIsPlayer1) {
            myUserPlayerInformation = createPlayerStateInformation(1);
            myAgentPlayerInformation = createPlayerStateInformation(2);
        }
        else {
            myUserPlayerInformation = createPlayerStateInformation(2);
            myAgentPlayerInformation = createPlayerStateInformation(1);
        }
    }

    private List<Integer> createPlayerStateInformation(int playerNum){
        List<Integer> stateInfo = myFileHandler.getPlayerStateInfo(playerNum);
        addPlayerStateImageInfoToMap(playerNum);
        return Collections.unmodifiableList(stateInfo);
    }

    private void addPlayerStateImageInfoToMap(int playerNum){
        Map<Integer, String> playerMapping = myFileHandler.getStateImageMapping(playerNum);
        for(Map.Entry<Integer, String> entry: playerMapping.entrySet()){
            myStateToImageMapping.put(entry.getKey(), entry.getValue());
        }
    }

    public Map<Integer, String> getStateImageMapping(){
        return myStateToImageMapping;
    }

    public List<Integer> getUserStateInfo(){
        return Collections.unmodifiableList(myUserPlayerInformation);
    }

    public List<Integer> getAgentStateInfo(){
        return Collections.unmodifiableList(myAgentPlayerInformation);
    }

    public void restartGame() throws IOException, ParseException {
        userTurn = userIsPlayer1;
        isPieceSelected = false;
        myGame = new Game(gameType, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(),
                myUserPlayerInformation,myAgentPlayerInformation, userIsPlayer1);
    }

    public boolean playerPass() {
        return myGame.didPlayerPass();
    }

    public boolean userTurn(){
        return userTurn;
    }

    public boolean doPiecesMove() throws IOException, ParseException {
        return  Boolean.parseBoolean(getStartingProperties().get("PiecesMove"));
    }

    @Override
    public void saveANewFile(String fileName, Map<String, String> startingProperties){
        myFileHandler.saveToFile(fileName, startingProperties, myGame.getVisualInfo());
    }

    @Override
    public Map<String,String> getStartingProperties() throws IOException, ParseException {
        return myFileHandler.loadFileProperties();
    }

    @Override
    public void pieceSelected(int x, int y) {
        isPieceSelected = true;
        pieceSelectedX = x;
        pieceSelectedY = y;
    }

    @Override
    public void squareSelected(int x, int y) {
        squareSelectedX = x;
        squareSelectedY = y;
    }

    @Override
    public void playMove() throws InvalidMoveException {
        if (!isPieceSelected) {
            pieceSelected(squareSelectedX, squareSelectedY);
        }
        myGame.makeGameMove(new ArrayList<>(List.of(pieceSelectedX, pieceSelectedY, squareSelectedX, squareSelectedY)));
        isPieceSelected = false;
        userTurn = myGame.isUserTurn();
    }

    @Override
    public List<List<Integer>> getGameVisualInfo() {
        return myGame.getVisualInfo();
    }

    @Override
    public String getGameFileName() {
        return gameFileName;
    }


    @Override
    public boolean isGameOver() {
        return myGame.getEndGameStatus() > 0;
    }

    @Override
    public int gameWinner() {
        return myGame.getEndGameStatus();
    }

    public List<List<Integer>> getPossibleMovesForView() { return myGame.possibleMovesForView(); }


}
