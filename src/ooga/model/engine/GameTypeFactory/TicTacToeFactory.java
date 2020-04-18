package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Agent.TicTacToeAgent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.TicTacToeGamePiece;

import java.util.List;

public class TicTacToeFactory implements GameTypeFactory{
    public static final int REGULAR_STATE_INDEX = 0;
    private final int myMaxPlayer;
    private final int myMinPlayer;

    public TicTacToeFactory(List<Integer> userStates, List<Integer> agentStates){
        myMaxPlayer = agentStates.get(REGULAR_STATE_INDEX);
        myMinPlayer = userStates.get(REGULAR_STATE_INDEX);

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
