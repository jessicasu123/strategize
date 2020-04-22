package ooga.model.engine.pieces.newPieces.MoveChecks;

import ooga.model.engine.pieces.newPieces.GamePiece;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.ConvertAllDirections;

import java.util.List;

public class CheckAllFlippableDirections implements MoveCheck{
    private ConvertAllDirections myNeighborhoodFinder;
    private int[][] directions;

    public CheckAllFlippableDirections() {
        myNeighborhoodFinder = new ConvertAllDirections();
        directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    }
    @Override
    public boolean isConditionMet(List<GamePiece> neighbors, int state) {
        for (int i = 0; i < directions.length;i++) {
            int[] direction = directions[i];
            if (myNeighborhoodFinder.checkFlippableDirection(direction[0], direction[1], neighbors)) {
                return true;
            }
        }
        return false;
    }
}
