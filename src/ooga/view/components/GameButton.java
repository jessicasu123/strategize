package ooga.view.components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class establishes, creates, and standardizes the look of all the game buttons
 * It also creates buttons with images on them or ones with just text
 */

public class GameButton {
    public static final int DEFAULT_WIDTH = 30;

    /**
     * @param buttonName - the name of the button
     * @param width - the custom width of the button
     * @return a game button
     */
    public Button createGameButton(String buttonName, double width) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setId(buttonName.replaceAll("\\s", ""));
        b.setMinWidth(width);
        return b;
    }

    /**
     * @param buttonName - the name of the button
     * @return a button just based off of the name
     */
    public Button createGameButton(String buttonName){
        return createGameButton(buttonName,DEFAULT_WIDTH);
    }

    /**
     * @param buttonName - the name of the button
     * @param width - the custom width of the button
     * @param imageName - the name of the image to go on the button
     * @return a game button with an image
     */
    public Button createGameButtonWithImage(String buttonName, int width, String imageName) {
        Button buttonWithImage = createGameButton(buttonName, width);
        Image img = new Image(imageName);
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(DEFAULT_WIDTH);
        gameIcon.setPreserveRatio(true);
        buttonWithImage.setGraphic(gameIcon);
        return buttonWithImage;
    }
}
