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
     *  - based on the Game rules it gets all of the possible moves
     *  - this acts to validate the move made by a user
     *  - and it helps the AI agent know what its options are
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *                  vertical, and diagonal neighbors
     * @return a list of end coordinates that this piece can move to
     *         for game types that don't move just change state this will just contain itself as the end coordinate
     *         the list will be empty if no moves are possible
     */
    List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors);

    /**
     * METHOD PURPOSE:
     *  - to officially make a move and change the state of the game piece and potentially its neighbors state
     */
    void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors);


}

