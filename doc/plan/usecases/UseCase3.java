package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: User is informed that they won/lost the game
 *      -User calls Game.getGameStatus and agent.findWinner() in the model to determine if the user or agent won the game.
 *      -PlayerInterface number of winner is passed to Controller and GameView calls controller.getUserNumber() and
 *      Popup.winner(player number) to display the winner.
 * NOTE: for all of the use cases the concrete classes that are needed have been implemented as mocked versions
 *      - in doing so, for the use cases some of the concrete classes have very simple implementation only show
 *      which objects are called from within another object's method. This doesn't accurately represent the logic
 *      of the implementation but rather how information will flow
 * In the actual implementation the controller will be called to action by the view (on action events) but to show
 * how the logic would work here the controller is called on directly
 */

public class UseCase3 {
    MockController myMockController;
    GameViewFramework myGameView;

    /**
     * INTERACTIONS:
     *      This use case demonstrates how the GameView interacts with the Controller and vice versa
     *      It also shows how the Controller interacts with Game which interacts with the Agent
     *      And how the Agent interacts with the Board to evaluate if the game is over
     *
     */
    public void useCaseImplementation() throws InvalidMoveException {
        /**
         * creates a controller based on the information from start view
         * Controller creates a FileHandler in the constructor
         * the FileHandler will find and then parse the data
         */
        myMockController = new MockController(new MockJSONHandler("Connect4.JSON"));

        /**
         * creates a game view using the controller as a parameter, allowing it to call on the controller
         */
        myGameView = new MockGameView(myMockController);

        /**
         * this would be called from within the GameView
         * the controller checks the Game class to see if the game is over if Game.getEndGameState is not 0
         * Game uses Agent.findWinner
         */
        myMockController.gameOver();

        /**
         * this would be called from within the GameView if the previous method outputed true
         * the controller checks the Game class to see the Game.getEndGameState
         * Game uses Agent.findWinner
         * this returns an integer corresponding to the player id of the winner
         */
        myMockController.gameWinner();

        /**
         * updates the front-end to reflect the changes in the back-end
         * uses the integer from gameWinner to compare with the userNumber to see which message to display
         * the display would happen in private helper methods/classes
         * also does:
         *      - calls Controller.getGameVisualInfo
         *      - Controller calls Game.getVisualInfo
         *      - Game calls Board.getStateInfo
         */
        myGameView.update();


    }
}
