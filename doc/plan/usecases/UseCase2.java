package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: Agent player moves game piece
 *      - User calls Game.assignPlayerNumber(), agent.calculateMove(), and board.makeMove in the model to determine
 *      and update the agent’s move 
 *      - Controller passes updated model configuration data to GameView which updates the view.   
 * NOTE: for all of the use cases the concrete classes that are needed have been implemented as mocked versions
 *      - in doing so, for the use cases some of the concrete classes have very simple implementation only show
 *      which objects are called from within another object's method. This doesn't accurately represent the logic
 *      of the implementation but rather how information will flow
 * In the actual implementation the controller will be called to action by the view (on action events) but to show
 * how the logic would work here the controller is called on directly
 */

public class UseCase2 {
    MockController myMockController;
    GameViewFramework myGameView;

    /**
     * INTERACTIONS:
     *      This use case demonstrates how the GameView interacts with the Controller and vice versa
     *      It also shows how the Controller interacts with Game which interacts with the Agent
     *      And therefore shows how the Agent interacts with the Board and the GamePieces
     *
     */
    public void useCaseImplementation() throws InvalidMoveException {
        /**
         * creates a controller based on the information from start view
         * Controller creates a FileHandler in the constructor
         * the FileHandler will find and then parse the data
         */
        myMockController = new MockController(new MockJSONHandler("filename"));

        /**
         * creates a game view using the controller as a parameter, allowing it to call on the controller
         */
        myGameView = new MockGameView(myMockController);


        /**
         * tells the back-end to do the logic for having an agent make a move
         * this would be called from within the GameView
         * Calls Game.makeAgentMove
         * Game calls AgentPlayer.makeMove and Board.makeMove
         * AgentPlayer creates its own version of the board and calls Board.getAllLegalMoves and Board.move
         * Board calls GamePiece.calculateAllPossibleMoves and GamePiece.move
         */
        myMockController.haveAgentMove();

        /**
         * updates the front-end to reflect the changes in the back-end
         * calls Controller.getGameVisualInfo
         * Controller calls Game.getVisualInfo
         * Game calls Board.getStateInfo
         */
        myGameView.update();

    }
}
