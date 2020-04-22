package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.ConvertAllDirections;

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
    public boolean isConditionMet(List<GamePiece> neighbors, int state) {
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
