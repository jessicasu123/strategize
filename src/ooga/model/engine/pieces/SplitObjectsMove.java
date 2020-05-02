package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;


public class SplitObjectsMove implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;
    private int myEmptyState;

    /**
     * MoveType to split the current piece's objects between itself and the neighbor
     * specified by an endCoordinate that belongs to the same player.

     * @author Sanya Kochhar
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     */
    public SplitObjectsMove(ConvertibleNeighborFinder convertibleNeighborFinder, int emptyState) {
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
        myEmptyState = emptyState;
    }

    /**
     * Completes the move based on the following rules:
     *  - If the piece has an even number of pieces, they are split evenly
     *  - If the piece has an odd number of pieces, one pieces is given to the other player
     *  - If the piece has no pieces left after the split, it becomes an empty state
     * @param selfPiece - piece whose objects are being split
     * @param endCoordinateInfo - the end coordinate of piece where objects are being split to
     * @param neighbors - the neighbors of the game piece being moved
     * @param playerState - the player who is making the move
     * @param direction - the direction this piece is being moved in
     */
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
            neighborPiece.incrementNumObjects(selfPiece.getNumObjects()/2);
            selfPiece.incrementNumObjects(-selfPiece.getNumObjects()/2);
        } else {
            selfPiece.incrementNumObjects(-1);
            neighborPiece.incrementNumObjects(1);
        }
    }

}
