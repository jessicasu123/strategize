package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: User clicks the save game button
 *      - User calls Popup.save() and prompts user to enter file details
 *      - JSON File with current configuration is created by the FileCreator
 */

public class UseCase4 {
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



    }
}
