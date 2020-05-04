package ooga.model.engine;

import ooga.model.engine.neighborhood.Neighborhood;
import ooga.model.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.GamePiece;
import ooga.model.engine.pieces.GamePieceCreator;
import java.util.*;

/**
 * CODE MASTERPIECE:
 * The Board sits between the Game and GamePieces and is an important design decision because
 * it is the only class that directly controls the GamePieces yet can still provide information
 * about the state of the game to other classes (Game, Controller, View) without them having
 * to interact with the GamePieces themselves.
 * I chose this class as my Code Masterpiece because it exhibits the following design principles:
 * 1. ENCAPSULATION - the data structure used to represent the Board itself is never revealed to
 * any other classes.
 *      - If other classes need information about the board (ex. getStateInfo, getObjectInfo, getPossibleMovesInfo),
 *      it is is wrapped in a BoardConfiguration object which can communicate
 *      the different states, number of objects, or possible moves for every position on the board.
 *      - Additionally, when the Board communicates to other classes (ex. Agent) about all the legal
 *      moves of a player, this information is also encapsulated in a LegalMovesCollection object
 *      to hide implementation details and allow for flexibility in the way this information is
 *      represented.
 * 2. MODULARITY - the Board's main responsibility is to manage the GamePiece move logic, which includes related operations
 * such as telling them when to make a move, keeping track of all possible moves, and knowing when there are no moves left.
 *      - Although this class may seem long, many of the helper methods are just matching information
 *      received from other classes (ex. coordinate from Neighborhood class) to the GamePiece information,
 *      since only the Board can access the GamePieces.
 *      - The Board is modular because it manages its own GamePiece data but delegates other tasks outside its main responsibility to other classes.
 *          - For example, in createBoardFromStartingConfig, the Board delegates the task of initializing GamePieces to the GamePieceCreator object.
 *          - Then, in getNeighborPositions, the Board calls on the Neighborhood objects to find the appropriate neighbors to give to each GamePiece.
 * 3. POLYMORPHISM
 *      - In getNeighborPositions, the getNeighbor method is called on a series of Neighborhood objects, which is a inheritance hierarchy with an abstract superclass.
 *      - The Board does NOT need to know what kind of Neighborhood it's getting neighbors from, so it's flexible in supporting any kind of neighborhood
 *      (horizontal, vertical, diagonal, toroidal, etc.) as long as the Neighborhood has a getNeighbors method.
 *      - These Neighborhoods are created according to the factory pattern and can be specified by the user in the JSON config file.
*  4. FLEXIBILITY / DATA-DRIVEN DESIGN
 *      - This Board is general enough to support ANY kind of strategy game that the user creates,
 *      as long as they specify the starting configuration, object configuration, and neighborhood
 *      types in the JSON config file.
 * 4. GOOD COMMUNICATION - methods are well-named, short, and have a single purpose
 *
 * Here are some commits that demonstrate my work on the Board class:
 * https://coursework.cs.duke.edu/compsci307_2020spring/final_team03/-/commit/919a80df23c4dfdb6bc8713194f3aa33dfad4500
 * https://coursework.cs.duke.edu/compsci307_2020spring/final_team03/-/commit/fd3a348fa52a11055e6d39393fc9e2d3b6790666
 * https://coursework.cs.duke.edu/compsci307_2020spring/final_team03/-/commit/447fbda6e9f4c3de2e318592d51edfcd08d5374c
 */

/**
 * This class is responsible for managing the GamePieces, which includes
 *  telling them when to make moves and sending information about them
 * to other classes that may need the information. This information includes
 * whether there are moves left/all the legal moves (used by the backend),
 * as well as what the current states/number of objects/number of possible moves
 * that the view may need.
 */
public class Board implements BoardFramework{
    public static final String STATE_INFO_IDENTIFIER = "state";
    public static final String OBJECT_INFO_IDENTIFIER = "object";
    private List<List<GamePiece>> myGamePieces;
    private BoardConfiguration myStartingConfiguration;
    private BoardConfiguration myObjectConfiguration;
    private GamePieceCreator myGamePieceFactory;
    private int numRows;
    private int numCols;
    private int myEmptyState;
    private boolean doesTurnChange;
    private List<Neighborhood> myNeighborhoods;

    /**
     * Constructor to create a Board object.
     * @param gamePieces - a GamePieceCreator object, which is responsible for storing all the
     *                   information (ex. move types, move checks, user states, agent states, etc.)
     *                   needed to create the game pieces and can be called on to create those pieces
     * @param startingConfiguration - the starting configuration read from the JSON file
     * @param objectConfiguration - the number of objects at each row,col position
     * @param neighborhoods - the types of neighbors to consider while making a move on this board
     * @param emptyState - the integer representing the empty state (ex. 0)
     */
    public Board(GamePieceCreator gamePieces, BoardConfiguration startingConfiguration,
                 BoardConfiguration objectConfiguration, List<Neighborhood> neighborhoods, int emptyState) {
        myGamePieces = new ArrayList<>();
        myEmptyState = emptyState;
        myStartingConfiguration = startingConfiguration;
        myGamePieceFactory = gamePieces;
        myNeighborhoods = neighborhoods;
        myObjectConfiguration = objectConfiguration;
        numRows = myStartingConfiguration.getNumRows();
        numCols = myStartingConfiguration.getNumCols();
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
    //checks if a certain player has no moves
    private boolean checkEmptyMovesForPlayer(List<Integer> playerStates) {
        return getAllLegalMoves(playerStates).getSize()==0;
    }
    //initializes a board based on the starting config from the JSON game file
    private void createBoardFromStartingConfig() {
        for (int r = 0; r < numRows; r++) {
            List<GamePiece> boardRow = new ArrayList<>();
            for (int c = 0; c < numCols; c++) {
                Coordinate pos = new Coordinate(r,c);
                int state = myStartingConfiguration.getValue(r,c);
                int object = myObjectConfiguration.getValue(r,c);
                GamePiece newPiece = myGamePieceFactory.createGamePiece(state, pos, object);
                boardRow.add(newPiece);
            }
            myGamePieces.add(boardRow);
        }
    }
    //based on the neighbor position returned by getNeighborPositions, finds the corresponding GamePieces
    private List<GamePiece> getGamePieceNeighbors(GamePiece currPiece) {
        List<Coordinate> coordinates = getNeighborPositions(currPiece.getPosition().getRow(),
                currPiece.getPosition().getCol());
        List<GamePiece> allNeighbors = new ArrayList<>();
        for (Coordinate c: coordinates) {
            allNeighbors.add(myGamePieces.get(c.getRow()).get(c.getCol()));
        }
        return allNeighbors;
    }
    //good example of MODULARITY/POLYMORPHISM/FLEXIBILITY - calls on neighborhood object to find neighbors
    private List<Coordinate> getNeighborPositions(int pieceRow, int pieceCol) {
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
    public LegalMovesCollection getAllLegalMoves(List<Integer> playerStates) {
        LegalMovesCollection allLegalMoves = new LegalMovesCollection();
        for (List<GamePiece> row: myGamePieces) {
            for(GamePiece currPiece: row){
                if (playerStates.contains(currPiece.getState()) || currPiece.getState() == myEmptyState) {
                    List<Coordinate> moves = currPiece.calculateAllPossibleMoves(getGamePieceNeighbors(currPiece), playerStates.get(0));
                    if (moves.size() > 0) {
                        allLegalMoves.add(currPiece.getPosition(), moves);
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
        List<GamePiece> neighbors = getGamePieceNeighbors(curr);
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
    public boolean changeTurns(){ return doesTurnChange; }

    /**
     * METHOD PURPOSE:
     *  - gets the info for all of the current states of the game pieces for the front-end to use
     * @return list of list of the integers used to represent the state at each location
     */
    @Override
    public BoardConfiguration getStateInfo() { return getVisualInfoFromPieces(STATE_INFO_IDENTIFIER); }

    /**
     * METHOD PURPOSE:
     *  - gets the number of objects in a certain row,col position that the view will need
     *  for games where there are multiple pieces per square
     * @return list of list of the integers used to represent the number of objects at each location
     */
    @Override
    public BoardConfiguration getObjectInfo() { return getVisualInfoFromPieces(OBJECT_INFO_IDENTIFIER); }
    //gets either the object info or state info from all the GamePieces
    private BoardConfiguration getVisualInfoFromPieces(String visualInfoType) {
        BoardConfiguration boardInfoConfig = new BoardConfiguration(myGamePieces.size(), myGamePieces.get(0).size());
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int curr;
                if (visualInfoType.equals(STATE_INFO_IDENTIFIER)) {
                    curr = myGamePieces.get(r).get(c).getState();
                } else {
                    curr = myGamePieces.get(r).get(c).getNumObjects();
                }
                boardInfoConfig.setValue(r,c,curr);
            }
        }
        return boardInfoConfig;
    }

    /**
     * METHOD PURPOSE:
     *  - returns a visual representation of the possible moves.
     * @return list of list of integers with the same row/col dimensions of a board.
     *  - 1 indicates that a position is a possible move
     *  - 0 indicated that a position is NOT a possible move
     */
    @Override
    public BoardConfiguration getPossibleMovesInfo(List<Integer> playerStates) {
        BoardConfiguration possibleMovesConfig = new BoardConfiguration(myGamePieces.size(), myGamePieces.get(0).size());
        List<Coordinate> possibleMoves = getAllLegalMoves(playerStates).getAllPossibleMoves();
        for (int r = 0; r< numRows;r++) {
            for (int c = 0; c < numCols;c++) {
                if (possibleMoves.contains(new Coordinate(r,c))) {
                    possibleMovesConfig.setValue(r,c,1);
                } else {
                    possibleMovesConfig.setValue(r,c,0);
                }
            }
        }
        return possibleMovesConfig; 
    }

    /**
     * METHOD PURPOSE:
     *  - makes a copy of the board so the agent can try out moves without affecting the actual game state
     * @return a copy of the board
     */
    @Override
    public BoardFramework copyBoard() {
        return new Board(myGamePieceFactory, getStateInfo(), getObjectInfo(), myNeighborhoods, myEmptyState);
    }
}
