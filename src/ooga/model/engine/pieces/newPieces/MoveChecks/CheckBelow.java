package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

public class CheckBelow implements MoveCheck {
    private GamePiece myPiece;
    private int row;

    public CheckBelow(GamePiece gamePiece){
        myPiece = gamePiece;
    }

    private int findNumColumns(List<GamePiece> neighbors) {
        int colCount = 0;
        for (GamePiece g: neighbors) {
            if (g.getPosition().getCol()==myPiece.getPosition().getCol()) {
                colCount++;
            }
        }
        return colCount;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation,GamePiece checking,List<GamePiece> neighbors, int state, List<Integer> directions) {
        row = findNumColumns(neighbors);
        if(myPiece.getPosition().getRow()==row) {
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
