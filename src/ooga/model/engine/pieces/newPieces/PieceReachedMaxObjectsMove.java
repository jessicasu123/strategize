package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

public class PieceReachedMaxObjectsMove implements MoveType {
    private int myEmptyState;
    private int myMaxObjects;
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;


    /**
     * @param maxObjects - max number of objects piece can hold without being converted to empty state
     */
    public PieceReachedMaxObjectsMove(int maxObjects, int emptyState, ConvertibleNeighborFinder convertibleNeighborFinder) {
        myMaxObjects = maxObjects;
        myEmptyState = emptyState;
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
    }


    /**
     * Finds all neighbors to possible convert using the neighborhood converter finder and
     * based on the neighborhood converter type.
     * @param selfPiece - the current piece that is being considered
     * @param endCoordinateInfo - the end coordinate of where the piece is potentially moving to
     * @param neighbors - the list of neighbors
     * @param playerState - the current player ID
     */
    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        for (GamePiece neighbor: neighborsToConvert) {
            if (neighbor.getNumObjects() > myMaxObjects) {
                neighbor.incrementNumObjects(-neighbor.getNumObjects());
                neighbor.changeState(myEmptyState);
            }
        }
    }



}
