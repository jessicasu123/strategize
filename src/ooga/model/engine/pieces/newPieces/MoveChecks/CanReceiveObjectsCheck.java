package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.List;

/**
 * @author Sanya Kochhar
 * Checks if the neighbor piece belongs to the same player and has 0 objects
 */

public class CanReceiveObjectsCheck implements MoveCheck{

    public CanReceiveObjectsCheck(){
    }

    @Override
    public boolean isConditionMet(Coordinate startingLocation, GamePiece checking, List<GamePiece> neighbors, int playerID, List<Integer> directions) {
        return checking.getNumObjects() == 0 && checking.getState() == playerID;
    }
}
