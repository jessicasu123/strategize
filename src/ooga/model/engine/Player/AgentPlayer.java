package ooga.model.engine.Player;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;

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
    public Map.Entry<Coordinate, Coordinate> calculateMove(BoardFramework boardCopy) throws InvalidMoveException {
        int bestMoveVal = getMaxPlayerMove(boardCopy, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return findMove(bestMoveVal, boardCopy);
    }

    private Map.Entry<Coordinate, Coordinate> findMove(int bestMoveVal,BoardFramework startBoard) throws InvalidMoveException {
        Map<Coordinate, Coordinate> allBestMoves = new HashMap<>();
        for(Map.Entry<Coordinate, List<Coordinate>> moves: startBoard.getAllLegalMoves(myID).entrySet()) {
            for (Coordinate moveTo : moves.getValue()) {
                BoardFramework testMoveBoard = startBoard.copyBoard();
                testMoveBoard.makeMove(myID, moves.getKey(), moveTo);
                if(myAgent.evaluateCurrentGameState(testMoveBoard.getStateInfo()) == bestMoveVal){
                    allBestMoves.put(moves.getKey(), moveTo);
                }
            }
        }
        for(Map.Entry<Coordinate, Coordinate> bestMoves: allBestMoves.entrySet()){
            return bestMoves;
        }
        //TODO: see if there is a way to return something other than null
        return null;
    }

    private int getMaxPlayerMove(BoardFramework boardCopy, int depth, int alpha, int beta) throws InvalidMoveException {
        int myAlpha = alpha;
        if(depth >= MAX_SEARCH_DEPTH || myAgent.isGaveOver(boardCopy.getStateInfo())){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo());
        }
        int currMaxVal = Integer.MIN_VALUE;
        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myID).entrySet()){
            for(Coordinate moveTo: moves.getValue()){
                BoardFramework testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(myID, moves.getKey(), moveTo);
                int curr = getMinPlayerMove(testMoveBoard, depth, myAlpha, beta);
                currMaxVal = Math.max(currMaxVal, curr);
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
        if(depth >= MAX_SEARCH_DEPTH || myAgent.isGaveOver(boardCopy.getStateInfo())){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo());
        }
        int currMinVal = Integer.MAX_VALUE;
        for(Map.Entry<Coordinate, List<Coordinate>> moves: boardCopy.getAllLegalMoves(myOpponent).entrySet()) {
            for (Coordinate moveTo : moves.getValue()) {
                BoardFramework testMoveBoard = boardCopy.copyBoard();
                testMoveBoard.makeMove(myOpponent, moves.getKey(), moveTo);
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
