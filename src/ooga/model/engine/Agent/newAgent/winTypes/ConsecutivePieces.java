package ooga.model.engine.Agent.newAgent.winTypes;

import java.util.ArrayList;
import java.util.List;

public class ConsecutivePieces implements WinType{
    private int myInARow;
    private int ROWS = 6;
    private int COLS = 7;

    public ConsecutivePieces(int InARow){
        myInARow = InARow;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        List<List<Integer>> rows = boardStateInfo;
        List<List<Integer>> cols = getCols(boardStateInfo);
        List<List<Integer>> diags = getDiagonals(boardStateInfo);
        return checkWinInGroup(rows, playerStates.get(1)) || checkWinInGroup(cols,playerStates.get(1)) || checkWinInGroup(diags, playerStates.get(1));
    }

    private boolean checkWinInGroup(List<List<Integer>> spaceChecking, int player){
        for(List<Integer> allOfGroup: spaceChecking){
            int consecutive = 0;
            for(int state : allOfGroup){
                if(state == player){
                    consecutive++;
                }else{
                    consecutive = 0;
                }
                if(consecutive >= myInARow){
                    return true;
                }
            }
        }
        return false;
    }

    private List<List<Integer>> getDiagonals(List<List<Integer>> boardStateInfo){
        if(myInARow==4) {
            List<List<Integer>> alldiag = new ArrayList<List<Integer>>();
            for (int row = ROWS - 4; row >= 0; row--) {
                for (int col = COLS - 4; col >= 0; col--) {
                    List<Integer> leftDiag = new ArrayList<Integer>();
                    List<Integer> rightDiag = new ArrayList<Integer>();
                    for (int i = 0; i < 4; i++) {
                        rightDiag.add(boardStateInfo.get(row + i).get(col - i + 3));
                        leftDiag.add(boardStateInfo.get(row + i).get(col + i));
                    }
                    alldiag.add(leftDiag);
                    alldiag.add(rightDiag);
                }
            }
            return alldiag;
        }
        else{
            List<Integer> leftDiag = new ArrayList<>();
            List<Integer> rightDiag = new ArrayList<>();
            for(int i = 0; i < Math.min(boardStateInfo.size(), boardStateInfo.get(0).size()); i++){
                leftDiag.add(boardStateInfo.get(i).get(i));
                rightDiag.add(boardStateInfo.get(i).get(boardStateInfo.size() - 1 - i));
            }
            return new ArrayList<>(List.of(leftDiag, rightDiag));
        }
    }

    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        List<List<Integer>> allCols = new ArrayList<>();
        for(int i = 0; i < boardStateInfo.get(0).size(); i++) {
            List<Integer> col = new ArrayList<>();
            for (List<Integer> row : boardStateInfo) {
                col.add(row.get(i));
            }
            allCols.add(col);
        }
        return allCols;
    }
}
