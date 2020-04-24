package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.Agent.winTypes.WinTypeFactory;
import ooga.model.engine.Player.Player;
import ooga.model.engine.exceptions.InvalidFileFormatException;
import ooga.model.engine.exceptions.InvalidGameTypeException;
import ooga.model.engine.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePieceFactory;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinderFactory;
import ooga.model.engine.pieces.newPieces.GamePieceCreator;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheckFactory;
import ooga.model.engine.pieces.newPieces.MoveType;
import ooga.model.engine.pieces.newPieces.MoveTypeFactory;
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
    private Player userPlayer;
    private Player agentPlayer;
    private List<Integer> myUserPlayerInfo;
    private List<Integer> myAgentPlayerInfo;
    private Map<Integer, String> myStateToImageMapping;
    private GamePieceCreator GamePieceCreator;
    private int emptyState;

    public Controller(String fileName, String userID, String opponent, String dimensions) throws InvalidGameTypeException, InvalidFileFormatException, Exception {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName, dimensions);
        myFileHandler.parseFile();
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        createUserAndAgentPlayers();
        myStateToImageMapping = new HashMap<>();
        setPlayerInformation();

        emptyState = myFileHandler.getEmptyState();
        List<List<Integer>> startingConfiguration = myFileHandler.loadFileConfiguration();
        myGamePieces = createGamePieceFactory();
        Agent gameAgent = createAgent(startingConfiguration);
        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();

        makeGamePieceCreator();
        myGame = new Game(myGamePieces, startingConfiguration, objectConfig, myFileHandler.getNeighborhood(), myUserPlayerInfo,
                myAgentPlayerInfo, userIsPlayer1, gameAgent);
    }

    private void createUserAndAgentPlayers() throws Exception {
        if(userIsPlayer1){
            userPlayer = makePlayer(1);
            agentPlayer = makePlayer(2);
        }else{
            userPlayer = makePlayer(2);
            agentPlayer = makePlayer(1);
        }
    }

    private void makeGamePieceCreator() {
        GamePieceCreator = new GamePieceCreator(userPlayer, agentPlayer);
    }

    private Player makePlayer(int player) throws Exception {
        List<Integer> playerStates = myFileHandler.getPlayerStateInfo(player);
        List<MoveCheck> selfMoveChecks = createSelfMoveCheckForPlayer(playerStates);
        List<MoveCheck> neighborMoveChecks = createNeighborMoveCheckForPlayer(playerStates);
        List<MoveType> moveTypes = createMoveTypesForPLayer(player, playerStates);
        List<Integer> directions = myFileHandler.getDirectionForPlayer(player);
        boolean isPlayer1 = (userIsPlayer1 && player==1) || (!userIsPlayer1 && player==2);
        return new Player(playerStates, directions, selfMoveChecks,
                neighborMoveChecks, moveTypes,isPlayer1);
    }


    private ConvertableNeighborFinder createConvertibleNeighborFinderForPlayer(List<Integer> stateToIgnore){
        String finderType = myFileHandler.getConverterTypes();
        return new ConvertableNeighborFinderFactory().createNeighborhoodConverterFinder(finderType, stateToIgnore);
    }

    private List<MoveType> createMoveTypesForPLayer(int player, List<Integer> playerStates) throws Exception {
        List<MoveType> moveTypes = new ArrayList<>();
        List<String> moveTypeNames = myFileHandler.getMoveTypes();
        boolean convertToEmptyState = myFileHandler.convertToEmptyState();
        List<Integer> statesToIgnore = myFileHandler.getStatesToIgnoreForPlayer(player);
        int promotionRow = myFileHandler.getPromotionRowForPlayer(player);
        ConvertableNeighborFinder neighborFinder = createConvertibleNeighborFinderForPlayer(statesToIgnore);
        for(String moveTypeName: moveTypeNames){
            MoveType move = new MoveTypeFactory().createMoveType(moveTypeName,neighborFinder,emptyState,
                    convertToEmptyState,statesToIgnore,promotionRow,playerStates);
            moveTypes.add(move);
        }

        return moveTypes;
    }

    private List<MoveCheck> createSelfMoveCheckForPlayer(List<Integer> playerStates) throws Exception {
        List<String> selfMoveCheck = myFileHandler.getSelfMoveChecks();
        return createMoveCheck(selfMoveCheck, playerStates);
    }

    private List<MoveCheck> createMoveCheck(List<String> moveCheckNames, List<Integer> playerStates) throws Exception {
        List<MoveCheck> moveChecks = new ArrayList<>();
        int objToCompare = myFileHandler.getSelfNumObjectsToCompare();
        for (String moveCheckName: moveCheckNames) {
            MoveCheck moveCheck = new MoveCheckFactory().createMoveCheck(moveCheckName,emptyState,
                    playerStates,objToCompare);
            moveChecks.add(moveCheck);
        }
        return moveChecks;
    }

    private List<MoveCheck> createNeighborMoveCheckForPlayer(List<Integer> playerStates) throws Exception {
        List<String> neighborMoveChecks = myFileHandler.getNeighborMoveChecks();
        return createMoveCheck(neighborMoveChecks, playerStates);
    }

    private Agent createAgent(List<List<Integer>> startingConfig){
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue, startingConfig);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue, startingConfig);
        return new Agent(winType, allEvals, myAgentPlayerInfo, myUserPlayerInfo);

    }

    private List<EvaluationFunction> createEvaluationFunctions(int winValue, List<List<Integer>> startingConfig) {
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        int userDirection = userPlayer.getDirections().get(0);
        int agentDirection = agentPlayer.getDirections().get(0);
        if(userIsPlayer1){ //TODO: remove once FileHandler gets directions from file
            userDirection = myFileHandler.player1Direction();
            agentDirection = myFileHandler.player2Direction();
        }else{
            userDirection = myFileHandler.player2Direction();
            agentDirection = myFileHandler.player1Direction();
        }
        List<List<Integer>> boardWeights = myFileHandler.getBoardWeights();
        List<String> evalFunctions = myFileHandler.getEvaluationFunctions();
        List<EvaluationFunction> allEvals = new ArrayList<>();
        boolean checkCurrConfig = myFileHandler.shouldCheckCurrConfig();
        for(String eval: evalFunctions){
            EvaluationFunction evalFunc = new EvaluationFunctionFactory().createEvaluationFunction(eval,
                    specialPieceIndex, myAgentPlayerInfo, myUserPlayerInfo, boardWeights, agentDirection,userDirection,
                    winValue, checkCurrConfig,startingConfig);
            allEvals.add(evalFunc);
        }
        return allEvals;
    }

    private WinType createWinType(int winValue, List<List<Integer>> startingConfig){
        String winTypeStr = myFileHandler.getWinType();
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        boolean checkCurrConfig = myFileHandler.shouldCheckCurrConfig();
        return new WinTypeFactory().createWinType(winTypeStr, emptyState,specialPieceIndex, winValue, checkCurrConfig, startingConfig);
    }

    private GamePieceFactory createGamePieceFactory(){
        String gameType = myFileHandler.getGameType();
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
        //return Collections.unmodifiableList(userPlayer.getPlayerStates());
        return Collections.unmodifiableList(myUserPlayerInfo);
    }

    public List<Integer> getAgentStateInfo(){
        //return Collections.unmodifiableList(agentPlayer.getPlayerStates()); 
        return Collections.unmodifiableList(myAgentPlayerInfo);
    }

    public void restartGame() throws IOException, ParseException {
        userTurn = userIsPlayer1;
        isPieceSelected = false;
//        squareSelected(-1,-1);
//        squareSelected(-1, -1);
        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();
        myGame = new Game(myGamePieces, myFileHandler.loadFileConfiguration(), objectConfig, myFileHandler.getNeighborhood(),
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

    public boolean hasMultiplePiecesPerSquare() {return myFileHandler.hasMultiplePiecesPerSquare();}

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
    public List<List<Integer>> getNumPiecesVisualInfo() { return myGame.getObjectInfo();}

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

    public List<List<Integer>> getPossibleMovesForView() {
        return myGame.possibleMovesForView(); }

    public int getVisualRowsPerSquare() {
        return myFileHandler.getNumRowsPerSquare();
    }

    public int getMaxPiecesPerSquare() {
        return myFileHandler.getMaxObjectsPerSquare();
    }

}
