package ooga.model.engine.Agent;

import ooga.model.engine.Board;
import ooga.model.engine.Coordinate;
import ooga.model.engine.InvalidMoveException;

import java.util.Map;

public class TicTacToeAgent implements Agent {
    @Override
    public int evaluateMoves(Board boardCopy) {
        return 0;
    }

    @Override
    public int findWinner() {
        return 0;
    }

    @Override
    public boolean isWin() {
        return false;
    }

    @Override
    public boolean isLose() {
        return false;
    }
}
