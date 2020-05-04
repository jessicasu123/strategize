package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.ImmutableGrid;
import java.util.List;

/**
 * Purpose:
 * - Evaluates the game's position by seeing how close the piece
 * of the state you are looking at the distances of is to the opponent pieces
 * - For aggressive play, this is minimized so that you want smaller distances
 * - Uses this to provide an overall evaluation of the game state
 *
 * This is an example of an evaluation function that would be composed within the Agent
 *
 * Why well designed:
 * - follows single responsibility principle while also active
 *      - this class only has the purpose of checking the distances of a piece type to the distance of your
 *      opponent's pieces
 *      - however this is still active as it is providing useful information and using logic to get that information
 *      rather than being based on getters and setters
 * - utilizes the evaluation function interface
 *       - this allows this class to be referenced as an abstraction so that in other parts of the code whatever
 *       type of Evaluation Function it is is irrelevant
 * - encapsulates complex logic
 *      - this class features a lot of math and calculations. By being its own class (rather than within the agent)
 *      when it is called on, the object is able to present an evaluation without the implementation of
 *      conditionals and math calculations being known
 * - flexible to handle any game type
 *      - there are no game specific assumptions so this evaluation can be used for any game that needs this criteria
 *      to evaluate a game state
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
     * @param objectInfo - current object configuration of the board
     * @return the difference in the distance of max pieces at the state index
     * to all of min pieces - the distance of min pieces at the state index to all of
     * max pieces
     *         - this is then multiplied by a factor to minimize it for more aggressive play
     */
    @Override
    public int evaluate(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo) {
        int maxDistanceEval = 0;
        int minDistanceEval = 0;
        int rowNum = 0;
        for(int r = 0; r < boardStateInfo.numRows(); r++){
            int colNum = 0;
            for(int c = 0; c < boardStateInfo.numCols(); c++){
                int state = boardStateInfo.getVal(r,c);
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

    //for a position finds all of the distances from itself to its opponents
    private int sumDistanceToOpponent(ImmutableGrid boardStateInfo, List<Integer> opponent, int startX, int startY){
        int rowNum = 0;
        int sumManhattanDistances = 0;
        for(int r = 0; r < boardStateInfo.numRows(); r++){
            int colNum = 0;
            for(int c = 0; c < boardStateInfo.numCols(); c++){
                int state = boardStateInfo.getVal(r,c);
                if(opponent.contains(state)){
                    sumManhattanDistances += manhattanDistance(startX, startY, rowNum, colNum);
                }
                colNum++;
            }
            rowNum++;
        }
        return sumManhattanDistances;
    }

    //this calculates the manhattan distance (x and y distances) between two coordinates
    //manhattan distance is used instead of euclidean distances to ensure it is never an overestimate
    //in case pieces can only move in a particular way
    private int manhattanDistance(int startX, int startY, int endX, int endY){
        return Math.abs(startX - endX) + Math.abs(startY - endY);
    }
}
