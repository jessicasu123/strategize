package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Responsible for allowing the user to customize the piece images
 * for both his/her player and the opponent's player.
 * These customization selections are finalized when the user
 * clicks on the "SET PREFERENCES" button and reflected
 * in the view.
 */
public class EndPopUp extends GamePopUp{

    private String winStatus;

    public static final int SPACING = 40;
    public static final String IMG_RESOURCES = DEFAULT_VIEW_RESOURCES + "images/";

    public EndPopUp(Stage stage, int width, int height, String fileName, String endStatus, GameButtonManager buttonManager) throws FileNotFoundException {
        super(stage, width, height, fileName, buttonManager);

        popUpWidth = width/2.0;
        xOffset = (width - popUpWidth)/2;
        winStatus = endStatus;
        setUpJSONReader();
    }

    /**
     * For the CustomizationPopUp, the content includes the ability
     * to customize the pieces for the player and opponent,
     * as well as options to change the color of the Grid background.
     */
    @Override
    public void createPopUpContents() {
        VBox gameEndContents = new VBox();
        gameEndContents.setSpacing(SPACING);
        gameEndContents.setPadding(new Insets(40,SPACING/2.0,0,SPACING/2.0));
        gameEndContents.setMinWidth(popUpWidth);
        gameEndContents.getChildren().addAll(createEndMessaging(), createNavigationOptions());
        gameEndContents.setAlignment(Pos.CENTER);
        myPopUpContents.getChildren().add(gameEndContents);

    }


    private VBox createEndMessaging() {
        VBox endMessaging = new VBox();
        endMessaging.setSpacing(SPACING/2.0);
        JSONObject statusContents = popUpScreenData.getJSONObject("EndStatuses").getJSONObject(winStatus);
        Text messageText = new Text(statusContents.getString("Text"));
        Text messageSubtext = new Text(statusContents.getString("Subtext"));
        Image messageImg = new Image(IMG_RESOURCES + statusContents.getString("Image"), popUpWidth/3.0, 0, true, true);
        ImageView displayImg = new ImageView(messageImg);

        messageText.setId(winStatus+"Text");
        messageSubtext.setId(winStatus+"Subtext");
        displayImg.setId(winStatus+"Image");

        endMessaging.getChildren().addAll(messageText, messageSubtext, displayImg);
        endMessaging.setAlignment(Pos.CENTER);

        return endMessaging;
    }

    private VBox createNavigationOptions() {
        VBox navOptions = new VBox(SPACING);
        JSONObject buttonInfo = popUpScreenData.getJSONObject("Buttons");
        Button playAgain = popUpGameButtonManager.createButton("Play Again", buttonInfo.getString("Play Again"), popUpWidth/3.0); //createButton("Play Again");
        Button setupScreen = popUpGameButtonManager.createButton("Return to Setup", buttonInfo.getString("Return to Setup"), popUpWidth/3.0); //createButton("Return to Setup");
        Button mainMenu = popUpGameButtonManager.createButton("Return to Main Menu", buttonInfo.getString("Return to Main Menu"), popUpWidth/3.0);//createButton("Return to Main Menu");
        navOptions.getChildren().addAll(playAgain, setupScreen, mainMenu);
        navOptions.setAlignment(Pos.CENTER);

        return navOptions;
    }

}
