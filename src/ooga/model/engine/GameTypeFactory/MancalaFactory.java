package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.MancalaAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.MancalaGamePiece;

import java.util.List;

public class MancalaFactory implements GameTypeFactory{
    public static final int SPECIAL_STATE_INDEX = 1;
    public static final int REGULAR_STATE_INDEX = 0;
    private final int myUserPlayer;
    private final int myAgentPlayer;
    private final int myUserGoal;
    private final int myAgentGoal;
    private final int myEmptyState;
    private final int myUserDirection;
    private final int myAgentDirection;
    private final int myMarbles;
    private Coordinate userGoalPosition;
    private Coordinate agentGoalPosition;

    public MancalaFactory(List<Integer> userStates, List<Integer> agentStates, boolean userPosDirection, int emptyState,
                          int numMarbles){
        myUserPlayer = userStates.get(REGULAR_STATE_INDEX);
        myAgentPlayer = agentStates.get(REGULAR_STATE_INDEX);
        myUserGoal = userStates.get(SPECIAL_STATE_INDEX);
        myAgentGoal = agentStates.get(SPECIAL_STATE_INDEX);
        if(userPosDirection){
            myUserDirection = 1;
        }else{
            myUserDirection = -1;
        }
        myAgentDirection = myUserDirection * -1;
        myEmptyState = emptyState;
        myMarbles = numMarbles;
    }

    @Override
    //TODO: how to get different number of marbles
    public GamePiece createGamePiece(int status, Coordinate position) {
        int numMarbles = myMarbles;
        if(status == myUserGoal || status == myAgentGoal){
            numMarbles = 0;
        }

        if(status == myUserPlayer || status == myUserGoal){
            userGoalPosition = position;
            return new MancalaGamePiece(status,myUserGoal,myAgentGoal,myUserDirection, numMarbles, myEmptyState, position);
        }else if(status == myAgentPlayer || status == myAgentGoal){
            agentGoalPosition = position;
            return new MancalaGamePiece(status,myAgentGoal,myUserGoal,myAgentDirection, numMarbles, myEmptyState, position);
        }else{
            return new MancalaGamePiece(status,myEmptyState,myEmptyState,myEmptyState, myEmptyState, myEmptyState, position);
        }
    }

    @Override
    //TODO: create agent
    public Agent createAgent() {
        return new MancalaAgent(myAgentPlayer, myUserPlayer, agentGoalPosition, userGoalPosition);
    }
}
