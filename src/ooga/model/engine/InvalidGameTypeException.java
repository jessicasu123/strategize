package ooga.model.engine;

/**
 * Exception for when the game type specified does not correspond to a supported game.
 * The supported games are: Checkers, Chopsticks, Connect4, Mancala, Othello, and Tic Tac Toe.
 */
public class InvalidGameTypeException extends Exception {
    public InvalidGameTypeException(String message, Object ... values) {
        super(String.format(message, values));
    }
}
