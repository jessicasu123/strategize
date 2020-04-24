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
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Responsible for creating the container that holds the visual
 * representation of the game board.
 */
public class BoardView {
    public static final String PIECES_RESOURCES = "resources/images/pieces/";
    public static final double DELAY = 1;
    public static final int PANE_PADDING = 20;
    public static final int GRID_PADDING = 2;
    public static final int CELL_SPACING = 1;

    private List<List<BoardCell>> myBoardCells;
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
    public static final int STATE_ID_POS = 0;
    private Map<Integer, List<Image>> myStateToImageMapping;
    private Map<Integer, String> mySpecialStateToColorMapping;
    private Map<Integer, String> stateToFileMapping;
    private List<Integer> myUser;
    private List<Integer> myAgent;
    private String boardOutlineColor;
    private boolean multiplePiecesPerSquare;
    private String squareClickType;

    public BoardView(int width, int height, int rows, int cols, Controller c) {
        myBoardCells = new ArrayList<>();
        myController = c;
        myStateToImageMapping = new HashMap<>();
        initializeValuesBasedOnController();
        initializeStateImageMapping();
        myUser = myController.getUserStateInfo();
        myAgent = myController.getAgentStateInfo();
        myBoard = makeGrid(width, height, rows, cols);

    }
    private void initializeStateImageMapping(){
        stateToFileMapping = myController.getStateImageMapping();
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
        boardColor = "white";
        boardOutlineColor = "black";
        piecesMove = myController.doPiecesMove();
        possibleMoveImage =  myController.getStartingProperties().get("possibleMove");
        multiplePiecesPerSquare = myController.hasMultiplePiecesPerSquare();
        squareClickType = myController.getStartingProperties().get("SquareClickType");
        mySpecialStateToColorMapping = myController.getSpecialStateColorMapping();
    }
    /**
     * @return - the container holding the grid where
     * the game will be played
     */
    public VBox getGridContainer() {
        return myBoard;
    }

    /**
     * creates the grid given a specific board dimension
     * @return Gridpane of board
     */
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

    /**
     * creates the cells that are added to the board
     * must be overriden by subclasses to allow for flexibility in cell shape
     * @param cellHeight - height of cell
     * @param cellWidth - width of cell
     */
    private void createCells(double cellWidth, double cellHeight, GridPane pane, int boardRows, int boardCols) {
        for (int x = 0; x < boardRows; x++) {
            List<BoardCell> row = new ArrayList<>();
            for (int y = 0; y < boardCols; y++) {
                BoardCell boardCell;
                if (multiplePiecesPerSquare) {
                    boardCell = new MultiPieceBoardCell(cellWidth, cellHeight,
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
        myStateToImageMapping.replace(myUser.get(STATE_ID_POS), userImages);
        myStateToImageMapping.replace(myAgent.get(STATE_ID_POS), agentImages);
        boardColor = newBoardColor;
        boardOutlineColor = mode;
        updateBoardAppearance();
    }

    private void processUserClickOnSquare(BoardCell rect, List<List<Integer>> gameStates, int finalX, int finalY, boolean possibleMove) {
        Image img = findImageForSquare(gameStates);
        if(hasSelectedSquare && squareClickType.equals("empty")){
            myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).clearFill(boardColor);
            updatePossibleMoveImageOnSquare(myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY), possibleMove);
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        if(hasSelectPiece){
            myBoardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).clearFill(boardColor);
            rect.updateImageOnSquare(img);
        }
        if(!piecesMove && squareClickType.equals("empty")){
            rect.updateImageOnSquare(img);
        }

    }

    private Image findImageForSquare(List<List<Integer>> gameStates) {
        Image img;
        if(hasSelectPiece){
            img = myStateToImageMapping.get(gameStates.get(lastPieceSelectedX).get(lastPieceSelectedY)).get(0);
        }else{
            img = myStateToImageMapping.get(myUser.get(STATE_ID_POS)).get(0);
        }
        return img;
    }


    protected void updateBoardAppearance() {
        List<List<Integer>> gameStates = myController.getGameVisualInfo();
        List<List<Integer>> possibleMoves = myController.getPossibleMovesForView();
        List<List<Integer>> numPiecesInfo = myController.getNumPiecesVisualInfo();
        for (int r = 0; r < myBoardCells.size(); r++) {
            for (int c = 0; c < myBoardCells.get(0).size(); c++) {
                BoardCell currSquare = myBoardCells.get(r).get(c);
                updateCellAppearance(currSquare, r, c, gameStates, possibleMoves, numPiecesInfo);
            }
        }
    }

    private void updateCellAppearance(BoardCell currSquare, int r, int c, List<List<Integer>> gameStates,
                                      List<List<Integer>> possibleMoves, List<List<Integer>> numPiecesInfo) {
        currSquare.setStyle(boardColor, boardOutlineColor);
        currSquare.clearFill(boardColor);
        currSquare.setImagePositionIndex(0);
        int currGameState = gameStates.get(r).get(c);
        Image currImage = null;

        int numPieces = numPiecesInfo.get(r).get(c);
        int imageIndex = 0;
        List<Image> possiblePieceImages = new ArrayList<>();

        if (myUser.contains(currGameState) || myAgent.contains(currGameState)) {
            currImage = myStateToImageMapping.get(currGameState).get(0);
            if (multiplePiecesPerSquare) possiblePieceImages = myStateToImageMapping.get(currGameState);
        }

        if (mySpecialStateToColorMapping.keySet().contains(currGameState)) {
            currSquare.updateCellFill(mySpecialStateToColorMapping.get(currGameState));
        }

        for (int i = 0; i < numPieces;i++) {
            if (multiplePiecesPerSquare) {
                currImage = possiblePieceImages.get(imageIndex);
            }
            boolean isPossibleMove = possibleMoves.get(r).get(c)==1;
            updatePossibleMoveImageOnSquare(currSquare, isPossibleMove);
            if (myUser.contains(currGameState)) {
                updatePlayerCell(currImage, currSquare, r, c, gameStates,isPossibleMove);
            }
            else if(myAgent.contains(currGameState)) {
                updateAgentCell(currImage, currSquare);
            }else{
                updateEmptyCell(currSquare, r,c, gameStates, isPossibleMove);
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

    private void updateAgentCell(Image playerImage, BoardCell currSquare){
        currSquare.updateImageOnSquare(playerImage);
        currSquare.getShape().setOnMouseClicked(null);
    }

    private void updatePlayerCell(Image playerImage, BoardCell currSquare, int r, int c, List<List<Integer>> gameStates, boolean possibleMove) {
        currSquare.updateImageOnSquare(playerImage);
        if (squareClickType.equals("player")) {
            clickableCell(currSquare, r, c, gameStates, possibleMove);
        } else {
            currSquare.getShape().setOnMouseClicked(e -> handlePieceSelected(r,c, playerImage));
        }
    }

    private void updateEmptyCell(BoardCell currSquare, int r, int c, List<List<Integer>> gameStates, boolean possibleMove) {
        if (squareClickType.equals("empty")) {
            clickableCell(currSquare, r,c,gameStates, possibleMove);
        }
    }

    private void clickableCell(BoardCell currSquare, int r, int c, List<List<Integer>> gameStates, boolean possibleMove) {
        EventHandler<MouseEvent> userClick = e -> processUserClickOnSquare(currSquare,gameStates,r,c, possibleMove);
        currSquare.getShape().setOnMouseClicked(userClick);
        currSquare.getShape().removeEventHandler(MouseEvent.MOUSE_CLICKED, userClick);//can't click on square with player already
    }

    private void handlePieceSelected(int r, int c, Image img) {
        if(piecesMove){
            if(hasSelectPiece){
                movePieceBackToOriginalSpot(img);
            }
            hasSelectPiece = true;
            lastPieceSelectedX = r;
            lastPieceSelectedY = c;
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
        if(!myController.userTurn() && !myController.isGameOver()){
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
