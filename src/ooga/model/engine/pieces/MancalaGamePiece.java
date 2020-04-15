package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MancalaGamePiece extends GamePiece {
    private int myMarbles;
    private int myGoalState;
    private int myDirection;
    private int myOpponentsGoal;
    private final int myEmptyState;

    public MancalaGamePiece(int state, int goalState,int opponentGoal, int direction, int numMarbles, int emptyState, Coordinate position) {
        super(state, position);
        myMarbles = numMarbles;
        myGoalState = goalState;
        myDirection = direction;
        myOpponentsGoal = opponentGoal;
        myEmptyState = emptyState;
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(this.getState() == playerID && myMarbles > 0){
            List<GamePiece> myRow = myRowOfPieces(neighbors);
            List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
            int endOfMoveInMyRow = findEndOfMoveBasedOffNumMarbles(possibleMoves, myRow, this.getPosition().getYCoord() + myDirection, myMarbles, myDirection);
            int numMarblesLeft = myMarbles - Math.abs(this.getPosition().getYCoord() - endOfMoveInMyRow);
            findEndOfMoveBasedOffNumMarbles(possibleMoves, myOpponentsRow, endOfMoveInMyRow + (myDirection * -1), numMarblesLeft, myDirection * -1);
        }
        return possibleMoves;
    }

    private int findEndOfMoveBasedOffNumMarbles(List<Coordinate> possibleMoves, List<GamePiece> row, int startingPos, int numMarbles, int direction) {
        int currentPos = startingPos;
        while (numMarbles > 0 && startingPos >= 0 && startingPos <= maxYCoord(row)) {
            currentPos = startingPos;
            for (GamePiece piece : row) {
                if (piece.getPosition().getYCoord() == currentPos) {
                    numMarbles--;
                    if(numMarbles == 0){
                        possibleMoves.add(piece.getPosition());
                        return currentPos;
                    }
                    break;
                }
            }
            startingPos += direction;
        }
        return currentPos;
    }

    private int maxYCoord(List<GamePiece> row){
        int max = 0;
        for(GamePiece piece: row){
            if(piece.getPosition().getYCoord() > max){
                max = piece.getPosition().getYCoord();
            }
        }
        return max;
    }

    private List<GamePiece> myRowOfPieces(List<GamePiece> neighbors){
        List<GamePiece> myRow = new ArrayList<>();
        for(GamePiece square: neighbors){
            if(square.getState() == this.getState() || square.getState() == myGoalState){
                myRow.add(square);
            }
        }
        return myRow;
    }

    private List<GamePiece> myOpponentRowOfPieces(List<GamePiece> neighbors){
        List<GamePiece> opponentsRow = new ArrayList<>();
        for(GamePiece square: neighbors){
            if(square.getState() != this.getState() && square.getState() != myGoalState && square.getState() != myEmptyState
                    && square.getState() != myOpponentsGoal){
                opponentsRow.add(square);
            }
        }
        return opponentsRow;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
        List<GamePiece> myRow = myRowOfPieces(neighbors);
        List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
        int numMarblesBefore = myMarbles;
        addMarblesToNeighborsBasedOnMove(myRow, this.getPosition().getYCoord() + myDirection, myDirection);
        int newStartingPos = this.getPosition().getYCoord() + (myDirection * (numMarblesBefore - myMarbles)) + + (myDirection * -1);
        addMarblesToNeighborsBasedOnMove(myOpponentsRow, newStartingPos, myDirection * -1);

    }

    private void addMarblesToNeighborsBasedOnMove(List<GamePiece> row, int startingPos, int direction) {
        int currentPos = startingPos;
        while (myMarbles > 0 && currentPos >= 0 && currentPos <= maxYCoord(row)) {
            for (GamePiece piece : row) {
                if (piece.getPosition().getYCoord() == currentPos) {
                    this.changeState(-1);
                    piece.changeState(1);
                    break;
                }
            }
            currentPos += direction;
        }
    }




    @Override
    public int getVisualRepresentation(){
        return myMarbles;
    }

    @Override
    protected void changeState(int numberMarblesToAdd){
        myMarbles += numberMarblesToAdd;
    }
}
