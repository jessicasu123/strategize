package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Mancala extends GamePiece {
    private int myMarbles;
    private int myGoalState;
    private int myDirection;

    public Mancala(int state, int goalState, int direction, int numMarbles, Coordinate position) {
        super(state, position);
        myMarbles = numMarbles;
        myGoalState = goalState;
        myDirection = direction;
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(this.getState() == playerID && myMarbles > 0){
            List<GamePiece> myRow = myRowOfPieces(neighbors);
            List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
            int marblesLeft = calculateMovesBasedOffNumMarbles(possibleMoves, myRow, myMarbles, myDirection);
            calculateMovesBasedOffNumMarbles(possibleMoves, myOpponentsRow, marblesLeft, myDirection * -1);
        }
        return possibleMoves;
    }

    private int calculateMovesBasedOffNumMarbles(List<Coordinate> possibleMoves, List<GamePiece> row, int numMarbles, int direction) {
        for (GamePiece piece : row) {
            while (numMarbles > 0) {
                if (piece.getPosition().getXCoord() * direction > row.size() * direction) {
                    numMarbles--;
                    possibleMoves.add(piece.getPosition());
                }
            }
        }
        return numMarbles;
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
            if(square.getState() != this.getState() || square.getState() != myGoalState){
                opponentsRow.add(square);
            }
        }
        return opponentsRow;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
        List<GamePiece> myRow = myRowOfPieces(neighbors);
        List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
        addMarblesToNeighborsBasedOnMove(myRow, myDirection);
        addMarblesToNeighborsBasedOnMove(myOpponentsRow, myDirection * -1);

    }

    private void addMarblesToNeighborsBasedOnMove(List<GamePiece> row, int direction) {
        for (GamePiece piece : row) {
            while (myMarbles > 0) {
                if (piece.getPosition().getXCoord() * direction > row.size() * direction) {
                    this.changeState(-1);
                    piece.changeState(1);
                }
            }
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
