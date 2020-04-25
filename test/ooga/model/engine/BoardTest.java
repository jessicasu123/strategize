package ooga.model.engine;

import ooga.model.engine.neighborhood.*;
import ooga.model.engine.player.PlayerInfoHolder;
import ooga.model.exceptions.InvalidMoveException;
import ooga.model.engine.pieces.*;
import ooga.model.engine.pieces.moveChecks.*;
import ooga.model.engine.pieces.convertibleNeighborFinder.ConvertibleNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.FlippableNeighborFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.NeighborsBetweenCoordinatesFinder;
import ooga.model.engine.pieces.convertibleNeighborFinder.NeighborsUntilNoObjectsFinder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.*;

public class BoardTest {
    Integer[][] startingConfig = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    Integer[][] noMovesConfig = {
            {1, 2, 1},
            {1, 2, 1},
            {2, 1, 2}
    };

    public List<List<Integer>> createTestConfig(Integer[][] testConfig) {
        List<List<Integer>> config = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            config.add(Arrays.asList(testConfig[i]));
        }
        return config;
    }

    //creating tic tac toe board with one player in center
    List<List<Integer>> config = createTestConfig(startingConfig);
    List<Neighborhood> neighborhoods = new ArrayList<>();
    List<Integer> user = new ArrayList<>(List.of(1));
    List<Integer> agent = new ArrayList<>(List.of(2));
    List<Integer> zeros = new ArrayList<>(List.of(0,0,0));
    List<Integer> direction = new ArrayList<>(List.of(1));
    MoveCheck checkEmptyState = new EmptyStateCheck(0);
    MoveType changeToNewState = new ChangeToNewStateMove();
    List<List<Integer>> objectConfig = new ArrayList<>(List.of(zeros,zeros,zeros));
    PlayerInfoHolder player1InfoTicTacToe = new PlayerInfoHolder(user, direction,new ArrayList<>(List.of(checkEmptyState)),new ArrayList<>(),new ArrayList<>(List.of(changeToNewState)),true);
    PlayerInfoHolder player2InfoTicTacToe = new PlayerInfoHolder(agent,direction,new ArrayList<>(List.of(checkEmptyState)),new ArrayList<>(),new ArrayList<>(List.of(changeToNewState)),false);

    GamePieceCreator gamePieceCreatorTicTacToe = new GamePieceCreator(player1InfoTicTacToe, player2InfoTicTacToe);

    Board ticTacToeBoard = new Board(gamePieceCreatorTicTacToe, config,objectConfig, neighborhoods,0);

    //board that has no more moves
    List<List<Integer>> noMoves = createTestConfig(noMovesConfig);
    Board noMovesBoard = new Board(gamePieceCreatorTicTacToe, noMoves,objectConfig, neighborhoods,0);

    List<Integer> row1 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row2 = new ArrayList<>(List.of(0, 0, 1, 0, 0, 0, 0, 0));
    List<Integer> row3 = new ArrayList<>(List.of(0, 1, 1, 0, 0, 0, 0, 0));
    List<Integer> row4 = new ArrayList<>(List.of(2, 2, 2, 2, 1, 0, 0, 0));
    List<Integer> row5 = new ArrayList<>(List.of(0, 0, 0, 1, 2, 0, 0, 0));
    List<Integer> row6 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row7 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));
    List<Integer> row8 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0));

    MoveCheck checkAllFlippableDirections = new AllFlippableDirectionsCheck();
    List<MoveCheck> moveChecks = List.of(checkEmptyState, checkAllFlippableDirections);

    FlippableNeighborFinder allFlippableNeighbors = new FlippableNeighborFinder();
    ChangeOpponentPiecesMove changeOpponentPiecesMove = new ChangeOpponentPiecesMove(allFlippableNeighbors, false,0);
    List<MoveType> moveTypes = List.of(changeToNewState, changeOpponentPiecesMove);

    PlayerInfoHolder player1Info = new PlayerInfoHolder(user, direction,moveChecks,new ArrayList<>(),moveTypes,true);
    PlayerInfoHolder player2Info = new PlayerInfoHolder(agent,direction,moveChecks,new ArrayList<>(),moveTypes,false);

    GamePieceCreator gamePieceCreator = new GamePieceCreator(player1Info, player2Info);

    List<List<Integer>> othelloConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6, row7, row8));
    Neighborhood horizontal = new HorizontalNeighborhood(8,8);
    Neighborhood vertical = new VerticalNeighborhood(8,8);
    Neighborhood diagonal = new DiagonalNeighborhood(8,8);
    List<Neighborhood> othelloNeighborhoods = List.of(horizontal, vertical, diagonal);
    List<List<Integer>> objectConfig2 = new ArrayList<>(List.of(row1,row1,row1,row1,row1,row1,row1,row1));
    Board othelloBoard = new Board(gamePieceCreator, othelloConfig,objectConfig2, othelloNeighborhoods,0);

    @Test
    void testOthelloBoard() {
        Map<Coordinate, List<Coordinate>> moves = othelloBoard.getAllLegalMoves(user);
        for (Coordinate c : moves.keySet()) {
            System.out.println(moves.get(c));
        }
    }

    @Test
    void testCreateBoardFromStartingConfig() {
        /**
         * stateInfo is a reflection of the Board contents -
         * it populates its values based on the STATE of the
         * PIECES initialized by the board. If the board is initialized
         * with game pieces correctly, their state should match the starting
         * config values.
         */

        List<List<Integer>> stateInfo = ticTacToeBoard.getStateInfo();
        assertEquals(stateInfo, config);
    }

    @Test
    void testCopyBoard() {
        BoardFramework newBoard = ticTacToeBoard.copyBoard();
        //making a move on newBoard - this should NOT affect other board, b
        newBoard.makeMove(1, new Coordinate(0, 0), new Coordinate(0, 0));

        //checking that states for newBoard are updated, but b remains same
        assertNotEquals(newBoard.getStateInfo(), ticTacToeBoard.getStateInfo());

        //b should still reflect starting config
        assertEquals(ticTacToeBoard.getStateInfo(), config);
    }

    @Test
    void testMakeMove() {
        //test for a valid move
        BoardFramework testMoveBoard = ticTacToeBoard.copyBoard();
        testMoveBoard.makeMove(1, new Coordinate(2, 2), new Coordinate(2, 2));
        Integer[][] desiredConfig = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        assertEquals(createTestConfig(desiredConfig), testMoveBoard.getStateInfo());

        //test for invalid move - can't try to move on a square that already has a player
        assertThrows(InvalidMoveException.class,
                () -> testMoveBoard.makeMove(2, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    @Test
    void testGetAllLegalMoves() {
        Map<Coordinate, List<Coordinate>> moves = ticTacToeBoard.getAllLegalMoves(user);
        Coordinate squareWithPlayer = new Coordinate(1, 1);

        //checking that a coordinate with an empty square is a legal "move"
        Coordinate emptySquare = new Coordinate(1, 2);
        assertEquals(1, moves.get(emptySquare).size());
    }

    @Test
    void testNoMovesLeft() {
        //should be no moves left on a full board with no winner
        assertEquals(0, noMovesBoard.checkNoMovesLeft(user,agent));
        //should be moves left on board with only one player
        assertTrue(ticTacToeBoard.checkNoMovesLeft(user,agent)!=0);
    }

    @Test
    void testCheckersBoard() {
        List<Integer> player1 = new ArrayList<>(List.of(1,3));
        List<Integer> player1Direction = new ArrayList<>(List.of(1));
        List<Integer> player2 = new ArrayList<>(List.of(2,4));
        List<Integer> player2Direction = new ArrayList<>(List.of(-1));
        int emptyState = 0;
        ConvertibleNeighborFinder myFinder = new NeighborsBetweenCoordinatesFinder();
        MoveCheck ownPiecePlayer1 = new OwnPieceCheck(player1);
        MoveCheck ownPiecePlayer2 = new OwnPieceCheck(player2);
        MoveCheck step = new StepCheck(emptyState);
        MoveCheck jumpPlayer1 = new JumpCheck(emptyState, player1);
        MoveCheck jumpPlayer2 = new JumpCheck(emptyState, player2);
        MoveType changeOpponent = new ChangeOpponentPiecesMove(myFinder,true,emptyState);
        MoveType positon = new PositionMove();
        MoveType promotionPlayer1 = new PromotionMove(0,3);
        MoveType promotionPlayer2 = new PromotionMove(8,4);

        List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1));
        List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2));
        List<MoveCheck> neighborMoveCheckPlayer1 = new ArrayList<>(List.of(step, jumpPlayer1));
        List<MoveCheck> neighborMoveCheckPlayer2 = new ArrayList<>(List.of(step, jumpPlayer2));
        List<MoveType> moveTypesPlayer1 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer1));
        List<MoveType> moveTypesPlayer2 = new ArrayList<>(List.of(changeOpponent,positon,promotionPlayer2));


        PlayerInfoHolder player1Info = new PlayerInfoHolder(player1,player1Direction,selfMoveCheckPlayer1,neighborMoveCheckPlayer1,moveTypesPlayer1,true);
        PlayerInfoHolder player2Info = new PlayerInfoHolder(player2,player2Direction,selfMoveCheckPlayer2,neighborMoveCheckPlayer2,moveTypesPlayer2,false);

        GamePieceCreator gamePieceCreator = new GamePieceCreator(player1Info, player2Info);


        row1 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row2 =new ArrayList<>(List.of(0,0,1,0,0,0,0,0));
        row3 =new ArrayList<>(List.of(0,1,1,0,0,0,0,0));
        row4 =new ArrayList<>(List.of(2,2,2,2,1,0,0,0));
        row5 =new ArrayList<>(List.of(0,0,0,1,2,0,0,0));
        row6 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row7 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        row8 =new ArrayList<>(List.of(0,0,0,0,0,0,0,0));
        List<List<Integer>> checkersConfig = new ArrayList<>(List.of(row1, row2, row3, row4, row5, row6, row7, row8));
        Neighborhood diagonal = new DiagonalNeighborhood(8,8);
        List<Neighborhood> checkersNeighborhoods = List.of(diagonal);
       Board checkersBoard = new Board(gamePieceCreator, checkersConfig,objectConfig2, checkersNeighborhoods,0);

        Coordinate start = new Coordinate(1,2);
        Coordinate end = new Coordinate(2,3);
        checkersBoard.makeMove(1,start,end);
        assertNotEquals(checkersConfig, checkersBoard.getStateInfo());
    }

    @Test
    void testMancalaBoard() {
        List<Integer> player1 = new ArrayList<>(List.of(2,1));
        List<Integer> player1Direction = new ArrayList<>(List.of(-1));
        List<Integer> player2 = new ArrayList<>(List.of(4,3));
        List<Integer> player2Direction = new ArrayList<>(List.of(1));
        List<Integer> player1StatesToIgnore = new ArrayList<>(List.of(0,3));
        List<Integer> player2StatesToIgnore = new ArrayList<>(List.of(0,1));
        ConvertibleNeighborFinder player1Finder = new NeighborsUntilNoObjectsFinder(player1StatesToIgnore);
        ConvertibleNeighborFinder player2Finder = new NeighborsUntilNoObjectsFinder(player2StatesToIgnore);

        MoveCheck ownPiecePlayer1 = new OwnPieceCheck(player1);
        MoveCheck ownPiecePlayer2 = new OwnPieceCheck(player2);
        MoveCheck immovablePlayer1 = new NotImmovableCheck(1);
        MoveCheck immovablePlayer2 = new NotImmovableCheck(3);
        MoveCheck numObjectsCheck = new NumObjectsCheck(0);

        MoveType forceMoveAgainPlayer1 = new ForceMoveAgainMove(player1,player1Finder);
        MoveType forceMoveAgainPlayer2 = new ForceMoveAgainMove(player2,player2Finder);
        MoveType specialCapturePlayer1 = new SpecialCaptureMove(player1,player1Finder);
        MoveType specialCapturePlayer2 = new SpecialCaptureMove(player2,player2Finder);
        MoveType changeNeighborsPlayer1 = new ChangeNeighborObjectsMove(player1Finder, false);
        MoveType changeNeighborsPlayer2 = new ChangeNeighborObjectsMove(player2Finder, false);
        MoveType clear = new ClearObjectsMove();

        List<MoveCheck> selfMoveCheckPlayer1 = new ArrayList<>(List.of(ownPiecePlayer1, immovablePlayer1, numObjectsCheck));
        List<MoveCheck> selfMoveCheckPlayer2 = new ArrayList<>(List.of(ownPiecePlayer2, immovablePlayer2, numObjectsCheck));
        List<MoveCheck> neighborMoveCheck = new ArrayList<>();
        List<MoveType> moveTypesPlayer1 = new ArrayList<>(List.of(forceMoveAgainPlayer1, changeNeighborsPlayer1,specialCapturePlayer1,clear));
        List<MoveType> moveTypesPlayer2 = new ArrayList<>(List.of(forceMoveAgainPlayer2, changeNeighborsPlayer2,specialCapturePlayer2,clear));

        PlayerInfoHolder player1Info = new PlayerInfoHolder(player1,player1Direction,selfMoveCheckPlayer1,neighborMoveCheck,moveTypesPlayer1,true);
        PlayerInfoHolder player2Info = new PlayerInfoHolder(player2,player2Direction,selfMoveCheckPlayer2,neighborMoveCheck,moveTypesPlayer2,false);

        GamePieceCreator gamePieceCreator = new GamePieceCreator(player1Info, player2Info);
        row1 =new ArrayList<>(List.of(1,2,2,2,2,2,2,0));
        row2 =new ArrayList<>(List.of(0,4,4,4,4,4,4,3));
        List<List<Integer>> mancalaConfig = new ArrayList<>(List.of(row1, row2));
        Neighborhood all = new CompleteNeighborhood(2,8);
        List<Neighborhood> mancalaNeighborhoods = List.of(all);
        List<Integer> rowConfig = new ArrayList<>(List.of(0,4,4,4,4,4,4,0));
        List<List<Integer>> objectConfig3 = new ArrayList<>(List.of(rowConfig,rowConfig));
        Board mancalaBoard = new Board(gamePieceCreator, mancalaConfig,objectConfig3, mancalaNeighborhoods,0);
        Coordinate start = new Coordinate(0,2);
        Coordinate end = new Coordinate(0,2);
        mancalaBoard.makeMove(2,start,end);
        assertNotEquals(objectConfig3, mancalaBoard.getObjectInfo());
        assertEquals(mancalaConfig, mancalaBoard.getStateInfo());
    }
}

