package ooga.view;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MultiPieceBoardCell extends BoardCell{

    public static final int PIECE_SIZE = 30;
    private List<Double> pieceXPositions;
    private List<Double> pieceYPositions;
    private int totalPiecesPerSquare;
    private int positionIndex;

    private Pane myCellPane;

    public MultiPieceBoardCell(int x, int y, double cellWidth, double cellHeight) {
        super(x,y,cellWidth, cellHeight);
        pieceXPositions = new ArrayList<>();
        pieceYPositions = new ArrayList<>();
        totalPiecesPerSquare = 24; //TODO: don't hardcode
        positionIndex = 0;
        createPiecePositions(PIECE_SIZE);
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

    //TODO: add boolean for special image?
    @Override
    public void updateImageOnSquare(Image image) {
        if (positionIndex==totalPiecesPerSquare) positionIndex = 0;
        ImageView pieceImage = new ImageView(image);
        pieceImage.setFitWidth(PIECE_SIZE);
        pieceImage.setFitHeight(PIECE_SIZE);
        pieceImage.setPreserveRatio(true);
        pieceImage.setLayoutX(pieceXPositions.get(positionIndex));
        pieceImage.setLayoutY(pieceYPositions.get(positionIndex));
        myCellPane.getChildren().add(pieceImage);
        positionIndex++;
    }

    @Override
    public void updateCellFill(String color) {
        myCellPane.setStyle("-fx-background-color: " + color);
    }

    @Override
    public void setStyle(String boardColor, String boardOutline) {
        updateCellFill(boardColor);
        myCellPane.setStyle("-fx-border-color: " + boardOutline);
    }

    public void createPiecePositions(int imageSize) {
        double endX = getCellWidth() - imageSize;
        double endY = getCellHeight() - imageSize;
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
    }
}
