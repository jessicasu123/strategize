package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.MancalaGamePiece;

public class MancalaFactory implements GameTypeFactory{
    private final int myUserPlayer;
    private final int myAgentPlayer;
    private final int myUserGoal;
    private final int myAgentGoal;
    private final int myEmptyState;
    private final int myUserDirection;
    private final int myAgentDirection;
    private final int myMarbles;

    public MancalaFactory(int userPlayer, int agentPlayer, int userGoal, int agentGoal, boolean userPosDirection,
                          int emptyState, int numMarbles){
        myUserPlayer = userPlayer;
        myAgentPlayer = agentPlayer;
        myUserGoal = userGoal;
        myAgentGoal = agentGoal;
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
            return new MancalaGamePiece(status,myUserGoal,myAgentGoal,myUserDirection, numMarbles, myEmptyState, position);
        }else if(status == myAgentPlayer || status == myAgentGoal){
            return new MancalaGamePiece(status,myAgentGoal,myUserGoal,myAgentDirection, numMarbles, myEmptyState, position);
        }else{
            return new MancalaGamePiece(status,myEmptyState,myEmptyState,myEmptyState, myEmptyState, myEmptyState, position);
        }
    }

    @Override
    //TODO: create agent
    public Agent createAgent() {
        return null;
    }
}
