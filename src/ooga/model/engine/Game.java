package ooga.model.engine;

import java.util.List;

public class Game implements GameFramework{
    BoardFramework myBoard;

    public Game(String gameType, List<List<Integer>> startingConfiguration) {
        myBoard = new Board(gameType, startingConfiguration);
    }

    @Override
    public void makeAgentMove() throws InvalidMoveException {

    }

    @Override
    public int getEndGameStatus() {
        return 0;
    }

    @Override
    public List<List<Integer>> getVisualInfo() {
        return myBoard.getStateInfo();
    }
}
