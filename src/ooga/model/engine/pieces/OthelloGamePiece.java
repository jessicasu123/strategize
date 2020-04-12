package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class OthelloGamePiece implements GamePiece {

    public OthelloGamePiece(int status, Coordinate position) {

    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        return null;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {

    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public Coordinate getPosition() {
        return null;
    }
}
