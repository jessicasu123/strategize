package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

/**
 * PURPOSE OF API:
 *  - the Game Pieces will implement the rules of the Games about how pieces move/act
 *  - On a move by a player they will move position, change state, and/or change state based on the game rules
 *  - the Game Pieces also calculate all the possible moves they can make according to the game rules
 *      - this allows moves to be verified
 *      - and allows other parts of the program to see all the possible moves a piece can makes
 *          - this can be useful either for an AI agent
 *  - this will later be implemented as an inheritance hierarchy for each game type
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
     *  - to officially make a move and change the state and/or position of the game piece and potentially
     *  its neighbors state
     * @param endCoordinateInfo - the coordinate of where this piece will move to
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     *                  a piece being captured)
     */
    void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors);


}

