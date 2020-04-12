package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

public class OthelloGamePiece extends GamePiece {

    public OthelloGamePiece(int status, Coordinate position) {
        super(status, position);
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        return null;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {

    }


}
