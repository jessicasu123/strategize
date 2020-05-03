package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.BoardConfiguration;
import ooga.model.engine.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluation function to be used by games who maximize for having
 * more pieces than their opponent in determining their next move
 * Ex: Mancala (for objects), Othello, Chopsticks
 *
 * @author Sanya Kochhar
 */
public class MorePieces implements EvaluationFunction{
    private int myStateEvalFor;
    private int opponentStateEvalFor;
    private final Boolean checkCurrConfig;
    private final BoardConfiguration myInitialConfig;

    /**
     *
     * @param stateIndex - index of the specific state to be checked for
     * @param maxStates - player states
     * @param minStates - opponent states
     * @param initialConfig - used for some games to determine board layout of specific states
     * @param checkCurrConfig - indicates whether to determine states to be checked from current config or initial config
     */
    public MorePieces(int stateIndex, List<Integer> maxStates, List<Integer> minStates, BoardConfiguration initialConfig, Boolean checkCurrConfig){
        myStateEvalFor = maxStates.get(stateIndex);
        opponentStateEvalFor = minStates.get(stateIndex);
        myInitialConfig = initialConfig;
        this.checkCurrConfig = checkCurrConfig;
    }

    /**
     * Function to determine the difference between the player's and opponents
     * pieces matching a specific player state
     * @param boardStateInfo - the current state configuration of the board
     * @param objectInfo - current configuration of objects on the board - used by object-based games (ex: Mancala)
     * @param noMovesLeft - boolean that represents if there are moves left
     * @return
     */
    @Override
    public int evaluate(BoardConfiguration boardStateInfo,BoardConfiguration objectInfo, boolean noMovesLeft) {
        BoardConfiguration boardStates;
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
        return countEvalStates(myEvalCoords, objectInfo) - countEvalStates(opponentEvalCoords, objectInfo);
    }

    /**
     * Finds occurrences of a specific state on the board
     * @param stateToFind - specific state to be found
     * @param config - board configuration
     * @return list of coordinates matching the given state
     */
    private ArrayList<Coordinate> getEvalStateCoords(int stateToFind, BoardConfiguration config) {
        ArrayList<Coordinate> stateCoords = new ArrayList<>();
        for (int r = 0; r < config.getNumRows(); r++) {
            for (int c = 0; c < config.getNumCols(); c++) {
                if (config.getValue(r,c) == stateToFind) {
                    stateCoords.add(new Coordinate(r, c));
                }
            }
        }
        return stateCoords;
    }

    /**
     * Counts the objects corresponding to the specific state occurrences for object-based games
     */
    private int countEvalStates(ArrayList<Coordinate> stateCoords, BoardConfiguration boardStateInfo) {
        int total = 0;
        for (Coordinate coord : stateCoords) {
            total += boardStateInfo.getValue(coord.getRow(), coord.getCol());
        }
        return total;
    }

}
