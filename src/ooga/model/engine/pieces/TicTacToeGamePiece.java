package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * so in tic tac toe, the user/agent will create a new piece with a user/agent status (1 or 2). once it has been placed in makemove,
 * we will update its position
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
        for (GamePiece n : neighbors ) {
            if (n.getState() == 0) { // TODO: check how we're representing empty state pieces
                possibleMoves.add(n.getPosition());
            }
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
