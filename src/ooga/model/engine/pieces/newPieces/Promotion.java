package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class Promotion implements MoveType {
    private int myPromotionRow;
    private int myPromotionState;

    public Promotion(int promotionRow, int promotionState){
        myPromotionRow = promotionRow;
        myPromotionState = promotionState;
    }
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        if(endCoordinateInfo.getRow() == myPromotionRow){
            moving.addDirection(-direction);
            moving.changeState(myPromotionState);
        }
    }

}
