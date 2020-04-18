package ooga.model.engine.Player;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An agent player is a player that uses AI to calculate its move
 * this player has an ID associated with it which corresponds to all of the pieces that it has
 * when called upon will calculate its move based on using the specific AI agent for the game being played
 *      - in calculating the move the agent player uses the minimax algorithm to determine the best move
 *      based on the evaluation from the agent
 *      - it uses minimax with alpha-beta pruning to allow for increased efficiency and thus enabling it to
 *      have a higher branching factor
 */
public class AgentPlayer implements Player{
    public static final int ID_STATE = 0;
    private Agent myAgent;
    private List<Integer> myStates;
    private List<Integer> myOpponentStates;
    private final int mySearchDepth;
    private final int myID;
    private final int myOpponentID;
    private Map<Integer, Map.Entry<Coordinate, Coordinate>> moveMappings;
    public static final int MAX_SEARCH_DEPTH = 3;

    /**
     * Creates an agent player using the default search depth
     * @param states - the id of this player (the state of the game piece's that belong to this player)
     * @param gameAgent - the AI agent for this type of game
     * @param opponentStates - the id of the opponent player (the state of the game piece's that belong to the opponent)
     */
    public AgentPlayer(List<Integer> states, Agent gameAgent, List<Integer> opponentStates){
        this(states, gameAgent, opponentStates, MAX_SEARCH_DEPTH);
    }

    /**
     * Creates an agent player using specified search depth
     * @param states - the id of this player (the state of the game piece's that belong to this player)
     * @param gameAgent - the AI agent for this type of game
     * @param opponentStates - the id of the opponent player (the state of the game piece's that belong to the opponent)
     */
    //TODO: take in search depth from data
    public AgentPlayer(List<Integer> states, Agent gameAgent, List<Integer> opponentStates, int searchDepth){
        myStates = states;
        myAgent = gameAgent;
        myID = myStates.get(ID_STATE);
        myOpponentStates = opponentStates;
        myOpponentID = myOpponentStates.get(ID_STATE);
        moveMappings = new HashMap<>();
        mySearchDepth = searchDepth;
    }
    /**
     * this keeps track of which id belongs to each player
     * @return the id of this player
     */
    //TODO: do we need this?
    @Override
    public int getPlayerID() {
        return myStates.get(0);
    }

    /**
     * Calculates the move that the agent player wants to make based on its agent's evaluation of the game state
     * @param boardCopy - a copy of the game board to tryout moves on
     * @return - the move that the agent player has chosen, if there are many that are equal it chooses the most recent
     * one evaluated
     * @throws InvalidMoveException - throws an exception if the move is not legal or no legal moves are available
     */
    public Map.Entry<Coordinate, Coordinate> calculateMove(BoardFramework boardCopy) throws InvalidMoveException {
        int bestMove = getMaxPlayerMove(boardCopy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if(moveMappings.isEmpty()){
            throw new InvalidMoveException("No legal moves for agent to play");
        }
        return moveMappings.get(bestMove);
    }

    private int getMaxPlayerMove(BoardFramework boardCopy, int depth, int alpha, int beta) throws InvalidMoveException {
        int myAlpha = alpha;
        if(depth >= mySearchDepth || myAgent.isGameWon(boardCopy.getStateInfo()) || boardCopy.checkNoMovesLeft(myStates, myOpponentStates)){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo());
        }
        int currMaxVal = Integer.MIN_VALUE;

        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myStates).entrySet()){
            for(Coordinate moveTo: moves.getValue()){
                BoardFramework testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(myID, moves.getKey(), moveTo);
                int curr = getMinPlayerMove(testMoveBoard, depth, myAlpha, beta);
                currMaxVal = Math.max(currMaxVal, curr);
                if(depth == 0){
                    moveMappings.put(curr, new AbstractMap.SimpleImmutableEntry<>(moves.getKey(), moveTo));
                }
                if(currMaxVal > beta){
                    return currMaxVal;
                }
                myAlpha = Math.max(currMaxVal, myAlpha);

            }
        }
        return currMaxVal;
    }

    private int getMinPlayerMove(BoardFramework boardCopy, int depth, int alpha, int beta) throws InvalidMoveException {
        int myBeta = beta;
        if(depth >= mySearchDepth || myAgent.isGameWon(boardCopy.getStateInfo())|| boardCopy.checkNoMovesLeft(myStates, myOpponentStates)){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo());
        }
        int currMinVal = Integer.MAX_VALUE;
        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myOpponentStates).entrySet()) {
            for (Coordinate moveTo : moves.getValue()) {
                BoardFramework testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(myOpponentID, moves.getKey(), moveTo);
                int curr = getMaxPlayerMove(testMoveBoard, depth + 1, alpha, myBeta);
                currMinVal = Math.min(currMinVal, curr);
                if(currMinVal < alpha){
                    return currMinVal;
                }
                myBeta = Math.min(currMinVal, myBeta);
            }
        }

        return currMinVal;
    }


}
