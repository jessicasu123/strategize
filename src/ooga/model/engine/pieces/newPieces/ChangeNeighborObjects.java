package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;


public class ChangeNeighborObjects implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;

    /**
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     */
    public ChangeNeighborObjects(ConvertibleNeighborFinder convertibleNeighborFinder) {
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
    }

    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        int objectsToGive = selfPiece.getNumObjects();
        objectsToGive = objectsToGive/neighborsToConvert.size();
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.incrementNumObjects(objectsToGive);
        }
    }


}
