package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OthelloGamePiece extends GamePiece {
    private int myPlayerID;
    private int currRowPos;
    private int currColPos;
    private List<GamePiece> neighborsToConvert;
    private int[][] directions;
    private int lastCheckedDirection;


    public OthelloGamePiece(int status, Coordinate position) {
        super(status, position);
        neighborsToConvert = new ArrayList<>();
        currRowPos = this.getPosition().getXCoord();
        currColPos = this.getPosition().getYCoord();
        directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        lastCheckedDirection = 0;
    }


    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        myPlayerID = playerID;
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState()==0 && checkValidMoveInAnyDirection(neighbors)) {
            possibleMoves.add(this.getPosition());
        }

        return possibleMoves;
    }

    private boolean checkValidMoveInAnyDirection(List<GamePiece> neighbors) {
        for (int i = 0; i < directions.length;i++) {
            int[] direction = directions[i];
            if (checkFlippableDirection(direction[0], direction[1], neighbors)) {
                lastCheckedDirection = i;
                return true;
            }
        }
        return false;
    }

    private boolean checkFlippableDirection(int rowOffset, int colOffset, List<GamePiece> neighbors) {
        int currRow = currRowPos + rowOffset;
        int currCol = currColPos + colOffset;
        int numOpponents = 0;
        List<GamePiece> possibleNeighborsToConvert = new ArrayList<>();
        GamePiece neighbor = getPieceNeighborFromCoordinate(neighbors, new Coordinate(currRow, currCol));
        while (neighbor!=null) {
            if (neighbor.getState()==0) return false;
            else if (neighbor.getState()==myPlayerID && numOpponents==0) return false;
            else if (neighbor.getState()!= myPlayerID && neighbor.getState()!=0) {
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

    private void checkRemainingDirections(List<GamePiece> neighbors) {
        for (int i = lastCheckedDirection; i < directions.length;i++) {
            checkFlippableDirection(directions[i][0], directions[i][1], neighbors);
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

    //TODO: assumption that calculate all possible moves is called FIRST
    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        checkRemainingDirections(neighbors);
        this.changeState(newState);
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.changeState(newState);
        }
    }



}
