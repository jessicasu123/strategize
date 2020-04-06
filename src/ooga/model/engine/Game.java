package ooga.model.engine;

import java.util.List;

public class Game implements GameFramework{
    BoardFramework myBoard;
    //TODO: create AgentPlayer instance var

    public Game(String gameType, List<List<Integer>> startingConfiguration) {
        myBoard = new Board(gameType, startingConfiguration);
    }

    @Override
    public void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        Coordinate startCoord = new Coordinate(moveCoordinates.get(0), moveCoordinates.get(1));
        Coordinate endCoord = new Coordinate(moveCoordinates.get(2), moveCoordinates.get(3));
        myBoard.makeMove(startCoord, endCoord);
    }

    @Override
    public void makeAgentMove() throws InvalidMoveException {
        //TODO: call on agent player calculateMove
    }

    @Override
    public int getEndGameStatus() {
        //TODO: call on agent player findWinner()
        return 0;
    }

    @Override
    public List<List<Integer>> getVisualInfo() {
        return myBoard.getStateInfo();
    }
}
