package ooga.model.engine.Agent;

import java.util.List;

public class OthelloAgent extends Agent {
    /**
     * Creates an AI agent for Othello
     *
     * @param maximizingPlayerID - the ID of the player who the agent will try to maximize its moves for
     * @param minimizingPlayerID - the ID of the player who the agent will try to minimize its moves for
     */
    public OthelloAgent(int maximizingPlayerID, int minimizingPlayerID) {
        super(maximizingPlayerID, minimizingPlayerID);
    }

    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo) {
        return 0;
    }

    /**
     * Win in Othello is determined by who has more piece on the board when
     * the entire board is full
     * @param playerID - the ID of the player checking to see if they won
     * @param boardStateInfo - all of the current state information from the board
     * @return
     */
    @Override
    protected boolean isWin(int playerID, List<List<Integer>> boardStateInfo) {
        int numOpponentPieces = 0;
        int numPlayerPieces = 0;
        for (int r = 0; r < boardStateInfo.size();r++) {
            for (int c = 0; c < boardStateInfo.get(0).size();c++) {
                int currCellState = boardStateInfo.get(r).get(c);
                if (currCellState==0) return false; //board isn't full yet
                if (currCellState!=playerID && currCellState!=0) numOpponentPieces++;
                if (currCellState==playerID) numPlayerPieces++;
            }
        }
        return numPlayerPieces > numOpponentPieces;
    }
}
