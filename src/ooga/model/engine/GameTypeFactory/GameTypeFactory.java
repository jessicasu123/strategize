package ooga.model.engine.GameTypeFactory;

import ooga.model.engine.Agent.Agent;
import ooga.model.engine.Coordinate;
import ooga.model.engine.pieces.*;


public interface GameTypeFactory {

    public abstract GamePiece createGamePiece(int status, Coordinate position);

    public abstract Agent createAgent();


}
