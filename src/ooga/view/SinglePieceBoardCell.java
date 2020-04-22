package ooga.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class SinglePieceBoardCell extends BoardCell {

    private Rectangle myShape;

    public SinglePieceBoardCell(int x, int y, double cellWidth, double cellHeight) {
        super(x, y, cellWidth, cellHeight);
        myShape.setId("cell" + x + y);

    }

    @Override
    public Node createCell() {
        myShape = new Rectangle(getCellWidth(), getCellHeight());
        return myShape;
    }


    @Override
    public void updateImageOnSquare(Image image) {
        myShape.setFill(new ImagePattern(image));
    }

    @Override
    public void updateCellFill(String color) {
        myShape.setFill(Color.valueOf(color));
    }

    @Override
    public void clearFill(String color) {
        updateCellFill(color);
    }

    @Override
    public void setStyle(String boardColor, String boardOutline) {
        updateCellFill(boardColor);
        myShape.setStroke(Color.valueOf(boardOutline));
    }
}
