package ooga.model.engine.pieces.newPieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.MoveTypes.MoveType;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.NeighborConverterFinder;
import ooga.model.engine.pieces.newPieces.NeighborhoodConverters.NeighborhoodConverterFactory;

import java.util.List;

public class ChangeOpponentPieces implements MoveType {
    private NeighborConverterFinder myNeighborhoodConverterFinder;
    private int stateToConvertTo;
    private int objectsToGive;
    private NeighborhoodConverterFactory converterFactory;

    /**
     * @param neighborConverterFinder - finds all the neighbors that need to be converted
     * @param convertedState - need this parameter because state to convert to
     *                       might not be the same as "newState" in completeMoveType
     */
    public ChangeOpponentPieces(NeighborConverterFinder neighborConverterFinder,int convertedState, int numObjects) {
        stateToConvertTo = convertedState;
        objectsToGive = numObjects;
        myNeighborhoodConverterFinder = neighborConverterFinder;
    }

//    private void createNeighborhoodConverter(String neighborConverterType) {
//        converterFactory = new NeighborhoodConverterFactory();
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
    public void completeMoveType(GamePiece moving, Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
        List<GamePiece> neighborsToConvert = myNeighborhoodConverterFinder.findNeighborsToConvert(moving.getPosition(), endCoordinateInfo,
                objectsToGive, playerState, neighbors);
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.changeState(stateToConvertTo);
        }
    }

}
