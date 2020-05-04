package ooga.model.engine.agent;

import ooga.model.engine.ImmutableGrid;
import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.winTypes.WinType;
import java.util.List;

/**
 * Purpose:
 * - This class represents the smart agent logic of the program
 * - This involves being able to evaluate the board state based on a
 * combination of evaluation functions comparing the max and min players game state
 * - Also determines if a player has won the game and if so which player
 * - This is utilized by the Agent Player to see if a game has been won and evaluate the game state
 * - And is used by the Game to find the game winner
 *
 * Why well designed:
 * - This class utilizes composition to allow for maximum flexibility and functionality
 *      - By having a list of evaluation functions a game state can be evaluated in any number and combination
 *      of way. This gives the functionality for complicated games to be evaluated in a way that is effective
 *      for them, not just use mechanisms for evaluating simple games
 * - Open/Closed principle
 *      - It is open to extension in that more functionality can be added by promulgating the list of evaluation
 *      functions with different evaluation functions or by adding a particular win type.
 *      - Closed to modification because the functionality of this class will change by extension
 *      without needing to touch the implementation of the Agent class itself. Additionally changes made within
 *      the agent will overall not require other parts of the project to change
 * - Active
 *      - This class has no getters or setters, rather it actively uses the tell don't ask policy
 *      to have other objects perform operations and then integrates the results together
 * - Encapsulation
 *      - The data of this class is encapsulated as there are no getters or setters within the class
 *      - The implementation of this class is also encapsulated. No other part of the program knows which evaluation
 *      functions or win type is used, so how the evaluation or finding the winner is done is not known by other classes
 *          - It also utilizes the abstraction of the Evaluation Functions and Win Type so there is no differences
 *          in how each type is treated
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
    public int evaluateCurrentGameState(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft){
        if(myWinType.isWin(myAgentStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MAX_VALUE;
        }else if(myWinType.isWin(myUserStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MIN_VALUE;
        }
        int evaluation = 0;
        for(EvaluationFunction evalFunc: myEvals){
            evaluation += evalFunc.evaluate(boardStateInfo,objectInfo);
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
    public boolean isGameWon(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft){
        return hasAgentWon(boardStateInfo,objectInfo,noMovesLeft) || hasUserWon(boardStateInfo,objectInfo,noMovesLeft);
    }

    /**
     * @param boardStateInfo - all of the current state information from the board
     * @param objectInfo - all of the current object information from the board
     * @param noMovesLeft - whether there are moves on the board or not
     * @return if the agent player has won
     */
    public boolean hasAgentWon(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft){
        return myWinType.isWin(myAgentStates, boardStateInfo,objectInfo, noMovesLeft);
    }

    /**
     * @param boardStateInfo - all of the current state information from the board
     * @param objectInfo - all of the current object information from the board
     * @param noMovesLeft - whether there are moves on the board or not
     * @return if the user player has won
     */
    public boolean hasUserWon(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo, boolean noMovesLeft){
        return myWinType.isWin(myUserStates, boardStateInfo,objectInfo, noMovesLeft);
    }

}
