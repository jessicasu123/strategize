package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: Agent player moves game piece
 *      - User calls Game.assignPlayerNumber(), agent.calculateMove(), and board.makeMove in the model to determine and update the agent’s move 
 *      - Controller passes updated model configuration data to GameView which updates the view.   
 */

public class UseCase2 {
    MockController myMockController;
    GameViewFramework myGameView;

    public void useCaseImplementation() throws InvalidMoveException {
        /**
         * creates a controller based on the information from start view
         * Controller creates a FileHandler in the constructor
         */
        myMockController = new MockController(new MockJSONHandler("filename"));

        /**
         * creates a game view using the controller as a parameter, allowing it to call on the controller
         */
        myGameView = new MockGameView(myMockController);

        /**
         * Calls Game.makeAgentMove
         * Game calls AgentPlayer.makeMove and Board.makeMove
         * AgentPlayer creates its own version of the board and calls Board.getAllLegalMoves and Board.move
         * Board calls GamePiece.calculateAllPossibleMoves and GamePiece.move
         */
        myMockController.haveAgentMove();

        /**
         * calls Controller.getGameVisualInfo
         * Controller calls Game.getVisualInfo
         * Game calls Board.getStateInfo
         */
        myGameView.update();

    }
}
