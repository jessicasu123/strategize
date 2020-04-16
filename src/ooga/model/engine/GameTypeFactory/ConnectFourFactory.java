package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.ConnectFourAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.ConnectFourGamePiece;
import ooga.model.engine.pieces.GamePiece;

public class ConnectFourFactory implements GameTypeFactory{
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public ConnectFourFactory(int userPlayer, int agentPlayer){
        myMaxPlayer = agentPlayer;
        myMinPlayer = userPlayer;
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
