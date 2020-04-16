package ooga.model.engine.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckersAgent extends Agent {
    public static final int PAWN_VALUE = 3;
    public static final int KING_VALUE = 5;
    public static final int PAWN_POS_BONUS = 2;
    public static final int PIECE_WEIGHT = 20;
    public static final int POSITION_WEIGHT = 7;
    public static final int DISTANCE_WEIGHT = 4;
    private int myMaxKingState;
    private int myMaxDirection;
    private int myMinDirection;
    private int myMinKingState;
    private int myEmptyState;
    private Map<Integer, Integer> myStateMapping;

    /**
     * Creates a Checkers AI agent
     * @param maximizingPlayerPawnState - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerPawnState - the ID of the player who the agent will try to minimize its moves for
     */
    public CheckersAgent(int maximizingPlayerPawnState, int minimizingPlayerPawnState, int maxKingState, int minKingState,int emptyState, int direction) {
        super(maximizingPlayerPawnState, minimizingPlayerPawnState);
        myMaxKingState = maxKingState;
        myMaxDirection = direction;
        myMinDirection = myMaxDirection * -1;
        myMinKingState = minKingState;
        myEmptyState = emptyState;
        myStateMapping = new HashMap<>();
        myStateMapping.put(maximizingPlayerPawnState, myMaxKingState);
        myStateMapping.put(minimizingPlayerPawnState, myMinKingState);
    }

    /**
     * Returns 0 when no moves are possible
     *  EVALUATION FUNCTION (inspired by Dave Evans and Carl Sable of Columbia University
     *                      and Miki Alex of the Hebrew University of Jerusalem):
     *      combines and weighs the following components
     *      -(number of kings(5 pts) and pawns (3 pts) max has) - (number of kings(5 pts) and pawns (3 pts) min has)
     *      - position of the piece (being close to the opponents side of the board)
     *      - sum of king distances (if more kings than opponent than want to minimize sum, otherwise maximize sum)
     * @param boardStateInfo - all of the current state information from the board
     * @return an integer representing the evaluation function's evaluation of the current game state
     */
    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo) {
        if(this.isWin(this.getMaxPlayer(), boardStateInfo)){
            return Integer.MAX_VALUE;
        }else if(this.isWin(this.getMinPlayer(), boardStateInfo)){
            return Integer.MIN_VALUE;
        }

        return (pieceValue(boardStateInfo) * PIECE_WEIGHT) + (evaluatePositions(boardStateInfo) * POSITION_WEIGHT) +
                (evaluateKingDistances(boardStateInfo) * DISTANCE_WEIGHT);
    }


    private int pieceValue(List<List<Integer>> boardStateInfo){
        int maxPieceVals = 0;
        int minPieceVals = 0;
        for(List<Integer> row: boardStateInfo){
            for(int state: row){
                if(state == this.getMaxPlayer()){
                    maxPieceVals += PAWN_VALUE;
                }else if(state == myMaxKingState){
                    maxPieceVals += KING_VALUE;
                }else if(state == this.getMinPlayer()){
                    minPieceVals += PAWN_VALUE;
                }else if(state == myMinKingState){
                    minPieceVals += KING_VALUE;
                }
            }
        }
        return maxPieceVals - minPieceVals;
    }
    private int evaluatePositions(List<List<Integer>> boardStateInfo){
        int maxPawnEvals = 0;
        int minPawnEvals = 0;
        int rowCount = 0;
        for(List<Integer> row: boardStateInfo) {
            for (int state : row) {
                if(state == this.getMaxPlayer() || state == this.myMaxKingState){
                    if(myMaxDirection > 0 && rowCount  * myMaxDirection > myMaxDirection * (boardStateInfo.size() - 1)/2 ){
                        maxPawnEvals += PAWN_POS_BONUS;
                    }else if(myMaxDirection < 0 && rowCount  * myMaxDirection >= myMaxDirection * (boardStateInfo.size() - 1)/2){
                        maxPawnEvals += PAWN_POS_BONUS;
                    }
                }else if(state == this.getMinPlayer() || state == this.myMinKingState){
                    if(myMinDirection < 0 && rowCount * myMinDirection >=  myMinDirection * (boardStateInfo.size() - 1)/2 ){
                        minPawnEvals += PAWN_POS_BONUS;
                    }else if(myMinDirection > 0 && rowCount * myMinDirection >  myMinDirection * (boardStateInfo.size() - 1)/2){
                        minPawnEvals += PAWN_POS_BONUS;
                    }
                }
            }
            rowCount++;
        }
        return maxPawnEvals - minPawnEvals;
    }
    
    private int evaluateKingDistances(List<List<Integer>> boardStateInfo){
        int maxDistanceEval = 0;
        int minDistanceEval = 0;
        int rowNum = 0;
        for(List<Integer> row: boardStateInfo){
            int colNum = 0;
            for(int state: row){
                if(state == this.myMaxKingState){
                    maxDistanceEval += sumDistanceToOpponent(boardStateInfo, this.getMinPlayer(), rowNum, colNum);
                }else if(state == this.myMinKingState){
                    minDistanceEval += sumDistanceToOpponent(boardStateInfo, this.getMaxPlayer(), rowNum, colNum);
                }
                colNum++;
            }
            rowNum++;
        }

        return -1 * (maxDistanceEval - minDistanceEval);
    }

    private int sumDistanceToOpponent(List<List<Integer>> boardStateInfo, int opponent, int startX, int startY){
        int rowNum = 0;
        int sumManhattanDistances = 0;
        for(List<Integer> row: boardStateInfo){
            int colNum = 0;
            for(int state: row){
                if(state == opponent || state == myStateMapping.get(opponent)){
                    sumManhattanDistances += manhattanDistance(startX, startY, rowNum, colNum);
                }
                colNum++;
            }
            rowNum++;
        }
        return sumManhattanDistances;
    }

    private int manhattanDistance(int startX, int startY, int endX, int endY){
        return Math.abs(startX - endX) + Math.abs(startY - endY);
    }
    /**
     * WIN BY:
     *      - your opponents have no pieces left
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return if that player has won or not
     */
    @Override
    protected boolean isWin(int playerID, List<List<Integer>> boardStateInfo) {
        int numOfOpponentsPieces = 0;
        for(List<Integer> row: boardStateInfo){
            for(int state : row){
                if(state != myStateMapping.get(playerID) && state != playerID && state != myEmptyState){
                    numOfOpponentsPieces++;
                }
            }
        }
        return numOfOpponentsPieces == 0;
    }
}
