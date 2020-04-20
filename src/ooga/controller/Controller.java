package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.Agent.winTypes.WinTypeFactory;
import ooga.model.engine.exceptions.InvalidGameTypeException;
import ooga.model.engine.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePieceFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;


public class Controller implements ControllerFramework {
    private GameFramework myGame;
    private GamePieceFactory myGamePieces;
    private FileHandler myFileHandler;
    private boolean isPieceSelected;
    private int pieceSelectedX;
    private int pieceSelectedY;
    private int squareSelectedX;
    private int squareSelectedY;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;
    private List<Integer> myUserPlayerInfo;
    private List<Integer> myAgentPlayerInfo;
    private Map<Integer, String> myStateToImageMapping;

    public Controller(String fileName, String userID, String opponent, String dimensions) throws IOException, ParseException, InvalidGameTypeException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName, dimensions);
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        myStateToImageMapping = new HashMap<>();
        setPlayerInformation();
        List<List<Integer>> startingConfiguration = myFileHandler.loadFileConfiguration();
        myGamePieces = createGamePieceFactory();
        Agent gameAgent = createAgent();
        myGame = new Game(myGamePieces, startingConfiguration, myFileHandler.getNeighborhood(), myUserPlayerInfo,
                myAgentPlayerInfo, userIsPlayer1, gameAgent);
    }

    private Agent createAgent() throws IOException, ParseException {
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue);
        return new Agent(winType, allEvals, myAgentPlayerInfo, myUserPlayerInfo);

    }

    private List<EvaluationFunction> createEvaluationFunctions(int winValue) throws IOException, ParseException {
        boolean playerPosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        List<List<Integer>> boardWeights = myFileHandler.getBoardWeights();
        List<String> evalFunctions = myFileHandler.getEvaluationFunctions();
        List<EvaluationFunction> allEvals = new ArrayList<>();
        for(String eval: evalFunctions){
            EvaluationFunction evalFunc = new EvaluationFunctionFactory().createEvaluationFunction(eval,
                    specialPieceIndex, myAgentPlayerInfo, myUserPlayerInfo, boardWeights, playerPosDirection,
                    winValue);
            allEvals.add(evalFunc);
        }
        return allEvals;
    }

    private WinType createWinType(int winValue) throws IOException, ParseException {
        String winTypeStr = myFileHandler.getWinType();
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));
        //TODO: change player row
        return new WinTypeFactory().createWinType(winTypeStr, emptyState, winValue, 0);
    }

    private GamePieceFactory createGamePieceFactory() throws IOException, ParseException {
        String gameType = getStartingProperties().get("Gametype");
        int emptyState = Integer.parseInt(getStartingProperties().get("EmptyState"));
        boolean playerPosDirection = Boolean.parseBoolean(getStartingProperties().get("Player1Direction"));
        return new GamePieceFactory(gameType, myUserPlayerInfo, myAgentPlayerInfo, emptyState, playerPosDirection);
    }

    private void setPlayerInformation(){
        if (userIsPlayer1) {
            myUserPlayerInfo = createPlayerStateInformation(1);
            myAgentPlayerInfo = createPlayerStateInformation(2);
        }
        else {
            myUserPlayerInfo = createPlayerStateInformation(2);
            myAgentPlayerInfo = createPlayerStateInformation(1);
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
        return Collections.unmodifiableList(myUserPlayerInfo);
    }

    public List<Integer> getAgentStateInfo(){
        return Collections.unmodifiableList(myAgentPlayerInfo);
    }

    public void restartGame() throws IOException, ParseException {
        userTurn = userIsPlayer1;
        isPieceSelected = false;
        myGame = new Game(myGamePieces, myFileHandler.loadFileConfiguration(), myFileHandler.getNeighborhood(),
                myUserPlayerInfo, myAgentPlayerInfo, userIsPlayer1, createAgent());
    }

    public String playerPass() {return myGame.whichPlayerPassed();}

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
