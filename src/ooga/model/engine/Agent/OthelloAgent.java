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

    /**
     * The evaluation function for Othello will be based on
     *  - disk difference (num of max pieces - num of min pieces)
     *  - corner positions (num of max corner positions - num of min corner positions)
     *  - square weights (total square weights for max - total square weights for min)
     *      NOTE: the square weights are assigned based on the strategic value of the current
     *      position (corners/regions along the border have high weights, danger zones
     *      directly next to the corners have low/negative weights, and other areas
     *      are neutral.
     * @param boardStateInfo - all of the current state information from the board
     * @return
     */
    @Override
    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo) {
        if(this.isWin(this.getMaxPlayer(), boardStateInfo)){
            return Integer.MAX_VALUE;
        }else if(this.isWin(this.getMinPlayer(), boardStateInfo)){
            return Integer.MIN_VALUE;
        }

        return calculateDiskDifference(boardStateInfo) +
                calculateCornerPositions(boardStateInfo)
                + calculateSquareWeights(boardStateInfo);
    }

    private int calculateDiskDifference(List<List<Integer>> boardStateInfo) {
        return calcDiffInPieces(boardStateInfo, this.getMaxPlayer());
    }

    private int calculateCornerPositions(List<List<Integer>> boardStateInfo) {
        return 0;
    }

    private int calculateSquareWeights(List<List<Integer>> boardStateInfo) {
        return 0;
    }

    private int calcDiffInPieces(List<List<Integer>> boardStateInfo, int playerID) {
        int numPlayerPieces = 0;
        int numOpponentPieces = 0;
        for (int r = 0; r < boardStateInfo.size();r++) {
            for (int c = 0; c < boardStateInfo.get(0).size();c++) {
                int currPiece = boardStateInfo.get(r).get(c);
                if (currPiece!=playerID && currPiece!=0) {
                    numOpponentPieces++;
                }
                if (boardStateInfo.get(r).get(c)==playerID) {
                    numPlayerPieces++;
                }
            }
        }
        return numPlayerPieces - numOpponentPieces;
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
        //game is not won if board still has empty spots
        for (int row = 0; row < boardStateInfo.size();row++) {
            if (boardStateInfo.get(row).contains(0)) return false;
        }
        return calcDiffInPieces(boardStateInfo, playerID) > 0;
    }
}
