package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.TicTacToeAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.TicTacToeGamePiece;

public class TicTacToeFactory implements GameTypeFactory{
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public TicTacToeFactory(int userPlayer, int agentPlayer){
        myMaxPlayer = agentPlayer;
        myMinPlayer = userPlayer;

    }
    @Override
    public GamePiece createGamePiece(int status, Coordinate position){
        return new TicTacToeGamePiece(status, position);
    }
    @Override
    public Agent createAgent(){
        return new TicTacToeAgent(myMaxPlayer, myMinPlayer, 3);
    }
}
