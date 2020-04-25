package ooga.model.engine.Agent.winTypes;

import ooga.model.engine.Neighborhood.DiagonalNeighborhood;
import ooga.model.engine.Neighborhood.VerticalNeighborhood;

import java.util.ArrayList;
import java.util.List;

public class ConsecutivePieces implements WinType {
    private int myInARow;
    private DiagonalNeighborhood calculateDiagonals;
    private VerticalNeighborhood calculateColumns;

    public ConsecutivePieces(int InARow){
        myInARow = InARow;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft) {
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
        calculateDiagonals = new DiagonalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateDiagonals.getAllDiagonals(boardStateInfo,
                myInARow);
    }



    private List<List<Integer>> getCols(List<List<Integer>> boardStateInfo){
        calculateColumns = new VerticalNeighborhood(boardStateInfo.size(),
                boardStateInfo.get(0).size());
        return calculateColumns.getAllVerticals(boardStateInfo);
    }
}
