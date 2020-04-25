package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.moveChecks.MoveCheck;

import java.util.ArrayList;
import java.util.List;
/**
 *  - This class is used to implement the game pieces for the various games, as generated by the GamePieceCreator
 *  - Given the move checks this piece needs to do on itself and possible its neighbors it can calculate all of its
 *  possible moves (whether that be itself or if it moves to another location)
 *  - By being given a list of all of the move types for this piece, it can iterate over them to complete all of the
 *  necessary components to make a move
 *  - This class also has getters so that other parts of the program can get basic information needed about this piece
 *  such as the state, position, and number of objects
 *  - There are also protected methods that allow the move types to set the different components of the game piece
 *  while still maintaining encapsulation to the rest of the program
 *
 */
public class GamePiece {
    public static final int MAIN_DIRECTION_INDEX = 0;
    private int myState;
    private Coordinate myPosition;
    private List<Integer> myDirections;
    private int myMainDirection;
    private int myObjects;
    private List<MoveCheck> myMoveChecks;
    private List<MoveCheck> myNeighborMoveChecks;
    private List<MoveType> myMoveTypes;
    private boolean turnChange;

    /**
     * Creates a game piece
     * @param state - the state of this game piece
     * @param position - the position of this game piece
     * @param numObjects - the number of objects this game piece has
     * @param directions - the directions this piece can move in
     * @param selfMoveChecks - all of the move checks this piece needs to perform on itself
     * @param neighborMoveChecks - all of the move checks this piece needs to perform on its neighbors
     * @param moveTypes - - all of the move types this piece needs to perform
     */
    public GamePiece(int state, Coordinate position, int numObjects, List<Integer> directions, List<MoveCheck> selfMoveChecks,
                     List<MoveCheck> neighborMoveChecks, List<MoveType> moveTypes){
        myState = state;
        myPosition = position;
        myDirections = directions;
        myMainDirection = myDirections.get(MAIN_DIRECTION_INDEX);
        myMoveChecks = selfMoveChecks;
        myNeighborMoveChecks = neighborMoveChecks;
        myMoveTypes = moveTypes;
        myObjects = numObjects;
        turnChange = true;
    }
    /**
     *  - this acts to validate the move made by a user
     *  - and it helps the AI agent know what its options are
     *  - determined by the list of self and neighbor checks this piece has
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *                  vertical, and diagonal neighbors
     * @param playerID - the player is being checked to see all the moves they have of this piece
     * @return a list of end coordinates that this piece can move to
     *         for game types that don't move just change state this will just contain itself as the end coordinate
     *         the list will be empty if no moves are possible
     */
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID){
        List<Coordinate> possibleMoves = new ArrayList<>();
        boolean selfConditionsMet = checkSelfConditions(neighbors, playerID);
        if (selfConditionsMet) {
            if (myNeighborMoveChecks.size() > 0) {
                checkNeighborConditions(neighbors, possibleMoves, playerID);
            } else {
                possibleMoves.add(myPosition);
            }
        }
        return possibleMoves;
    }

    /*
     * @deprecated List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors);
     */

    private boolean checkSelfConditions(List<GamePiece> neighbors, int playerID) {
        for (MoveCheck check : myMoveChecks) {
            if (!check.isConditionMet(myPosition, this, neighbors, playerID, myDirections)) {
                return false;
            }
        }
        return true;
    }

    private void checkNeighborConditions(List<GamePiece> neighbors, List<Coordinate> possibleMoves, int playerID) {
        for (GamePiece neighbor : neighbors) {
            boolean neighborConditionsMet = false;
            for (MoveCheck check : myNeighborMoveChecks) {
                boolean isCheckMet = check.isConditionMet(myPosition, neighbor, neighbors, playerID, myDirections);
                neighborConditionsMet = neighborConditionsMet || isCheckMet;

            }
            if (neighborConditionsMet) {
                possibleMoves.add(neighbor.getPosition());
            }
        }
    }
    /**
     * This method makes a "move": it changes the state and/or position and/or number of objects of the game piece
     * and potentially its neighbors state as well as other components of this piece (like direction)
     * determined by the move types
     * @param endCoordinateInfo - the coordinate of where this piece will move to
     * @param playerState - the player that is moving this piece
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     *                  a piece being captured)
     */
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState){
        for(MoveType move: myMoveTypes){
            move.completeMoveType(this, endCoordinateInfo, neighbors, playerState, myMainDirection);
        }
    }

    /*
     * @deprecated void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors);
     */

    /**
     * @return the state of the piece
     */
    public int getState(){
        return myState;
    }
    /**
     * @return whether the turn should change after this move
     */
    public boolean changeTurnAfterMove(){
        return turnChange;
    }

    /**
     * @return the number of objects of the piece
     */
    public int getNumObjects(){
        return myObjects;
    }
    /**
     * @return the position of the piece
     */
    public Coordinate getPosition(){
        return myPosition;
    }

    /**
     * allows move types to determine whether the turn should change afterwards, is true by default
     * @param changeTurns - the value of if the turn should change
     */
    protected void changeTurn(boolean changeTurns){
        turnChange = changeTurns;
    }
    /**
     * changes the state of this piece
     * @param newState - the new state this piece will take on
     */
    protected void changeState(int newState){
        myState = newState;
    }
    /**
     * allows move types to alter the number of objects of game pieces, can increment by using a positive number
     * and decrement by using a negative number
     * @param incrementBy - the number of objects to add to the current amount
     */
    protected void incrementNumObjects(int incrementBy){
        myObjects += incrementBy;
    }
    /**
     * changes the position of this piece
     * @param moveTo - the new position this piece will take on
     */
    protected void move(Coordinate moveTo){
        myPosition = moveTo;
    }
    /**
     * adds a new direction this piece is allowed to move in as determined by a move type
     * @param newDirection - the new direction this piece can now move in
     */
    protected void addDirection(int newDirection){
        if(!myDirections.contains(newDirection)){
            myDirections.add(newDirection);
        }
    }

    /*
     * @deprecated int evaluateState( List<GamePiece> neighbors);
     */
}
