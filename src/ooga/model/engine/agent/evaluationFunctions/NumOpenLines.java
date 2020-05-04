package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.Grid;
import ooga.model.engine.ImmutableGrid;
import ooga.model.engine.neighborhood.DiagonalNeighborhood;
import ooga.model.engine.neighborhood.VerticalNeighborhood;

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
     * @return integer representing the number of open lines
     */
    @Override
    public int evaluate(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo) {
        int rowEvaluation = evaluateMaxOpenMinusMinOpen(boardStateInfo);
        int colEvaluation = evaluateMaxOpenMinusMinOpen(getCols(boardStateInfo));
        int diagEvaluation = evaluateMaxOpenMinusMinOpen(getDiagonals(boardStateInfo));
        return rowEvaluation + colEvaluation + diagEvaluation;
    }

    /**
     * @param neighborhood - nested list of neighbors
     * @return - calculates the max open - min open
     */
    private int evaluateMaxOpenMinusMinOpen(ImmutableGrid neighborhood){
        int numOpenMax = 0;
        int numOpenMin = 0;
        for(int i = 0; i < neighborhood.numRows(); i++){
            if(checkNeighborhoodOpen(neighborhood, i, myStateEvalFor)){
                numOpenMax++;
            }
            if(checkNeighborhoodOpen(neighborhood,i, myOpponentStateEvalFor)){
                numOpenMin++;
            }
        }

        return numOpenMax - numOpenMin;
    }

    /**
     * @param gridChecking - list of states to check
     * @param playerOpenFor open player
     * @return - whether or not the neighborhood is open
     */
    private boolean checkNeighborhoodOpen(ImmutableGrid gridChecking, int rowChecking, int playerOpenFor){
        int consecutiveUnblockedSpots = 0;
        for(int j = 0; j < gridChecking.numCols(); j++){
            int state = gridChecking.getVal(rowChecking,j);
            if(state == playerOpenFor || (state != myStateEvalFor && state != myOpponentStateEvalFor)){
                consecutiveUnblockedSpots++;
            }else{
                consecutiveUnblockedSpots = 0;
            }
        }
        return consecutiveUnblockedSpots >= myInARow;
    }

    private ImmutableGrid getDiagonals(ImmutableGrid boardStateInfo){
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.numRows(),
                boardStateInfo.numCols());
        return calculateDiagonals.getAllDiagonals(boardStateInfo,
                myInARow);
    }




    private ImmutableGrid getCols(ImmutableGrid boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.numRows(),
                boardStateInfo.numCols());
        return calculateColumns.getAllVerticals(boardStateInfo);
    }
}
