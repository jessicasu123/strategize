package ooga.model.engine.Agent.newAgent.evaluationFunctions;

import ooga.model.engine.Coordinate;

import java.util.HashMap;
import java.util.List;

public class MorePieces implements EvaluationFunction{
    public static final int MINIMIZE_FACTOR = -1;
    private final int myStateEvalFor;
    private final int myOpponentStateEvalFor;
    private final Coordinate myEvalCoords;
    private final Coordinate myOpponentEvalCoords;

    public MorePieces(int stateIndex, List<Integer> maxStates, List<Integer> minStates, List<List<Integer>> initialConfig){
        myStateEvalFor = maxStates.get(stateIndex);
        myOpponentStateEvalFor = minStates.get(stateIndex);
        myEvalCoords = getEvalStateCoords(myStateEvalFor, initialConfig);
        myOpponentEvalCoords = getEvalStateCoords(myOpponentStateEvalFor, initialConfig);
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        int myGoalState = boardStateInfo.get(myEvalCoords.getXCoord()).get(myEvalCoords.getYCoord());
        int opponentGoalState = boardStateInfo.get(myOpponentEvalCoords.getXCoord()).get(myOpponentEvalCoords.getYCoord());
        return myGoalState - opponentGoalState;
    }

    private Coordinate getEvalStateCoords(int stateToFind, List<List<Integer>> initialConfig) {
        for (int r = 0; r < initialConfig.size(); r++) {
            for (int c = 0; c < initialConfig.get(0).size(); c++) {
                if (initialConfig.get(r).get(c) == stateToFind) {
                    return new Coordinate(r, c);
                }
            }
        }
//        initialConfig.indexOf(stateToFind); // TODO: check what this would do instead
        return null; // TODO: check is not null
    }

}
