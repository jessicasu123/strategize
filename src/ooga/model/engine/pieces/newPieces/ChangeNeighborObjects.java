package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.NeighborConverterFinder;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.NeighborhoodConverterFactory;

import java.util.List;

public class ChangeNeighborObjects implements MoveType {
    private NeighborConverterFinder myNeighborhoodConverterFinder;
    private NeighborhoodConverterFactory converterFactory;
    private int objectsToGive;

    /**
     * @param neighborConverterFinder - finds all the neighbors that need to be converted
     * @param numObjects - number of objects to add to the piece
     */
    public ChangeNeighborObjects(NeighborConverterFinder neighborConverterFinder, int numObjects) {
        objectsToGive = numObjects;
        myNeighborhoodConverterFinder = neighborConverterFinder;
    }

    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.incrementNumObjects(objectsToGive);
        }
    }

    @Override
    public boolean addOppositeDirection() {
        return false;
    }


    @Override
    public boolean doesTurnChange() {
        return true;
    }

}
