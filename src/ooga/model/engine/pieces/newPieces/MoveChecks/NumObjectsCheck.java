package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * MoveCheck to determine whether the number of objects held by that piece
 * is greater than a comparison number provided by the data file
 * Ex: must have more than 0 pieces to make a move for a piece in Mancala, Chopsticks
 * @author Sanya Kochhar
 */
public class NumObjectsCheck implements MoveCheck{

    private int myComparisonNum;

    public NumObjectsCheck(int numObjectsToCompare){
        myComparisonNum = numObjectsToCompare;
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int player, List<Integer> directions) {
        return checking.getNumObjects() > myComparisonNum;
    }
}
