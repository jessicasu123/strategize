package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinder;
import ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder.ConvertableNeighborFinderFactory;

import java.util.List;

public class ChangeOpponentPieces implements MoveType {
    private ConvertableNeighborFinder myNeighborhoodConverterFinder;
    private int myEmptyState;
    private boolean convertToEmptyState;
    private ConvertableNeighborFinderFactory converterFactory;

    /**
     * @param convertableNeighborFinder - finds all the neighbors that need to be converted

     */
    public ChangeOpponentPieces(ConvertableNeighborFinder convertableNeighborFinder, boolean convertToEmptyState, int emptyState) {
        myEmptyState = emptyState;
        this.convertToEmptyState = convertToEmptyState;
        myNeighborhoodConverterFinder = convertableNeighborFinder;
    }

//    private void createNeighborhoodConverter(String neighborConverterType) {
//        converterFactory = new ConvertableNeighborFinderFactory();
//        myNeighborhoodConverterFinder = converterFactory.createNeighborhoodConverterFinder(neighborConverterType);
//    }

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

    @Override
    public boolean addOppositeDirection() {
        return false;
    }


}
