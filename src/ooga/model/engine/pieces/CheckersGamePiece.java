package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CheckersGamePiece extends GamePiece {

    // direction -> positive = moving towards bottom of board, negative = moving towards top of board
    private int myDirection;
    private final int myPawnState;
    private final int myKingState;
    private boolean isKing;

    //TODO: get empty state from data file
    public CheckersGamePiece(int state, int pawnState, int kingState, int direction, Coordinate position){
        super(state, position);
        myPawnState = pawnState;
        myKingState = kingState;
        myDirection = direction;
        isKing = state == kingState;
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        for(GamePiece neighbor: neighbors){
            if(neighbor.getState() == 0 && isAdjacentDiagonal(neighbor.getPosition())){
                possibleMoves.add(neighbor.getPosition());
            }
        }
        calculateAllPossibleJumps(neighbors, possibleMoves);
        return possibleMoves;
    }

    protected void calculateAllPossibleJumps(List<GamePiece> neighbors, List<Coordinate> possibleMoves) {
        for(GamePiece neighbor: neighbors) {
            if (checkIfOpponentPiece(neighbor) && isAdjacentDiagonal(neighbor.getPosition())) {
                calculateIfLegalJump(neighbors, possibleMoves, neighbor);
            }
        }
    }

    private void calculateIfLegalJump(List<GamePiece> neighbors, List<Coordinate> possibleMoves, GamePiece neighbor) {
        int jumpDirection = yDifference(neighbor.getPosition());
        for (GamePiece endOfPossibleJump : neighbors) {
            if(checkJumpConditions(endOfPossibleJump, neighbor, jumpDirection) && !possibleMoves.contains(endOfPossibleJump.getPosition())){
                possibleMoves.add(endOfPossibleJump.getPosition());
                CheckersGamePiece jumpResult = new CheckersGamePiece(this.getState(), myPawnState, myKingState, myDirection, endOfPossibleJump.getPosition());
                jumpResult.calculateAllPossibleJumps(neighbors, possibleMoves);
            }
        }
    }

    private boolean checkJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor, int jumpDirection){
        return checkYJumpConditions(endOfPossibleJump, neighbor, jumpDirection) &&
                checkXJumpConditions(endOfPossibleJump, neighbor) &&
                endOfPossibleJump.getState() == 0;

    }
    private boolean checkXJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor){
        return  endOfPossibleJump.getPosition().getXCoord() == neighbor.getPosition().getXCoord() + myDirection ||
                isKing && endOfPossibleJump.getPosition().getXCoord() == neighbor.getPosition().getXCoord() + (myDirection * -1);
    }
    private boolean checkYJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor, int jumpDirection){
       return endOfPossibleJump.getPosition().getYCoord() == neighbor.getPosition().getYCoord() + jumpDirection ;
    }
    private boolean checkIfOpponentPiece(GamePiece neighbor) {
        return neighbor.getState() != myPawnState && neighbor.getState() != 0 && neighbor.getState() != myKingState;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        if(isAdjacentDiagonal(endCoordinateInfo)){
            this.move(endCoordinateInfo);
        }else {
            makeJumpMove(endCoordinateInfo, neighbors);
        }
        int endDiagonalLoc = findKingPromotionRow(neighbors);
        if(endCoordinateInfo.getXCoord() == endDiagonalLoc){
            isKing = true;
            this.changeState(myKingState);
        }
    }

    private void makeJumpMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors) {
        int numJumps = Math.abs((this.getPosition().getXCoord() - endCoordinateInfo.getXCoord()) / 2);
        for(int i = 0; i < numJumps; i++){
            for(GamePiece neighbor: neighbors){
                if(isAdjacentDiagonal(neighbor.getPosition()) && isOnPathToEndCoord(neighbor.getPosition(), endCoordinateInfo)){
                    neighbor.changeState(0);
                    jump(neighbor.getPosition());
                }
            }
        }
    }


    private int findKingPromotionRow(List<GamePiece> neighbors) {
        int endDiagonalLoc = 0;
        boolean initiated = false;
        for(GamePiece neighbor: neighbors){
            if(!initiated || neighbor.getPosition().getXCoord() * myDirection > endDiagonalLoc * myDirection){
                initiated = true;
                endDiagonalLoc = neighbor.getPosition().getXCoord();
            }
        }
        return endDiagonalLoc;
    }

    private boolean isOnPathToEndCoord(Coordinate compareTo, Coordinate goingTo){
        return isYDistanceShorter(compareTo, goingTo) && isXDistanceShorter(compareTo, goingTo);
    }

    private boolean isYDistanceShorter(Coordinate compareTo, Coordinate goingTo){
        return Math.abs(compareTo.getXCoord() - goingTo.getXCoord()) < Math.abs(this.getXCoordinate() - goingTo.getXCoord());
    }

    private boolean isXDistanceShorter(Coordinate compareTo, Coordinate goingTo){
        if(isKing){
            return Math.abs(goingTo.getXCoord() - compareTo.getXCoord()) < Math.abs(goingTo.getXCoord() - this.getXCoordinate());
        }
        return myDirection * (goingTo.getXCoord() - compareTo.getXCoord()) < myDirection * (goingTo.getXCoord() - this.getXCoordinate());
    }

    private int yDifference(Coordinate compareTo){
        return compareTo.getYCoord() - this.getYCoordinate();
    }

    private void jump(Coordinate jumpingOver){
        int newXCoord = findNewXCoordinateLocation();
        int newYCoord = findNewYCoordinateLocation(jumpingOver);
        this.move(new Coordinate(newXCoord, newYCoord));
    }

    //see if going over left or right
    private int findNewYCoordinateLocation(Coordinate jumpingOver) {
        int newYCoord = this.getYCoordinate();
        if (yDifference(jumpingOver) > 0){
            newYCoord += 2;
        }else{
            newYCoord -= 2;
        }
        return newYCoord;
    }

    private int findNewXCoordinateLocation() {
        return this.getXCoordinate() + (myDirection * 2);
    }

    private int getXCoordinate(){
        return this.getPosition().getXCoord();
    }

    private int getYCoordinate(){
        return this.getPosition().getYCoord();
    }

    private boolean isAdjacentDiagonal(Coordinate neighbor){
        int neighborXPos = neighbor.getXCoord();
        int neighborYPos = neighbor.getYCoord();

        return isYCoordinateNeighbor(neighborYPos) && (neighborXPos == this.getXCoordinate() + (myDirection) || isKing && neighborXPos == this.getXCoordinate() + (myDirection * -1));
    }

    private boolean isYCoordinateNeighbor(int neighborYPos) {
        return neighborYPos + 1 == this.getYCoordinate() || neighborYPos - 1 == this.getYCoordinate();
    }


}
