package ooga.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameIcon {
    public static double DEFAULT_WIDTH = 30;

    public ImageView createGameIcon(String imageName, double width) {
        Image img = new Image(imageName.split(",")[0]);
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(width);
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }

    public ImageView createGameIcon(String imageName) {return createGameIcon(imageName, DEFAULT_WIDTH);}
}
