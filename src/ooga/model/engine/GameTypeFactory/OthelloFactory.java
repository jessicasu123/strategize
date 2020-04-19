package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.OthelloAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.OthelloGamePiece;

import java.util.List;

public class OthelloFactory implements GameTypeFactory {
    public static final int REGULAR_STATE_INDEX = 0;
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public OthelloFactory(List<Integer> userStates, List<Integer> agentStates){
        myMaxPlayer = agentStates.get(REGULAR_STATE_INDEX);
        myMinPlayer = agentStates.get(REGULAR_STATE_INDEX);
    }

    @Override
    public GamePiece createGamePiece(int status, Coordinate position) {
        return new OthelloGamePiece(status, position);
    }

    @Override
    public Agent createAgent() {
        return new OthelloAgent(myMaxPlayer,myMinPlayer);
    }
}
