package ooga.model.engine;

import ooga.model.engine.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockBoard implements BoardFramework {

    List<List<GamePiece>> myGamePieces;

    public MockBoard(String gameType, List<List<Integer>> startingConfiguration){
        myGamePieces = new ArrayList<>();
    }

    @Override
    public Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player) {
        return null;
    }

    @Override
    public void makeMove(Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException {
        GamePiece curr = myGamePieces.get(startCoordinate.getXCoord()).get(startCoordinate.getYCoord());
        if(curr.calculateAllPossibleMoves(new ArrayList<GamePiece>()).contains(endCoordinate)){
            curr.makeMove(endCoordinate, new ArrayList<GamePiece>());
        }else{
            throw new InvalidMoveException();
        }

    }

    @Override
    public List<List<Integer>> getStateInfo() {
        return null;
    }

    @Override
    public BoardFramework copyBoard() {
        return null;
    }
}
