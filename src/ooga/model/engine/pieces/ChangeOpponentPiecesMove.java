package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;

import java.util.List;

public class ChangeOpponentPiecesMove implements MoveType {
    private ConvertibleNeighborFinder myNeighborhoodConverterFinder;
    private int myEmptyState;
    private boolean convertToEmptyState;

    /**
     * @param convertibleNeighborFinder - finds all the neighbors that need to be converted

     */
    public ChangeOpponentPiecesMove(ConvertibleNeighborFinder convertibleNeighborFinder, boolean convertToEmptyState, int emptyState) {
        myEmptyState = emptyState;
        this.convertToEmptyState = convertToEmptyState;
        myNeighborhoodConverterFinder = convertibleNeighborFinder;
    }


    /**
     * Finds all neighbors to possible convert using the neighborhood converter finder and
     * based on the neighborhood converter type.
     * @param moving - the current piece that is being considered
     * @param endCoordinateInfo - the end coordinate of where the piece is potentially moving to
     * @param neighbors - the list of neighbors
     * @param playerState - the current player ID
     */
    @Override
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState, int direction) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(moving.getPosition(), endCoordinateInfo,
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
