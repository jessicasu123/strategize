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

public class TicTacToeGamePiece implements GamePiece {
    private int myState;
    private Coordinate myPosition;

    public TicTacToeGamePiece(int state, Coordinate position){
        myState = state;
        myPosition = position;
    }

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState() == 0) {
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        myPosition = endCoordinateInfo;
        myState = newState;
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
