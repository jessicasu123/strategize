package ooga.view.components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameButton {
    public static final int DEFAULT_WIDTH = 30;
    public Button createGameButton(String buttonName, double width) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setId(buttonName.replaceAll("\\s", ""));
        b.setMinWidth(width);
        return b;
    }

    public Button createGameButton(String buttonName){
        return createGameButton(buttonName,DEFAULT_WIDTH);
    }

    public Button createGameButtonWithImage(String buttonName, int width, String imageName) {
        Button buttonWithImage = createGameButton(buttonName, width);
        Image img = new Image(imageName);
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(30);
        gameIcon.setPreserveRatio(true);
        buttonWithImage.setGraphic(gameIcon);
        return buttonWithImage;
    }
}
