package ooga.view;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grid view with recatangular shaped cells.
 */
public class RectangleGridView extends GridView {
    public RectangleGridView(int width, int height, int rows, int cols) {
        super(width, height, rows, cols);
    }

    /**
     * creates cells that are rectangular in shape
     * @param cellWidth - width of cell
     * @param cellHeight - height of cell
     */
    @Override
    public void createCells(double cellWidth, double cellHeight) {
        for (int x = 0; x < boardRows; x++) {
            List<Shape> boardRow = new ArrayList<>();
            for (int y = 0; y < boardCols; y++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(cellWidth);
                rect.setHeight(cellHeight);
                rect.setId("cell" + x + y);
                //updateCellAppearance(rect,x,y);
                boardRow.add(rect);
                pane.add(rect, y, x);
            }
            boardCells.add(boardRow);
        }
    }
}
