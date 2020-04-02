package ooga.model.engine;

import java.util.List;

public class MockGame implements GameFramework {
    BoardFramework myBoard;
    AgentPlayer myAgent;
    public MockGame(String gameType, List<List<Integer>> startingConfiguration){
        myBoard = new MockBoard(gameType, startingConfiguration);
        myAgent = new MockAgentPlayer(0);
    }


    @Override
    public void makeUserMove(List<Integer> moveCoordinates) throws InvalidMoveException {
        myBoard.makeMove(new MockCoordinate(0,0), new MockCoordinate(0,0));
    }

    @Override
    public void makeAgentMove() throws InvalidMoveException {
        myBoard.makeMove(myAgent.calculateMove(myBoard.copyBoard()).getKey(),myAgent.calculateMove(myBoard.copyBoard()).getValue());
    }

    @Override
    public int getUserGameStatus() {
        return 0;
    }

    @Override
    public List<List<Integer>> getVisualInfo() {
        return myBoard.getStateInfo();
    }
}
