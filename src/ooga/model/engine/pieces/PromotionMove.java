package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

/**
 * This class determines if based on the move a piece should be promoted based on reaching a particular row
 * A promotion involves adding the opposite direction to the legal directions a piece can move in
 * and changing the state to a promotion state
 * @author Holly Ansel
 */
public class PromotionMove implements MoveType {
    private int myPromotionRow;
    private int myPromotionState;

    /**
     * @param promotionRow - the row that once this piece reaches it is promoted
     * @param promotionState - the state that once promoted a piece should have
     */
    public PromotionMove(int promotionRow, int promotionState){
        myPromotionRow = promotionRow;
        myPromotionState = promotionState;
    }

    /**
     * If the end coordinate is in the promotion row then the opposite direction is added for this piece
     * and its state is changed to the promotion state
     * @param moving - the game piece that is being moved
     * @param endCoordinateInfo - the end coordinate of where that piece is being moved to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is moving the piece
     * @param direction - the direction this piece is being moved in
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        if(endCoordinateInfo.getRow() == myPromotionRow){
            moving.addDirection(-direction);
            moving.changeState(myPromotionState);
        }
    }

}
