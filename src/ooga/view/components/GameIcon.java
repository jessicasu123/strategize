package ooga.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates a standardized icon to be used by different parts of the game
 */
public class GameIcon {
    public static double DEFAULT_WIDTH = 30;

    /**
     * @param imageName - the name of the image to be used for the icon
     * @param width - the width of the icon
     * @return a game icon with the image that is the size of the width provided
     */
    public ImageView createGameIcon(String imageName, double width) {
        Image img = new Image(imageName.split(",")[0]);
        ImageView gameIcon = new ImageView(img);
        gameIcon.setFitWidth(width);
        gameIcon.setPreserveRatio(true);
        return gameIcon;
    }

    /**
     * @param imageName - the name of the image to be used for the icon
     * @return a game icon with the image that is of a default size
     */
    public ImageView createGameIcon(String imageName) {return createGameIcon(imageName, DEFAULT_WIDTH);}
}
