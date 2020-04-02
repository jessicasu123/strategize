package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;


public class MockGamePiece implements GamePiece {

    public MockGamePiece(int state, Coordinate position){

    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        return null;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors) {

    }
}
