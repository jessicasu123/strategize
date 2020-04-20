package ooga.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameScene {
    public static final String STYLESHEET = "resources/style.css";
    private BorderPane myRoot;

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

    public Scene createScene(int spacing, int width, int height, VBox content, String titleText){
        Scene scene = createScene(spacing, width, height, content);
        myRoot.setTop(addTitle(titleText));
        return scene;
    }

    public void updateStyle(String stylesheet) {
        myRoot.getStyleClass().add(stylesheet);
    }

    public void removeStyle(String stylesheet) {
        if (myRoot.getStyleClass().contains(stylesheet)) {
            myRoot.getStyleClass().remove(stylesheet);
        }
    }



    private Text addTitle(String titleText){
        Text title = new Text(titleText.toUpperCase());
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);
        return title;
    }

}
