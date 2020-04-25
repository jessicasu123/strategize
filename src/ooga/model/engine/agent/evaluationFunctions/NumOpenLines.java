package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.neighborhood.DiagonalNeighborhood;
import ooga.model.engine.neighborhood.VerticalNeighborhood;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows the agent
 * to determine the max difference between lines
 * in order for it to place a piece
 * @author Brian Li
 */
public class NumOpenLines implements EvaluationFunction {
    private int myInARow;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;
    private DiagonalNeighborhood calculateDiagonals;
    private VerticalNeighborhood calculateColumns;

    /**
     * Constructor for NumOpenLines
     * @param stateIndex - the index of the state
     * @param maxStates - List of the player states
     * @param minStates - List of the opponent states
     * @param inaRow - target number of pieces in a row
     */
    public NumOpenLines(int stateIndex, List<Integer> maxStates, List<Integer> minStates, int inaRow){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myInARow = inaRow;
    }

    /**
     * @param boardStateInfo - the current state configuration of the board
     * @param objectInfo
     * @param noMovesLeft - boolean that represents if there are moves left
     * @return integer representing the number of open lines
     */
    @Override
    public int evaluate(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
        int rowEvaluation = evaluateMaxOpenMinusMinOpen((boardStateInfo));
        int colEvaluation = evaluateMaxOpenMinusMinOpen(getCols(boardStateInfo));
        int diagEvaluation = evaluateMaxOpenMinusMinOpen(getDiagonals(boardStateInfo));
        return rowEvaluation + colEvaluation + diagEvaluation;
    }

    /**
     * @param neighborhood - nested list of neighbors
     * @return - calculates the max open - min open
     */
    private int evaluateMaxOpenMinusMinOpen(List<List<Integer>> neighborhood){
        int numOpenMax = 0;
        int numOpenMin = 0;
        for(List<Integer> group : neighborhood){
            if(checkNeighborhoodOpen(group, myStateEvalFor)){
                numOpenMax++;
            }
            if(checkNeighborhoodOpen(group, myOpponentStateEvalFor)){
                numOpenMin++;
            }
        }
        return numOpenMax - numOpenMin;
    }

    /**
     * @param check - list of states to check
     * @param playerOpenFor open player
     * @return - whether or not the neighborhood is open
     */
    private boolean checkNeighborhoodOpen(List<Integer> check, int playerOpenFor){
        int consecutiveUnblockedSpots = 0;
        for(int state: check){
            if(state == playerOpenFor || (state != myStateEvalFor && state != myOpponentStateEvalFor)){
                consecutiveUnblockedSpots++;
            }else{
                consecutiveUnblockedSpots = 0;
            }
        }
        return consecutiveUnblockedSpots >= myInARow;
    }

    private List<List<Integer>> getDiagonals(List<List<Integer>> boardStateInfo){
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateDiagonals.getAllDiagonals(boardStateInfo,
                myInARow);
    }




    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateColumns.getAllVerticals(boardStateInfo);
    }
}
