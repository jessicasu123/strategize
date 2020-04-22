package ooga.view;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class MultiPieceBoardCell extends BoardCell{

    public static final int DEFAULT_PIECE_SIZE = 20;

    private double pieceWidth;
    private double pieceHeight;
    private List<Double> pieceXPositions;
    private List<Double> pieceYPositions;
    private int totalPiecesPerSquare;
    private int positionIndex;
    private int numRowsPerSquare;
    private int numPiecesPerRow;

    private Pane myCellPane;

    public MultiPieceBoardCell(double cellWidth, double cellHeight,
                               int numVisualRows, int maxObjects) {
        super(cellWidth, cellHeight);
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


    @Override
    public Node createCell() {
        myCellPane = new Pane();
        myCellPane.setMinWidth(getCellWidth());
        myCellPane.setMinHeight(getCellHeight());

        return myCellPane;
    }

    @Override
    public void setMessage(int message) {
        myCellPane.setOnMouseEntered(e -> createHoverMessage(message));
    }

    private void createHoverMessage(int message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(String.valueOf(message));
        Tooltip.install(myCellPane, tooltip);
    }

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

    @Override
    public void setImagePositionIndex(int newIndex) {
        positionIndex = newIndex;
    }

    @Override
    public void updateCellFill(String color) {
        myCellPane.setStyle("-fx-background-color: " + color);
    }

    @Override
    public void clearFill(String color) {
        if (myCellPane.getChildren().size()!=0) {
            myCellPane.getChildren().clear();
        }
    }

    @Override
    public void setStyle(String boardColor, String boardOutline) {
        updateCellFill(boardColor);
        myCellPane.setStyle("-fx-border-color: " + boardOutline);
    }

    public void createPiecePositions() {
        createSetPositions();
        //createRandomPositions();
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
