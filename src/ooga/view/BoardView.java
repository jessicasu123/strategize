package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.*;

/**
 * Responsible for creating the container that holds the visual
 * representation of the game board.
 */
public class BoardView {
    protected List<List<Shape>> boardCells;
    protected int boardRows;
    protected int boardCols;
    protected int paneWidth;
    protected int paneHeight;
    protected GridPane pane;
    public static final int PANE_PADDING = 20;
    public static final int GRID_PADDING = 2;
    public static final int CELL_SPACING = 1;

    private double cellHeight;
    private double cellWidth;

    public BoardView(int width, int height, int rows, int cols) {
        boardCells = new ArrayList<>();
        boardRows = rows;
        boardCols = cols;
        paneWidth = width;
        paneHeight = height;
    }

    /**
     * @return - the container holding the grid where
     * the game will be played
     */
    public VBox getGridContainer() {
        VBox panecontainer = new VBox(PANE_PADDING);
        panecontainer.getChildren().add(makeGrid());
        panecontainer.setAlignment(Pos.CENTER);
        return panecontainer;
    }

    /**
     * This method returns all shapes that represent
     * all the cells on the board.
     * Will be used by the GameView to manipulate the shape's appearance.
     * @return - list of list of shapes that represent the game board.
     */

    public List<List<Shape>> getBoardCells() {
        return boardCells;
    }

    /**
     * creates the grid given a specific board dimension
     * @return Gridpane of board
     */
    private GridPane makeGrid() {
        pane = new GridPane();
        pane.setPadding(new Insets(GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));
        pane.setHgap(CELL_SPACING);
        pane.setVgap(CELL_SPACING);
        cellHeight = paneHeight / boardRows;
        cellWidth = paneWidth / boardCols;
        createCells(cellWidth, cellHeight);
        pane.setAlignment(Pos.TOP_CENTER);
        return pane;
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    /**
     * creates the cells that are added to the board
     * must be overriden by subclasses to allow for flexibility in cell shape
     * @param cellHeight - height of cell
     * @param cellWidth - width of cell
     */
    public void createCells(double cellWidth, double cellHeight) {

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



}
