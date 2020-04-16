package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the game pieces for Mancala, allowing the board to make moves and calculate all possible moves.
 * This class calculates all the possible moves of a mancala piece which is itself if it has marbles and is not a goal
 * or empty state
 * It also acts to move a piece and place marbles in the appropriate spots as specified by the game rules
 * @author Holly Ansel
 */
public class MancalaGamePiece extends GamePiece {
    public static final int MARBLES_TO_ADD = 1;
    private int myMarbles;
    private int myGoalState;
    private int myDirection;
    private int myOpponentsGoal;
    private final int myEmptyState;

    /**
     * Creates a mancala piece
     * @param state - state of this piece
     * @param goalState - the state of the bin that collects all the pieces earned
     * @param opponentGoal - the state of the opponents bin (so can ensure don't add pieces to it)
     * @param direction - the direction the marbles from this square will move in
     * @param numMarbles - the number of marbles this square starts with
     * @param emptyState - the empty state on the board (so can know to ignore)
     * @param position - the position this square lives at
     */
    public MancalaGamePiece(int state, int goalState,int opponentGoal, int direction, int numMarbles, int emptyState, Coordinate position) {
        super(state, position);
        myMarbles = numMarbles;
        myGoalState = goalState;
        myDirection = direction;
        myOpponentsGoal = opponentGoal;
        myEmptyState = emptyState;
    }

    /**
     * The possible moves is itself if it is not a goal or empty state, belongs to the player trying to move,
     * and has marbles in it
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *                  vertical, and diagonal neighbors
     * @param playerID - the id of the player trying to find their possible moves
     * @return
     */
    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(this.getState() == playerID && myMarbles > 0){
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    private int maxYCoord(List<GamePiece> row){
        int max = 0;
        for(GamePiece piece: row){
            if(piece.getPosition().getYCoord() > max){
                max = piece.getPosition().getYCoord();
            }
        }
        return max;
    }

    private List<GamePiece> myRowOfPieces(List<GamePiece> neighbors){
        List<GamePiece> myRow = new ArrayList<>();
        for(GamePiece square: neighbors){
            if(square.getState() == this.getState() || square.getState() == myGoalState){
                myRow.add(square);
            }
        }
        return myRow;
    }

    private List<GamePiece> myOpponentRowOfPieces(List<GamePiece> neighbors){
        List<GamePiece> opponentsRow = new ArrayList<>();
        for(GamePiece square: neighbors){
            if(square.getState() != this.getState() && square.getState() != myGoalState && square.getState() != myEmptyState
                    && square.getState() != myOpponentsGoal){
                opponentsRow.add(square);
            }
        }
        return opponentsRow;
    }

    /**
     * Makes the move for the piece which removes all of its own marbles and places one in each square it touches until
     * there are no marbles left
     * A special capture is when the last marble from your turn ends in one of your own squares and it is empty, this
     * causes you to collect all of the marbles from that square(1) and the marbles of your opponents square that is
     * directly across
     * @param endCoordinateInfo - the coordinate of where this piece will move to
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     * @param playerState - the id of the player making the move
     */
    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
        List<GamePiece> myRow = myRowOfPieces(neighbors);
        List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
        int numMarblesBefore = myMarbles;
        addMarblesToNeighborsBasedOnMove(myRow, myOpponentsRow, this.getPosition().getYCoord() + myDirection, myDirection);
        int newStartingPos = this.getPosition().getYCoord() + (myDirection * (numMarblesBefore - myMarbles)) + (myDirection * -MARBLES_TO_ADD);
        addMarblesToNeighborsBasedOnMove(myOpponentsRow, myRow, newStartingPos, myDirection * -MARBLES_TO_ADD);

    }

    private void addMarblesToNeighborsBasedOnMove(List<GamePiece> row, List<GamePiece> otherRow, int startingPos, int direction) {
        int currentPos = startingPos;
        while (myMarbles > 0 && currentPos >= 0 && currentPos <= maxYCoord(row)) {
            for (GamePiece piece : row) {
                if (piece.getPosition().getYCoord() == currentPos) {
                    this.changeState(-MARBLES_TO_ADD);
                    if(isSpecialCapture(piece)){
                        performSpecialCapture(row, otherRow, currentPos);
                    }else{
                        piece.changeState(MARBLES_TO_ADD);
                    }

                    break;
                }
            }
            currentPos += direction;
        }
    }

    private void performSpecialCapture(List<GamePiece> row, List<GamePiece> otherRow, int currYPos) {
        for(GamePiece otherPiece: otherRow){
            if(otherPiece.getPosition().getYCoord() == currYPos){
                for(GamePiece myPiece: row){
                    if(myPiece.getState() == myGoalState){
                        myPiece.changeState(MARBLES_TO_ADD + otherPiece.getVisualRepresentation());
                        otherPiece.changeState(-otherPiece.getVisualRepresentation());
                        break;
                    }
                }
                break;
            }
        }
    }

    private boolean isSpecialCapture(GamePiece piece) {
        return myMarbles == 0 && piece.getState() == this.getState() && piece.getVisualRepresentation() == 0;
    }


    /**
     * Overrides because the visual information this game wants to display is the number of marbles in each square
     * @return number of marbles
     */
    @Override
    public int getVisualRepresentation(){
        return myMarbles;
    }

    /**
     * Overrides because the state that changes is not who a piece belongs to but rather the number of marbles
     * @param numberMarblesToAdd - the marbles adding to this square (if negative will remove them instead of adding)
     */
    @Override
    protected void changeState(int numberMarblesToAdd){
        myMarbles += numberMarblesToAdd;
    }
}
