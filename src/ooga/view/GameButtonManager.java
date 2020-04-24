package ooga.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Button;
import ooga.view.components.GameButton;

/**
 * This class is responsible for managing buttons that are displayed
 * in sub-panels on a view or in pop-ups. It can create different
 * kinds of buttons (with images, without images) and also
 * returns a mapping of the buttons themselves to the actions they are associated with.
 *
 * @author: Jessica Su
 */
public class GameButtonManager {

    private Map<Button,String> buttonActionsMap;
    private Set<Button> handledButtons;

    public GameButtonManager() {
        buttonActionsMap = new HashMap<>();
        handledButtons = new HashSet<>();
    }

    /**
     * Creates a button to be used on many subviews/pop ups.
     * @param buttonName - name of button
     * @param methodName - name of method action that button triggers
     * @param width - size of button
     * @return - new button
     */
    public Button createButton(String buttonName, String methodName, double width) {
        Button b = new GameButton().createGameButton(buttonName,width);
        buttonActionsMap.put(b, methodName);
        return b;
    }

    /**
     * Creates a button with an image on top.
     * @param buttonName - name of button
     * @param methodName - name of action associated with button
     * @param width - width of the button
     * @param imageName - name of the image to be displayed on button
     * @return
     */
    public Button createButtonWithImage(String buttonName, String methodName, int width, String imageName) {
        Button buttonWithImage = new GameButton().createGameButtonWithImage(buttonName,width,imageName);
        buttonActionsMap.put(buttonWithImage, methodName);
        return buttonWithImage;
    }

    /**
     * Gives the GameView the buttons created in any of the subviews, as well
     * as the method name of the actions they should be associated with.
     * These methods are created/called in GameView.
     * @return - Map with keys as the Buttons and values
     * as the string names of the methods (in GameView) that the buttons trigger
     */
    public Map<Button,String> getButtonActionsMap() {
        return buttonActionsMap;
    }

    /**
     * This method allows the button manager to keep track of which buttons have been handled.
     * When buttons are hooked up to actions in the view, this method keeps track of them
     * so that buttons aren't attached to actions more than once.
     * @param b - button
     */
    public void hasHandledButton(Button b) {
        handledButtons.add(b);
    }

    /**
     * Determines if a button has not yet been hooked up to an action.
     * @param b - button
     * @return - true if button doesn't have action handler, false if it does
     */
    public boolean needsToAddActionToButton(Button b) {
        return ! handledButtons.contains(b);
    }

    /**
     * Allows a button's button to be set to a new text.
     * Used in the GameView class when a turn requires something
     * else to be displayed on the "Make Move" button.
     * @param buttonID - the button whose text needs to be changed
     * @param newText - the new text
     */
    public void resetButtonText(String buttonID, String newText) {
        Button buttonToResetText = findButton(buttonID);
        buttonToResetText.setText(newText);
    }

    private Button findButton(String buttonID) {
        for (Button b: buttonActionsMap.keySet()) {
            if (b.getId().equals(buttonID)) {
                return b;
            }
        }
        return null;
    }
}
