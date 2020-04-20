package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the game pieces for Chopsticks, allowing the board to make moves and calculate all possible moves.
 * The Chopsticks board consists of four square - two squares representing each players two hands
 * This class calculates all the possible moves of a chopsticks piece to interact with its other "hand"
 * and the opponents "hands"
 * It also acts to move a piece and place chopsticks in the appropriate spots as specified by the game rules
 * @author Sanya Kochhar
 */
public class ChopsticksGamePiece extends GamePiece {
    private boolean myPlayerMovesAgain;
    private final int myEmptyState;
    private int myNumSticks;

    /**
     * Creates a mancala piece
     * @param state - state of this piece
     * @param opponentGoal - the state of the opponents bin (so can ensure don't add pieces to it)
     * @param numSticks - the number of sticks this square starts with
     * @param emptyState - the empty state on the board (so can know to ignore)
     * @param position - the position this square lives at
     */
    public ChopsticksGamePiece(int state, int opponentGoal, int numSticks, int emptyState, Coordinate position) {
        super(state, position);
        myEmptyState = emptyState;
        myNumSticks = numSticks;
    }

    /**
     * The possible moves are:
     *  - SPLIT: if it has >= 2 sticks and its other hand has 0, it can add one or more sticks to its other "hand"
     *  - ATTACK: add sticks to opponent's live "hand"
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
     *                  vertical, and diagonal neighbors TODO: check neighbors in data file
     * @param playerID - the id of the player trying to find their possible moves
     * @return list that contains this piece's coordinate if valid move, empty list otherwise
     */
    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if (myNumSticks >= 1) {
            for (GamePiece neighbor : neighbors) {
                if (myNumSticks >= 2 && neighbor.getState() == playerID && neighbor.getVisualRepresentation() == 0) {
                    possibleMoves.add(neighbor.getPosition());
                } else if (neighbor.getVisualRepresentation() > 0) {
                    possibleMoves.add(neighbor.getPosition());
                }
            }
        }
        return possibleMoves;
    }

//    private List<GamePiece> myRowOfPieces(List<GamePiece> neighbors){
//        List<GamePiece> myRow = new ArrayList<>();
//        for(GamePiece square: neighbors){
//            if(square.getState() == this.getState() || square.getState() == myGoalState){
//                myRow.add(square);
//            }
//        }
//        return myRow;
//    }
//
//    private List<GamePiece> myOpponentRowOfPieces(List<GamePiece> neighbors){
//        List<GamePiece> opponentsRow = new ArrayList<>();
//        for(GamePiece square: neighbors){
//            if(square.getState() != this.getState() && square.getState() != myGoalState && square.getState() != myEmptyState
//                    && square.getState() != myOpponentsGoal){
//                opponentsRow.add(square);
//            }
//        }
//        return opponentsRow;
//    }

    /**
     * Makes the move for the piece. The possible moves are:
     *  - SPLIT: if it has >= 2 sticks and its other hand has 0, it can add one or more sticks to its other "hand"
     *  - ATTACK: add sticks to opponent's live "hand"
     *      - if opponent hand reaches 5 pieces, it dies
     *      - if opponent hand reaches > 5 pieces, it is left with (new total - 5) sticks
     *          Eg: player adds 4 to opponent hand that has 2 pieces -> opponent now has (4+2-5) = 1 piece
     * @param endCoordinateInfo - the coordinate of the hand this piece will act on
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     * @param playerState - the id of the player making the move
     */
    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
//        if endCoord == playerState
            // endGoal.splitMove()
//        } else {
//
    }

    /**
     * Implements Split move:
     *  - if the piece has an even number of sticks, it evenly divides
     *  - if add: transfers 1 stick
     */
    private void splitMove(GamePiece otherPiece) {
        if (myNumSticks % 2 == 0) {
            this.changeState(-myEmptyState/2);
            otherPiece.changeState(myEmptyState/2);
        } else {
            this.changeState(-1);
            otherPiece.changeState(1);
        }
    }

//    private void performOneMove(List<GamePiece> row, List<GamePiece> otherRow, int currentPos, GamePiece piece) {
//        this.changeState(-MARBLES_TO_ADD);
//        if(isSpecialMove(piece)){
//            if(isSpecialCapture(piece)) {
//                performSpecialCapture(row, otherRow, currentPos);
//            }else{
//                myPlayerMovesAgain = true;
//                piece.changeState(MARBLES_TO_ADD);
//            }
//        }else{
//            piece.changeState(MARBLES_TO_ADD);
//        }
//    }

//    private void performSpecialCapture(List<GamePiece> row, List<GamePiece> otherRow, int currYPos) {
//        for(GamePiece otherPiece: otherRow){
//            if(otherPiece.getPosition().getCol() == currYPos){
//                for(GamePiece myPiece: row){
//                    if(myPiece.getState() == myGoalState){
//                        myPiece.changeState(MARBLES_TO_ADD + otherPiece.getVisualRepresentation());
//                        otherPiece.changeState(-otherPiece.getVisualRepresentation());
//                        break;
//                    }
//                }
//                break;
//            }
//        }
//    }
//
//    private boolean isSpecialMove(GamePiece piece){
//        return myMarbles == 0 && (piece.getState() == this.getState() || piece.getState() == myGoalState);
//    }
//
//    private boolean isSpecialCapture(GamePiece piece) {
//        return piece.getVisualRepresentation() == 0 && piece.getState() != myGoalState;
//    }

    @Override
    public boolean changeTurnAfterMove(){
        return !myPlayerMovesAgain;
    }

    /**
     * Overrides because the visual information this game wants to display is the number of marbles in each square
     * @return number of marbles
     */
    @Override
    public int getVisualRepresentation(){
        return myNumSticks;
    }

    /**
     * Overrides because the state that changes is not who a piece belongs to but rather the number of marbles
     * @param numberMarblesToAdd - the marbles adding to this square (if negative will remove them instead of adding)
     */
    @Override
    protected void changeState(int numberMarblesToAdd){
        myNumSticks += numberMarblesToAdd;
    }
}
