package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the game pieces for Connect4, allowing the board to make moves and calculate all possible moves.
 * The board is initialized to have all coordinates "filled" with game pieces with empty states.
 * When a piece is "placed" in Connect4, it will take the state of the player who played it.
 * @author Brian Li
 */

public class ConnectFourGamePiece extends GamePiece {

    private int ROWS = 5;
    private int myPlayerID;

    public ConnectFourGamePiece(int state, Coordinate position, int numPieces) {
        super(state, position, numPieces);
    }

    /**
     *
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *                  vertical, and diagonal neighbors
     * @param playerID - ID of the player
     * @return - a list of the coordinates that can be moved to
     */

    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        myPlayerID = playerID;
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (this.getState() == 0 && checkBelow(neighbors)) {
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    /**
     *
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *            vertical, and diagonal neighbors
     * @return - a boolean of whether of not a piece exists below or if the piece is placed on the bottom row
     */
    //TODO: replace number with DIM-1
    private boolean checkBelow(List<GamePiece> neighbors){
        if(!(this.getPosition().getRow()==ROWS)) {
            for (GamePiece neighbor : neighbors) {
                if (neighbor.getPosition().getRow()-1 == this.getPosition().getRow()  && neighbor.getPosition().getCol() == this.getPosition().getCol()) {
                    if (neighbor.getState()!=0) return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState) {
        this.move(endCoordinateInfo);
        this.changeState(newState);
    }

}
