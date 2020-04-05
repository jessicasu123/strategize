package ooga.model.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board implements BoardFramework{

    private List<List<GamePiece>> myGamePieces;
    private List<List<Integer>> myStartingConfiguration;
    private String myGameType;

    public Board(String gameType, List<List<Integer>> startingConfiguration) {
        myGamePieces = new ArrayList<>();
        myStartingConfiguration = startingConfiguration;
        myGameType = gameType;
    }

    private void createBoardFromStartingConfig() {
        int numRows = myStartingConfiguration.size();
        int numCols = myStartingConfiguration.get(0).size();
        GamePieceFactory gamePieceCreator = new GamePieceFactory();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                List<GamePiece> boardRow = new ArrayList<>();
                Coordinate pos = new Coordinate(r,c);
                int state = myStartingConfiguration.get(r).get(c);
                GamePiece newPiece = gamePieceCreator.createGamePiece(myGameType, state, pos);
                boardRow.append(newPiece);
            }
            myGamePieces.append(boardRow);
        }
    }

    private List<GamePiece> getNeighbors(GamePiece currPiece) {

    }

    @Override
    public Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player) {
        return null;
    }

    @Override
    public int evaluateBoard(int player) {
        return 0;
    }

    @Override
    public void makeMove(Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException {

    }

    @Override
    public List<List<Integer>> getStateInfo() {
        return Collections.unmodifiableList(myGamePieces);
    }

    @Override
    public BoardFramework copyBoard() {
        return null;
    }
}
