package ooga.model.engine.Agent.winTypes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoMovesMorePiecesTest {

    @Test
    void testIsWinMancala() {
        List<Integer> initial_row1 = new ArrayList<>(List.of(4,3,3,3,3,3,3,0));
        List<Integer> initial_row2 = new ArrayList<>(List.of(0,1,1,1,1,1,1,2));
        List<List<Integer>> initialConfig = new ArrayList<>(List.of(initial_row1,initial_row2));

        List<Integer> userStateInfo = new ArrayList<>(List.of(1,2));
        List<Integer> agentStateInfo = new ArrayList<>(List.of(3,4));
        int stateIndex = 1;

        NoMovesMorePieces mancalaWinType = new NoMovesMorePieces(stateIndex, initialConfig, false, 0);

        List<Integer> gipRow1 = new ArrayList<>(List.of(14,12,0,0,0,2,0,0));
        List<Integer> gipRow2 = new ArrayList<>(List.of(0,1,1,1,0,0,0,17));
        List<List<Integer>> boardForGameInProgress = new ArrayList<>(List.of(gipRow1,gipRow2));
        assertFalse(mancalaWinType.isWin(userStateInfo, boardForGameInProgress, false));

        List<Integer> userWinRow1 = new ArrayList<>(List.of(14,2,0,3,0,2,0,0));
        List<Integer> userWinRow2 = new ArrayList<>(List.of(0,0,0,0,0,0,0,25));
        List<List<Integer>> boardForUserWin = new ArrayList<>(List.of(userWinRow1,userWinRow2));
        assertTrue(mancalaWinType.isWin(userStateInfo, boardForUserWin, true));

        List<Integer> userLoseRow1 = new ArrayList<>(List.of(14,7,8,0,0,2,0,0));
        List<Integer> userLoseRow2 = new ArrayList<>(List.of(0,0,0,0,0,0,0,17));
        List<List<Integer>> boardForUserLose = new ArrayList<>(List.of(userLoseRow1,userLoseRow2));
        assertFalse(mancalaWinType.isWin(userStateInfo, boardForUserLose, true));

        List<Integer> tieRow1 = new ArrayList<>(List.of(24,0,0,0,0,0,0,0));
        List<Integer> tieRow2 = new ArrayList<>(List.of(0,0,1,1,0,3,0,17));
        List<List<Integer>> boardForTie = new ArrayList<>(List.of(tieRow1,tieRow2));
        assertFalse(mancalaWinType.isWin(userStateInfo, boardForTie, true)
                && mancalaWinType.isWin(agentStateInfo, boardForTie, true));

    }

}