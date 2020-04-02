package ooga.view;

/**
 * PURPOSE OF INTERFACE:
 *  - the front-end of the application once the game is running
 *      - this will also contain other classes that will have non-public methods that have functionality such as a
 *      save screen, settings screen, etc
 *  - this will visually show the states calculated in the back-end
 *  - will allow the user to interact with the program by clicking the game screen to make moves and control settings
 */
public interface GameViewFramework {

    /**
     * METHOD PURPOSE:
     *  - will update the view to express the current state of the game
     *  — this will be called based on a Timeline frequency that will happen in the main class
     */
    void update();


}
