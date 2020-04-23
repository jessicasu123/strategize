package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class ForceDirectionChange implements MoveType {
    private boolean reachedPromotion;
    private int myPromotionRow;

    public ForceDirectionChange(int promotionRow){
        myPromotionRow = promotionRow;
    }
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        if(endCoordinateInfo.getRow() == myPromotionRow){
            reachedPromotion = true;
        }
    }

    @Override
    public boolean addOppositeDirection() {
        return reachedPromotion;
    }

    @Override
    public boolean doesTurnChange() {
        return true;
    }
}
