// package ooga.model.engine.pieces;
//
//import ooga.model.engine.Coordinate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * This implements the game pieces for Chopsticks, allowing the board to make moves and calculate all possible moves.
// * The Chopsticks board consists of four square - two squares representing each players two hands
// * This class calculates all the possible moves of a chopsticks piece to interact with its other "hand"
// * and the opponents "hands"
// * It also acts to move a piece and place chopsticks in the appropriate spots as specified by the game rules
// * @author Sanya Kochhar
// */
//public class ChopsticksGamePiece extends GamePiece {
//    private boolean myPlayerMovesAgain;
//    private final int myEmptyState;
//    private final int myHandMaxPieces;
//
//    /**
//     * Creates a chopstics piece
//     * @param state - state of this piece
//     * @param emptyState - the empty state on the board - a hand that has died
//     * @param position - the position this square lives at
//     * @param numPieces - the number of stick objects this square starts with
//     */
//    public ChopsticksGamePiece(int state, int emptyState, Coordinate position, int numPieces) {
//        super(state, position, numPieces);
//        myEmptyState = emptyState;
//        myHandMaxPieces = numPieces;
//    }
//
//    /**
//     * The possible moves are:
//     *  - SPLIT: if it has >= 2 sticks and its other hand has 0, it can add one or more sticks to its other "hand"
//     *  - ATTACK: add sticks to opponent's live "hand"
//     * @param neighbors - the neighbors of the Game Piece as determined by the Board, will be horizontal
//     *                  vertical, and diagonal neighbors TODO: check neighbors in data file
//     * @param playerID - the id of the player trying to find their possible moves
//     * @return list that contains this piece's coordinate if valid move, empty list otherwise
//     */
//    @Override
//    public List<Coordinate> calculateAllPossibleMoves(List<GamePiece> neighbors, int playerID) {
//        List<Coordinate> possibleMoves = new ArrayList<>();
//        if (this.getState() == playerID && this.getNumObjects() > 0) {
//            for (GamePiece neighbor : neighbors) {
//                if (this.getNumObjects() >= 2 && neighbor.getState() == playerID && neighbor.getNumObjects() == 0
//                        || neighbor.getNumObjects() > 0 && neighbor.getState() != playerID) {
//                    possibleMoves.add(neighbor.getPosition());
//                }
//            }
//        }
//        return possibleMoves;
//    }
//
//
//    /**
//     * Makes the move for the piece. The possible moves are:
//     *  - SPLIT: if it has >= 2 sticks and its other hand has 0, it can add one or more sticks to its other "hand"
//     *  - ATTACK: add sticks to opponent's live "hand"
//     *      - if opponent hand reaches 5 pieces, it dies
//     *      - if opponent hand reaches > 5 pieces, it is left with (new total - 5) sticks
//     *          Eg: player adds 4 to opponent hand that has 2 pieces -> opponent now has (4+2-5) = 1 piece
//     * @param endCoordinateInfo - the coordinate of the hand this piece will act on
//     * @param neighbors - the neighbors of this game piece which may be affected by the movement of this piece (ex:
//     * @param playerState - the id of the player making the move
//     */
//    @Override
//    public void makeMove(Coordinate endCoordinateInfo, List<GamePiece> neighbors, int playerState) {
//        GamePiece target;
//        for (GamePiece neighbor : neighbors) {
//            if (neighbor.getPosition().equals(endCoordinateInfo)) {
//                target = neighbor;
//            }
//        }
//        if (target.getState() == this.getState()) {
//            splitMove(target);
//        } else {
//            attackMove(target);
//        }
//    }
//
//    private void attackMove(GamePiece target) {
//        int sum = target.getNumObjects() + this.getNumObjects();
//        if (sum == myHandMaxPieces) {
//            target.changeState(myEmptyState);
//            target.changeNumObjects(-myHandMaxPieces);
//        } else if (sum > myHandMaxPieces) {
//            target.changeNumObjects(sum - myHandMaxPieces);
//        } else {
//            target.changeNumObjects(myHandMaxPieces);
//        }
//    }
//
//    /**
//     * Implements Split move:
//     *  - if the piece has an even number of sticks, it evenly divides
//     *  - if add: transfers 1 stick
//     */
//    private void splitMove(GamePiece otherPiece) {
//        if (this.getNumObjects() % 2 == 0) {
//            this.changeNumObjects(-myEmptyState/2);
//            otherPiece.changeNumObjects(myEmptyState/2);
//            otherPiece.changeState(this.getState());
//        } else {
//            this.changeState(-1);
//            otherPiece.changeNumObjects(1);
//            otherPiece.changeState(1);
//        }
//    }
//
//}
