package ooga.view;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    //private List<List<Shape>> boardCells;
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
    private Map<Integer, Image> myStateToImageMapping;
    private Map<Integer, String> stateToFileMapping;
    private List<Integer> myUser;
    private List<Integer> myAgent;
    private String boardOutlineColor;
    private boolean multiplePiecesPerSquare;
    private GridPane pane;

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
            if (! multiplePiecesPerSquare) {
                Image img = new Image(PIECES_RESOURCES +  entry.getValue());
                myStateToImageMapping.put(entry.getKey(), img);
            }
        }
    }

    private void initializeValuesBasedOnController(){
        boardColor = "white";
        boardOutlineColor = "black";
        try {
            piecesMove = myController.doPiecesMove();
            possibleMoveImage =  myController.getStartingProperties().get("possibleMove");
            multiplePiecesPerSquare = Boolean.parseBoolean(myController.getStartingProperties().get("MultiplePiecesPerSquare"));
        } catch (Exception e) {
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
        pane = new GridPane();
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
            //List<Shape> boardRow = new ArrayList<>();
            List<BoardCell> row = new ArrayList<>();
            for (int y = 0; y < boardCols; y++) {
                BoardCell boardCell;

                //Rectangle rect = boardCell.getShape();
                if (multiplePiecesPerSquare) {
                    boardCell = new MultiPieceBoardCell(x, y, cellWidth, cellHeight);
                } else {
                    boardCell = new SinglePieceBoardCell(x,y, cellWidth, cellHeight);
                }
                //Rectangle rect = new Rectangle(cellWidth, cellHeight);
                //rect.setId("cell" + x + y);
                //boardRow.add(rect);
                row.add(boardCell);
                pane.add(boardCell.getShape(), y, x);

            }
            //boardCells.add(boardRow);
            myBoardCells.add(row);
        }



    }

    /**
     * updates the visual information of the board based on what it is told to look like
     * @param newUserImage - the new image used to represent the user
     * @param newAgentImage - the new image used to represent the agent
     * @param newBoardColor - the new color of the board
     */
    protected void updateVisuals(String newUserImage, String newAgentImage, String newBoardColor, String mode){
        Image userImg = new Image(PIECES_RESOURCES + newUserImage);
        Image agentImg = new Image(PIECES_RESOURCES + newAgentImage);
        myStateToImageMapping.replace(myUser.get(STATE_ID_POS), userImg);
        myStateToImageMapping.replace(myAgent.get(STATE_ID_POS), agentImg);
        boardColor = newBoardColor;
        boardOutlineColor = mode;
        updateBoardAppearance();
    }

    private void processUserClickOnSquare(BoardCell rect, List<List<Integer>> gameStates, int finalX, int finalY, boolean possibleMove) {
        Image img = findImageForSquare(gameStates);
        if(hasSelectedSquare){
            //TODO: account for mancala where the num of pieces stays same even if you click different things
            myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).updateCellFill(boardColor);
            updatePossibleMoveImageOnSquare(myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY), possibleMove);
        }
        hasSelectedSquare = true;
        lastSquareSelectedX = finalX;
        lastSquareSelectedY = finalY;
        if(hasSelectPiece){
            myBoardCells.get(lastPieceSelectedX).get(lastPieceSelectedY).updateCellFill(boardColor);
            rect.updateImageOnSquare(img);
        }
        if(!piecesMove){
            rect.updateImageOnSquare(img);
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
        int currGameState = gameStates.get(r).get(c);
        Image currImage = myStateToImageMapping.get(currGameState);

        int numPieces = numPiecesInfo.get(r).get(c);

        int imageIndex = 0;
        List<Image> possiblePieceImages = new ArrayList<>();

        if (multiplePiecesPerSquare &&
                (myUser.contains(currGameState) || myAgent.contains(currGameState))) {
            possiblePieceImages = findPieceImage(currGameState);
        }

        //TODO: find better way to do this
        if (multiplePiecesPerSquare &&
                (myUser.get(myUser.size()-1)==currGameState || myAgent.get(myAgent.size()-1)==currGameState)) {
            handleBanks(currSquare, myUser.get(myUser.size()-1)==currGameState);
        }

        for (int i = 0; i < numPieces;i++) {
            if (multiplePiecesPerSquare) {
                currImage = possiblePieceImages.get(imageIndex);
            }
            boolean isPossibleMove = possibleMoves.get(r).get(c)==1;
            updatePossibleMoveImageOnSquare(currSquare, isPossibleMove);
            if (myUser.contains(currGameState)) {
                updatePlayerCell(currImage, currSquare, r, c);
            }
            else if(myAgent.contains(currGameState)) {
                updateAgentCell(currImage, currSquare);
            }else{
                updateEmptyCell(currSquare, r,c, gameStates, isPossibleMove);
            }
            imageIndex++;
            if (imageIndex == possiblePieceImages.size()) imageIndex = 0;
        }
    }

    private void handleBanks(BoardCell currSquare, boolean isUser) {
        if (isUser) currSquare.updateCellFill("blue");
        else currSquare.updateCellFill("pink");
    }

    private List<Image> findPieceImage(int currState) {
        String[] imageNames = stateToFileMapping.get(currState).split(",");
        List<Image> possiblePieceImages = new ArrayList<>();
        for (String image : imageNames) {
            possiblePieceImages.add(new Image(PIECES_RESOURCES + image));
        }
        return possiblePieceImages;
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

    private void updatePlayerCell(Image playerImage, BoardCell currSquare, int r, int c) {
        currSquare.updateImageOnSquare(playerImage);
        //TODO: make sure that user can select their own pieces and count as piece selected
        currSquare.getShape().setOnMouseClicked(e -> handlePieceSelected(r,c, playerImage));
    }

    private void updateEmptyCell(BoardCell currSquare, int r, int c, List<List<Integer>> gameStates, boolean possibleMove) {
        //TODO: make sure Mancala user cannot click empty cell
        clickableCell(currSquare, r,c,gameStates, possibleMove);
    }

    //TODO: allow player cell to become clickable cell for Mancala - read in playerCellClickable from config?
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
            myBoardCells.get(lastSquareSelectedX).get(lastSquareSelectedY).updateCellFill(boardColor);
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
