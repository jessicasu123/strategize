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
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(myMarbles > 0){
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {

    }
}
