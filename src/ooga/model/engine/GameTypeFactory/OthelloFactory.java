package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.OthelloAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.OthelloGamePiece;

public class OthelloFactory implements GameTypeFactory {
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public OthelloFactory(int userPlayer, int agentPlayer){
        myMaxPlayer = agentPlayer;
        myMinPlayer = userPlayer;
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
