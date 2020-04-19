package ooga.model.engine.Agent.newAgent;

import ooga.model.engine.Agent.newAgent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.Agent.newAgent.winTypes.WinType;

import java.util.List;

public class Agent {
    private WinType myWinType;
    private List<EvaluationFunction> myEvals;
    private List<Integer> myAgentStates;
    private List<Integer> myUserStates;
    private int myEmptyState;

    public Agent(WinType winType, List<EvaluationFunction> evals, List<Integer> agentStates, List<Integer> userStates, int emptyState){
        myWinType = winType;
        myEvals = evals;
        myAgentStates = agentStates;
        myUserStates = userStates;
        myEmptyState = emptyState;
    }

    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo, boolean noMovesLeft){
        if(myWinType.isWin(myAgentStates, boardStateInfo, noMovesLeft)){
            return Integer.MAX_VALUE;
        }else if(myWinType.isWin(myUserStates, boardStateInfo, noMovesLeft)){
            return Integer.MIN_VALUE;
        }
        int evaluation = 0;
        for(EvaluationFunction evalFunc: myEvals){
            evaluation += evalFunc.evaluate(boardStateInfo, noMovesLeft);
        }
        return evaluation;
    }

    public boolean isGameWon(List<List<Integer>> boardStateInfo, boolean noMovesLeft){
        return myWinType.isWin(myAgentStates, boardStateInfo, noMovesLeft) ||
                myWinType.isWin(myUserStates, boardStateInfo, noMovesLeft);
    }

    public int findGameWinner(List<List<Integer>> boardStateInfo, boolean noMovesLeft){
        if(isGameWon(boardStateInfo, noMovesLeft)){
            if(myWinType.isWin(myAgentStates, boardStateInfo, noMovesLeft)){
                return myAgentStates.get(0);
            }
            else {
                return myUserStates.get(0);
            }
        }
        return 0;
    }
}
