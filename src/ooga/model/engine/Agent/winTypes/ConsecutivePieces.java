package ooga.model.engine.Agent.winTypes;

import java.util.ArrayList;
import java.util.List;

public class ConsecutivePieces implements WinType {
    private int myInARow;

    public ConsecutivePieces(int InARow){
        myInARow = InARow;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        List<List<Integer>> rows = boardStateInfo;
        List<List<Integer>> cols = getCols(boardStateInfo);
        List<List<Integer>> diags = getDiagonals(boardStateInfo);
        return checkWinInGroup(rows, playerStates) || checkWinInGroup(cols,playerStates) || checkWinInGroup(diags, playerStates);
    }

    private boolean checkWinInGroup(List<List<Integer>> spaceChecking, List<Integer> player){
        for(List<Integer> allOfGroup: spaceChecking){
            int consecutive = 0;
            for(int state : allOfGroup){
                if(player.contains(state)){
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
        boolean isSquare = boardStateInfo.size() == boardStateInfo.get(0).size();
        if (isSquare) {
            return getDiagForSquareGrid(boardStateInfo);
        } else {
            return getDiagForRectangularGrid(boardStateInfo);
        }
    }

    private List<List<Integer>> getDiagForSquareGrid(List<List<Integer>> boardStateInfo) {
        List<Integer> leftDiag = new ArrayList<>();
        List<Integer> rightDiag = new ArrayList<>();
        for(int i = 0; i < Math.min(boardStateInfo.size(), boardStateInfo.get(0).size()); i++){
            leftDiag.add(boardStateInfo.get(i).get(i));
            rightDiag.add(boardStateInfo.get(i).get(boardStateInfo.size() - 1 - i));
        }
        return new ArrayList<>(List.of(leftDiag, rightDiag));
    }

    private List<List<Integer>> getDiagForRectangularGrid(List<List<Integer>> boardStateInfo) {
        List<List<Integer>> alldiag = new ArrayList<List<Integer>>();
        int rows = boardStateInfo.size();
        int cols = boardStateInfo.get(0).size();
        int remainder = myInARow - 1;
        for (int row = rows - myInARow; row >= 0; row--) {
            for (int col = cols - myInARow; col >= 0; col--) {
                List<Integer> leftDiag = new ArrayList<Integer>();
                List<Integer> rightDiag = new ArrayList<Integer>();
                for (int i = 0; i < myInARow; i++) {
                    rightDiag.add(boardStateInfo.get(row + i).get(col - i + remainder));
                    leftDiag.add(boardStateInfo.get(row + i).get(col + i));
                }
                alldiag.add(leftDiag);
                alldiag.add(rightDiag);
            }
        }
        return alldiag;
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