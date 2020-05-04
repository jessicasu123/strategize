package ooga.model.engine.pieces.convertibleNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * This class is responsible for finding neighbors in ALL 8 directions
 * that could possibly be flipped.
 * The criteria for an opponent being flipped is that there is
 * at least one opponent piece between an empty spot and another player piece.
 *
 * This will be used when checking the validity of a move that requires flipping
 * neighbors and when the move of flipping neighbors is actually executed.
 *
 * @author Jessica Su
 */
public class NeighborsInAllDirectionsFinder implements ConvertibleNeighborFinder {

    private int currRowPos;
    private int currColPos;
    private int[][] directions;
    private List<GamePiece> neighborsToConvert;
    private int myPlayerID;

    public NeighborsInAllDirectionsFinder() {
        neighborsToConvert = new ArrayList<>();
        directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
    }

    /**
     * This method finds all the neighbors in potentially multiple of the 8 directions
     * that are consecutive opponent pieces and followed by a player piece.
     * @param currCoordinate - current (start) coordinate
     * @param endCoordinate - end coordinate (position of the last neighbor to be converted)
     * @param numObjects - number of objects (continue giving number of objects until there are none left)
     * @param playerID - the current player's ID
     * @param direction - the direction of the piece
     * @param neighbors - the list of all possible neighbors to be considered
     * @return
     */
    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                        int playerID, int direction, List<GamePiece> neighbors) {
        neighborsToConvert = new ArrayList<>(); //has to be reset each time this method is called
        myPlayerID = playerID;
        currRowPos = currCoordinate.getRow();
        currColPos = currCoordinate.getCol();
        findFlippableNeighborsInAllDirections(neighbors);
        return neighborsToConvert;
    }

    //check neighbors in all 8 directions and adds neighbors that can be flipped to a neighborsToConvert variable
    private void findFlippableNeighborsInAllDirections(List<GamePiece> neighbors) {
        for (int i = 0; i < directions.length;i++) {
            int[] direction = directions[i];
            checkOneFlippableDirection(currRowPos, currColPos, myPlayerID, direction[0], direction[1], neighbors);
        }
    }
    /**
     * Also used by AllFlippableDirectionsCheck. The logic of checking that there is at least 1 out of 8
     * valid directions to flip and actually flipping all the neighbors is essentially the same.
     * @param  currRowPos - the current row position of the piece whose neighbors are being checked
     * @param currColPos - the current col position of the piece whose neighbors are being checked
     * @param myPlayerID - the integer representing the ID of the player
     * @param rowOffset - the amount to add to the current row to find the row coordinate of the NEIGHBOR
     * @param colOffset - the amount to add to the current col to find the col coordinate of the NEIGHBOR
     * @param neighbors - the list of all the neighbors
     * @return a boolean of whether there is at least ONE neighbor to flip
     */
    public boolean checkOneFlippableDirection(int currRowPos, int currColPos, int myPlayerID,
                                              int rowOffset, int colOffset, List<GamePiece> neighbors) {
        int currRow = currRowPos + rowOffset;
        int currCol = currColPos + colOffset;
        int numOpponents = 0;
        List<GamePiece> possibleNeighborsToConvert = new ArrayList<>();
        GamePiece neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        while (neighbor!=null) {
            if (neighbor.getState()!= myPlayerID && neighbor.getState()!=0) {
                possibleNeighborsToConvert.add(neighbor);
                numOpponents++;
            }
            else if (neighbor.getState()==myPlayerID && numOpponents >0) {
                neighborsToConvert.addAll(possibleNeighborsToConvert);
                return true;
            } else {
                return false;
            }
            currRow += rowOffset;
            currCol += colOffset;
            neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        }
        return false;
    }
    //finds the GamePiece in the List of GamePiece neighbors based on the current coordinate
    private GamePiece getPieceNeighborFromCoordinate(List<GamePiece> neighbors, Coordinate c) {
        for (GamePiece g: neighbors) {
            if (g.getPosition().equals(c)) {
                return g;
            }
        }
        return null;
    }
}
