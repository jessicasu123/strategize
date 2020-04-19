package ooga.view;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import ooga.controller.Controller;
import org.json.simple.parser.ParseException;
import java.io.IOException;
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
    public static final double DELAY = 0.25;
    public static final int PANE_PADDING = 20;
    public static final int GRID_PADDING = 2;
    public static final int CELL_SPACING = 1;

    private List<List<Shape>> boardCells;
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
    private Map<Integer, Image> myStateToImageMapping;
    private List<Integer> myUser;
    private List<Integer> myAgent;

    public BoardView(int width, int height, int rows, int cols, Controller c) {
        boardCells = new ArrayList<>();
        myController = c;
        myStateToImageMapping = new HashMap<>();
        initializeStateImageMapping();
        myUser = myController.getUserStateInfo();
        myAgent = myController.getAgentStateInfo();
        myBoard = makeGrid(width, height, rows, cols);
        initializeValuesBasedOnController();
    }
    private void initializeStateImageMapping(){
        Map<Integer, String> stateToFileMapping = myController.getStateImageMapping();
        for(Map.Entry<Integer, String> entry: stateToFileMapping.entrySet()){
            Image img = new Image(PIECES_RESOURCES +  entry.getValue());
            myStateToImageMapping.put(entry.getKey(), img);
        }
    }

    private void initializeValuesBasedOnController(){
        boardColor = "white";
        try {
            piecesMove = myController.doPiecesMove();
            possibleMoveImage =  myController.getStartingProperties().get("possibleMove");
        } catch (IOException | ParseException e) {
            System.out.println("error");
        }
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
        VBox panecontainer = new VBox(PANE_PADDING);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));
        pane.setHgap(CELL_SPACING);
        pane.setVgap(CELL_SPACING);
        int cellHeight = paneHeight / boardRows;
        int cellWidth = paneWidth / boardCols;
        createCells(cellWidth, cellHeight, pane, boardRows, boardCols);
        pane.setAlignment(Pos.TOP_CENTER);
        panecontainer.getChildren().add(pane);
        panecontainer.setAlignment(Pos.CENTER);
        return panecontainer;
    }

    /**
     * creates the cells that are added to the board
     * must be overriden by subclasses to allow for flexibility in cell shape
     * @param cellHeight - height of cell
     * @param cellWidth - width of cell
     */
    private void createCells(double cellWidth, double cellHeight, GridPane pane, int boardRows, int boardCols) {
        for (int x = 0; x < boardRows; x++) {
            List<Shape> boardRow = new ArrayList<>();
            for (int y = 0; y < boardCols; y++) {
                Rectangle rect = new Rectangle(cellWidth, cellHeight);
                rect.setId("cell" + x + y);
                boardRow.add(rect);
                pane.add(rect, y, x);
            }
            boardCells.add(boardRow);
        }
    }

    /**
     * updates the visual information of the board based on what it is told to look like
     * @param newUserImage - the new image used to represent the user
     * @param newAgentImage - the new image used to represent the agent
     * @param newBoardColor - the new color of the board
     */
    protected void updateVisuals(String newUserImage, String newAgentImage, String newBoardColor){
        Image userImg = new Image(PIECES_RESOURCES + newUserImage);
        Image agentImg = new Image(PIECES_RESOURCES + newAgentImage);
        myStateToImageMapping.replace(myUser.get(STATE_ID_POS), userImg);
        myStateToImageMapping.replace(myAgent.get(STATE_ID_POS), agentImg);
        boardColor = newBoardColor;
        updateBoardAppearance();
    }

    private void processUserClickOnSquare(Shape rect, List<List<Integer>> gameStates, int finalX, int finalY) {
        Image img = findImageForSquare(gameStates);
        if(hasSelectedSquare){
            boardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).setFill(Color.valueOf(boardColor));
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        if(hasSelectPiece){
            boardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).setFill(Color.valueOf(boardColor));
            updateImageOnSquare(rect, img);
        }
        if(!piecesMove){
            updateImageOnSquare(rect, img);
        }

    }

    private Image findImageForSquare(List<List<Integer>> gameStates) {
        Image img;
        if(hasSelectPiece){
            img = myStateToImageMapping.get(gameStates.get(lastPieceSelectedX).get(lastPieceSelectedY));
        }else{
            img = myStateToImageMapping.get(myUser.get(STATE_ID_POS));
        }
        return img;
    }

    private void updateImageOnSquare(Shape rect, Image img) {
        rect.setFill(new ImagePattern(img));
    }

    protected void updateBoardAppearance() {
        List<List<Integer>> gameStates = myController.getGameVisualInfo();
        for (int r = 0; r < boardCells.size(); r++) {
            for (int c = 0; c < boardCells.get(0).size(); c++) {
                Shape currSquare = boardCells.get(r).get(c);
                updateCellAppearance(currSquare, r, c, gameStates);
            }
        }
    }

    private void updateCellAppearance(Shape currSquare, int r, int c, List<List<Integer>> gameStates) {
        currSquare.setFill(Color.valueOf(boardColor));
        currSquare.setStroke(Color.BLACK);
        int currGameState = gameStates.get(r).get(c);
        if (myController.getPossibleMovesForView().get(r).get(c) == 1 && !possibleMoveImage.equals("") && myController.userTurn()) {
            Image possibleMove = new Image(PIECES_RESOURCES + possibleMoveImage);
            updateImageOnSquare(currSquare, possibleMove);
        }
        if (myUser.contains(currGameState)) {
            Image player1Image = myStateToImageMapping.get(currGameState);
            updatePlayerCell(player1Image, currSquare, r, c);
        }
        else if(myAgent.contains(currGameState)) {
            Image player2Image = myStateToImageMapping.get(currGameState);
            updateAgentCell(player2Image, currSquare);
        }else{
            updateEmptyCell(currSquare, r,c, gameStates);
        }
    }

    private void updateAgentCell(Image playerImage, Shape currSquare){
        updateImageOnSquare(currSquare, playerImage);
        currSquare.setOnMouseClicked(null);
    }

    private void updatePlayerCell(Image playerImage, Shape currSquare, int r, int c) {
        updateImageOnSquare(currSquare, playerImage);
        currSquare.setOnMouseClicked(e -> handlePieceSelected(r,c, playerImage));
    }

    private void updateEmptyCell(Shape currSquare, int r, int c, List<List<Integer>> gameStates) {

        EventHandler<MouseEvent> userClick = e -> processUserClickOnSquare(currSquare,gameStates,r,c);
        currSquare.setOnMouseClicked(userClick);
        currSquare.removeEventHandler(MouseEvent.MOUSE_CLICKED, userClick);//can't click on square with player already
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
        updateImageOnSquare(boardCells.get(lastPieceSelectedX).get(lastPieceSelectedY), img);
        if(hasSelectedSquare){
            boardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).setFill(Color.valueOf(boardColor));
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
        if(!myController.userTurn()){
            myController.playMove();
            PauseTransition wait = new PauseTransition(Duration.seconds(DELAY));
            wait.setOnFinished(e -> updateBoardAppearance());
            wait.play();
        }
    }

}
