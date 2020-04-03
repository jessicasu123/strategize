package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;;
import java.util.HashMap;

/**
 * USE CASE: User clicks the save game button
 *      - User calls Popup.save() and prompts user to enter file details
 *      - JSON File with current configuration is created by the FileCreator
 * NOTE: for all of the use cases the concrete classes that are needed have been implemented as mocked versions
 *      - in doing so, for the use cases some of the concrete classes have very simple implementation only show
 *      which objects are called from within another object's method. This doesn't accurately represent the logic
 *      of the implementation but rather how information will flow
 * In the actual implementation the controller will be called to action by the view (on action events) but to show
 * how the logic would work here the controller is called on directly
 */

public class UseCase4 {
    MockController myMockController;
    GameViewFramework myGameView;

    /**
     * INTERACTIONS:
     *      This use case demonstrates how the GameView interacts with the Controller and vice versa
     *      It also shows how the Controller interacts with the FileHandler
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
         * the GameView will contain a button to detect when the user wants to save a file
         * the GameView will contain helper methods/classes for implementing a pop-up to specify information
         *      on how they want the file to be save
         */
        myGameView = new MockGameView(myMockController);

        /**
         * when the save button is pressed on the game view it will call the controller to do this method
         * where the parameters will come from the GameView
         * this method calls on Game.getVisualInfo
         * this method then calls the FileHandler and calls the saveToFileMethod
         *
         */
        myMockController.saveANewFile("newfilename", new HashMap<String, String>());



    }
}
