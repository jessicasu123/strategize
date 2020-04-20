package ooga.model.engine.Agent.newAgent.winTypes;

import java.util.List;

public class NoMovesMorePieces implements WinType {
    private final int myOpponentGoal;

    public NoMovesMorePieces(int opponentGoalState){
        myOpponentGoal = opponentGoalState;
    }

    @Override
    public boolean isWin(List<Integer> playerStates, List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int numPlayerPieces = countNumPieces(boardStateInfo, playerStates);
        int numOpponentsPieces = countNumPieces(boardStateInfo, opponentStates);
        return noMovesLeft && numPlayerPieces > numOpponentsPieces;
    }

    private int countNumPieces(List<List<Integer>> boardStateInfo, List<Integer> playerGoalStates) {
        int count = 0;
        for (List<Integer> row: boardStateInfo) {
            for (int state : row) {
                if (!playerGoalStates.contains(state)) {
                    count++;
                }
            }
        }
        return count;
    }
}
