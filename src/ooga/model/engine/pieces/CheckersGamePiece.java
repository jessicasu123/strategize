package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This implements the game pieces for Checkers, allowing the board to make moves and calculate all possible moves.
 * This class calculates all the possible moves of a checkers piece including normal moves, jumps, and multiple jumps
 * It also acts to move a piece to the specified spot (verified by the board) and will capture opponent pieces as
 * specified by the game rules
 * @author Holly Ansel
 */
public class CheckersGamePiece extends GamePiece {

    public static final int JUMP_SIZE = 2;
    // direction -> positive = moving towards bottom of board, negative = moving towards top of board
    private int myDirection;
    private final int myPawnState;
    private final int myKingState;
    private final int myEmptyState;
    private boolean isKing;


    /**
     * Creates a Checkers Game Piece
     * @param state - the current state of this piece
     * @param pawnState - the state this piece would be in when it is a pawn
     * @param kingState - the state this piece would be in when it is a king
     * @param direction - the direction this piece moves in (either +1 or -1)
     * @param position - the coordinate for the position of this piece
     */
    public CheckersGamePiece(int state, int pawnState, int kingState, int emptyState, int direction, Coordinate position){
        super(state, position);
        myPawnState = pawnState;
        myKingState = kingState;
        myEmptyState = emptyState;
        myDirection = direction;
        isKing = state == kingState;
    }

    /**
     * METHOD PURPOSE:
     *  - based on the Checkers rules it gets all of the possible moves
     *      - Can only move to empty squares on adjacent diagonal in forward direction
     *      (unless king and can move in forward and backwards direction)
     *      -if there is an opponents piece in adjacent diagonal and an empty space in the following adjacent diagonal
     *      can jump over and capture opponents piece, can have as many jumps as the board position allows
     *  - this acts to validate the move made by a user
     *  - and it helps the AI agent know what its options are
     * @param neighbors - the neighbors of the Game Piece as determined by the Board, for checkers it will be
     *                  diagonal neighbors
     * @return a list of end coordinates that this piece can move to, this includes either a step to an adjacent diagonal
     *         square or a jump (or multiple) which will capture an opponents piece
     *
     */
    @Override
    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        if(this.getState() == playerID  || (this.getState() == myKingState && playerID == myPawnState)) {
            for (GamePiece neighbor : neighbors) {
                if (neighbor.getState() == myEmptyState && isAdjacentDiagonal(neighbor.getPosition())) {
                    possibleMoves.add(neighbor.getPosition());
                }
            }
            calculateAllPossibleJumps(neighbors, possibleMoves);
        }
        return possibleMoves;
    }

    /**
     * Recursively calls calculateIfLegalJump so it can see if it is a jump based on the new position for this piece,
     * without actually moving it
     * @param neighbors  - the neighbors of the Game Piece as determined by the Board, for checkers it will be
     *                        diagonal neighbors
     * @param possibleMoves - the current list of possible moves so far, will add to this list if find more jump moves
     */
    protected void calculateAllPossibleJumps(List<GamePiece> neighbors, List<Coordinate> possibleMoves) {
        for(GamePiece neighbor: neighbors) {
            if (checkIfOpponentPiece(neighbor) && isAdjacentDiagonal(neighbor.getPosition())) {
                calculateIfLegalJump(neighbors, possibleMoves, neighbor);
            }
        }
    }

    private void calculateIfLegalJump(List<GamePiece> neighbors, List<Coordinate> possibleMoves, GamePiece neighbor) {
        int jumpDirection = yDifference(neighbor.getPosition());
        for (GamePiece endOfPossibleJump : neighbors) {
            if(checkJumpConditions(endOfPossibleJump, neighbor, jumpDirection) && !possibleMoves.contains(endOfPossibleJump.getPosition())){
                possibleMoves.add(endOfPossibleJump.getPosition());
                CheckersGamePiece jumpResult = new CheckersGamePiece(this.getState(), myPawnState, myKingState, myEmptyState, myDirection, endOfPossibleJump.getPosition());
                jumpResult.calculateAllPossibleJumps(neighbors, possibleMoves);
            }
        }
    }

    private boolean checkJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor, int jumpDirection){
        return checkYJumpConditions(endOfPossibleJump, neighbor, jumpDirection) &&
                checkXJumpConditions(endOfPossibleJump, neighbor) &&
                endOfPossibleJump.getState() == 0;

    }
    private boolean checkXJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor){
        return  endOfPossibleJump.getPosition().getXCoord() == neighbor.getPosition().getXCoord() + myDirection ||
                isKing && endOfPossibleJump.getPosition().getXCoord() == neighbor.getPosition().getXCoord() + (myDirection * -1);
    }
    private boolean checkYJumpConditions(GamePiece endOfPossibleJump, GamePiece neighbor, int jumpDirection){
       return endOfPossibleJump.getPosition().getYCoord() == neighbor.getPosition().getYCoord() + jumpDirection ;
    }
    private boolean checkIfOpponentPiece(GamePiece neighbor) {
        return neighbor.getState() != myPawnState && neighbor.getState() != myEmptyState && neighbor.getState() != myKingState;
    }

    /**
     * This method makes a "move": it changes the state and/or position of this game piece and potentially
     *  its neighbors state if they are captured and will switch positions with the cell it is moving to
     * @param endCoordinateInfo - the coordinate of where this piece will move to
     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
     *                  a piece being captured)
     */
    @Override
    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {

        Coordinate oldPosition = new Coordinate(this.getXCoordinate(), this.getYCoordinate());
        if (isAdjacentDiagonal(endCoordinateInfo)) {
            this.move(endCoordinateInfo);
        } else {
            makeJumpMove(endCoordinateInfo, neighbors);
        }
        int endDiagonalLoc = findKingPromotionRow(neighbors);
        if (endCoordinateInfo.getXCoord() == endDiagonalLoc) {
            isKing = true;
            this.changeState(myKingState);
        }
        switchMoveToLocationToCurrLocation(endCoordinateInfo, neighbors, oldPosition);

    }

    private void switchMoveToLocationToCurrLocation(Coordinate endCoordinateInfo, List<GamePiece> neighbors, Coordinate posSwitchTo) {
        for(GamePiece neighbor: neighbors){
            if(neighbor.getPosition().equals(endCoordinateInfo)){
                neighbor.move(posSwitchTo);
                break;
            }
        }
    }

    private void makeJumpMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors) {
        int numJumps = Math.abs(this.getPosition().getXCoord() - endCoordinateInfo.getXCoord()) / JUMP_SIZE;
        for(int i = 0; i < numJumps; i++){
            for(GamePiece neighbor: neighbors){
                if(isAdjacentDiagonal(neighbor.getPosition()) && isOnPathToEndCoord(neighbor.getPosition(), endCoordinateInfo)){
                    neighbor.changeState(myEmptyState);
                    jump(neighbor.getPosition());
                    break;
                }
            }
        }
    }
    
    //TODO fix when becomes king at end of diagonal but not last row
    private int findKingPromotionRow(List<GamePiece> neighbors) {
        int endDiagonalLoc = 0;
        boolean initiated = false;
        for(GamePiece neighbor: neighbors){
            if(!initiated || neighbor.getPosition().getXCoord() * myDirection > endDiagonalLoc * myDirection){
                initiated = true;
                endDiagonalLoc = neighbor.getPosition().getXCoord();
            }
        }
        return endDiagonalLoc;
    }

    private boolean isOnPathToEndCoord(Coordinate compareTo, Coordinate goingTo){
        return isYDistanceShorter(compareTo, goingTo) && isXDistanceShorter(compareTo, goingTo);
    }

    private boolean isYDistanceShorter(Coordinate compareTo, Coordinate goingTo){
        return Math.abs(compareTo.getYCoord() - goingTo.getYCoord()) < Math.abs(this.getYCoordinate() - goingTo.getYCoord());
    }

    private boolean isXDistanceShorter(Coordinate compareTo, Coordinate goingTo){
        if(isKing){
            return Math.abs(goingTo.getXCoord() - compareTo.getXCoord()) < Math.abs(goingTo.getXCoord() - this.getXCoordinate());
        }
        return myDirection * (goingTo.getXCoord() - compareTo.getXCoord()) < myDirection * (goingTo.getXCoord() - this.getXCoordinate());
    }

    private int yDifference(Coordinate compareTo){
        return compareTo.getYCoord() - this.getYCoordinate();
    }

    private void jump(Coordinate jumpingOver){
        int newXCoord = findNewXCoordinateLocation(jumpingOver);
        int newYCoord = findNewYCoordinateLocation(jumpingOver);
        this.move(new Coordinate(newXCoord, newYCoord));
    }

    //see if going over left or right
    private int findNewYCoordinateLocation(Coordinate jumpingOver) {
        int newYCoord = this.getYCoordinate();
        if (yDifference(jumpingOver) > 0){
            newYCoord += JUMP_SIZE;
        }else{
            newYCoord -= JUMP_SIZE;
        }
        return newYCoord;
    }

    private int findNewXCoordinateLocation(Coordinate jumpingOver) {
        int newXCoord = this.getXCoordinate();
        if(!isKing) {
            newXCoord += myDirection * JUMP_SIZE;
        }else{
           if(jumpingOver.getXCoord() - this.getXCoordinate() > 0){
               newXCoord += JUMP_SIZE;
           }else{
               newXCoord -= JUMP_SIZE;
           }
        }
        return newXCoord;
    }

    private int getXCoordinate(){
        return this.getPosition().getXCoord();
    }

    private int getYCoordinate(){
        return this.getPosition().getYCoord();
    }

    private boolean isAdjacentDiagonal(Coordinate neighbor){
        int neighborXPos = neighbor.getXCoord();
        int neighborYPos = neighbor.getYCoord();

        return isYCoordinateNeighbor(neighborYPos) && (neighborXPos == this.getXCoordinate() + (myDirection)
                || isKing && neighborXPos == this.getXCoordinate() + (myDirection * -1));
    }

    private boolean isYCoordinateNeighbor(int neighborYPos) {
        return neighborYPos + 1 == this.getYCoordinate() || neighborYPos - 1 == this.getYCoordinate();
    }


}
