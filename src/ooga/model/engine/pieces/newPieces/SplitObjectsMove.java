package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.time.temporal.Temporal;
import java.util.List;


public class SplitObjectsMove implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;
    private int myEmptyState;

    /**
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     */
    public SplitObjectsMove(ConvertibleNeighborFinder convertibleNeighborFinder, int emptyState) {
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
        myEmptyState = emptyState;
    }

    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        for (GamePiece neighbor: neighborsToConvert) {
            if (neighbor.getState() == selfPiece.getState()) {
                splitObjects(selfPiece, neighbor);
            }
        }
        if (selfPiece.getNumObjects() == 0) {
            selfPiece.changeState(myEmptyState);
        }
    }

    private void splitObjects(GamePiece selfPiece, GamePiece neighborPiece) {
        if (selfPiece.getNumObjects() % 2 == 0) {
            selfPiece.incrementNumObjects(-selfPiece.getNumObjects()/2);
            neighborPiece.incrementNumObjects(selfPiece.getNumObjects()/2);
        } else {
            selfPiece.incrementNumObjects(-1);
            neighborPiece.incrementNumObjects(1);
        }
    }


}
