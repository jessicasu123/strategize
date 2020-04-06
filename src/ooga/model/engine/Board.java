package ooga.model.engine;

import java.util.*;

public class Board implements BoardFramework{

    private List<List<GamePiece>> myGamePieces;
    private List<List<Integer>> myStartingConfiguration;
    private String myGameType;
    private int numRows;
    private int numCols;

    /**
     * Constructor to create a Board object.
     * @param gameType - type of game (ex. tic-tac-toe, mancala, etc.)
     * @param startingConfiguration - the starting configuration read from the JSON file
     */
    public Board(String gameType, List<List<Integer>> startingConfiguration) {
        myGamePieces = new ArrayList<>();
        myStartingConfiguration = startingConfiguration;
        myGameType = gameType;
        createBoardFromStartingConfig();
    }

    /**
     * Constructor for copying board to give it to the AgentPlayer.
     */
    public Board (Board originalBoard) {
        this.myGameType = originalBoard.myGameType;
        this.myStartingConfiguration = originalBoard.myStartingConfiguration;
        //need to deep copy the current state of game pieces
        this.myGamePieces = new ArrayList<>(originalBoard.myGamePieces);
    }

    private void createBoardFromStartingConfig() {
        numRows = myStartingConfiguration.size();
        numCols = myStartingConfiguration.get(0).size();
        GamePieceFactory gamePieceCreator = new GamePieceFactory();
        for (int r = 0; r < numRows; r++) {
            List<GamePiece> boardRow = new ArrayList<>();
            for (int c = 0; c < numCols; c++) {
                Coordinate pos = new Coordinate(r,c);
                int state = myStartingConfiguration.get(r).get(c);
                GamePiece newPiece = null;
                try {
                    newPiece = gamePieceCreator.createGamePiece(myGameType, state, pos);
                } catch (InvalidGameTypeException e) {
                    e.printStackTrace();
                }
                boardRow.add(newPiece);
            }
            myGamePieces.add(boardRow);
        }
    }

    private List<GamePiece> getNeighbors(GamePiece currPiece) {
        //TODO: implement finding neighbors
        List<GamePiece> allNeighbors = new ArrayList<GamePiece>();
        int r = currPiece.getPosition().getXCoord();
        int c = currPiece.getPosition().getYCoord();
        allNeighbors.addAll(getMainDiagNeighbors(r,c)); //adding main diag
        allNeighbors.addAll(getMinorDiagNeighbors(r,c)); //adding minor diag
        allNeighbors.addAll(getHorizontalAndVerticalNeighbors(r,c)); //adding horiz & vert
        return allNeighbors;
    }

    private List<GamePiece> getMainDiagNeighbors(int r, int c) {
        int row = 0;
        int col = 0;
        int boundChecker, upperLim;
        if (c > r) { //to the right of center diag
            col = c - r; //col offset on row 0
            boundChecker = col; //ends on last column
            upperLim = numCols;
        }
        else { //to the left or ON center diag
            row = r - c; //row offset on col 0
            boundChecker = row; //ends on last row
            upperLim = numRows;
        }
        List<GamePiece> mainDiag = new ArrayList<>();
        while (boundChecker < upperLim) {
            addPiece(mainDiag,r,c,row,col);
            row++;
            col++;
            boundChecker++;
        }
        return mainDiag;
    }

    private List<GamePiece> getMinorDiagNeighbors(int r, int c) {
        int row = 0;
        int col = numCols-1;
        int lowerlim;
        boolean colBound = false;
        boolean rowBound = false;
        List<GamePiece> minorDiag = new ArrayList<>();
        if (r+c<=numCols-1) { //to the left or ON center diag
            lowerlim = 0; //ends on col 0
            col = r+c;
            colBound = true;
        }
        else { //to the right of center diag
            lowerlim = numRows; //ends on last row
            row = (r+c)-(numCols-1);
            rowBound = true;
        }
        while ((row < lowerlim && rowBound) || (col >= lowerlim && colBound)){
            addPiece(minorDiag, r,c,row,col);
            row++;
            col--;
        }
        return minorDiag;
    }

    private List<GamePiece> getHorizontalAndVerticalNeighbors(int r, int c) {
        List<GamePiece> horizAndVert = new ArrayList<>();
        boolean addedRow = false;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (horizAndVert.size()==numCols+numRows) { break; }
                if (!addedRow && row == r) {
                    for (int i = 0; i < numCols; i++) {
                        addPiece(horizAndVert,r,c,row,i); //adding row
                    }
                    addedRow = true;
                }
                if (col==c) { addPiece(horizAndVert,r,c,row,col); }
            }
        }
        return horizAndVert;
    }

    private void addPiece(List<GamePiece> lst, int origRow, int origCol, int newRow, int newCol) {
        if (newRow != origRow && newCol != origCol) {
            lst.add(myGamePieces.get(newRow).get(newCol));
        }
    }

    @Override
    public Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player) {
        Map<Coordinate, List<Coordinate>> allLegalMoves = new HashMap<>();
        for (List<GamePiece> row: myGamePieces) {
            for (int col = 0; col < row.size();col++) {
                GamePiece currPiece = row.get(col);
                if (currPiece.getStatus()==player) {
                    Coordinate currCoord = currPiece.getPosition();
                    List<Coordinate> moves = currPiece.calculateAllPossibleMoves(getNeighbors(currPiece));
                    allLegalMoves.put(currCoord, moves);
                }
            }
        }
        return allLegalMoves;
    }

    @Override
    public int evaluateBoard(int player) {
        //TODO: figure out what this does
        return 0;
    }

    @Override
    public void makeMove(Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException {
        GamePiece curr = myGamePieces.get(startCoordinate.getXCoord()).get(startCoordinate.getYCoord());
        List<GamePiece> neighbors = getNeighbors(curr);
        if (curr.calculateAllPossibleMoves(neighbors).contains(endCoordinate)) {
            curr.makeMove(endCoordinate, neighbors);
        } else {
            throw new InvalidMoveException("Your move to " + endCoordinate.toString() + " is invalid");
        }
    }

    @Override
    public List<List<Integer>> getStateInfo() {
        List<List<Integer>> currStateConfig = new ArrayList<>();
        for (List<GamePiece> row: myGamePieces) {
            List<Integer> rowStates = new ArrayList<>();
            for (int col = 0; col < row.size(); col++) {
                int currState = row.get(col).getState();
                rowStates.add(currState);
            }
            currStateConfig.add(rowStates);
        }
        return Collections.unmodifiableList(currStateConfig);
    }

    @Override
    public BoardFramework copyBoard() {
        //TODO: test that changing values of copied board don't affect original
        return new Board(this);
    }
}
