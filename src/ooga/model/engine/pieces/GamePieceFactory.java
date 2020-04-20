package ooga.model.engine.pieces;

import ooga.model.engine.Coordinate;
import ooga.model.engine.exceptions.InvalidGameTypeException;

import java.util.List;

public class GamePieceFactory {
    public static final int REGULAR_STATE_INDEX = 0;
    public static final int SPECIAL_STATE_INDEX = 1;
    private String myGameType;
    private List<Integer> myUserStates;
    private List<Integer> myAgentStates;
    private int myEmptyState;
    private int myUserDirection;
    private int myAgentDirection;

    public GamePieceFactory(String gameType, List<Integer> userStates, List<Integer> agentStates, int emptyState, boolean userPosDirection){
        myGameType = gameType;
        myUserStates = userStates;
        myAgentStates = agentStates;
        myEmptyState = emptyState;
        if(userPosDirection){
            myUserDirection = 1;
        }else{
            myUserDirection = -1;
        }
        myAgentDirection = myUserDirection * -1;
    }

    public GamePiece createGamePiece(int state, Coordinate position, int numPieces){
        int regularState = 0;
        int direction = 0;
        if(myUserStates.contains(state)){
            regularState = myUserStates.get(REGULAR_STATE_INDEX);
            direction = myUserDirection;
        }else if(myAgentStates.contains(state)){
            regularState = myAgentStates.get(REGULAR_STATE_INDEX);
            direction = myAgentDirection;
        }
        switch (myGameType){
            case "Tic-Tac-Toe":
                return new TicTacToeGamePiece(state, position);
            case "Othello":
                return new OthelloGamePiece(state, position);
            case "Connect4":
                return new ConnectFourGamePiece(state,position);
            case "Checkers":
                return createCheckersGamePiece(state, position, regularState, direction);
            case "Mancala":
                return createMancalaGamePiece(state, position, numPieces, direction);
            default:
                throw new InvalidGameTypeException(myGameType + " is not a supported game type.");
        }

    }

    private GamePiece createCheckersGamePiece(int state, Coordinate position, int regularState, int direction) {
        int kingState = 0;
        if(myUserStates.contains(state)){
            kingState = myUserStates.get(SPECIAL_STATE_INDEX);
        }else if(myAgentStates.contains(state)){
            kingState = myAgentStates.get(SPECIAL_STATE_INDEX);
        }
        return new CheckersGamePiece(state, regularState, kingState, myEmptyState, direction, position);
    }

    private GamePiece createMancalaGamePiece(int state, Coordinate position, int numPieces, int direction) {
        int goalState = 0;
        int opponentGoalState = 0;
        if(myUserStates.contains(state)){
            goalState = myUserStates.get(SPECIAL_STATE_INDEX);
            opponentGoalState = myAgentStates.get(SPECIAL_STATE_INDEX);
        }else if(myAgentStates.contains(state)){
            goalState = myAgentStates.get(SPECIAL_STATE_INDEX);
            opponentGoalState = myUserStates.get(SPECIAL_STATE_INDEX);
        }
        return new MancalaGamePiece(state, goalState, opponentGoalState, direction, numPieces, myEmptyState, position);
    }
}
