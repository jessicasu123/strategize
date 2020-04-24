package ooga.view;

import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * This class represents a cell on the board.
 * Its primary purpose is to allow for flexibility in the creation
 * of multi-piece board cells (for games like Mancala, Chopsticks) AND
 * single-piece board cells (for games like Tic-Tac-Toe, Othello, Checkers,Connect4).
 *
 * @author; Jessica Su
 */
public abstract class BoardCell {
    private double myCellWidth;
    private double myCellHeight;

    private Node myShape;

    /**
     * Constructor for BoardCell.
     * @param cellWidth - width of the cell
     * @param cellHeight - height of the cell
     */
    public BoardCell (double cellWidth, double cellHeight) {
        myCellWidth = cellWidth;
        myCellHeight = cellHeight;
        myShape = createCell();
    }

    /**
     * Returns the Node that represents the board cell.
     * Currently, this can either be a Pane of many images (for multi-piece cells)
     * or a Rectangle with one image (single-piece cells).
     * @return
     */
    public Node getShape() {
        return myShape;
    }

    /**
     * @return width of the cell
     */
    public double getCellWidth() {return myCellWidth; }

    /**
     * @return height of the cell
     */
    public double getCellHeight() {return myCellHeight;}

    /**
     * Allows a cell to convey a message about its state
     * (ex. how many pieces it has).
     * @param message
     */
    public void setMessage(int message) {}

    /**
     * Allows another View class to choose the position of the image on a cell
     * based on positions generated by the BoardCell itself.
     * @param newIndex
     */
    public void setImagePositionIndex(int newIndex) {}

    /**
     * @return - the Node (ex. rectangle, Pane) representing the cell.
     */
    public abstract Node createCell();

    /**
     * Updates the appearance of the cell with image
     * corresponding to each player.
     * @param image - the image to update the cell with.
     */
    public abstract void updateImageOnSquare(Image image);

    /**
     * Updates the background color of the cell.
     * @param color - the color to set the cell to
     */
    public abstract void updateCellFill(String color);

    /**
     * Clears the current cell fill.
     * @param color
     */
    public abstract void clearFill(String color);

    /**
     * Sets the style of the cell using a color and an outline.
     * @param boardColor - color of the cell
     * @param boardOutline - outline of the cell
     */
    public abstract void setStyle(String boardColor, String boardOutline);


}
