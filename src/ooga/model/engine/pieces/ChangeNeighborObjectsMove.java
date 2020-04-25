package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;


public class ChangeNeighborObjectsMove implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;
    private boolean onlyChangeOpponent;

    /**
     * Responsible for evenly giving the number of objects in the current piece between neighbor pieces
     * as found by the convertibleNeighborFinder. The number of piece own objects are not decremented
     * @author Sanya Kochhar
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     * @param onlyGiveToOpponent - boolean whether objects should only be given to opponent pieces
     */
    public ChangeNeighborObjectsMove(ConvertibleNeighborFinder convertibleNeighborFinder, boolean onlyGiveToOpponent) {
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
        onlyChangeOpponent = onlyGiveToOpponent;
    }

    @Override
    public void completeMoveType(GamePiece selfPiece, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(selfPiece.getPosition(),
                endCoordinateInfo, selfPiece.getNumObjects(), playerState,direction, neighbors);
        int objectsToGive = selfPiece.getNumObjects();
        objectsToGive = objectsToGive/neighborsToConvert.size();
        for (GamePiece neighbor: neighborsToConvert) {
            if (onlyChangeOpponent && neighbor.getState() == selfPiece.getState()) {
                return;
            }
            neighbor.incrementNumObjects(objectsToGive);
        }
    }


}
