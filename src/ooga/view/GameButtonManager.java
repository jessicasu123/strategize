package ooga.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameButtonManager {

    private Map<Button,String> buttonActionsMap;
    private Set<Button> handledButtons;

    public GameButtonManager() {
        buttonActionsMap = new HashMap<>();
        handledButtons = new HashSet<>();
    }

    public Button createButton(String buttonName, String methodName, double width) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setId(buttonName.replaceAll("\\s", ""));
        b.setMinWidth(width);
        buttonActionsMap.put(b, methodName);
        return b;
    }

    public Button createButtonWithImage(String buttonName, String methodName, int width, String imageName) {
        Button buttonWithImage = createButton(buttonName, methodName, width);
        Image img = new Image(imageName);
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        buttonWithImage.setGraphic(gameIcon);
        return buttonWithImage;
    }

    /**
     * Gives the GameView the buttons created in this panel, as well
     * as the method name of the actions they should be associated with.
     * These methods are created/called in GameView.
     * @return - Map with keys as the Buttons controlling navigation and values
     * as the string names of the methods (in GameView) that the buttons trigger
     */
    public Map<Button,String> getButtonActionsMap() {
        return buttonActionsMap;
    }

    public void hasHandledButton(Button b) {
        handledButtons.add(b);
    }

    public boolean needsToAddActionToButton(Button b) {
        return ! handledButtons.contains(b);
    }
}
