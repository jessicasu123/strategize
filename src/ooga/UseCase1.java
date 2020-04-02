package ooga;

import ooga.controller.MockController;
import ooga.model.data.MockJSONHandler;
import ooga.model.engine.InvalidMoveException;
import ooga.view.GameViewFramework;
import ooga.view.MockGameView;

import java.util.ArrayList;

/**
 * USE CASE: User moves game piece to valid location
 *      - User passes x and y coordinates of new game piece location to controller 
 *      - User calls board.makeMove to validate the move with board.getMovesOfPiece and the model is updated  
 *      - MockController passes new configuration to GameView which updates the view. 
 */

public class UseCase1 {
    MockController myMockController;
    GameViewFramework myGameView;
    public void useCaseImplementation() throws InvalidMoveException {
        /**
         * creates a controller based on the information from start view
         * Controller creates a FileHandler in the constructor
         */
        myMockController = new MockController(new MockJSONHandler("filename"));

        myMockController.pieceSelected(0,0);
        myMockController.squareSelected(0,0);

        /**
         * creates a game view using the controller as a parameter, allowing it to call on the controller
         */
        myGameView = new MockGameView(myMockController);
        /**
         * uses the information from game view to send to back end
         * this then interacts with the Game by calling Game.makeUserMove
         * Game then calls Board.makeMove
         * And then Board calls GamePiece.calculateAllPossibleMoves and GamePiece.move
         */
        myMockController.playMove();

        /**
         * calls Controller.getGameVisualInfo
         * Controller calls Game.getVisualInfo
         * Game calls Board.getStateInfo
         */
        myGameView.update();

    }
}
