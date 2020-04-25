package ooga.model.engine.pieces.moveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

/**
 * This class checks to see if a connect4 piece can be added
 * i.e. if the row is the bottom row or if there is a piece below
 * that a new piece can be placed on top of
 * @author Brian Li
 */
public class BelowCheck implements MoveCheck {
    private GamePiece myPiece;
    private int bottomRow;

    /**
     * @param neighbors - list of gamepiece neighbors in column
     * @return the number of rows
     */
    public int findNumRows(List<GamePiece> neighbors) {
        int rowCount = 0;
        for (GamePiece g: neighbors) {
            if (g.getPosition().getCol()==myPiece.getPosition().getCol()) {
                rowCount++;
            }
        }
        return rowCount;
    }

    /**
     * @param startingLocation - the starting location of piece that is calculating its possible moves
     * @param checking - the game piece that is being checked
     * @param neighbors - the neighbors of the piece that is calculating its possible moves
     * @param player - the player which the move is being checked for
     * @param directions - all the legal directions this piece can move in
     * @return whether condition is met
     */
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
