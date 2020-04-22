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
    private int myGoalState;
    private int myDirection;
    private int myOpponentsGoal;
    private boolean myPlayerMovesAgain;
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
        super(state, position, numMarbles);
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
     * @return list that contains this piece's coordinate if valid move, empty list otherwise
     */
    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(this.getState() == playerID && this.getNumObjects() > 0){
            possibleMoves.add(this.getPosition());
        }
        return possibleMoves;
    }

    private int maxYCoord(List<GamePiece> row){
        int max = 0;
        for(GamePiece piece: row){
            if(piece.getPosition().getCol() > max){
                max = piece.getPosition().getCol();
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
        int yPos = this.getPosition().getCol();
        List<GamePiece> myRow = myRowOfPieces(neighbors);
        List<GamePiece> myOpponentsRow = myOpponentRowOfPieces(neighbors);
        int startingPos = yPos + myDirection;
        while(this.getNumObjects() > 0){
            addMarblesToNeighborsBasedOnMove(myRow, myOpponentsRow, startingPos);
            startingPos = findFirstYCoord(neighbors);
        }

    }
    private int findFirstYCoord(List<GamePiece> neighbors){
        int min = 0;
        for(GamePiece piece: neighbors){
            if(piece.getPosition().getCol() * myDirection < min){
                min = piece.getPosition().getCol();
            }
        }
        return min;
    }

    private void addMarblesToNeighborsBasedOnMove(List<GamePiece> row, List<GamePiece> otherRow, int startingPos) {
        int currentPos = startingPos;
        int endPos = currentPos;
        while (this.getNumObjects() > 0 && currentPos >= 0 && currentPos <= maxYCoord(row)) {
            for (GamePiece piece : row) {
                if (piece.getPosition().getCol() == currentPos) {
                    performOneMove(row, otherRow, currentPos, piece);
                    endPos = currentPos;
                    break;
                }
            }
            currentPos += myDirection;
        }
        currentPos = endPos;
        while(currentPos < 0){
            currentPos++;
        }
        while(currentPos > maxYCoord(otherRow)){
            currentPos--;
        }
        while (this.getNumObjects() > 0 && currentPos >= 0 && currentPos <= maxYCoord(otherRow)) {
            for (GamePiece piece : otherRow) {
                if (piece.getPosition().getCol() == currentPos && piece.getState() != myOpponentsGoal) {
                    this.changeNumObjects(-MARBLES_TO_ADD);
                    piece.changeNumObjects(MARBLES_TO_ADD);
                    myPlayerMovesAgain = false;
                    break;
                }
            }
            currentPos -= myDirection;
        }

    }

    private void performOneMove(List<GamePiece> row, List<GamePiece> otherRow, int currentPos, GamePiece piece) {
        this.changeNumObjects(-MARBLES_TO_ADD);
        if(isSpecialMove(piece)){
            if(isSpecialCapture(piece)) {
                myPlayerMovesAgain = false;
                performSpecialCapture(row, otherRow, currentPos);
            }else{
                myPlayerMovesAgain = true;
                piece.changeNumObjects(MARBLES_TO_ADD);
            }
        }else{
            myPlayerMovesAgain = false;
            piece.changeNumObjects(MARBLES_TO_ADD);
        }
    }

    private void performSpecialCapture(List<GamePiece> row, List<GamePiece> otherRow, int currYPos) {
        for(GamePiece otherPiece: otherRow){
            if(otherPiece.getPosition().getCol() == currYPos){
                for(GamePiece myPiece: row){
                    if(myPiece.getState() == myGoalState){
                        myPiece.changeNumObjects(MARBLES_TO_ADD + otherPiece.getNumObjects());
                        otherPiece.changeNumObjects(-otherPiece.getNumObjects());
                        break;
                    }
                }
                break;
            }
        }
    }

    private boolean isSpecialMove(GamePiece piece){
        return this.getNumObjects() == 0 && (piece.getState() == this.getState() || piece.getState() == myGoalState);
    }

    private boolean isSpecialCapture(GamePiece piece) {
        return piece.getNumObjects() == 0 && piece.getState() != myGoalState;
    }

    @Override
    public boolean changeTurnAfterMove(){
        return !myPlayerMovesAgain;
    }

}
