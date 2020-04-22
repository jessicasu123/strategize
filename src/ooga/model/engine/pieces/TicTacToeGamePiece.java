package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the game pieces for TicTacToe, allowing the board to make moves and calculate all possible moves.
 * The board is initialized to have all coordinates "filled" with game pieces with empty states.
 * When a piece is "placed" in Tic Tac Toe, it will take the state of the player who played it.
 * @author Sanya Kochhar
 */

public class TicTacToeGamePiece extends GamePiece {


    public TicTacToeGamePiece(int state, Coordinate position, int numPieces){
        super(state, position, numPieces);
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState() == 0) {
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        this.move(endCoordinateInfo);
        this.changeState(newState);
    }

}
