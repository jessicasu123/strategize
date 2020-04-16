package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.CheckersAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.CheckersGamePiece;
import ooga.model.engine.pieces.GamePiece;

public class CheckersFactory implements GameTypeFactory {
    private final int myUserPawnState;
    private final int myUserKingState;
    private final int myAgentPawnState;
    private final int myAgentKingState;
    private final int myEmptyState;
    private final int myUserDirection;
    private final int myAgentDirection;


    public CheckersFactory(int userPawn, int agentPawn, int userKing, int agentKing, boolean userPosDirection, int emptyState){
        myUserPawnState = userPawn;
        myAgentPawnState = agentPawn;
        myUserKingState = userKing;
        myAgentKingState = agentKing;
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
