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
     * @param userStates - all the states that belong to the user
     * @param agentStates - all the states that belong to the agent
     * @return boolean
     */
    public boolean checkNoMovesLeft(List<Integer> userStates, List<Integer> agentStates) {
        //TODO: decide whether or not to change to OR. game in Othello is not over until BOTH players don't have moves.
        return checkEmptyMovesForPlayer(userStates) &&
                checkEmptyMovesForPlayer(agentStates);
    }


    public boolean checkEmptyMovesForPlayer(List<Integer> playerStates) {
        //check that list of moves is EMPTY for every piece with playerID
        Map<Coordinate, List<Coordinate>> allMoves = getAllLegalMoves(playerStates);
        for (List<Coordinate> coords: allMoves.values()) {
            if (coords.size() > 0) {
                return false;
            }
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
     * @param playerStates - the list of states for the player checking to find moves for
     * @return a map which maps the start coordinates of a piece to all of the possible end coordinates that piece
     * can legally move to
     * uses tree map to sort the coordinates
     */
    @Override
    public Map<Coordinate, List<Coordinate>> getAllLegalMoves(List<Integer> playerStates) {
        Map<Coordinate, List<Coordinate>> allLegalMoves = new TreeMap<>();
        for (List<GamePiece> row: myGamePieces) {
            for (int col = 0; col < row.size();col++) {
                GamePiece currPiece = row.get(col);
                //TODO: later change to use data value for empty state
                if (playerStates.contains(currPiece.getState()) || currPiece.getState() == 0) {
                    Coordinate currCoord = currPiece.getPosition();
                    List<Coordinate> moves = currPiece.calculateAllPossibleMoves(getNeighbors(currPiece), playerStates.get(0));
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
     * @param player - the player to be moved
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
    public List<List<Integer>> possibleMovesVisualInfo(List<Integer> playerStates) {
        List<List<Integer>> possibleMovesConfig = new ArrayList<>();
        List<Coordinate> possibleMoves = getPossibleMovesAsList(playerStates);
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

    private List<Coordinate> getPossibleMovesAsList(List<Integer> playerStates) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        for (List<Coordinate> moves: getAllLegalMoves(playerStates).values()) {
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
