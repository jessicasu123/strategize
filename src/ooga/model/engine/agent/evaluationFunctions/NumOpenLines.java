package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.neighborhood.DiagonalNeighborhood;
import ooga.model.engine.neighborhood.VerticalNeighborhood;

import java.util.ArrayList;
import java.util.List;

public class NumOpenLines implements EvaluationFunction {
    private int myInARow;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;
    private DiagonalNeighborhood calculateDiagonals;
    private VerticalNeighborhood calculateColumns;

    public NumOpenLines(int stateIndex, List<Integer> maxStates, List<Integer> minStates, int inaRow){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myInARow = inaRow;
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
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
        //boolean isSquare = boardStateInfo.size()==boardStateInfo.get(0).size();
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateDiagonals.getAllDiagonals(boardStateInfo,
                myInARow);
//        if (isSquare) {
//            return getDiagForSquareGrid(boardStateInfo);
//        } else {
//            return getDiagForRectangularGrid(boardStateInfo);
//        }
    }

    //TODO: delete if dependency on neighborhood is ok
    private List<List<Integer>> getDiagForSquareGrid(List<List<Integer>> boardStateInfo) {
        List<Integer> leftDiag = new ArrayList<>();
        List<Integer> rightDiag = new ArrayList<>();
        for(int i = 0; i < Math.min(boardStateInfo.size(), boardStateInfo.get(0).size()); i++){
            leftDiag.add(boardStateInfo.get(i).get(i));
            rightDiag.add(boardStateInfo.get(i).get(boardStateInfo.size() - 1 - i));
        }
        return new ArrayList<>(List.of(leftDiag, rightDiag));
    }

    //TODO: delete if dependency on neighborhood is ok
    private List<List<Integer>> getDiagForRectangularGrid(List<List<Integer>> boardStateInfo) {
        List<List<Integer>> alldiag = new ArrayList<List<Integer>>();
        int rows = boardStateInfo.size();
        int cols = boardStateInfo.get(0).size();
        int remainder = myInARow - 1;
        for (int row = rows - myInARow; row >= 0; row--) {
            for (int col = cols - myInARow; col >= 0; col--) {
                List<Integer> leftDiag = new ArrayList<Integer>();
                List<Integer> rightDiag = new ArrayList<Integer>();
                for (int i = 0; i < myInARow; i++) {
                    rightDiag.add(boardStateInfo.get(row + i).get(col - i + remainder));
                    leftDiag.add(boardStateInfo.get(row + i).get(col + i));
                }
                alldiag.add(leftDiag);
                alldiag.add(rightDiag);
            }
        }
        return alldiag;
    }


    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateColumns.getAllVerticals(boardStateInfo);
//        List<List<Integer>> allCols = new ArrayList<>();
//        for(int i = 0; i < boardStateInfo.get(0).size(); i++) {
//            List<Integer> col = new ArrayList<>();
//            for (List<Integer> row : boardStateInfo) {
//                col.add(row.get(i));
//            }
//            allCols.add(col);
//        }
//        return allCols;
    }
}