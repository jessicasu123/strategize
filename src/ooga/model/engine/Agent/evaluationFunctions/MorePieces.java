package ooga.model.engine.Agent.evaluationFunctions;

import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MorePieces implements EvaluationFunction{
    private int myStateEvalFor;
    private int opponentStateEvalFor;
    private final Boolean checkCurrConfig;
    private final List<List<Integer>> myInitialConfig;

    //TODO: is there a way to give more weights to certain types of pieces?
    public MorePieces(int stateIndex, List<Integer> maxStates, List<Integer> minStates, List<List<Integer>> initialConfig, Boolean checkCurrConfig){
        myStateEvalFor = maxStates.get(stateIndex);
        opponentStateEvalFor = minStates.get(stateIndex);
        myInitialConfig = initialConfig;
        this.checkCurrConfig = checkCurrConfig;
    }

    @Override
    public int evaluate(List<List<Integer>> boardStateInfo, boolean noMovesLeft) {
        List<List<Integer>> boardStates;
        if (checkCurrConfig) {
            boardStates = boardStateInfo;
        } else {
            boardStates = myInitialConfig;
        }
        ArrayList<Coordinate> myEvalCoords = getEvalStateCoords(myStateEvalFor, boardStates);
        ArrayList<Coordinate> opponentEvalCoords = getEvalStateCoords(opponentStateEvalFor, boardStates);
        if (checkCurrConfig) {
            return myEvalCoords.size() - opponentEvalCoords.size();
        }
        return countEvalStates(myEvalCoords, boardStateInfo) - countEvalStates(opponentEvalCoords, boardStateInfo);
    }

    private ArrayList<Coordinate> getEvalStateCoords(int stateToFind, List<List<Integer>> config) {
        ArrayList<Coordinate> stateCoords = new ArrayList<>();
        for (int r = 0; r < config.size(); r++) {
            for (int c = 0; c < config.get(0).size(); c++) {
                if (config.get(r).get(c) == stateToFind) {
                    stateCoords.add(new Coordinate(r, c));
                }
            }
        }
        return stateCoords;
    }

    private int countEvalStates(ArrayList<Coordinate> stateCoords, List<List<Integer>> boardStateInfo) {
        int total = 0;
        for (Coordinate coord : stateCoords) {
            total += boardStateInfo.get(coord.getRow()).get(coord.getCol());
        }
        return total;
    }

}
