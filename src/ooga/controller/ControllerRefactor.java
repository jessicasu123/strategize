//package ooga.controller;
//
//import ooga.model.data.FileHandler;
//import ooga.model.data.JSONFileReader;
//import ooga.model.engine.Game;
//import ooga.model.engine.GameFramework;
//import ooga.model.engine.agent.Agent;
//import ooga.model.engine.neighborhood.Neighborhood;
//import ooga.model.engine.pieces.GamePieceCreator;
//import ooga.model.engine.player.PlayerInfoHolder;
//import ooga.model.exceptions.*;
//
//import java.util.*;
//
///**
// * This class is the controller that facilitates communication between the data files, the model and the view. It uses
// * data extracted by the JSON file handler to pass initializing information to the various game components and is
// * queried about game status changes by the board.
// *@author Sanya Kochhar
// */
//
//public class ControllerRefactor implements ControllerFramework {
//    private GameFramework myGame;
//    private FileHandler myFileHandler;
//    private boolean isPieceSelected;
//    private int pieceSelectedRow;
//    private int pieceSelectedCol;
//    private int squareSelectedRow;
//    private int squareSelectedCol;
//    private String gameFileName;
//    private boolean userIsPlayer1;
//    private boolean userTurn;
//    private PlayerInfoHolder myUserPlayerInfoHolder;
//    private PlayerInfoHolder myAgentPlayerInfoHolder;
//    private Map<Integer, String> myStateToImageMapping;
//    private GamePieceCreator myGamePieceCreator;
//    private int emptyState;
//    private Creator myCreator;
//
//    /**
//     * Constructor to create a Controller object
//     * @param fileName of the game selected by the user
//     * @param userID of the player selected by the user
//     * @param dimensions of the board chosen by the user
//     * @throws InvalidFileFormatException when file does not match jsonFileReader specifications
//     * @throws InvalidNeighborhoodException if specified neighbor type does not exist
//     * @throws InvalidMoveCheckException if specified MoveCheck does not exist
//     * @throws InvalidEvaluationFunctionException if specified EvaluationFunction does not exist
//     * @throws InvalidWinTypeException if specified WinType does not exist
//     * @throws InvalidConvertibleNeighborFinderException if specified ConvertibleNeighborFinder does not exist
//     */
//    public ControllerRefactor(String fileName, String userID, String dimensions) throws InvalidFileFormatException, InvalidNeighborhoodException, InvalidMoveCheckException, InvalidEvaluationFunctionException, InvalidWinTypeException, InvalidConvertibleNeighborFinderException, InvalidMoveTypeException {
//        gameFileName = fileName;
//        myFileHandler = new JSONFileReader(gameFileName, dimensions);
//        myFileHandler.parseFile();
//        userIsPlayer1 = userID.equals("Player1");
//        userTurn = userIsPlayer1;
//        myStateToImageMapping = new HashMap<>();
//        emptyState = myFileHandler.getEmptyState();
//
//        Creator myCreator = new Creator(myFileHandler, userIsPlayer1, int emptyState);
//        myCreator.createUserAndAgentPlayers(myStateToImageMapping);
//        List<List<Integer>> startingConfiguration = myFileHandler.loadFileConfiguration();
//        Agent gameAgent = myCreator.createAgent(startingConfiguration);
//        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();
//        List<Neighborhood> allNeighborhoods = myCreator.createNeighborhoods(startingConfiguration.size(), startingConfiguration.get(0).size());
//        myGamePieceCreator = myCreator.makeGamePieceCreator();
//        myGame = new Game(myGamePieceCreator, startingConfiguration, objectConfig, allNeighborhoods, myUserPlayerInfoHolder,
//                myAgentPlayerInfoHolder, gameAgent, emptyState);
//    }
//// CREATORS ------------------------ LARGELY INTERACT WITH THE FILEHANDLER OR INFO PASSED IN
////    Getters to be used by front-end? ---------------------------
////    private void makePlayerInfoHolders() throws InvalidConvertibleNeighborFinderException, InvalidMoveCheckException, InvalidMoveTypeException {
////        if (userIsPlayer1) {
////            myUserPlayerInfoHolder = myCreator.createPlayerInfo(1, myStateToImageMapping);
////            myAgentPlayerInfoHolder = myCreator.createPlayerInfo(2, myStateToImageMapping);
////        } else {
////            myAgentPlayerInfoHolder = myCreator.createPlayerInfo(1, myStateToImageMapping);
////            myUserPlayerInfoHolder = myCreator.createPlayerInfo(2, myStateToImageMapping);
////        }
////    }
//
//    /**
//     * Gets a mapping of the special states to the colors that represent them.
//     * @return map with keys as special states and values as colors
//     */
//    @Override
//    public Map<Integer,String> getSpecialStateColorMapping() {
//        Map<Integer,String> specialStateColorMap = myFileHandler.getSpecialStateColorMapping(1);
//        specialStateColorMap.putAll(myFileHandler.getSpecialStateColorMapping(2));
//        return specialStateColorMap;
//    }
//
//    /**
//     * Gets a mapping of the states for all players and the images that represent them.
//     * @return map with keys as states and values as images.
//     */
//    @Override
//    public Map<Integer, String> getStateImageMapping(){
//        return myStateToImageMapping;
//    }
//
//    /**
//     * Gets the states that represent the user.
//     * @return list of integers representing the user states.
//     */
//    @Override
//    public List<Integer> getUserStateInfo(){
//        return Collections.unmodifiableList(myUserPlayerInfoHolder.getPlayerStates());
//    }
//
//    /**
//     * Gets the states that represent the agent.
//     * @return list of integers representing the agent states.
//     */
//    @Override
//    public List<Integer> getAgentStateInfo(){
//        return Collections.unmodifiableList(myAgentPlayerInfoHolder.getPlayerStates());
//    }
//
//    /**
//     * Queries the game if a player passes
//     * @return ID of the player who passed
//     */
//    @Override
//    public String playerPass() {return myGame.whichPlayerPassed();}
//
//    /** Allows view and game to keep track of whose turn it is */
//    @Override
//    public boolean userTurn(){
//        return userTurn;
//    }
//
//    /** Returns whether pieces have the ability to move based on the data specification */
//    @Override
//    public boolean doPiecesMove(){
//        return myFileHandler.doPiecesMove();
//    }
//
//    /** @return configuration of board that is to be represented on screen */
//    @Override
//    public List<List<Integer>> getGameVisualInfo() {
//        return myGame.getVisualInfo();
//    }
//
//    /** @return configuration of objects on the board */
//    @Override
//    public List<List<Integer>> getNumPiecesVisualInfo() { return myGame.getObjectInfo();}
//
//    /** @return name of game file */
//    @Override
//    public String getGameFileName() {
//        return gameFileName;
//    }
//
//    /** @return true if the game has ended (win/loss/tie) */
//    @Override
//    public boolean gameOver() {
//        return myGame.getEndGameStatus() > 0;
//    }
//
//    /** Used to check who won the game to be reflected by the View */
//    @Override
//    public int gameWinner() {
//        return myGame.getEndGameStatus();
//    }
//
//    /** @return list of possible moves to be visually depicted by faint images on the BoardView */
//    @Override
//    public List<List<Integer>> getPossibleMovesForView() {
//        return myGame.possibleMovesForView(); }
//
//    /**
//     * Gets the number of rows per square that should be visually represented. Relevant for games with multi-piece cells.
//     * @return number of rows that the images should populate in the view
//     */
//    @Override
//    public int getVisualRowsPerSquare() {
//        return myFileHandler.getNumRowsPerSquare();
//    }
//
//    /** @return the maximum number of pieces per square that the view can show */
//    @Override
//    public int getMaxPiecesPerSquare() {
//        return myFileHandler.getMaxObjectsPerSquare();
//    }
//
//    /**
//     * Gets the types of squares (empty, player, opponent) that a user can click that would be accepted as a valid move.
//     * @return a list of strings representing the supported square click types
//     */
//    @Override
//    public List<String> getSquareClickTypes() { return myFileHandler.getSquareClickTypes(); }
//
//    /**
//     * Passes in the game file name and configuration to be saved from the view to the filehandler
//     * @param fileName - the String the user indicates they want the file to be saved as
//     * @param startingProperties - starting properties
//     */
//    @Override
//    public void saveANewFile(String fileName, Map<String, String> startingProperties){
//        myFileHandler.saveToFile(fileName, myGame.getVisualInfo(), myGame.getObjectInfo());
//    }
//
//    /** Allows the view to differentiate whether a piece can contain multiple objects to be displayed by the view */
//    @Override
//    public boolean hasMultiplePiecesPerSquare() {return myFileHandler.hasMultiplePiecesPerSquare();}
//
//    /** Queries the fileHandler for starting properties */
//    @Override
//    public Map<String,String> getStartingProperties(){
//        return myFileHandler.loadFileProperties();
//    }
//
//    /**
//     * Keeps track of the piece selected by the user on the GUI
//     * @param row - row coordinate of piece selected
//     * @param col - column coordinate of piece selected
//     */
//    public void selectPiece(int row, int col) {
//        isPieceSelected = true;
//        pieceSelectedRow = row;
//        pieceSelectedCol = col;
//    }
//
//    /**
//     * Keeps track of the square selected by the user if the game involves selected a piece to move as well
//     * as a destination square to impact
//     * @param row - row coordinate of square selected
//     * @param col - column coordinate of square selected
//     */
//    public void selectSquare(int row, int col) {
//        squareSelectedRow = row;
//        squareSelectedCol = col;
//    }
//
//    /** Is called by the Make Move button in GameView to make the move on the back-end */
//    @Override
//    public void playMove() throws InvalidMoveException {
//        try {
//            if (!isPieceSelected) {
//                selectPiece(squareSelectedRow, squareSelectedCol);
//            }
//            myGame.makeGameMove(new ArrayList<>(List.of(pieceSelectedRow, pieceSelectedCol, squareSelectedRow, squareSelectedCol)));
//            isPieceSelected = false;
//            userTurn = myGame.isUserTurn();
//        }catch(Exception e){
//            isPieceSelected = false;
//            throw e;
//        }
//    }
//
//    /** Resets game variables and creates a new game */
//    @Override
//    public void restartGame() throws InvalidNeighborhoodException, InvalidEvaluationFunctionException, InvalidWinTypeException {
//        userTurn = userIsPlayer1;
//        isPieceSelected = false;
//        List<List<Integer>> startingConfig = myFileHandler.loadFileConfiguration();
//        List<List<Integer>> objectConfig = myFileHandler.getObjectConfig();
//        List<Neighborhood> allNeighborhoods = myCreator.createNeighborhoods(startingConfig.size(), startingConfig.get(0).size());
//        myGame = new Game(myGamePieceCreator, startingConfig, objectConfig, allNeighborhoods, myUserPlayerInfoHolder,
//                myAgentPlayerInfoHolder, myCreator.createAgent(startingConfig), emptyState);
//    }
//}