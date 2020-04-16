package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.List;

/**
 *  This interface is used to implement the game pieces for the various games, as generated by the GamePieceFactory
 *  Each piece in this hierarchy will implement the rules of the Games about how pieces move/act
 *  - On a move by a player it will move position, change state, and/or change state based on the game rules
 *  - the Game Pieces also calculate all the possible moves they can make according to the game rules
 *      - this allows moves to be verified
 *      - and allows other parts of the program to see all the possible moves a piece can makes
 *          - this can be useful either for an AI agent
 */

public abstract class GamePiece {
    private int myState;
    private Coordinate myPosition;

    public GamePiece(int state, Coordinate position){
        myState = state;
        myPosition = position;
    }
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
    public abstract List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID);

    /**
     * This method makes a "move": it changes the state and/or position of the game piece and potentially
     *  its neighbors state
     * @param endCoordinateInfo - the coordinate of where this piece will move to
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     *                  a piece being captured)
     */
    public abstract void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int newState);

    public boolean changeTurnAfterMove(){
        return true;
    }

    /**
     * @return the state of the piece
     */
    public int getState(){
        return myState;
    }

    /**
     * This method is so that pieces who have a visual representation can override this
     * By default it will return the state
     * @return the visual representation of this piece
     */
    public int getVisualRepresentation(){
        return myState;
    }
    /**
     *
     * @return the position of the piece
     */
    public Coordinate getPosition(){
        return myPosition;
    }

    /**
     * changes the state of this piece
     * @param newState - the new state this piece will take on
     */
    protected void changeState(int newState){
        myState = newState;
    }

    /**
     * changes the position of this piece
     * @param moveTo - the new position this piece will take on
     */
    protected void move(Coordinate moveTo){
        myPosition = moveTo;
    }
}

