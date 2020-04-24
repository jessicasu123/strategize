package ooga.model.engine.Player;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.newPieces.MoveChecks.MoveCheck;
import ooga.model.engine.pieces.newPieces.MoveType;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentPlayer extends Player{
    public static final int ID_STATE = 0;
    private Agent myAgent;
    private List<Integer> myStates;
    private List<Integer> myOpponentStates;
    private final int mySearchDepth;
    private final int myID;
    private final int myOpponentID;
    private Map<Integer, Map.Entry<Coordinate, Coordinate>> moveMappings;
    public static final int MAX_SEARCH_DEPTH = 1;


    public AgentPlayer(List<Integer> states, List<Integer> directions, List<MoveCheck> selfMoveChecks,
                       List<MoveCheck> neighborMoveChecks, List<MoveType> moveTypes, boolean isPlayer1,
                       List<Integer> opponentStates, Agent gameAgent) {
        super(states, directions, selfMoveChecks, neighborMoveChecks, moveTypes, isPlayer1);
        myStates = states;
        myAgent = gameAgent;
        myID = myStates.get(ID_STATE);
        myOpponentStates = opponentStates;
        myOpponentID = myOpponentStates.get(ID_STATE);
        moveMappings = new HashMap<>();
        mySearchDepth = MAX_SEARCH_DEPTH;
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
        boolean noMovesLeft = boardCopy.checkNoMovesLeft(myStates, myOpponentStates) == 0;
        if(depth >= mySearchDepth || myAgent.isGameWon(boardCopy.getStateInfo(), noMovesLeft) || noMovesLeft){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo(), noMovesLeft);
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
        boolean noMovesLeft = boardCopy.checkNoMovesLeft(myStates, myOpponentStates) == 0;
        if(depth >= mySearchDepth || myAgent.isGameWon(boardCopy.getStateInfo(), noMovesLeft)|| noMovesLeft){
            return myAgent.evaluateCurrentGameState(boardCopy.getStateInfo(), noMovesLeft);
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
