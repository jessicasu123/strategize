package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;


/**
 * PURPOSE OF INTERFACE:
 *  -
 *  -
 *  -
 */
public interface GamePiece {
    /**
     * METHOD PURPOSE:
     *  -
     */
    List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors);

    /**
     * METHOD PURPOSE:
     *  -
     */
    void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors);


}

