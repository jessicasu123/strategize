package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.Collection;
import java.util.List;

/**
 * This class is responsible for changing the state of a series of
 * opponents/neighbors, whether it's to the empty state or to the current player state.
 * The neighbors are decided by a ConvertibleNeighborFinder object and passed
 * in as a parameter.
 *
 * @author Jessica Su
 */
public class ChangeOpponentPiecesMove implements MoveType {
    private ConvertibleNeighborFinder myConvertibleNeighborFinder;
    private int myEmptyState;
    private boolean convertToEmptyState;

    /**
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted
     * @param convertToEmptyState - boolean to decide whether to convert to the empty state or not
     * @param emptyState - the integer that specifies the empty state
     */
    public ChangeOpponentPiecesMove(ConvertibleNeighborFinder convertibleNeighborFinder, boolean convertToEmptyState, int emptyState) {
        myEmptyState = emptyState;
        this.convertToEmptyState = convertToEmptyState;
        myConvertibleNeighborFinder = convertibleNeighborFinder;
    }

    /**
     * Converts all the neighbors provided by neighborhood converter finder
     * (based on the neighborhood converter type) to a specified new state.
     *
     * @param moving - the current piece that is being considered
     * @param endCoordinateInfo - the end coordinate of where the piece is potentially moving to
     * @param neighbors - the list of neighbors
     * @param playerState - the current player ID
     * @param direction - the direction of the player
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myConvertibleNeighborFinder.findNeighborsToConvert(moving.getPosition(), endCoordinateInfo,
                moving.getNumObjects(), playerState,direction, neighbors);
        for (GamePiece neighbor: neighborsToConvert) {
            if(convertToEmptyState){
                neighbor.changeState(myEmptyState);
            }else{
                neighbor.changeState(playerState);
            }
        }
    }
}
