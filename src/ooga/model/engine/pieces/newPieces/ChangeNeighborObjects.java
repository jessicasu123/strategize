package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;

import java.util.List;

public class ChangeNeighborObjects implements MoveType {
    private ConvertableNeighborFinder myNeighborhoodConverterFinder;
    private boolean doDivideObjects;

    /**
     * @param convertableNeighborFinder - finds all the neighbors that need to be converted
     * @param divideObjects - boolean to indicate whether to divide objects among neighbors
     */
    public ChangeNeighborObjects(ConvertableNeighborFinder convertableNeighborFinder, boolean divideObjects) {
        doDivideObjects = divideObjects;
        myNeighborhoodConverterFinder = convertableNeighborFinder;
    }

    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        int objectsToGive = selfPiece.getNumObjects();
        if (doDivideObjects) {
            objectsToGive = objectsToGive/neighbors.size();
        }
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.incrementNumObjects(objectsToGive);
        }
    }

    @Override
    public boolean addOppositeDirection() {
        return false;
    }


}
