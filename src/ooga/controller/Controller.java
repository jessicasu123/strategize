package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.Agent.winTypes.WinType;
import ooga.model.engine.Agent.winTypes.WinTypeFactory;
import ooga.model.engine.Player.PlayerInfoHolder;
import ooga.model.engine.exceptions.*;
import ooga.model.engine.pieces.GamePieceFactory;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinderFactory;
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
    private FileHandler myFileHandler;
    private boolean isPieceSelected;
    private int pieceSelectedX;
    private int pieceSelectedY;
    private int squareSelectedX;
    private int squareSelectedY;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;
    private PlayerInfoHolder myUserPlayerInfoHolder;
    private PlayerInfoHolder myAgentPlayerInfoHolder;
    private Map<Integer, String> myStateToImageMapping;
    private GamePieceCreator myGamePieceCreator;
    private int emptyState;

    public Controller(String fileName, String userID, String opponent, String dimensions) throws InvalidGameTypeException, InvalidFileFormatException {
        gameFileName = fileName;
        myFileHandler = new JSONFileReader(gameFileName, dimensions);
        myFileHandler.parseFile();
        userIsPlayer1 = userID.equals("Player1");
        userTurn = userIsPlayer1;
        myStateToImageMapping = new HashMap<>();
        createUserAndAgentPlayers();
        emptyState = myFileHandler.getEmptyState();
        List<List<Integer>> startingConfiguration = myFileHandler.loadFileConfiguration();
        Agent gameAgent = createAgent(startingConfiguration);
        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();

        makeGamePieceCreator();
        myGame = new Game(myGamePieceCreator, startingConfiguration, objectConfig, myFileHandler.getNeighborhood(), myUserPlayerInfoHolder,
                myAgentPlayerInfoHolder, gameAgent);
    }

    private void createUserAndAgentPlayers() {
        if(userIsPlayer1){
            myUserPlayerInfoHolder = makePlayer(1);
            myAgentPlayerInfoHolder = makePlayer(2);

        }else{
            myUserPlayerInfoHolder = makePlayer(2);
            myAgentPlayerInfoHolder = makePlayer(1);
        }
        addPlayerStateImageInfoToMap(1);
        addPlayerStateImageInfoToMap(2);
    }

    private void makeGamePieceCreator() {
        myGamePieceCreator = new GamePieceCreator(myUserPlayerInfoHolder, myAgentPlayerInfoHolder);
    }

    private PlayerInfoHolder makePlayer(int player) {
        List<Integer> playerStates = myFileHandler.getPlayerStateInfo(player);
        int immovableState = myFileHandler.getImmovableStateForPlayer(player);
        List<MoveCheck> selfMoveChecks = createSelfMoveCheckForPlayer(playerStates, immovableState);
        List<MoveCheck> neighborMoveChecks = createNeighborMoveCheckForPlayer(playerStates, immovableState);
        List<MoveType> moveTypes = createMoveTypesForPlayer(player, playerStates);
        List<Integer> directions = myFileHandler.getDirectionForPlayer(player);
        boolean isPlayer1 = (userIsPlayer1 && player == 1) || (!userIsPlayer1 && player == 1);
        return new PlayerInfoHolder(playerStates, directions, selfMoveChecks,
                neighborMoveChecks, moveTypes,isPlayer1);
    }

    private ConvertibleNeighborFinder createConvertibleNeighborFinderForPlayer(List<Integer> stateToIgnore){
        String finderType = myFileHandler.getConverterType();
        return new ConvertibleNeighborFinderFactory().createNeighborhoodConverterFinder(finderType, stateToIgnore);
    }

    private List<MoveType> createMoveTypesForPlayer(int player, List<Integer> playerStates) throws InvalidMoveTypeException {
        List<MoveType> moveTypes = new ArrayList<>();
        List<String> moveTypeNames = myFileHandler.getMoveTypes();
        boolean convertToEmptyState = myFileHandler.convertToEmptyState();

        List<Integer> statesToIgnore = myFileHandler.getStatesToIgnoreForPlayer(player);

        ConvertibleNeighborFinder neighborFinder = createConvertibleNeighborFinderForPlayer(statesToIgnore);
        int promotionRow = myFileHandler.getPromotionRowForPlayer(player);
        boolean onlyChangeOpponent = myFileHandler.getOnlyChangeOpponent();
        for(String moveTypeName: moveTypeNames){
            MoveType move = new MoveTypeFactory().createMoveType(moveTypeName,neighborFinder,emptyState,
                    convertToEmptyState,promotionRow,playerStates,onlyChangeOpponent);
            moveTypes.add(move);
        }
        return moveTypes;
    }

    private List<MoveCheck> createSelfMoveCheckForPlayer(List<Integer> playerStates, int immovableState) {
        List<String> selfMoveCheck = myFileHandler.getSelfMoveChecks();
        return createMoveCheck(selfMoveCheck, playerStates, immovableState);
    }

    private List<MoveCheck> createMoveCheck(List<String> moveCheckNames, List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
        List<MoveCheck> moveChecks = new ArrayList<>();
        int objToCompare = myFileHandler.getSelfNumObjectsToCompare();
        for (String moveCheckName: moveCheckNames) {
            MoveCheck moveCheck = new MoveCheckFactory().createMoveCheck(moveCheckName,emptyState,
                    playerStates,objToCompare, immovableState);
            moveChecks.add(moveCheck);
        }
        return moveChecks;
    }

    private List<MoveCheck> createNeighborMoveCheckForPlayer(List<Integer> playerStates, int immovableState) {
        List<String> neighborMoveChecks = myFileHandler.getNeighborMoveChecks();
        return createMoveCheck(neighborMoveChecks, playerStates, immovableState);
    }

    private Agent createAgent(List<List<Integer>> startingConfig){
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue, startingConfig);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue, startingConfig);
        List<Integer> agentInfo = myAgentPlayerInfoHolder.getPlayerStates();
        List<Integer> userInfo = myUserPlayerInfoHolder.getPlayerStates();
        return new Agent(winType, allEvals, agentInfo, userInfo);

    }

    private List<EvaluationFunction> createEvaluationFunctions(int winValue, List<List<Integer>> startingConfig) {
        int specialPieceIndex = myFileHandler.getSpecialPieceIndex();
        int userDirection = myUserPlayerInfoHolder.getDirections().get(0);
        int agentDirection = myAgentPlayerInfoHolder.getDirections().get(0);

        List<List<Integer>> boardWeights = myFileHandler.getBoardWeights();
        List<String> evalFunctions = myFileHandler.getEvaluationFunctions();
        List<EvaluationFunction> allEvals = new ArrayList<>();
        boolean checkCurrConfig = myFileHandler.shouldCheckCurrConfig();
        List<Integer> agentInfo = myAgentPlayerInfoHolder.getPlayerStates();
        List<Integer> userInfo = myUserPlayerInfoHolder.getPlayerStates();
        for(String eval: evalFunctions){
            EvaluationFunction evalFunc = new EvaluationFunctionFactory().createEvaluationFunction(eval,
                    specialPieceIndex, agentInfo, userInfo, boardWeights, agentDirection,userDirection,
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

    private void addPlayerStateImageInfoToMap(int playerNum){
        Map<Integer, String> playerMapping = myFileHandler.getStateImageMapping(playerNum);
        for(Map.Entry<Integer, String> entry: playerMapping.entrySet()){
            myStateToImageMapping.put(entry.getKey(), entry.getValue());
        }
    }

    public Map<Integer,String> getSpecialStateColorMapping() {
        Map<Integer,String> specialStateColorMap = myFileHandler.getSpecialStateColorMapping(1);
        specialStateColorMap.putAll(myFileHandler.getSpecialStateColorMapping(2));
        return specialStateColorMap;
    }

    public Map<Integer, String> getStateImageMapping(){
        return myStateToImageMapping;
    }

    public List<Integer> getUserStateInfo(){
        return Collections.unmodifiableList(myUserPlayerInfoHolder.getPlayerStates());
    }

    public List<Integer> getAgentStateInfo(){
        return Collections.unmodifiableList(myAgentPlayerInfoHolder.getPlayerStates());
    }

    public void restartGame() throws IOException, ParseException {
        userTurn = userIsPlayer1;
        isPieceSelected = false;
//        squareSelected(-1,-1);
//        squareSelected(-1, -1);
        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();
        myGame = new Game(myGamePieceCreator, myFileHandler.loadFileConfiguration(), objectConfig, myFileHandler.getNeighborhood(),
                myUserPlayerInfoHolder, myAgentPlayerInfoHolder, createAgent(myFileHandler.loadFileConfiguration()));
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
