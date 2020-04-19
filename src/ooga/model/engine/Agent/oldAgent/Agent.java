package ooga.model.engine.Agent.oldAgent;

import java.util.List;

/**
 * The abstract super class for an agent
 * Serves to specify the functionality an agent must have
 * Can evaluate a game state based on its implementation of an evaluation function and can also see if the game has
 * been won, lost, or tied
 */
public abstract class Agent {
    private final int myMaxPlayer;
    private final int myMinPlayer;

    /**
     * Creates an AI agent
     * @param maximizingPlayerID - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerID - the ID of the player who the agent will try to minimize its moves for
     */
    public Agent(int maximizingPlayerID, int minimizingPlayerID){
        myMaxPlayer = maximizingPlayerID;
        myMinPlayer = minimizingPlayerID;
    }

    /**
     * Gives the evaluation function's evaluation of a game state based on the agent's interpretation of the game rules
     * @param boardStateInfo - all of the current state information from the board
     * @return an integer representing the evaluation function's evaluation of the current game state
     */
    public abstract int evaluateCurrentGameState(List<List<Integer>> boardStateInfo, boolean noMovesLeft);


    /**
     * Calculates if one of the players is a winner based on the game rules for how to win
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return if that player has won or not
     */
    protected abstract boolean isWin(int playerID, List<List<Integer>> boardStateInfo, boolean noMovesLeft);

    /**
     * @return the ID of the maximizing player
     */
    protected int getMaxPlayer(){
        return myMaxPlayer;
    }

    /**
     * @return the ID of the minimizing player
     */
    protected int getMinPlayer(){
        return myMinPlayer;
    }
    /**
     *
     * @param boardStateInfo - all of the current state information from the board
     * @return the game winner
     *          - if the game is won this is the player ID of the winner
     *          - if this method was called and the game isn't over it returns 0
     */
    public int findGameWinner(List<List<Integer>> boardStateInfo, boolean noMovesLeft){
        if(isGameWon(boardStateInfo, noMovesLeft)){
            if(isWin(myMaxPlayer, boardStateInfo, noMovesLeft)){
                return myMaxPlayer;
            }
            else {
                return myMinPlayer;
            }
        }
        return 0;
    }

    /**
     *
     * @param boardStateInfo - all of the current state information from the board
     * @return a boolean for if the game is over has been won
     */
    public boolean isGameWon(List<List<Integer>> boardStateInfo, boolean noMovesLeft){
        return isWin(myMaxPlayer, boardStateInfo, noMovesLeft) || isWin(myMinPlayer, boardStateInfo, noMovesLeft);
    }
}
