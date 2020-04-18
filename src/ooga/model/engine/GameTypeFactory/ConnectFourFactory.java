package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.ConnectFourAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.ConnectFourGamePiece;
import ooga.model.engine.pieces.GamePiece;

import java.util.List;

public class ConnectFourFactory implements GameTypeFactory{
    public static final int REGULAR_STATE_INDEX = 0;
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public ConnectFourFactory(List<Integer> userStates, List<Integer> agentStates){
        myMaxPlayer = agentStates.get(REGULAR_STATE_INDEX);
        myMinPlayer = userStates.get(REGULAR_STATE_INDEX);
    }

    @Override
    public GamePiece createGamePiece(int status, Coordinate position) {
        return new ConnectFourGamePiece(status, position);
    }

    @Override
    public Agent createAgent() {
        return new ConnectFourAgent(myMaxPlayer,myMinPlayer, 4);
    }
}
