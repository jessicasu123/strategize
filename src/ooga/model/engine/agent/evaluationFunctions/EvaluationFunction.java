package ooga.model.engine.agent.evaluationFunctions;

import ooga.model.engine.Grid;
import ooga.model.engine.ImmutableGrid;

/**
 * This interface provides the evaluation function
 * that is used by the AI minimax algorithm to determine the
 * agent's best possible move.
 *
 * The evaluation function(s) can be specified in the game configuration
 * file, which provides flexibility in the types of evaluations used
 * to determine the quality of a move in a game.
 */
public interface EvaluationFunction {
    /**
     * A value representing the quality of the agent (max) player's move
     * compared to the user (min) player's move, with higher positive values
     * representing more desirable/strategic moves, and lower negative values
     * representing unfavorable ones.
     * @param boardStateInfo - the current state configuration of the board
     * @return an integer that represents the evaluation of a certain move
     */
    int evaluate(ImmutableGrid boardStateInfo, ImmutableGrid objectInfo);
}
