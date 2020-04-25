package ooga.model.engine.agent.winTypes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoPiecesForOpponentTest {
    WinType noPieces = new NoPiecesForOpponent(0);
    List<Integer> user = new ArrayList<>(List.of(3,4));
    List<Integer> agent = new ArrayList<>(List.of(1,2));
    @Test
    void testisWinNoWinner() {
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertFalse(noPieces.isWin(user,boardConfig,boardConfig, false));
        assertFalse(noPieces.isWin(agent,boardConfig,boardConfig,false));
    }

    @Test
    void testisWinPlayer1Wins() {
        //winning state for player1
        List<Integer> row1 = new ArrayList<>(List.of(1,0,1,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,0,0,0));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertFalse(noPieces.isWin(user,boardConfig,boardConfig,false));
        assertTrue(noPieces.isWin(agent,boardConfig,boardConfig,true));
    }

    @Test
    void testisWinPlayer2Wins() {
        List<Integer> row1 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row2 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row3 = new ArrayList<>(List.of(0,0,0,0));
        List<Integer> row4 = new ArrayList<>(List.of(0,3,0,3));
        List<List<Integer>> boardConfig = new ArrayList<>(List.of(row1,row2,row3,row4));
        assertTrue(noPieces.isWin(user,boardConfig,boardConfig,true));
        assertFalse(noPieces.isWin(agent,boardConfig,boardConfig,false));
    }
}