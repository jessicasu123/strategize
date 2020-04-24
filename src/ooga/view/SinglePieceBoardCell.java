package ooga.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class is responsible for creating a cell on the board
 * that holds a single image. The image will be automatically
 * scaled to take up the entire size of the cell.
 *
 * @author: Jessica Su
 */
public class SinglePieceBoardCell extends BoardCell {
    private Rectangle myShape;

    /**
     * Constructor for SinglePieceBoardCell
     * @param x - the row position of the cell
     * @param y - the column position of the cell
     * @param cellWidth - the width of the cell
     * @param cellHeight - the height of the cell
     */
    public SinglePieceBoardCell(int x, int y, double cellWidth, double cellHeight) {
        super(cellWidth, cellHeight);
        myShape.setId("cell" + x + y);
    }

    /**
     * Creates a Rectangle that will serve as the cell.
     * @return - Node (rectangle) where a single image will fill the entire space.
     */
    @Override
    public Node createCell() {
        myShape = new Rectangle(getCellWidth(), getCellHeight());
        return myShape;
    }

    /**
     * Updates a SINGLE image on the square to fill the entire area.
     * @param image - the image to update the cell with.
     */
    @Override
    public void updateImageOnSquare(Image image) {
        myShape.setFill(new ImagePattern(image));
    }

    /**
     * Updates the cell fill without changing the border color.
     * @param color - the color to set the cell to
     */
    @Override
    public void updateCellFill(String color) {
        myShape.setFill(Color.valueOf(color));
    }

    /**
     * Clears the current cell fill by setting it
     * to another fill.
     */
    @Override
    public void clearFill(String color) {
        updateCellFill(color);
    }

    /**
     * Sets both the color and outline of the cell.
     * @param boardColor - color of the cell
     * @param boardOutline - outline of the cell
     */
    @Override
    public void setStyle(String boardColor, String boardOutline) {
        updateCellFill(boardColor);
        myShape.setStroke(Color.valueOf(boardOutline));
    }
}
