package ooga.model.engine;

import ooga.model.engine.GameTypeFactory.GameTypeFactory;
import ooga.model.engine.Neighborhood.Neighborhood;
import ooga.model.engine.Neighborhood.NeighborhoodFactory;
import ooga.model.engine.pieces.GamePiece;

import java.util.*;


public class Board implements BoardFramework{
    private List<List<GamePiece>> myGamePieces;
    private List<List<Integer>> myStartingConfiguration;
    private GameTypeFactory myGameTypeFactory;
    private int numRows;
    private int numCols;
    private boolean doesTurnChange;
    private List<String> myNeighborhoods;
    private NeighborhoodFactory neighborFactory;

    /**
     * Constructor to create a Board object.
     * @param gameType - type of game (ex. tic-tac-toe, mancala, etc.)
     * @param startingConfiguration - the starting configuration read from the JSON file
     */
    public Board(GameTypeFactory gameType, List<List<Integer>> startingConfiguration, List<String> neighborhoods) {
        myGamePieces = new ArrayList<>();
        myStartingConfiguration = startingConfiguration;
        myGameTypeFactory = gameType;
        myNeighborhoods = neighborhoods;
        neighborFactory = new NeighborhoodFactory();
        createBoardFromStartingConfig();
    }

    /**
     * Checks that there are no moves left for either the user player
     * or the agent player.
     * @param userID - the ID of the user player
     * @param agentID - the ID of the agent player
     * @return
     */
    public boolean checkNoMovesLeft(int userID, int agentID) {
        //TODO: decide whether or not to change to order. game in Othello is not over until BOTH players don't have moves.
        return checkEmptyMovesForPlayer(userID) &&
                checkEmptyMovesForPlayer(agentID);
    }

    private boolean checkEmptyMovesForPlayer(int playerID) {
        //check that list of moves is EMPTY for every piece with playerID
        for (List<Coordinate> coords: getAllLegalMoves(playerID).values()) {
            if (coords.size() > 0) return false;
        }
        return true;
    }

    private void createBoardFromStartingConfig() {
        numRows = myStartingConfiguration.size();
        numCols = myStartingConfiguration.get(0).size();
        for (int r = 0; r < numRows; r++) {
            List<GamePiece> boardRow = new ArrayList<>();
            for (int c = 0; c < numCols; c++) {
                Coordinate pos = new Coordinate(r,c);
                int state = myStartingConfiguration.get(r).get(c);
                GamePiece newPiece = myGameTypeFactory.createGamePiece(state, pos);
                boardRow.add(newPiece);
            }
            myGamePieces.add(boardRow);
        }
    }

    private List<GamePiece> getNeighbors(GamePiece currPiece) {
        //call on neighborhood to get neighbor positions
        //instantiate with actual GamePieces
        int pieceRow = currPiece.getPosition().getXCoord();
        int pieceCol = currPiece.getPosition().getYCoord();
        List<Coordinate> coordinates = getNeighborCoordinates(pieceRow,pieceCol);
        List<GamePiece> allNeighbors = new ArrayList<GamePiece>();
        for (Coordinate coord: coordinates) {
            int row = coord.getXCoord();
            int col = coord.getYCoord();
            allNeighbors.add(myGamePieces.get(row).get(col));
        }
        return allNeighbors;
    }

    private List<Coordinate> getNeighborCoordinates(int pieceRow, int pieceCol) {
        List<Coordinate> allCoords = new ArrayList<>();
        for (String neighbor: myNeighborhoods) {
            try {
                Neighborhood neighborhood = neighborFactory.createNeighborhood(neighbor, numRows, numCols);
                List<Coordinate> neighbors = neighborhood.getNeighbors(pieceRow,pieceCol);
                allCoords.addAll(neighbors);
            } catch (InvalidNeighborhoodException e) {
                System.out.println(e.getMessage());
            }
        }
        return allCoords;
    }

    /**
     * METHOD PURPOSE:
     *  - gets all the legal moves of each of the pieces of the player indicated by the parameter
     *  - this will be used by the Agent to determine the best move
     * @param player - the player whose moves you are looking for
     * @return a map which maps the start coordinates of a piece to all of the possible end coordinates that piece
     * can legally move to
     * uses tree map to sort the coordinates
     */
    @Override
    public Map<Coordinate, List<Coordinate>> getAllLegalMoves(int player) {
        Map<Coordinate, List<Coordinate>> allLegalMoves = new TreeMap<>();
        for (List<GamePiece> row: myGamePieces) {
            for (int col = 0; col < row.size();col++) {
                GamePiece currPiece = row.get(col);
                //TODO: later change to use data value for empty state
                if (currPiece.getState() == player || currPiece.getState() == 0) {
                    Coordinate currCoord = currPiece.getPosition();
                    List<Coordinate> moves = currPiece.calculateAllPossibleMoves(getNeighbors(currPiece),player);
                    if (moves.size()>0) {
                        allLegalMoves.put(currCoord, moves);
                    }
                }
            }
        }
        return allLegalMoves;
    }

    /**
     * METHOD PURPOSE:
     *  - moves a piece on the board and updates the state accordingly
     *  - calls on the Game pieces to do this
     *  - verifies the move
     * @param player - the player to be moved (1 or 2)
     * @param startCoordinate - the coordinate you are moving from
     * @param endCoordinate - the coordinate you are moving to (may be the same as start coordinate if no movement
     *                      is happening)
     */
    @Override
    public void makeMove(int player, Coordinate startCoordinate, Coordinate endCoordinate) throws InvalidMoveException {
        GamePiece curr = myGamePieces.get(startCoordinate.getXCoord()).get(startCoordinate.getYCoord());
        Coordinate oldPos = curr.getPosition();
        List<GamePiece> neighbors = getNeighbors(curr);
        if (curr.calculateAllPossibleMoves(neighbors,player).contains(endCoordinate)) {
            curr.makeMove(endCoordinate, neighbors, player);
            doesTurnChange = curr.changeTurnAfterMove();
            if(curr.getPosition() != oldPos){
                Coordinate currPosition = curr.getPosition();
                GamePiece switchedWith = myGamePieces.get(currPosition.getXCoord()).get(currPosition.getYCoord());
                myGamePieces.get(oldPos.getXCoord()).set(oldPos.getYCoord(), switchedWith);
                myGamePieces.get(currPosition.getXCoord()).set(currPosition.getYCoord(), curr);
            }
        } else {
            throw new InvalidMoveException("Your move to " + endCoordinate.toString() + " is invalid");
        }
    }


    public boolean changeTurns(){
        return doesTurnChange;
    }

    /**
     * METHOD PURPOSE:
     *  - gets the info for all of the current states of the game pieces for the front-end to use
     * @return list of list of the integers used to represent the state at each location
     */
    @Override
    public List<List<Integer>> getStateInfo() {
        List<List<Integer>> currStateConfig = new ArrayList<>();
        for (List<GamePiece> row: myGamePieces) {
            List<Integer> rowStates = new ArrayList<>();
            for (int col = 0; col < row.size(); col++) {
                int currState = row.get(col).getVisualRepresentation();
                rowStates.add(currState);
            }
            currStateConfig.add(rowStates);
        }
        return Collections.unmodifiableList(currStateConfig);
    }

    @Override
    public List<List<Integer>> possibleMovesVisualInfo(int playerID) {
        List<List<Integer>> possibleMovesConfig = new ArrayList<>();
        List<Coordinate> possibleMoves = getPossibleMovesAsList(playerID);
        for (int r = 0; r< numRows;r++) {
            List<Integer> possibleMovesRow = new ArrayList<>();
            for (int c = 0; c < numCols;c++) {
                if (possibleMoves.contains(new Coordinate(r,c))) {
                    possibleMovesRow.add(1);
                } else {
                    possibleMovesRow.add(0);
                }
            }
            possibleMovesConfig.add(possibleMovesRow);
        }
        return possibleMovesConfig; 
    }

    private List<Coordinate> getPossibleMovesAsList(int playerID) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        for (List<Coordinate> moves: getAllLegalMoves(playerID).values()) {
            for (Coordinate c: moves) {
                possibleMoves.add(c);
            }
        }
        return possibleMoves;
    }


    /**
     * METHOD PURPOSE:
     *  - makes a copy of the board so the agent can try out moves without affecting the actual game state
     * @return a copy of the board
     */
    @Override
    public BoardFramework copyBoard() {
        return new Board(myGameTypeFactory, new ArrayList<>(this.getStateInfo()), new ArrayList<>(this.myNeighborhoods));
    }
}
