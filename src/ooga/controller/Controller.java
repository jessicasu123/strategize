package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.Agent.winTypes.WinTypeFactory;
import ooga.model.engine.exceptions.InvalidFileFormatException;
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

    public Controller(String fileName, String userID, String opponent, String dimensions) throws InvalidGameTypeException, InvalidFileFormatException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName, dimensions);
        myFileHandler.parseFile();
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        myStateToImageMapping = new HashMap<>();
        setPlayerInformation();
        List<List<Integer>> startingConfiguration = myFileHandler.loadFileConfiguration();
        myGamePieces = createGamePieceFactory();
        Agent gameAgent = createAgent(startingConfiguration);
        myGame = new Game(myGamePieces, startingConfiguration, myFileHandler.getNeighborhood(), myUserPlayerInfo,
                myAgentPlayerInfo, userIsPlayer1, gameAgent);
    }

    private Agent createAgent(List<List<Integer>> startingConfig){
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue, startingConfig);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue, startingConfig);
        return new Agent(winType, allEvals, myAgentPlayerInfo, myUserPlayerInfo);

    }

    private List<EvaluationFunction> createEvaluationFunctions(int winValue, List<List<Integer>> startingConfig) {
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        int userDirection;
        int agentDirection;
        if(userIsPlayer1){
            userDirection = myFileHandler.player1Direction();
            agentDirection = myFileHandler.player2Direction();
        }else{
            userDirection = myFileHandler.player2Direction();
            agentDirection = myFileHandler.player1Direction();
        }
        List<List<Integer>> boardWeights = myFileHandler.getBoardWeights();
        List<String> evalFunctions = myFileHandler.getEvaluationFunctions();
        List<EvaluationFunction> allEvals = new ArrayList<>();
        for(String eval: evalFunctions){
            EvaluationFunction evalFunc = new EvaluationFunctionFactory().createEvaluationFunction(eval,
                    specialPieceIndex, myAgentPlayerInfo, myUserPlayerInfo, boardWeights, agentDirection,userDirection,
                    winValue, true,startingConfig);
            allEvals.add(evalFunc);
        }
        return allEvals;
    }

    private WinType createWinType(int winValue, List<List<Integer>> startingConfig){
        String winTypeStr = myFileHandler.getWinType();
        int emptyState = myFileHandler.getEmptyState();
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        return new WinTypeFactory().createWinType(winTypeStr, emptyState,specialPieceIndex, winValue, true, startingConfig);
    }

    private GamePieceFactory createGamePieceFactory(){
        String gameType = myFileHandler.getGameType();
        int emptyState = myFileHandler.getEmptyState();
        int userDirection;
        int agentDirection;
        if(userIsPlayer1){
            userDirection = myFileHandler.player1Direction();
            agentDirection = myFileHandler.player2Direction();
        }else{
            userDirection = myFileHandler.player2Direction();
            agentDirection = myFileHandler.player1Direction();
        }
        return new GamePieceFactory(gameType, myUserPlayerInfo, myAgentPlayerInfo, emptyState, userDirection, agentDirection);
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
                myUserPlayerInfo, myAgentPlayerInfo, userIsPlayer1, createAgent(myFileHandler.loadFileConfiguration()));
    }

    public String playerPass() {return myGame.whichPlayerPassed();}

    public boolean userTurn(){
        return userTurn;
    }

    public boolean doPiecesMove(){
        return myFileHandler.doPiecesMove();
    }

    @Override
    public void saveANewFile(String fileName, Map<String, String> startingProperties){
        myFileHandler.saveToFile(fileName, startingProperties, myGame.getVisualInfo());
    }

    @Override
    public Map<String,String> getStartingProperties(){
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
