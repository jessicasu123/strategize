package ooga.model.engine.agent.winTypes;

import ooga.model.engine.BoardConfiguration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoMovesMorePiecesTest {
    List<Integer> mancalaInitRow1 = new ArrayList<>(List.of(4,3,3,3,3,3,3,0));
    List<Integer> mancalaInitRow2 = new ArrayList<>(List.of(0,1,1,1,1,1,1,2));
    BoardConfiguration initialConfig = new BoardConfiguration(new ArrayList<>(List.of(mancalaInitRow1, mancalaInitRow2)));
    List<Integer> mancalaPlayer1States = new ArrayList<>(List.of(1,2));
    List<Integer> mancalaPlayer2States = new ArrayList<>(List.of(3,4));

    NoMovesMorePieces mancalaWinType = new NoMovesMorePieces(1, initialConfig, false, 0);

    List<Integer> othelloPlayer1States = new ArrayList<>(List.of(1));
    List<Integer> othelloPlayer2States = new ArrayList<>(List.of(2));

    NoMovesMorePieces othelloWinType = new NoMovesMorePieces(0, initialConfig, true, 0);


    @Test
    void testMancalaGameInProgress() {
        List<Integer> gipRow1 = new ArrayList<>(List.of(14, 12, 0, 0, 0, 2, 0, 0));
        List<Integer> gipRow2 = new ArrayList<>(List.of(0, 1, 1, 1, 0, 0, 0, 17));
        BoardConfiguration boardForGameInProgress = new BoardConfiguration(new ArrayList<>(List.of(gipRow1, gipRow2)));
        assertFalse(mancalaWinType.isWin(mancalaPlayer1States, boardForGameInProgress,boardForGameInProgress, false));
    }

    @Test
    void testMancalaPlayer1Win() {
        List<Integer> userWinRow1 = new ArrayList<>(List.of(20, 0, 0, 0, 0, 0, 0, 0));
        List<Integer> userWinRow2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 28));
        BoardConfiguration boardForUserWin = new BoardConfiguration(new ArrayList<>(List.of(userWinRow1, userWinRow2)));
        assertTrue(mancalaWinType.isWin(mancalaPlayer1States, boardForUserWin,boardForUserWin, true));
        assertFalse(mancalaWinType.isWin(mancalaPlayer2States, boardForUserWin,boardForUserWin, true));
    }

    @Test
    void testMancalaPlayer2Win() {
        List<Integer> userLoseRow1 = new ArrayList<>(List.of(25, 0, 0, 0, 0, 0, 0, 0));
        List<Integer> userLoseRow2 = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 23));
        BoardConfiguration boardForUserLose = new BoardConfiguration(new ArrayList<>(List.of(userLoseRow1, userLoseRow2)));
        assertFalse(mancalaWinType.isWin(mancalaPlayer1States, boardForUserLose,boardForUserLose, true));
        assertTrue(mancalaWinType.isWin(mancalaPlayer2States, boardForUserLose,boardForUserLose, true));
    }

    @Test
    void testMancalaTie() {
        List<Integer> tieRow1 = new ArrayList<>(List.of(24,0,0,0,0,0,0,0));
        List<Integer> tieRow2 = new ArrayList<>(List.of(0,0,1,1,0,3,0,17));
        BoardConfiguration boardForTie = new BoardConfiguration(new ArrayList<>(List.of(tieRow1,tieRow2)));
        assertFalse(mancalaWinType.isWin(mancalaPlayer1States, boardForTie,boardForTie, true)
                && mancalaWinType.isWin(mancalaPlayer2States, boardForTie,boardForTie, true));
    }

    @Test
    void testOthelloNoWin() {
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,2,1,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertFalse(othelloWinType.isWin(othelloPlayer2States, boardConfig,boardConfig, false));
        assertFalse(othelloWinType.isWin(othelloPlayer1States, boardConfig, boardConfig, false));
    }

    @Test
    void testOthelloPlayer1Win() {
        //test for player 1 win
        List<Integer> row1 = new ArrayList<>(List.of(1,1,2,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,1,1,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,2,1,2));
        List<Integer> row4 = new ArrayList<>(List.of(2,2,1,1));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertTrue(othelloWinType.isWin(othelloPlayer1States, boardConfig,boardConfig, true));
        assertFalse(othelloWinType.isWin(othelloPlayer2States, boardConfig,boardConfig, true));
    }

    @Test
    void testOthelloPlayer2Win() {
        List<Integer> row1 = new ArrayList<>(List.of(2,2,2,1));
        List<Integer> row2 = new ArrayList<>(List.of(1,2,1,1));
        List<Integer> row3 = new ArrayList<>(List.of(1,2,1,2));
        List<Integer> row4 = new ArrayList<>(List.of(2,2,2,2));
        BoardConfiguration boardConfig = new BoardConfiguration(new ArrayList<>(List.of(row1,row2,row3,row4)));
        assertFalse(othelloWinType.isWin(othelloPlayer1States, boardConfig,boardConfig, true));
        assertTrue(othelloWinType.isWin(othelloPlayer2States, boardConfig,boardConfig, true));
    }

}