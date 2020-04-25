package ooga.model.engine.agent;

import ooga.model.engine.agent.evaluationFunctions.EvaluationFunction;
import ooga.model.engine.agent.winTypes.WinType;

import java.util.List;

public class Agent {
    private WinType myWinType;
    private List<EvaluationFunction> myEvals;
    private List<Integer> myAgentStates;
    private List<Integer> myUserStates;

    public Agent(WinType winType, List<EvaluationFunction> evals, List<Integer> agentStates, List<Integer> userStates){
        myWinType = winType;
        myEvals = evals;
        myAgentStates = agentStates;
        myUserStates = userStates;
    }

    public int evaluateCurrentGameState(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        if(myWinType.isWin(myAgentStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MAX_VALUE;
        }else if(myWinType.isWin(myUserStates, boardStateInfo, objectInfo,noMovesLeft)){
            return Integer.MIN_VALUE;
        }
        int evaluation = 0;
        for(EvaluationFunction evalFunc: myEvals){
            evaluation += evalFunc.evaluate(boardStateInfo,objectInfo, noMovesLeft);
        }
        return evaluation;
    }

    public boolean isGameWon(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        return myWinType.isWin(myAgentStates, boardStateInfo,objectInfo, noMovesLeft) ||
                myWinType.isWin(myUserStates, boardStateInfo,objectInfo, noMovesLeft);
    }

    public int findGameWinner(List<List<Integer>> boardStateInfo,List<List<Integer>> objectInfo, boolean noMovesLeft){
        if(isGameWon(boardStateInfo,objectInfo, noMovesLeft)){
            if(myWinType.isWin(myAgentStates, boardStateInfo, objectInfo,noMovesLeft)){
                return myAgentStates.get(0);
            }
            else {
                return myUserStates.get(0);
            }
        }
        return 0;
    }
}
