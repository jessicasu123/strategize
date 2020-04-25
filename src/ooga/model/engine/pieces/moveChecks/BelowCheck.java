package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

public class BelowCheck implements MoveCheck {
    private GamePiece myPiece;
    private int bottomRow;

    public int findNumRows(List<GamePiece> neighbors) {
        int rowCount = 0;
        for (GamePiece g: neighbors) {
            if (g.getPosition().getCol()==myPiece.getPosition().getCol()) {
                rowCount++;
            }
        }
        return rowCount;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        myPiece = checking;
        bottomRow = findNumRows(neighbors);
        if(! (myPiece.getPosition().getRow()== bottomRow)) {
            for (GamePiece neighbor : neighbors) {
                if (neighbor.getPosition().getRow()-1 == myPiece.getPosition().getRow()) {
                    if (neighbor.getState()!=0) return true;
                }
            }
            return false;
        }
        return true;
    }

}
