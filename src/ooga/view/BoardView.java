package ooga.view;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ooga.controller.Controller;
import ooga.model.engine.BoardConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Responsible for creating the container that holds the visual
 * representation of the game board. Also responsible for executing
 * player and opponent moves, as well as updating the visual
 * appearance of the board after a move.
 *
 * @author Brian Li, Holly Ansel, Jessica Su
 */
public class BoardView {
    public static final String PIECES_RESOURCES = "resources/images/pieces/";
    public static final double DELAY = 1;
    public static final int PANE_PADDING = 20;
    public static final int GRID_PADDING = 2;
    public static final int CELL_SPACING = 1;
    public static final int STATE_ID_POS = 0;
    public static final int SPECIAL_STATE_ID_POS = 1;
    public static final String EMPTY_CLICK_TYPE = "empty";
    public static final String PLAYER_CLICK_TYPE = "player";
    public static final String AGENT_CLICK_TYPE = "agent";
    public static final String DEFAULT_BOARD_COLOR = "white";
    public static final String DEFAULT_BOARD_OUTLINE = "black";
    public static final String POSSIBLE_MOVE_KEY = "possibleMove";
    public static final String OWN_PLAYER_CLICK_TYPE = "own player";

    private List<List<BoardCell>> myBoardCells;
    private BoardConfiguration gameStates;
    private BoardConfiguration possibleMoves;
    private BoardConfiguration numPiecesInfo;
    private VBox myBoard;
    private Controller myController;
    private String boardColor;
    private String possibleMoveImage;
    private boolean piecesMove;
    private int lastSquareSelectedX;
    private int lastSquareSelectedY;
    private int lastPieceSelectedX;
    private int lastPieceSelectedY;
    private boolean hasSelectPiece;
    private boolean hasSelectedSquare;
    private Map<Integer, List<Image>> myStateToImageMapping;
    private Map<Integer, String> mySpecialStateToColorMapping;
    private List<Integer> myUser;
    private List<Integer> myAgent;
    private String boardOutlineColor;
    private boolean multiplePiecesPerSquare;
    private List<String> squareClickType;

    /**
     * Constructor for BoardView.
     * @param width - width of the board
     * @param height - height of the board
     * @param rows - number of rows in the board
     * @param cols - number of columns in the board
     * @param controller - Controller to ger backend information from
     */
    public BoardView(int width, int height, int rows, int cols, Controller controller) {
        myBoardCells = new ArrayList<>();
        myController = controller;
        myStateToImageMapping = new HashMap<>();
        initializeValuesBasedOnController();
        initializeStateImageMapping();
        myUser = myController.getUserStateInfo();
        myAgent = myController.getAgentStateInfo();
        myBoard = makeGrid(width, height, rows, cols);

    }
    private void initializeStateImageMapping(){
        Map<Integer, String> stateToFileMapping = myController.getStateImageMapping();
        for(Map.Entry<Integer, String> entry: stateToFileMapping.entrySet()){
            List<String> images = List.of(entry.getValue().split(","));
            myStateToImageMapping.put(entry.getKey(), convertStringToImages(images));
        }
    }

    private List<Image> convertStringToImages(List<String> imageNames) {
        List<Image> imageList = new ArrayList<>();
        for (String img: imageNames) {
            imageList.add(new Image(PIECES_RESOURCES + img));
        }
        return imageList;
    }

    private void initializeValuesBasedOnController(){
        boardColor = DEFAULT_BOARD_COLOR;
        boardOutlineColor = DEFAULT_BOARD_OUTLINE;
        piecesMove = myController.doPiecesMove();
        possibleMoveImage =  myController.getStartingProperties().get(POSSIBLE_MOVE_KEY);
        multiplePiecesPerSquare = myController.hasMultiplePiecesPerSquare();
        squareClickType = myController.getSquareClickTypes();
        mySpecialStateToColorMapping = myController.getSpecialStateColorMapping();
    }
    /**
     * @return - the container holding the grid where
     * the game will be played
     */
    protected VBox getGridContainer() {
        return myBoard;
    }


    private VBox makeGrid(int paneWidth, int paneHeight, int boardRows, int boardCols) {
        VBox paneContainer = new VBox(PANE_PADDING);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));
        pane.setHgap(CELL_SPACING);
        pane.setVgap(CELL_SPACING);
        int cellHeight = paneHeight / boardRows;
        int cellWidth = paneWidth / boardCols;
        createCells(cellWidth, cellHeight, pane, boardRows, boardCols);
        pane.setAlignment(Pos.TOP_CENTER);
        paneContainer.getChildren().add(pane);
        paneContainer.setAlignment(Pos.CENTER);
        return paneContainer;
    }

    private void createCells(double cellWidth, double cellHeight, GridPane pane, int boardRows, int boardCols) {
        for (int x = 0; x < boardRows; x++) {
            List<BoardCell> row = new ArrayList<>();
            for (int y = 0; y < boardCols; y++) {
                BoardCell boardCell;
                if (multiplePiecesPerSquare) {
                    boardCell = new MultiPieceBoardCell(x,y,cellWidth, cellHeight,
                            myController.getVisualRowsPerSquare(),
                            myController.getMaxPiecesPerSquare());
                } else {
                    boardCell = new SinglePieceBoardCell(x,y, cellWidth, cellHeight);
                }
                row.add(boardCell);
                pane.add(boardCell.getShape(), y, x);

            }
            myBoardCells.add(row);
        }
    }

    /**
     * updates the visual information of the board based on what it is told to look like
     * @param newUserImages - the new image used to represent the user
     * @param newAgentImages - the new image used to represent the agent
     * @param newBoardColor - the new color of the board
     */
    protected void updateVisuals(List<String> newUserImages, List<String> newAgentImages, String newBoardColor, String mode){
        List<Image> userImages = convertStringToImages(newUserImages);
        List<Image> agentImages = convertStringToImages(newAgentImages);
        updateStateToImageMapping(STATE_ID_POS, userImages, agentImages);
        if (multiplePiecesPerSquare) {
            updateStateToImageMapping(SPECIAL_STATE_ID_POS, userImages, agentImages);
        }
        boardColor = newBoardColor;
        boardOutlineColor = mode;
        updateBoardAppearance();
    }

    private void updateStateToImageMapping(int playerIDPosition, List<Image> userImages, List<Image> agentImages) {
        myStateToImageMapping.replace(myUser.get(playerIDPosition), userImages);
        myStateToImageMapping.replace(myAgent.get(playerIDPosition), agentImages);
    }

    private void processUserClickOnSquare(BoardCell rect,int finalX, int finalY, boolean possibleMove) {
        Image img = findImageForSquare(gameStates);
        if(hasSelectedSquare && squareClickType.contains(EMPTY_CLICK_TYPE)){
            myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).clearFill(boardColor);
            updatePossibleMoveImageOnSquare(myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY), possibleMove);
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        if(hasSelectPiece && ! squareClickType.contains(AGENT_CLICK_TYPE)){
            myBoardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).clearFill(boardColor);
            rect.updateImageOnSquare(img);
        }
        if(!piecesMove && squareClickType.contains(EMPTY_CLICK_TYPE)){
            rect.updateImageOnSquare(img);
        }

    }

    private Image findImageForSquare(BoardConfiguration gameStates) {
        Image img;
        if(hasSelectPiece){
            img = myStateToImageMapping.get(gameStates.getValue(lastPieceSelectedX, lastPieceSelectedY)).get(0);
        }else{
            img = myStateToImageMapping.get(myUser.get(STATE_ID_POS)).get(0);
        }
        return img;
    }


    /**
     * Updates every cell in the board. Involves updating the current image on the cell
     * depending on the player/cell style, updating images to represent possible move locations,
     * and adding action handlers to player/opponent/empty cells as necessary.
     */
    protected void updateBoardAppearance() {
        gameStates = myController.getGameVisualInfo();
        possibleMoves = myController.getPossibleMovesForView();
        numPiecesInfo = myController.getNumPiecesVisualInfo();
        for (int r = 0; r < myBoardCells.size(); r++) {
            for (int c = 0; c < myBoardCells.get(0).size(); c++) {
                BoardCell currSquare = myBoardCells.get(r).get(c);
                updateCellAppearance(currSquare, r, c);
            }
        }
    }

    private void updateCellAppearance(BoardCell currSquare, int r, int c) {
        currSquare.setStyle(boardColor, boardOutlineColor);
        currSquare.clearFill(boardColor);
        int currGameState = gameStates.getValue(r,c);
        Image currImage = null;
        int numPieces = numPiecesInfo.getValue(r,c);

        List<Image> possiblePieceImages = new ArrayList<>();
        if (myUser.contains(currGameState) || myAgent.contains(currGameState)) {
            currImage = myStateToImageMapping.get(currGameState).get(STATE_ID_POS);
            if (multiplePiecesPerSquare) possiblePieceImages = myStateToImageMapping.get(currGameState);
        }

        if (mySpecialStateToColorMapping.containsKey(currGameState)) {
            currSquare.updateCellFill(mySpecialStateToColorMapping.get(currGameState));
        }
        updateAllPiecesOnCell(currSquare, numPieces, currImage,
                currGameState, possiblePieceImages, r, c);
    }

    private void updateAllPiecesOnCell(BoardCell currSquare, int numPieces, Image currImage,
                                       int currGameState, List<Image> possiblePieceImages, int r, int c) {
        int imageIndex = 0;
        currSquare.setImagePositionIndex(imageIndex);
        for (int i = 0; i < numPieces;i++) {
            if (multiplePiecesPerSquare) {
                currImage = possiblePieceImages.get(imageIndex);
            }
            boolean isPossibleMove = possibleMoves.getValue(r,c)==1;
            updatePossibleMoveImageOnSquare(currSquare, isPossibleMove);
            if (myUser.contains(currGameState)) {
                updatePlayerCell(currImage, currSquare, r, c, isPossibleMove);
            }
            else if(myAgent.contains(currGameState)) {
                updateAgentCell(currImage, currSquare, r , c, isPossibleMove);
            }else{
                updateEmptyCell(currSquare, r,c,isPossibleMove);
            }
            imageIndex++;
            if (imageIndex == possiblePieceImages.size()) imageIndex = 0;
        }
        currSquare.setMessage(numPieces);
    }


    private void updatePossibleMoveImageOnSquare(BoardCell currSquare, boolean isPossibleMove) {
        if (isPossibleMove && !possibleMoveImage.equals("") && myController.userTurn()) {
            Image possibleMove = new Image(PIECES_RESOURCES + possibleMoveImage);
            currSquare.updateImageOnSquare(possibleMove);
        }
    }

    private void updateAgentCell(Image playerImage, BoardCell currSquare, int r, int c, boolean possibleMove){
        currSquare.updateImageOnSquare(playerImage);
        if (squareClickType.contains(AGENT_CLICK_TYPE)) {
            clickableCell(currSquare, r, c, possibleMove);
        } else {
            currSquare.getShape().setOnMouseClicked(null);
        }

    }

    private void updatePlayerCell(Image playerImage, BoardCell currSquare, int r, int c,
                                  boolean possibleMove) {
        currSquare.updateImageOnSquare(playerImage);
        makePlayerCellClickable(playerImage, currSquare, r, c, possibleMove);
    }

    private void makePlayerCellClickable(Image playerImage, BoardCell currSquare, int r, int c,
                                         boolean possibleMove) {
        EventHandler<MouseEvent> pieceSelected = e -> handlePieceSelected(r,c, playerImage, possibleMove);
        if (squareClickType.contains(PLAYER_CLICK_TYPE)) {
            clickableCell(currSquare, r, c,possibleMove);
        } else if (squareClickType.contains(OWN_PLAYER_CLICK_TYPE) && hasSelectPiece) {
            currSquare.getShape().removeEventHandler(MouseEvent.MOUSE_CLICKED, pieceSelected);
            clickableCell(currSquare, r, c, possibleMove);
        }
        else {
            currSquare.getShape().setOnMouseClicked(pieceSelected);
        }
    }

    private List<BoardCell> findOtherOwnPlayerCells() {
        List<BoardCell> otherPlayerCells = new ArrayList<>();
        for (int r = 0; r < myBoardCells.size();r++) {
            for (int c= 0; c < myBoardCells.get(0).size();c++) {
                if (myUser.contains(gameStates.getValue(r,c))) {
                    otherPlayerCells.add(myBoardCells.get(r).get(c));
                }
            }
        }
        return otherPlayerCells;
    }

    private void allowOtherOwnPlayerCellsToBeClicked(Image img, boolean possibleMove) {
        List<BoardCell> otherPlayers = findOtherOwnPlayerCells();
        for (int i = 0; i < otherPlayers.size();i++) {
            makePlayerCellClickable(img, otherPlayers.get(i), otherPlayers.get(i).getRow(),
                    otherPlayers.get(i).getCol(), possibleMove);
        }
    }

    private void updateEmptyCell(BoardCell currSquare, int r, int c,
                                 boolean possibleMove) {
        if (squareClickType.contains(EMPTY_CLICK_TYPE)) {
            clickableCell(currSquare, r,c,possibleMove);
        }
    }

    private void clickableCell(BoardCell currSquare, int r, int c,
                               boolean possibleMove) {
        EventHandler<MouseEvent> userClick = e -> processUserClickOnSquare(currSquare,r,c,possibleMove);
        currSquare.getShape().setOnMouseClicked(userClick);
        currSquare.getShape().removeEventHandler(MouseEvent.MOUSE_CLICKED, userClick);
    }


    private void handlePieceSelected(int r, int c, Image img, boolean possibleMove) {
        if(piecesMove || squareClickType.contains(AGENT_CLICK_TYPE)){
            if(hasSelectPiece && piecesMove){
                movePieceBackToOriginalSpot(img);
            }
            hasSelectPiece = true;
            lastPieceSelectedX = r;
            lastPieceSelectedY = c;
            if (squareClickType.contains(OWN_PLAYER_CLICK_TYPE)) {
                allowOtherOwnPlayerCellsToBeClicked(img, possibleMove);
            }
        }
    }

    private void movePieceBackToOriginalSpot(Image img) {
        myBoardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).updateImageOnSquare(img);
        if(hasSelectedSquare){
            myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).clearFill(boardColor);
        }
    }

    /**
     * makes a move indicated by the user on the board
     */
    protected void makeUserMove(){
        if (hasSelectPiece) {
            myController.pieceSelected(lastPieceSelectedX, lastPieceSelectedY);
        }
        myController.squareSelected(lastSquareSelectedX, lastSquareSelectedY);
        myController.playMove();
        hasSelectedSquare = false;
        hasSelectPiece = false;
        updateBoardAppearance();
    }

    /**
     * makes a move for the agent on the board
     */
    protected void makeAgentMove(){
        if(!myController.userTurn() && !myController.gameOver()){
            myController.playMove();
            PauseTransition wait = new PauseTransition(Duration.seconds(DELAY));
            wait.setOnFinished(e -> {
                updateBoardAppearance();
                makeAgentMove();
            });
            wait.play();
        }
    }

}
