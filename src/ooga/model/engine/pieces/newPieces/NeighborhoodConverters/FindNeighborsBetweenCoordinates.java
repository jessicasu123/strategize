package ooga.model.engine.pieces.newPieces.NeighborhoodConverters;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a list of all of the neighbors and a start and end coordinate finds all of the pieces in between the start
 * and end coordinate
 * In between means that that piece is closer to the end distance than the start piece is to the end distance
 *
 */
public class FindNeighborsBetweenCoordinates implements NeighborConverterFinder {


    public static final int SQUARE = 2;

    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects, int playerID,
                                                  List<GamePiece> neighbors) {
        List<GamePiece> neighborsToConvert = new ArrayList<>();
        for(GamePiece neighbor: neighbors){
            if(isOnPathToEndCoord(currCoordinate,neighbor.getPosition(),endCoordinate)){
                neighborsToConvert.add(neighbor);
            }
        }

        return neighborsToConvert;
    }

    private boolean isOnPathToEndCoord(Coordinate curr, Coordinate compareTo, Coordinate goingTo){

        return isShorter(curr.getCol(), compareTo.getCol(), goingTo.getCol()) &&
                isShorter(curr.getRow(),compareTo.getRow(),goingTo.getRow()) &&
                notPastTarget(curr, compareTo,goingTo) &&
                progressFromStart(curr, compareTo, goingTo);
    }

    private boolean isShorter(int curr, int compareTo, int goingTo){
        return Math.abs(compareTo - goingTo) <= Math.abs(curr - goingTo);
    }

    private boolean notPastTarget(Coordinate start, Coordinate compareTo, Coordinate end){
        return euclidenDistance(start, compareTo) < euclidenDistance(start,end);
    }

    private boolean progressFromStart(Coordinate start, Coordinate compareTo, Coordinate end){
        return euclidenDistance(start, end) > euclidenDistance(compareTo, end);
    }
    private int euclidenDistance(Coordinate start, Coordinate end){
            return (int) Math.sqrt(Math.pow(start.getRow() - end.getRow(), SQUARE) + Math.pow(start.getCol() - end.getCol(), SQUARE));
    }

}
