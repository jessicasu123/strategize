package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import java.util.ArrayList;
import java.util.List;

public class NumOpenLines implements EvaluationFunction{
    private int myInARow;
    private int ROWS = 6;
    private int COLS = 7;
    private final List<Integer> myStates;
    private final List<Integer> myOpponentsStates;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;

    public NumOpenLines(int stateIndex, List<Integer> maxStates, List<Integer> minStates, int inaRow){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myStates = maxStates;
        myOpponentsStates = minStates;
        myInARow = inaRow;
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int rowEvaluation = evaluateMaxOpenMinusMinOpen((boardStateInfo));
        int colEvaluation = evaluateMaxOpenMinusMinOpen(getCols(boardStateInfo));
        int diagEvaluation = evaluateMaxOpenMinusMinOpen(getDiagonals(boardStateInfo));
        return rowEvaluation + colEvaluation + diagEvaluation;
    }

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
        if(myInARow==4) {
            List<List<Integer>> alldiag = new ArrayList<List<Integer>>();
            for (int row = ROWS - 4; row >= 0; row--) {
                for (int col = COLS - 4; col >= 0; col--) {
                    List<Integer> leftDiag = new ArrayList<Integer>();
                    List<Integer> rightDiag = new ArrayList<Integer>();
                    for (int i = 0; i < 4; i++) {
                        rightDiag.add(boardStateInfo.get(row + i).get(col - i + 3));
                        leftDiag.add(boardStateInfo.get(row + i).get(col + i));
                    }
                    alldiag.add(leftDiag);
                    alldiag.add(rightDiag);
                }
            }
            return alldiag;
        }
        else{
            List<Integer> leftDiag = new ArrayList<>();
            List<Integer> rightDiag = new ArrayList<>();
            for(int i = 0; i < Math.min(boardStateInfo.size(), boardStateInfo.get(0).size()); i++){
                leftDiag.add(boardStateInfo.get(i).get(i));
                rightDiag.add(boardStateInfo.get(i).get(boardStateInfo.size() - 1 - i));
            }
            return new ArrayList<>(List.of(leftDiag, rightDiag));
        }
    }

    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        List<List<Integer>> allCols = new ArrayList<>();
        for(int i = 0; i < boardStateInfo.get(0).size(); i++) {
            List<Integer> col = new ArrayList<>();
            for (List<Integer> row : boardStateInfo) {
                col.add(row.get(i));
            }
            allCols.add(col);
        }
        return allCols;
    }
}
