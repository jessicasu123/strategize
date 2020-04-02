package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: User selects Connect4 game in the StartView
 *      - Controller is created and passed in for Connect4 GameView 
 *      - User calls FileReader.read() to parse JSON file for default Connect4 game configuration. 
 *      - User calls GameView.display() to show game configuration on board
 */
//TODO: make sure once figure out controller to explain properly for all use cases
public class UseCase3 {
    MockController myMockController;
    GameViewFramework myGameView;

    public void useCaseImplementation() throws InvalidMoveException {
        /**
         * creates a controller based on the information from start view
         * Controller creates a FileHandler in the constructor
         * Uses this file to do the Game preferences
         */
        myMockController = new MockController(new MockJSONHandler("Connect4.JSON"));

        /**
         * creates a game view using the controller as a parameter, allowing it to call on the controller
         */
        myGameView = new MockGameView(myMockController);



    }
}
