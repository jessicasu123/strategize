package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;


public class ChangeNeighborObjectsMove implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;
    private boolean onlyChangeOpponent;

    /**
     * MoveType responsible for evenly giving the number of objects in the current piece between neighbor pieces
     * as found by the convertibleNeighborFinder. The number of piece own objects are not decremented
     *
     * @author Sanya Kochhar
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     * @param onlyGiveToOpponent - boolean whether objects should only be given to opponent pieces
     */
    public ChangeNeighborObjectsMove(ConvertibleNeighborFinder convertibleNeighborFinder, boolean onlyGiveToOpponent) {
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
        onlyChangeOpponent = onlyGiveToOpponent;
    }

    /**
     * Completes the move by distributing an equivalent number of objects as
     * the piece holds to neighbors specified by the neighbor converter
     * Does not change the number of objects the piece itself holds
     * @param selfPiece - piece whose number of objects are to be given
     * @param endCoordinateInfo - the end coordinate for object distribution
     * @param neighbors - the neighbors of selfPiece
     * @param playerState - the player who is carrying out the move
     * @param direction - the direction this move is occurring in
     */
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
