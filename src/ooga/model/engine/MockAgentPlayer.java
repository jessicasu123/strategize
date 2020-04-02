package ooga.model.engine;

import java.util.List;
import java.util.Map;

public class MockAgentPlayer implements AgentPlayer {


    public MockAgentPlayer(int id){

    }


    @Override
    public Map.Entry<Coordinate,Coordinate> calculateMove(BoardFramework boardCopy) throws InvalidMoveException {
        Map<Coordinate, List<Coordinate>> myCoordinates = boardCopy.getAllLegalMoves(0);
        boardCopy.makeMove(new MockCoordinate(0,0),new MockCoordinate(0,0));
        return null;
    }

    @Override
    public int findWinner(BoardFramework boardCopy) {
        return 0;
    }
}
