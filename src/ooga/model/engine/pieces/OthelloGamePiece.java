package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OthelloGamePiece extends GamePiece {
    private int opponentID;
    private int currRowPos;
    private int currColPos;
    private List<GamePiece> neighborsToConvert;
    private List<GamePiece> horizLeftNeighbors;
    private List<GamePiece> horizRightNeighbors;
    private List<GamePiece> vertTopNeighbors;
    private List<GamePiece> vertBottomNeighbors;
    private List<GamePiece> rightDiagPrevNeighbors;
    private List<GamePiece> rightDiagNextNeighbors;
    private List<GamePiece> leftDiagPrevNeighbors;
    private List<GamePiece> leftDiagNextNeighbors;


    public OthelloGamePiece(int status, Coordinate position) {
        super(status, position);
        neighborsToConvert = new ArrayList<>();
        opponentID = 3 - status; //TODO: figure better way to get opponent ID
        currRowPos = this.getPosition().getXCoord();
        currColPos = this.getPosition().getYCoord();
        initializeSubNeighborhoods();
    }

    private void initializeSubNeighborhoods() {
        horizLeftNeighbors = new ArrayList<>();
        horizRightNeighbors = new ArrayList<>();
        vertTopNeighbors = new ArrayList<>();
        vertBottomNeighbors = new ArrayList<>();
        rightDiagPrevNeighbors = new ArrayList<>();
        rightDiagNextNeighbors = new ArrayList<>();
        leftDiagPrevNeighbors = new ArrayList<>();
        leftDiagNextNeighbors = new ArrayList<>();
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        getSubNeighborhoods(neighbors);
        List<Coordinate> possibleMoves = new ArrayList<>();
        boolean validHorizontalMove = checkHorizontalAdjOpponentPieces();
        boolean validVerticalMove = checkVerticalAdjOpponentPieces();
        boolean validDiagonalMove = checkDiagonalAdjOpponentPieces();

        if (this.getState()==0 &&
                (validHorizontalMove || validVerticalMove || validDiagonalMove)) {
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    private void getSubNeighborhoods(List<GamePiece> allNeighbors) {
        for (GamePiece neighbor: allNeighbors) {
            int neighborRow = neighbor.getPosition().getXCoord();
            int neighborCol = neighbor.getPosition().getYCoord();
            if (neighborCol < currColPos && neighborRow == currRowPos) {
                horizLeftNeighbors.add(neighbor); }
            else if (neighborCol > currColPos && neighborRow == currRowPos) {
                horizRightNeighbors.add(neighbor);}
            else if (neighborRow < currRowPos && neighborCol == currColPos) {
                vertTopNeighbors.add(neighbor);}
            else if (neighborRow > currRowPos && neighborCol== currColPos) {
                vertBottomNeighbors.add(neighbor);}
            else if (neighborRow < currRowPos && neighborCol < currColPos) {
                rightDiagPrevNeighbors.add(neighbor);
            }
            else if (neighborRow > currRowPos && neighborCol > currColPos) {
                rightDiagNextNeighbors.add(neighbor);
            }
            else if (neighborRow < currRowPos && neighborCol > currColPos) {
                leftDiagPrevNeighbors.add(neighbor);
            } else {
                leftDiagNextNeighbors.add(neighbor);
            }
        }
    }

    private boolean checkHorizontalAdjOpponentPieces() {
        Collections.reverse(horizRightNeighbors); //TODO: change to sort
        return checkSubNeighborhood(horizLeftNeighbors, horizRightNeighbors);
    }

    private boolean checkVerticalAdjOpponentPieces() {
        Collections.reverse(vertTopNeighbors);
        return checkSubNeighborhood(vertTopNeighbors, vertBottomNeighbors);
    }

    private boolean opponentBetweenEmptyAndMyPieces(List<GamePiece> neighbors) {
        int numOpponents = 0;
        List<GamePiece> possibleNeighborsToConvert = new ArrayList<>();
        for (GamePiece piece: neighbors) {
            if (piece.getState()==0) return false; //TODO: fix
            if (piece.getState()==opponentID) {
                possibleNeighborsToConvert.add(piece);
                numOpponents++;
            }
            if (piece.getState()==this.getState()&&numOpponents>0) {
                neighborsToConvert.addAll(possibleNeighborsToConvert);
                return true;
            }
        }
        return false;
    }

    private boolean checkSubNeighborhood(List<GamePiece> prevNeighbors, List<GamePiece> nextNeighbors) {
        boolean validMoveInPrevNeighbors = opponentBetweenEmptyAndMyPieces(prevNeighbors);
        boolean validMoveInNextNeighbors = opponentBetweenEmptyAndMyPieces(nextNeighbors);
        return validMoveInPrevNeighbors || validMoveInNextNeighbors;
    }


    private boolean checkDiagonalAdjOpponentPieces() {
        Collections.reverse(rightDiagPrevNeighbors); //TODO: sort instead
        Collections.reverse(leftDiagPrevNeighbors); //TODO: sort instead
        boolean rightDiagHasValid = checkSubNeighborhood(rightDiagPrevNeighbors, rightDiagNextNeighbors);
        boolean leftDiagHasValid = checkSubNeighborhood(leftDiagPrevNeighbors, leftDiagNextNeighbors);
        return rightDiagHasValid || leftDiagHasValid;
    }


    //TODO: assumption that calculate all possible moves is called FIRST
    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        this.changeState(newState);
        for (GamePiece neighbor: neighborsToConvert) {
            neighbor.changeState(newState);
        }
    }


}
