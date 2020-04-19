package ooga.model.engine.Agent.newAgent.winTypes;

import java.util.List;

public class NoPiecesForOpponent implements WinType {
    private final int myEmptyState;
    
    public NoPiecesForOpponent(int emptyState){
        myEmptyState = emptyState;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int numOfOpponentsPieces = 0;
        for(List<Integer> row: boardStateInfo){
            for(int state : row){
                if(!playerStates.contains(state) && state != myEmptyState){
                    numOfOpponentsPieces++;
                }
            }
        }
        return numOfOpponentsPieces == 0;
    }
}
