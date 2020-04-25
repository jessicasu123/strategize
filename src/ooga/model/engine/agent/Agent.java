package ooga.model.engine.agent;

import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.winTypes.WinType;

import java.util.List;

/**
 * - This class represents the agent logic of the program
 * - This involves being able to evaluate the board state based on a
 * combination of evaluation functions comparing the max and min players game state
 * - Also determines if a player has won the game and if so which player
 * @author Holly Ansel
 */
public class Agent {
    private WinType myWinType;
    private List<EvaluationFunction> myEvals;
    private List<Integer> myAgentStates;
    private List<Integer> myUserStates;

    /**
     * Creates an AI agent
     * @param winType - the win type used to determine if and who won the game
     * @param agentStates- the ID of the player who the agent will try to maximize its moves for
     * @param userStates - the ID of the player who the agent will try to minimize its moves for
     */
    public Agent(WinType winType, List<EvaluationFunction> evals, List<Integer> agentStates, List<Integer> userStates){
        myWinType = winType;
        myEvals = evals;
        myAgentStates = agentStates;
        myUserStates = userStates;
    }

    /**
     * Gives the sum of all of the evaluation functions' evaluation of a game state
     * @param boardStateInfo - all of the current state information from the board
     * @param objectInfo - all of the current object information from the board
     * @param noMovesLeft - whether there are moves on the board or not
     * @return an integer representing the evaluation function's evaluation of the current game state
     */
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        if(myWinType.isWin(myAgentStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MAX_VALUE;
        }else if(myWinType.isWin(myUserStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MIN_VALUE;
        }
        int evaluation = 0;
        for(EvaluationFunction evalFunc: myEvals){
            evaluation += evalFunc.evaluate(boardStateInfo,objectInfo, noMovesLeft);
        }
        return evaluation;
    }

    /**
     * Calculates if the game has been won by either player based on the win type
     * @param boardStateInfo - all of the current state information from the board
     * @param objectInfo - all of the current object information from the board
     * @param noMovesLeft - whether there are moves on the board or not
     * @return if a player has won the game or not
     */
    public boolean isGameWon(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        return myWinType.isWin(myAgentStates, boardStateInfo,objectInfo, noMovesLeft) ||
                myWinType.isWin(myUserStates, boardStateInfo,objectInfo, noMovesLeft);
    }

    /**
     * returns the winner of the game and 0 if there isn't a winner
     * @param boardStateInfo - all of the current state information from the board
     * @return the game winner
     *          - if the game is won this is the player ID of the winner
     *          - if there is no winner returns 0
     */
    public int findGameWinner(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        if(isGameWon(boardStateInfo,objectInfo, noMovesLeft)){
            if(myWinType.isWin(myAgentStates, boardStateInfo, objectInfo,noMovesLeft)){
                return myAgentStates.get(0);
            }
            else {
                return myUserStates.get(0);
            }
        }
        return 0;
    }
}
