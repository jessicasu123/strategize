package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

/**
 * USE CASE: User moves game piece to valid location
 *      - User passes x and y coordinates of new game piece location to controller 
 *      - User calls board.makeMove to validate the move with board.getMovesOfPiece and the model is updated  
 *      - MockController passes new configuration to GameView which updates the view. 
 * NOTE: for all of the use cases the concrete classes that are needed have been implemented as mocked versions
 *      - in doing so, for the use cases some of the concrete classes have very simple implementation only show
 *      which objects are called from within another object's method. This doesn't accurately represent the logic
 *      of the implementation but rather how information will flow
 * In the actual implementation the controller will be called to action by the view (on action events) but to show
 * how the logic would work here the controller is called on directly
 */

public class UseCase1 {
    MockController myMockController;
    GameViewFramework myGameView;

    /**
     * INTERACTIONS:
     *      This use case demonstrates how the GameView interacts with the Controller and vice versa
     *      It also shows how the Controller interacts with Game which interacts with Board which interacts with
     *          the GamePieces
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
         * when a user clicks on the piece on the game view, it will give the controller the location it clicked on
         * and the controller will store that location with this method
         */
        myMockController.pieceSelected(0,0);

        /**
         * when a user clicks on the piece on the game view, it will give the controller the location it clicked on
         * and the controller will store that location with this method
         */
        myMockController.squareSelected(0,0);


        /**
         * uses the information from game view shown from the previous two methods
         * this method is called once the user pressed the "Play Move" button to send to back end
         * this then interacts with the Game by calling Game.makeUserMove
         * Game then calls Board.makeMove
         * And then Board calls GamePiece.calculateAllPossibleMoves to validate and GamePiece.move
         */
        myMockController.playMove();

        /**
         * updates the front-end to reflect the changes in the back-end
         * calls Controller.getGameVisualInfo
         * Controller calls Game.getVisualInfo
         * Game calls Board.getStateInfo
         */
        myGameView.update();

    }
}
