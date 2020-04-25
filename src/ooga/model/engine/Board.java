package ooga.model.engine;

import ooga.model.engine.neighborhood.Neighborhood;
import ooga.model.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.GamePieceCreator;
import java.util.*;

/**
 * This class is responsible for managing the GamePieces, which includes
 * creating them, telling them when to make moves, and sending information about them
 * to other classes that may need the information. This information includes
 * whether there are moves left/all the legal moves (used by the backend),
 * as well as what the current states/number of objects/number of possible moves
 * that the view may need.
 *
 * @author Jessica Su, Holly Ansel
 */
public class Board implements BoardFramework{
    private List<List<GamePiece>> myGamePieces;
    private List<List<Integer>> myStartingConfiguration;
    private List<List<Integer>> myObjectConfiguration;
    private GamePieceCreator myGamePieceFactory;
    private int numRows;
    private int numCols;
    private int myEmptyState;
    private boolean doesTurnChange;
    private List<Neighborhood> myNeighborhoods;

    /**
     * Constructor to create a Board object.
     * @param gamePieces - type of game (ex. tic-tac-toe, mancala, etc.)
     * @param startingConfiguration - the starting configuration read from the JSON file
     * @param objectConfiguration - the number of objects at each row,col position
     * @param neighborhoods - the types of neighbors to consider while making a move on this board
     * @param emptyState - the integer representing the empty state (ex. 0)
     */
    public Board(GamePieceCreator gamePieces, List<List<Integer>> startingConfiguration,
                 List<List<Integer>> objectConfiguration, List<Neighborhood> neighborhoods, int emptyState) {
        myGamePieces = new ArrayList<>();
        myEmptyState = emptyState;
        myStartingConfiguration = startingConfiguration;
        myGamePieceFactory = gamePieces;
        myNeighborhoods = neighborhoods;
        myObjectConfiguration = objectConfiguration;
        createBoardFromStartingConfig();
    }

    /**
     * Checks that there are no moves left for either the user player
     * or the agent player.
     *  returns 0 if there are no more moves for either player
     *  returns 1 if there are no moves for player 1
     *  returns 2 if there are no moves for player 2
     *  returns 3 if there are still moves for both players
     * @param userStates - all the states that belong to the user
     * @param agentStates - all the states that belong to the agent
     * @return boolean
     */
    @Override
    public int checkNoMovesLeft(List<Integer> userStates, List<Integer> agentStates) {
        boolean noMovesForPlayer1 = checkEmptyMovesForPlayer(userStates);
        boolean noMovesForPlayer2 = checkEmptyMovesForPlayer(agentStates);
        if (noMovesForPlayer1 && noMovesForPlayer2) return 0;
        else if (noMovesForPlayer1) return 1;
        else if (noMovesForPlayer2) return 2;
        else return 3;
    }

    private boolean checkEmptyMovesForPlayer(List<Integer> playerStates) {
        Map<Coordinate, List<Coordinate>> allMoves = getAllLegalMoves(playerStates);
        return allMoves.size() == 0;
    }

    private void createBoardFromStartingConfig() {
        numRows = myStartingConfiguration.size();
        numCols = myStartingConfiguration.get(0).size();
        for (int r = 0; r < numRows; r++) {
            List<GamePiece> boardRow = new ArrayList<>();
            for (int c = 0; c < numCols; c++) {
                Coordinate pos = new Coordinate(r,c);
                int state = myStartingConfiguration.get(r).get(c);
                int object = myObjectConfiguration.get(r).get(c);
                GamePiece newPiece = myGamePieceFactory.createGamePiece(state, pos, object);
                boardRow.add(newPiece);
            }
            myGamePieces.add(boardRow);
        }
    }

    private List<GamePiece> getNeighbors(GamePiece currPiece) {
        int pieceRow = currPiece.getPosition().getRow();
        int pieceCol = currPiece.getPosition().getCol();
        List<Coordinate> coordinates = getNeighborCoordinates(pieceRow,pieceCol);
        List<GamePiece> allNeighbors = new ArrayList<>();
        for (Coordinate coord: coordinates) {
            int row = coord.getRow();
            int col = coord.getCol();
            allNeighbors.add(myGamePieces.get(row).get(col));
        }
        return allNeighbors;
    }

    private List<Coordinate> getNeighborCoordinates(int pieceRow, int pieceCol) {
        List<Coordinate> allCoords = new ArrayList<>();
        for (Neighborhood neighborhood: myNeighborhoods) {
            List<Coordinate> neighbors = neighborhood.getNeighbors(pieceRow,pieceCol);
            allCoords.addAll(neighbors);
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
            for(GamePiece currPiece: row){
                if (playerStates.contains(currPiece.getState()) || currPiece.getState() == myEmptyState) {
                    Coordinate currCoord = currPiece.getPosition();
                    List<Coordinate> moves = currPiece.calculateAllPossibleMoves(getNeighbors(currPiece), playerStates.get(0));
                    if (moves.size() > 0) {
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
        GamePiece curr = myGamePieces.get(startCoordinate.getRow()).get(startCoordinate.getCol());
        Coordinate oldPos = curr.getPosition();
        List<GamePiece> neighbors = getNeighbors(curr);
        if (curr.calculateAllPossibleMoves(neighbors,player).contains(endCoordinate)) {
            curr.makeMove(endCoordinate, neighbors, player);
            doesTurnChange = curr.changeTurnAfterMove();
            if(curr.getPosition() != oldPos){
                Coordinate currPosition = curr.getPosition();
                GamePiece switchedWith = myGamePieces.get(currPosition.getRow()).get(currPosition.getCol());
                myGamePieces.get(oldPos.getRow()).set(oldPos.getCol(), switchedWith);
                myGamePieces.get(currPosition.getRow()).set(currPosition.getCol(), curr);
            }
        } else {
            throw new InvalidMoveException("Your move to " + endCoordinate.toString() + " is invalid");
        }
    }


    /**
     * Determines whether the turn should be switched
     * according to the moves made by the pieces.
     * @return true if the turn changes, false if it doesn't
     */
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
        List<List<Integer>> currStateConfig = getVisualInfoFromPieces("state");
        return Collections.unmodifiableList(currStateConfig);
    }

    /**
     * METHOD PURPOSE:
     *  - gets the number of objects in a certain row,col position that the view will need
     *  for games where there are multiple pieces per square
     * @return list of list of the integers used to represent the number of objects at each location
     */
    @Override
    public List<List<Integer>> getObjectInfo() {
        List<List<Integer>> currObjectConfig = getVisualInfoFromPieces("object");
        return Collections.unmodifiableList(currObjectConfig);
    }

    private List<List<Integer>> getVisualInfoFromPieces(String visualInfoType) {
        List<List<Integer>> visualInfo = new ArrayList<>();
        for (List<GamePiece> row: myGamePieces) {
            List<Integer> rowObjects = new ArrayList<>();
            for (GamePiece gamePiece : row) {
                int curr;
                if (visualInfoType.equals("state")) {
                    curr = gamePiece.getState();
                } else {
                    curr = gamePiece.getNumObjects();
                }
                rowObjects.add(curr);
            }
            visualInfo.add(rowObjects);
        }
        return visualInfo;
    }

    /**
     * METHOD PURPOSE:
     *  - returns a visual representation of the possible moves.
     * @return list of list of integers with the same row/col dimensions of a board.
     *  - 1 indicates that a position is a possible move
     *  - 0 indicated that a position is NOT a possible move
     */
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
            possibleMoves.addAll(moves);
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
        return new Board(myGamePieceFactory, new ArrayList<>(this.getStateInfo()),new ArrayList<>(this.getObjectInfo()),
                new ArrayList<>(this.myNeighborhoods), myEmptyState);
    }
}
