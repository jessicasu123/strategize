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
import java.util.*;

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
    private int userID;
    private int agentID;
    private String userImage;
    private String agentImage;
    private String boardColor;
    private String possibleMoveImage;
    private boolean piecesMove;
    private int lastSquareSelectedX;
    private int lastSquareSelectedY;
    private int lastPieceSelectedX;
    private int lastPieceSelectedY;
    private boolean hasSelectPiece;
    private boolean hasSelectedSquare;

    public BoardView(int width, int height, int rows, int cols, Controller c) {
        boardCells = new ArrayList<>();
        myController = c;
        myBoard = makeGrid(width, height, rows, cols);
        initializeValuesBasedOnController();
    }

    private void initializeValuesBasedOnController(){
        userID = myController.getUserNumber();
        agentID = myController.getAgentNumber();
        userImage = myController.getUserImage();
        agentImage = myController.getAgentImage();
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

    protected void updateVisuals(String newUserImage, String newAgentImage, String newBoardColor){
        userImage = newUserImage;
        agentImage = newAgentImage;
        boardColor = newBoardColor;
        updateBoardAppearance();
    }

    private void processUserClickOnSquare(Shape rect, Image img, int finalX, int finalY) {
        if(hasSelectedSquare){
            boardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).setFill(Color.valueOf(boardColor));
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        if(hasSelectPiece && piecesMove){
            boardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).setFill(Color.valueOf(boardColor));
            updateImageOnSquare(rect, img);
        }
        if(!piecesMove) {
            updateImageOnSquare(rect, img);
        }
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
        if (myController.getPossibleMovesForView().get(r).get(c) == 1 && !possibleMoveImage.equals("") && myController.userTurn()) {
            Image possibleMove = new Image(PIECES_RESOURCES + possibleMoveImage);
            updateImageOnSquare(currSquare, possibleMove);
        }
        if (gameStates.get(r).get(c) == userID) {
            Image player1Image = new Image(PIECES_RESOURCES + userImage);
            updatePlayerCell(player1Image, currSquare, r, c);
        }
        else if (gameStates.get(r).get(c)==agentID) {
            Image player2Image = new Image(PIECES_RESOURCES + agentImage);
            updateAgentCell(player2Image, currSquare);
        } else {
            updateEmptyCell(currSquare, r,c);
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

    private void updateEmptyCell(Shape currSquare, int r, int c) {
        Image playerImg = new Image(PIECES_RESOURCES + userImage);
        EventHandler<MouseEvent> userClick = e -> processUserClickOnSquare(currSquare,playerImg,r,c);
        currSquare.setOnMouseClicked(userClick);
        currSquare.removeEventHandler(MouseEvent.MOUSE_CLICKED, userClick);//can't click on square with player already
    }

    private void handlePieceSelected(int r, int c, Image img) {
        if(piecesMove){
            if(!hasSelectPiece){
                hasSelectPiece = true;
            }else{
                updateImageOnSquare(boardCells.get(lastPieceSelectedX).get(lastPieceSelectedY), img);
                if(hasSelectedSquare){
                    boardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).setFill(Color.valueOf(boardColor));
                }
            }
            lastPieceSelectedX = r;
            lastPieceSelectedY = c;
        }
    }
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
    protected void makeAgentMove(){
        if(!myController.userTurn()){
            myController.playMove();
            PauseTransition wait = new PauseTransition(Duration.seconds(DELAY));
            wait.setOnFinished(e -> updateBoardAppearance());
            wait.play();

        }
    }



}
