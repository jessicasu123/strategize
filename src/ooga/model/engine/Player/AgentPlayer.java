package ooga.model.engine.Player;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Board;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;

import java.util.List;
import java.util.Map;

/**
 * An agent player is a player that uses AI to calculate its move
 * this player has an ID associated with it which corresponds to all of the pieces that it has
 * when called upon will calculate its move based on using the specific AI agent for the game being played
 */
public class AgentPlayer implements Player{
    private int myID;
    private Agent myAgent;
    private int myOpponent;
    public static final int MAX_SEARCH_DEPTH = 5;

    /**
     * Creates an agent player
     * @param id - the id of this player (the state of the game piece's that belong to this player)
     * @param gameAgent - the AI agent for this type of game
     * @param opponentID - the id of the opponent player (the state of the game piece's that belong to the opponent)
     */
    public AgentPlayer(int id, Agent gameAgent, int opponentID){
        myID = id;
        myAgent = gameAgent;
        myOpponent = opponentID;
    }

    /**
     * this keeps track of which id belongs to each player
     * @return the id of this player
     */
    @Override
    public int getPlayerID() {
        return myID;
    }

    /**
     * Calculates the move that the agent player wants to make based on its agent's evaluation of the game state
     * @param boardCopy - a copy of the game board to tryout moves on
     * @return - the move that the agent player has chosen
     * @throws InvalidMoveException - throws an exception if the move is not legal
     */
    public Map.Entry<Coordinate, Coordinate> calculateMove(Board boardCopy) throws InvalidMoveException {
        int bestMoveVal = getMaxPlayerMove(boardCopy, 1,Integer.MIN_VALUE, Integer.MAX_VALUE);
        return myAgent.findMoveByEvaluation(bestMoveVal);
    }

    private int getMaxPlayerMove(Board boardCopy, int depth, int alpha, int beta) throws InvalidMoveException {
        if(depth > MAX_SEARCH_DEPTH || myAgent.isWin(myID) || myAgent.isWin(myOpponent)){
            return myAgent.evaluateCurrentGameState(boardCopy);
        }
        int currMaxVal = Integer.MIN_VALUE;
        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myID).entrySet()){
            for(Coordinate moveTo: moves.getValue()){
                Board testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(moves.getKey(), moveTo);
                int curr = getMinPlayerMove(testMoveBoard, depth, alpha, beta);
                currMaxVal = Math.max(currMaxVal, curr);
                if(currMaxVal > beta){
                    return currMaxVal;
                }
                alpha = Math.max(currMaxVal, alpha);
            }
        }
        return currMaxVal;
    }

    private int getMinPlayerMove(Board boardCopy, int depth, int alpha, int beta) throws InvalidMoveException {
        if(depth > MAX_SEARCH_DEPTH || myAgent.isWin(myID) || myAgent.isWin(myOpponent)){
            return myAgent.evaluateCurrentGameState(boardCopy);
        }
        int currMinVal = Integer.MAX_VALUE;
        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myOpponent).entrySet()) {
            for (Coordinate moveTo : moves.getValue()) {
                Board testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(moves.getKey(), moveTo);
                int curr = getMinPlayerMove(testMoveBoard, depth + 1, alpha, beta);
                currMinVal = Math.min(currMinVal, curr);
                if(currMinVal < alpha){
                    return currMinVal;
                }
                beta = Math.min(currMinVal, beta);
            }
        }
        return currMinVal;
    }


}
