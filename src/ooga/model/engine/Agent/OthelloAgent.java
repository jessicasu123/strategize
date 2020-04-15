package ooga.model.engine.Agent;

import java.util.List;

public class OthelloAgent extends Agent {

    public static final int CORNER_BONUS = 4;
    private static final int ADJ_CORNER_PENALTY = -2;
    private static final int BORDER_STABLE_BONUS = 3;
    private static final int RISK_REGION_PENALTY = -1;
    private static final int INNER_CORNER_BONUS = 2;
    private int numRows;
    private int numCols;

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
        numRows = boardStateInfo.size();
        numCols = boardStateInfo.get(0).size();
        if(this.isWin(this.getMaxPlayer(), boardStateInfo)){
            return Integer.MAX_VALUE;
        }else if(this.isWin(this.getMinPlayer(), boardStateInfo)){
            return Integer.MIN_VALUE;
        }
        return calculateDiskDifference(boardStateInfo) + calculateCornerPositions(boardStateInfo)
                + calculateSquareWeights(boardStateInfo);
    }

    private int calculateDiskDifference(List<List<Integer>> boardStateInfo) {
        return calcDiffInPieces(boardStateInfo, this.getMaxPlayer());
    }

    private int calculateCornerPositions(List<List<Integer>> boardStateInfo) {
        return countNumCornersPerPlayer(boardStateInfo, this.getMaxPlayer()) -
                countNumCornersPerPlayer(boardStateInfo, this.getMinPlayer());
    }
    private int calculateSquareWeights(List<List<Integer>> boardStateInfo) {
        int[][] boardWeights = createBoardWeights();
        return calculateWeightsPerPlayer(boardWeights, boardStateInfo, this.getMaxPlayer()) -
                calculateWeightsPerPlayer(boardWeights, boardStateInfo, this.getMinPlayer());
    }

    private int calculateWeightsPerPlayer(int[][] boardWeights, List<List<Integer>> boardStateInfo, int playerID) {
        int playerPositionsWeight = 0;
        for (int r = 0; r < boardWeights.length;r++) {
            for (int c = 0; c < boardWeights[0].length;c++) {
                if (boardStateInfo.get(r).get(c)==playerID) {
                    playerPositionsWeight += boardWeights[r][c];
                }
            }
        }
        return playerPositionsWeight;
    }

    private int[][] createBoardWeights() {
        int[][] boardWeights = new int[numRows][numCols];
        initializeBoardWeights(boardWeights);
        //update weights for specific positions
        rowWithCornerAndStableZone(0, boardWeights);
        rowWithCornerAndStableZone(numRows-1, boardWeights);
        rowWithMainRiskRegion(1, boardWeights);
        rowWithMainRiskRegion(numRows-2, boardWeights);
        for (int r = 3; r < numRows-2;r++) {
            updateMiddleRowWeights(r, boardWeights);
        }
        return boardWeights;
    }

    private void rowWithCornerAndStableZone(int r, int[][] boardWeights) {
        for (int c = 0; c < numCols;c++) {
            int weight;
            if (c==0 || c==numCols-1) { weight = CORNER_BONUS; }
            else if (c==1 || c==numCols-2) { weight = ADJ_CORNER_PENALTY;
            } else { weight = BORDER_STABLE_BONUS; }
            boardWeights[r][c]  = weight;
        }
    }

    private void rowWithMainRiskRegion(int r, int[][] boardWeights) {
        for (int c = 0; c < numCols;c++) {
            if (c==0 || c==numCols-1 || c==1 || c==numCols-2) {
                boardWeights[r][c] =ADJ_CORNER_PENALTY; }
            else {
                boardWeights[r][c] = RISK_REGION_PENALTY; }
        }
    }

    private void updateMiddleRowWeights(int r, int[][] boardWeights) {
        for (int c = 0; c < numCols;c++) {
            if (c==0 || c==numCols-1) { boardWeights[r][c] = BORDER_STABLE_BONUS; }
            else if (c==1 || c==numCols-2) { boardWeights[r][c] = RISK_REGION_PENALTY;};
        }
    }

    private void initializeBoardWeights(int[][] boardWeights) {
        for (int r = 0; r < numRows;r++) {
            for (int c = 0; c <numCols;c++) {
                boardWeights[r][c] = 0;
            }
        }
    }
    private int countNumCornersPerPlayer(List<List<Integer>> boardStateInfo, int playerID) {
        int numPlayerCorners = 0;
        //top left
        if (boardStateInfo.get(0).get(0)==playerID) numPlayerCorners++;
        //top right
        if (boardStateInfo.get(0).get(numCols-1)==playerID) numPlayerCorners++;
        //bottom left
        if (boardStateInfo.get(numRows-1).get(0)==playerID) numPlayerCorners++;
        //bottom right
        if (boardStateInfo.get(numRows-1).get(numCols-1)==playerID) numPlayerCorners++;
        return numPlayerCorners;
    }

    private int calcDiffInPieces(List<List<Integer>> boardStateInfo, int playerID) {
        int numPlayerPieces = 0;
        int numOpponentPieces = 0;
        for (int r = 0; r < numRows;r++) {
            for (int c = 0; c < numCols;c++) {
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
        //game is not won if board still has empty spots
        for (int row = 0; row < boardStateInfo.size();row++) {
            if (boardStateInfo.get(row).contains(0)) return false;
        }
        return calcDiffInPieces(boardStateInfo, playerID) > 0;
    }
}
