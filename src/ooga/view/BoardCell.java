package ooga.view;

import javafx.scene.Node;
import javafx.scene.image.Image;

public abstract class BoardCell {

    private double myCellWidth;
    private double myCellHeight;

    private Node myShape;

    public BoardCell (int x, int y, double cellWidth, double cellHeight) {
        myCellWidth = cellWidth;
        myCellHeight = cellHeight;
        myShape = createCell();
    }

    public Node getShape() {
        return myShape;
    }

    public double getCellWidth() {return myCellWidth; }

    public double getCellHeight() {return myCellHeight;}

    public void setMessage(int message) {}

    public abstract Node createCell();

    public abstract void updateImageOnSquare(Image image);

    public abstract void updateCellFill(String color);

    public abstract void clearFill(String color);

    public abstract void setStyle(String boardColor, String boardOutline);


}
