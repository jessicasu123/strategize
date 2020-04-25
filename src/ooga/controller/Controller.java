package ooga.controller;

import ooga.model.data.FileHandler;
import ooga.model.data.JSONFileReader;
import ooga.model.engine.*;
import ooga.model.engine.agent.Agent;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunctionFactory;
import ooga.model.engine.agent.winTypes.WinType;
import ooga.model.engine.agent.winTypes.WinTypeFactory;
import ooga.model.engine.neighborhood.Neighborhood;
import ooga.model.engine.neighborhood.NeighborhoodFactory;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.exceptions.*;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinderFactory;
import ooga.model.engine.pieces.GamePieceCreator;
import ooga.model.engine.pieces.moveChecks.MoveCheck;
import ooga.model.engine.pieces.moveChecks.MoveCheckFactory;
import ooga.model.engine.pieces.MoveType;
import ooga.model.engine.pieces.MoveTypeFactory;

import java.util.*;

/**
 * This class is the controller that facilitates communication between the
 * data files, the model and the view. It uses data extracted by the
 * JSON file handler to pass initializing information to the various
 * game components and is queried about game status changes by the board.
 *
 * @author Sanya Kochhar
 */

public class Controller implements ControllerFramework {
    private GameFramework myGame;
    private FileHandler myFileHandler;
    private boolean isPieceSelected;
    private int pieceSelectedRow;
    private int pieceSelectedCol;
    private int squareSelectedRow;
    private int squareSelectedCol;
    private String gameFileName;
    private boolean userIsPlayer1;
    private boolean userTurn;
    private PlayerInfoHolder myUserPlayerInfoHolder;
    private PlayerInfoHolder myAgentPlayerInfoHolder;
    private Map<Integer, String> myStateToImageMapping;
    private GamePieceCreator myGamePieceCreator;
    private int emptyState;

    /**
     * Contructor to create a Controller object
     * @param fileName of the game selected by the user
     * @param userID of the player selected by the user
     * @param dimensions of the board chosen by the user
     * @throws InvalidFileFormatException when file does not match jsonFileReader specifications
     * @throws InvalidNeighborhoodException if specified neighbor type does not exist
     * @throws InvalidMoveCheckException if specified MoveCheck does not exist
     * @throws InvalidEvaluationFunctionException if specified EvaluationFunction does not exist
     * @throws InvalidWinTypeException if specified WinType does not exist
     * @throws InvalidConvertibleNeighborFinderException if specified ConvertibleNeighborFinder does not exist
     */
    public Controller(String fileName, String userID, String dimensions) throws InvalidFileFormatException, InvalidNeighborhoodException, InvalidMoveCheckException, InvalidEvaluationFunctionException, InvalidWinTypeException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
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
        List<Neighborhood> allNeighborhoods = createNeighborhoods(startingConfiguration.size(), startingConfiguration.get(0).size());
        makeGamePieceCreator();
        myGame = new Game(myGamePieceCreator, startingConfiguration, objectConfig, allNeighborhoods, myUserPlayerInfoHolder,
                myAgentPlayerInfoHolder, gameAgent, emptyState);
    }

    private List<Neighborhood> createNeighborhoods(int numRows, int numCols) throws InvalidNeighborhoodException {
        List<Neighborhood> neighborhoods = new ArrayList<>();
        NeighborhoodFactory neighborFactory = new NeighborhoodFactory();
        for(String neighborhood: myFileHandler.getNeighborhood()){
            neighborhoods.add(neighborFactory.createNeighborhood(neighborhood,numRows, numCols));
        }
        return neighborhoods;
    }

    /**
     * Based on user selection, assigns the user and agent players to player 1 or 2
     */
    private void createUserAndAgentPlayers() throws InvalidMoveCheckException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
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

    private void makeGamePieceCreator(){
        myGamePieceCreator = new GamePieceCreator(myUserPlayerInfoHolder, myAgentPlayerInfoHolder);
    }

    private PlayerInfoHolder makePlayer(int player) throws InvalidMoveCheckException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
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

    /**
     * Creates a ConvertibleNeighborFinder based on specification from the data file
     */
    private ConvertibleNeighborFinder createConvertibleNeighborFinderForPlayer(List<Integer> stateToIgnore) throws InvalidConvertibleNeighborFinderException{
        String finderType = myFileHandler.getConverterType();
        return new ConvertibleNeighborFinderFactory().createNeighborhoodConverterFinder(finderType, stateToIgnore);
    }

    /**
     * Creates various MoveTypes specified in the game data file based on player selected
     * @param player for whom MoveTypes are being created
     * @param playerStates used by specific MoveTypes to consider a player's alternate states
     * @return a list of MoveTypes
     */
    private List<MoveType> createMoveTypesForPlayer(int player, List<Integer> playerStates) throws InvalidMoveTypeException, InvalidConvertibleNeighborFinderException {
        List<MoveType> moveTypes = new ArrayList<>();
        List<String> moveTypeNames = myFileHandler.getMoveTypes();
        boolean convertToEmptyState = myFileHandler.convertToEmptyState();

        List<Integer> statesToIgnore = myFileHandler.getStatesToIgnoreForPlayer(player);

        ConvertibleNeighborFinder neighborFinder = createConvertibleNeighborFinderForPlayer(statesToIgnore);
        int promotionRow = myFileHandler.getPromotionRowForPlayer(player);
        boolean onlyChangeOpponent = myFileHandler.getOnlyChangeOpponent();
        int objToCompare = myFileHandler.getNeighborNumObjectsToCompare();
        for(String moveTypeName: moveTypeNames){
            MoveType move = new MoveTypeFactory().createMoveType(moveTypeName,neighborFinder,emptyState,
                    convertToEmptyState,promotionRow,playerStates,onlyChangeOpponent, objToCompare);
            moveTypes.add(move);
        }
        return moveTypes;
    }

    /**
     * Call to creates MoveChecks the piece must perform on itself to calculate potential valid moves
     */
    private List<MoveCheck> createSelfMoveCheckForPlayer(List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
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

    /**
     * Call to create MoveChecks the piece must perform in its neighbors to calculate potential valid moves
     */
    private List<MoveCheck> createNeighborMoveCheckForPlayer(List<Integer> playerStates, int immovableState) throws InvalidMoveCheckException {
        List<String> neighborMoveChecks = myFileHandler.getNeighborMoveChecks();
        return createMoveCheck(neighborMoveChecks, playerStates, immovableState);
    }

    /**
     * Creates the agent to carry out evaluations on the game state and potential moves
     */
    private Agent createAgent(List<List<Integer>> startingConfig) throws InvalidEvaluationFunctionException, InvalidWinTypeException {
        int winValue = myFileHandler.getWinValue();
        WinType winType = createWinType(winValue, startingConfig);
        List<EvaluationFunction> allEvals = createEvaluationFunctions(winValue, startingConfig);
        List<Integer> agentInfo = myAgentPlayerInfoHolder.getPlayerStates();
        List<Integer> userInfo = myUserPlayerInfoHolder.getPlayerStates();
        return new Agent(winType, allEvals, agentInfo, userInfo);

    }

    /**
     * Creates the evaluation functions that compose the agent, as specified in the game data file
     */
    private List<EvaluationFunction> createEvaluationFunctions(int winValue, List<List<Integer>> startingConfig) throws InvalidEvaluationFunctionException{
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

    /**
     * Creates the WinTypes to be used by the agent to evaluate win status,
     * as specified in the data file
     */
    private WinType createWinType(int winValue, List<List<Integer>> startingConfig) throws InvalidWinTypeException{
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

    /**
     * Resets game variables and creates a new game
     */
    public void restartGame() throws InvalidNeighborhoodException, InvalidEvaluationFunctionException, InvalidWinTypeException {
        userTurn = userIsPlayer1;
        isPieceSelected = false;
        List<List<Integer>> startingConfig = myFileHandler.loadFileConfiguration();
        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();
        List<Neighborhood> allNeighborhoods = createNeighborhoods(startingConfig.size(), startingConfig.get(0).size());
        myGame = new Game(myGamePieceCreator, startingConfig, objectConfig, allNeighborhoods, myUserPlayerInfoHolder,
                myAgentPlayerInfoHolder, createAgent(startingConfig), emptyState);
    }

    /**
     * Queries the game if a player passes
     * @return ID of the player who passed
     */
    public String playerPass() {return myGame.whichPlayerPassed();}

    /**
     * Allows view and game to keep track of whose turn it is
     */
    public boolean userTurn(){
        return userTurn;
    }

    /**
     * Returns whether pieces have the ability to move based on the data specificaiton
     */
    public boolean doPiecesMove(){
        return myFileHandler.doPiecesMove();
    }

    /**
     * Communicates file saving actions from the view to the filehandler to pass in the
     * game file name and configuration being saved
     * @param fileName - the String the user indicates they want the file to be saved as
     * @param startingProperties
     */
    @Override
    public void saveANewFile(String fileName, Map<String, String> startingProperties){
        myFileHandler.saveToFile(fileName, myGame.getVisualInfo(), myGame.getObjectInfo());
    }

    /**
     * Allows the view to differentiate whether a piece can contain multiple
     * objects to be displayed by the view
     */
    public boolean hasMultiplePiecesPerSquare() {return myFileHandler.hasMultiplePiecesPerSquare();}

    /**
     * Queries the fileHandler for starting properties
     */
    @Override
    public Map<String,String> getStartingProperties(){
        return myFileHandler.loadFileProperties();
    }

    /**
     * Keeps track of the piece selected by the user on the GUI
     * @param row - row coordinate of piece selected
     * @param col - column coordinate of piece selected
     */
    @Override
    public void pieceSelected(int row, int col) {
        isPieceSelected = true;
        pieceSelectedRow = row;
        pieceSelectedCol = col;
    }

    /**
     * Keeps track of the square selected by the user if
     * the game involves selected a piece to move as well
     * as a destination square to impact
     * @param row - row coordinate of square selected
     * @param col - column coordinate of square selected
     */
    @Override
    public void squareSelected(int row, int col) {
        squareSelectedRow = row;
        squareSelectedCol = col;
    }

    /**
     * Is called by the Make Move button in GameView to make the move on the back-end
     */
    @Override
    public void playMove() throws InvalidMoveException {
        try {
            if (!isPieceSelected) {
                pieceSelected(squareSelectedRow, squareSelectedCol);
            }
            myGame.makeGameMove(new ArrayList<>(List.of(pieceSelectedRow, pieceSelectedCol, squareSelectedRow, squareSelectedCol)));
            isPieceSelected = false;
            userTurn = myGame.isUserTurn();
        }catch(Exception e){
            isPieceSelected = false;
            throw e;
        }
    }

    /**
     * @return configuration of board that is to be represented on screen
     */
    @Override
    public List<List<Integer>> getGameVisualInfo() {
        return myGame.getVisualInfo();
    }

    /**
     * @return configuration of objects on the board
     */
    @Override
    public List<List<Integer>> getNumPiecesVisualInfo() { return myGame.getObjectInfo();}

    /**
     * @return name of game file
     */
    @Override
    public String getGameFileName() {
        return gameFileName;
    }

    /**
     * @return true if the game has ended (win/loss/tie)
     */
    @Override
    public boolean gameOver() {
        return myGame.getEndGameStatus() > 0;
    }

    /**
     * Used to check who won the game to be reflected by the View
     */
    @Override
    public int gameWinner() {
        return myGame.getEndGameStatus();
    }

    /**
     * @return list of possible moves to be visually depicted by faint images on the BoardView
     */
    public List<List<Integer>> getPossibleMovesForView() {
        return myGame.possibleMovesForView(); }

    public int getVisualRowsPerSquare() {
        return myFileHandler.getNumRowsPerSquare();
    }

    public int getMaxPiecesPerSquare() {
        return myFileHandler.getMaxObjectsPerSquare();
    }

    public List<String> getSquareClickTypes() { return myFileHandler.getSquareClickTypes(); }

}
