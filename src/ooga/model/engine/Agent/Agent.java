package ooga.model.engine.Agent;

import ooga.model.engine.Board;
import ooga.model.engine.BoardFramework;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;

import java.util.Map;

public interface Agent {

    int evaluateCurrentGameState(BoardFramework boardCopy);

    Map.Entry<Coordinate,Coordinate> findMoveByEvaluation(int eval);


    boolean isWin(int playerID);


}
