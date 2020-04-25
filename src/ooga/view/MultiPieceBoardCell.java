package ooga.view;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for creating a board cell
 * that can hold multiple pieces.
 * This is especially relevant for games such as Mancala
 * and Chopsticks.
 *
 * @author: Jessica Su
 */
public class MultiPieceBoardCell extends BoardCell{

    public static final int DEFAULT_PIECE_SIZE = 20;
    public static final String BACKGROUND_COLOR_STYLING = "-fx-background-color: ";
    public static final String BORDER_COLOR_STYLING = "-fx-border-color: ";
    private double pieceWidth;
    private double pieceHeight;
    private List<Double> pieceXPositions;
    private List<Double> pieceYPositions;
    private int totalPiecesPerSquare;
    private int positionIndex;
    private int numRowsPerSquare;
    private int numPiecesPerRow;
    private Pane myCellPane;

    /**
     * Constructor for MultiPieceBoardCell.
     * @param cellWidth - width of cell
     * @param cellHeight - height of cell
     * @param numVisualRows - number of visual rows. This will be useful
     *                      when calculating a fixed set of positions
     *                      where the images should appear.
     * @param maxObjects - maximum number of objects that the cell can hold.
     */
    public MultiPieceBoardCell(int x, int y, double cellWidth, double cellHeight,
                               int numVisualRows, int maxObjects) {
        super(x,y,cellWidth, cellHeight);
        pieceXPositions = new ArrayList<>();
        pieceYPositions = new ArrayList<>();
        totalPiecesPerSquare = maxObjects;
        positionIndex = 0;
        numRowsPerSquare = numVisualRows;
        numPiecesPerRow = totalPiecesPerSquare/numRowsPerSquare;
        pieceWidth = cellWidth/ numPiecesPerRow;
        pieceHeight = cellHeight / numRowsPerSquare;
        createPiecePositions();
    }

    /**
     * Creates a pane that represents the cell.
     * @return - Pane on which multiple images can be added
     */
    @Override
    public Node createCell() {
        myCellPane = new Pane();
        myCellPane.setMinWidth(getCellWidth());
        myCellPane.setMinHeight(getCellHeight());
        return myCellPane;
    }

    /**
     * Allows the user to hover over a multi-piece cell and see
     * how many pieces it holds.
     * @param message
     */
    @Override
    public void setMessage(int message) {
        myCellPane.setOnMouseEntered(e -> createHoverMessage(message));
    }

    private void createHoverMessage(int message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(String.valueOf(message));
        Tooltip.install(myCellPane, tooltip);
    }

    /**
     * Updates the image on the cell. For a multi-piece board cell,
     * the image must be placed in a specified position on a pane.
     * This method will be called as many times as the number of pieces
     * that the cell needs to show.
     * @param image - the image to update the cell with.
     */
    @Override
    public void updateImageOnSquare(Image image) {
        if (positionIndex==totalPiecesPerSquare) positionIndex = 0;
        ImageView pieceImage = new ImageView(image);
        pieceImage.setFitWidth(pieceWidth);
        pieceImage.setFitHeight(pieceHeight);
        pieceImage.setPreserveRatio(true);
        pieceImage.setLayoutX(pieceXPositions.get(positionIndex));
        pieceImage.setLayoutY(pieceYPositions.get(positionIndex));
        myCellPane.getChildren().add(pieceImage);
        positionIndex++;
    }

    /**
     * Allows a different class to choose the position
     * of an image by choosing an index from which
     * to access the list of x positions/y positions
     * generated in this class.
     * @param newIndex - the index at which to get the
     *                 position from the x positions/y positions list.
     */
    @Override
    public void setImagePositionIndex(int newIndex) {
        positionIndex = newIndex;
    }

    /**
     * Updates the cell fill without changing the
     * border color.
     * @param color - the color to set the cell to
     */
    @Override
    public void updateCellFill(String color) {
        myCellPane.setStyle(BACKGROUND_COLOR_STYLING + color);
    }

    /**
     * Clears the contents of the cell.
     */
    @Override
    public void clearFill(String color) {
        if (myCellPane.getChildren().size()!=0) {
            myCellPane.getChildren().clear();
        }
    }

    /**
     * Sets the style of the board cell by updating the fill
     * and creating a border of a certain color.
     * @param boardColor - color of the cell
     * @param boardOutline - outline of the cell
     */
    @Override
    public void setStyle(String boardColor, String boardOutline) {
        updateCellFill(boardColor);
        myCellPane.setStyle(BORDER_COLOR_STYLING + boardOutline);
    }

    public void createPiecePositions() {
        createSetPositions();
    }

    private void createSetPositions() {
        double currXPos = 0;
        double currYPos = 0;
        for (int r = 0; r < numRowsPerSquare; r++) {
            for (int c = 0; c < numPiecesPerRow;c++) {
                pieceXPositions.add(currXPos);
                pieceYPositions.add(currYPos);
                currXPos += pieceWidth;
            }
            currXPos = 0;
            currYPos += pieceHeight;
        }
    }

    private void createRandomPositions() {
        double endX = getCellWidth() - DEFAULT_PIECE_SIZE;
        double endY = getCellHeight() - DEFAULT_PIECE_SIZE;
        for (int i = 0; i < totalPiecesPerSquare;i++) {
            double xPos = (Math.random() * (endX));
            while (pieceXPositions.contains(xPos)) {
                xPos = (Math.random() * (endX));
            }
            pieceXPositions.add(xPos);

            double yPos = (Math.random() * (endY));
            while (pieceYPositions.contains(yPos)) {
                yPos = (Math.random() * (endY));
            }
            pieceYPositions.add(yPos);
        }
        pieceWidth = DEFAULT_PIECE_SIZE;
        pieceHeight = DEFAULT_PIECE_SIZE;
    }

}
