package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;


public class TicTacToeGamePiece implements GamePiece {
    private int myState;
    private Coordinate myPosition;

    public TicTacToeGamePiece(int state, Coordinate position){
        myState = state;
        myPosition = position;
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        return null;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors) {

    }

    @Override
    public int getState() {
        return myState;
    }

    @Override
    public Coordinate getPosition() {
        return myPosition;
    }
}
