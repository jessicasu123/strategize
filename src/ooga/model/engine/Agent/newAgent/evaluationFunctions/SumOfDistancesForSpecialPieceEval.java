package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import java.util.List;

public class SumOfDistancesForSpecialPieceEval implements EvaluationFunction{
    public static final int MINIMIZE_FACTOR = -1;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;
    private final List<Integer> myStates;
    private final List<Integer> myOpponentsStates;

    public SumOfDistancesForSpecialPieceEval(int stateIndex, List<Integer> maxStates, List<Integer> minStates){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myStates = maxStates;
        myOpponentsStates = minStates;
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int maxDistanceEval = 0;
        int minDistanceEval = 0;
        int rowNum = 0;
        for(List<Integer> row: boardStateInfo){
            int colNum = 0;
            for(int state: row){
                if(state == myStateEvalFor){
                    maxDistanceEval += sumDistanceToOpponent(boardStateInfo, myOpponentsStates, rowNum, colNum);
                }else if(state == myOpponentStateEvalFor){
                    minDistanceEval += sumDistanceToOpponent(boardStateInfo, myStates, rowNum, colNum);
                }
                colNum++;
            }
            rowNum++;
        }

        return (MINIMIZE_FACTOR * (maxDistanceEval - minDistanceEval));
    }
    private int sumDistanceToOpponent(List<List<Integer>> boardStateInfo, List<Integer> opponent, int startX, int startY){
        int rowNum = 0;
        int sumManhattanDistances = 0;
        for(List<Integer> row: boardStateInfo){
            int colNum = 0;
            for(int state: row){
                if(opponent.contains(state)){
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
}
