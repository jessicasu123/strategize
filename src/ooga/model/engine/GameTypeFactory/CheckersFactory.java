package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.oldAgent.Agent;
import ooga.model.engine.Agent.oldAgent.CheckersAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.CheckersGamePiece;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

public class CheckersFactory implements GameTypeFactory {
    public static final int SPECIAL_STATE_INDEX = 1;
    public static final int REGULAR_STATE_INDEX = 0;
    private final int myUserPawnState;
    private final int myUserKingState;
    private final int myAgentPawnState;
    private final int myAgentKingState;
    private final int myEmptyState;
    private final int myUserDirection;
    private final int myAgentDirection;


    public CheckersFactory(List<Integer> userStates, List<Integer> agentStates, boolean userPosDirection, int emptyState){

        myUserPawnState = userStates.get(REGULAR_STATE_INDEX);
        myAgentPawnState = agentStates.get(REGULAR_STATE_INDEX);
        myUserKingState = userStates.get(SPECIAL_STATE_INDEX);
        myAgentKingState = agentStates.get(SPECIAL_STATE_INDEX);
        if(userPosDirection){
            myUserDirection = 1;
        }else{
            myUserDirection = -1;
        }
        myAgentDirection = myUserDirection * -1;
        myEmptyState = emptyState;
    }

    @Override
    public GamePiece createGamePiece(int status, Coordinate position) {
        if(status == myUserPawnState || status == myUserKingState){
            return new CheckersGamePiece(status,myUserPawnState,myUserKingState,myEmptyState,myUserDirection,position);
        }else if(status == myAgentPawnState || status == myAgentKingState){
            return new CheckersGamePiece(status, myAgentPawnState,myAgentKingState,myEmptyState,myAgentDirection,position);
        }else{
            return new CheckersGamePiece(status,myEmptyState,myEmptyState,myEmptyState,myEmptyState,position);
        }
    }

    @Override
    public Agent createAgent() {
        //TODO add user direction?
        return new CheckersAgent(myAgentPawnState,myUserPawnState,myAgentKingState,myUserKingState,myEmptyState,myAgentDirection);
    }
}
