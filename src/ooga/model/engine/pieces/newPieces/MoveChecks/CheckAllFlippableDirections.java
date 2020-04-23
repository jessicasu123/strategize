package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.FindAllFlippableNeighbors;

import java.util.List;

public class CheckAllFlippableDirections implements MoveCheck{
    private FindAllFlippableNeighbors myNeighborhoodFinder;
    private int[][] myDirections;

    public CheckAllFlippableDirections() {
        myNeighborhoodFinder = new FindAllFlippableNeighbors();
        myDirections = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    }

    //TODO: is the state the playerID?
    @Override
    public boolean isConditionMet(Coordinate startingLocation,GamePiece checking,List<GamePiece> neighbors, int state, List<Integer> directions) {
        for (int i = 0; i < myDirections.length;i++) {
            int[] direction = myDirections[i];
            if (myNeighborhoodFinder.checkFlippableDirection(startingLocation.getRow(),
                    startingLocation.getCol(), state, direction[0], direction[1], neighbors)) {
                return true;
            }
        }
        return false;
    }
}
