package ooga.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Creates a scene based on the information provided
 * Directly adds the style sheet used in the program
 *
 */
public class GameScene {
    public static final String STYLESHEET = "resources/style.css";
    private BorderPane myRoot;

    /**
     * Creates a scene using a border pane
     * @param spacing - the spacing to be used
     * @param width - the width of the scene
     * @param height - the height of the scene
     * @param content - a VBox that contains the content to be presented in the scene
     * @return a scene with the program styling and content provided
     */
    public Scene createScene(int spacing, int width, int height, VBox content){
        myRoot = new BorderPane();
        myRoot.setPadding(new Insets(spacing, 0, spacing,0));
        Scene scene = new Scene(myRoot, width, height);
        scene.getStylesheets().add(STYLESHEET);
        myRoot.getStyleClass().add("root");
        myRoot.setCenter(content);
        myRoot.setMaxWidth(width);
        return scene;
    }

    /**
     *
     * @param spacing - the spacing to be used
     * @param width - the width of the scene
     * @param height -  the height of the scene
     * @param content - a VBox that contains the content to be presented in the scene
     * @param titleText - a title to give to the scene
     * @return a scene that additionally feature a title
     */
    public Scene createScene(int spacing, int width, int height, VBox content, String titleText){
        Scene scene = createScene(spacing, width, height, content);
        myRoot.setTop(addTitle(titleText));
        return scene;
    }

    /**
     * @param stylesheet - the stylesheet to change to
     */
    public void updateStyle(String stylesheet) {
        myRoot.getStyleClass().add(stylesheet);
    }

    /**
     * @param stylesheet - the stylesheet to remove
     */
    public void removeStyle(String stylesheet) {
        myRoot.getStyleClass().remove(stylesheet);
    }

    private Text addTitle(String titleText){
        Text title = new Text(titleText.toUpperCase());
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);
        return title;
    }

}
