package ooga.model.engine.pieces.newPieces.ConvertableNeighborFinder;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.newPieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

public class FindNeighborsUntilNoObjects implements ConvertibleNeighborFinder {

    public static final int REVERSE_DIRECTION = -1;
    private List<Integer> myStatesToIgnore;

    public FindNeighborsUntilNoObjects(List<Integer> statesToIgnore){
        myStatesToIgnore = statesToIgnore;
    }
    @Override
    public List<GamePiece> findNeighborsToConvert(Coordinate currCoordinate, Coordinate endCoordinate, int numObjects,
                                                  int playerID, int direction, List<GamePiece> neighbors) {
        List<GamePiece> toConvert = new ArrayList<>();
        int currentXPos = currCoordinate.getRow();
        int currentYPos = currCoordinate.getCol();
        int objectCount = numObjects;
        while(objectCount > 0){
            currentYPos += direction;
            objectCount = iterateOverRow(currentYPos, objectCount, direction, neighbors, toConvert, currentXPos);
            direction *= REVERSE_DIRECTION;
            currentXPos = findNextRow(neighbors, currentXPos);
        }

        return toConvert;
    }

    private int findNextRow(List<GamePiece> neighbors, int currRow){
        for(GamePiece neighbor: neighbors){
            if(neighbor.getPosition().getRow() == currRow + 1){
                return neighbor.getPosition().getRow();
            }
        }
        return 0;
    }

    private int iterateOverRow(int currentYPos, int numObjects, int direction, List<GamePiece> neighbors,
                               List<GamePiece> toConvert, int currentXPos) {
        int objects = numObjects;
        while (objects > 0 && currentYPos >= 0 && currentYPos <= maxYCoord(neighbors)) {
            for(GamePiece neighbor: neighbors){
                if(!myStatesToIgnore.contains(neighbor.getState()) && neighbor.getPosition().getCol() == currentYPos
                && neighbor.getPosition().getRow() == currentXPos){
                    toConvert.add(neighbor);
                    objects--;
                    break;
                }
            }
            currentYPos += direction;
        }
        return objects;
    }

    private int maxYCoord(List<GamePiece> row){
        int max = 0;
        for(GamePiece piece: row){
            if(piece.getPosition().getCol() > max){
                max = piece.getPosition().getCol();
            }
        }
        return max;
    }
}
