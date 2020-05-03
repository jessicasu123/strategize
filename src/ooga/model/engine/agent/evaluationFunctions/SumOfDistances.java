package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.BoardConfiguration;

import java.util.List;

/**
 * Evaluates the game's position by seeing how close the piece
 * of the state you are looking at the distances of is to the opponent pieces
 * For aggressive play, this is minimized so that you want smaller distances
 * @author Holly Ansel
 */
public class SumOfDistances implements EvaluationFunction {
    public static final int MINIMIZE_FACTOR = -1;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;
    private final List<Integer> myStates;
    private final List<Integer> myOpponentsStates;

    /**
     * @param stateIndex - the index of the state who you want to see its distance to other pieces
     * @param maxStates - all of the states of the max player
     * @param minStates - all of the states of the min player
     */
    public SumOfDistances(int stateIndex, List<Integer> maxStates, List<Integer> minStates){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myStates = maxStates;
        myOpponentsStates = minStates;
    }

    /**
     * Analyzes how aggressive the piece positions are
     * @param boardStateInfo - the current state configuration of the board
     * @param noMovesLeft - boolean that represents if there are moves left
     * @return the difference in the distance of max pieces at the state index
     * to all of min pieces - the distance of min pieces at the state index to all of
     * max pieces
     *         - this is then multiplied by a factor to minimize it
     */
    @Override
    public int evaluate(BoardConfiguration boardStateInfo, BoardConfiguration objectInfo, boolean noMovesLeft) {
        int maxDistanceEval = 0;
        int minDistanceEval = 0;
        int rowNum = 0;
        for(int r = 0; r < boardStateInfo.getNumRows();r++){
            int colNum = 0;
            for(int c = 0; c < boardStateInfo.getNumCols();c++){
                int state = boardStateInfo.getValue(r,c);
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
    private int sumDistanceToOpponent(BoardConfiguration boardStateInfo, List<Integer> opponent, int startX, int startY){
        int rowNum = 0;
        int sumManhattanDistances = 0;
        for (int r = 0; r < boardStateInfo.getNumRows();r++) {
            int colNum = 0;
            for (int c = 0; c < boardStateInfo.getNumCols();c++) {
                int state = boardStateInfo.getValue(r,c);
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
