package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

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
public class FindNeighborsBetweenCoordinates implements ConvertibleNeighborFinder {


    public static final int SQUARE = 2;

    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID, int direction, List<GamePiece> neighbors) {
        List<GamePiece> neighborsToConvert = new ArrayList<>();
        int currRow = currCoordinate.getRow();
        int currCol = currCoordinate.getCol();
        int targetRow = endCoordinate.getRow();
        int targetCol = endCoordinate.getCol();

        int rowDirection = getDirectionGroup(currRow, targetRow);
        int colDirection = getDirectionGroup(currCol, targetCol);

        checkInBetween(currRow,currCol,rowDirection, colDirection, neighbors,targetRow,targetCol, neighborsToConvert);

        return neighborsToConvert;
    }

    private int getDirectionGroup(int curr, int target) {
        if(curr > target){
            return -1;
        }else if(curr == target){
            return 0;
        }else{
            return 1;
        }
    }

    private void checkInBetween(int currRowPos, int currColPos, int rowOffset, int colOffset, List<GamePiece> neighbors,
                                   int targetRow, int targetCol, List<GamePiece> neighborsToConvert) {
        int currRow = currRowPos + rowOffset;
        int currCol = currColPos + colOffset;
        GamePiece neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        while (currRow != targetRow || currCol != targetCol) {
            if(neighbor != null){
                neighborsToConvert.add(neighbor);
            }
            if(currRow != targetRow) {
                currRow += rowOffset;
            }
            if(currCol != targetCol) {
                currCol += colOffset;
            }
            neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        }
    }

    private GamePiece getPieceNeighborFromCoordinate(List<GamePiece> neighbors, Coordinate c) {
        for (GamePiece g: neighbors) {
            if (g.getPosition().equals(c)) {
                return g;
            }
        }
        return null;
    }



}
