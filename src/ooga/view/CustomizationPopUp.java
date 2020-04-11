package ooga.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomizationPopUp extends GamePopUp{
    private Pane customizationPane;
    private JSONObject labelText;
    private JSONObject buttonText;
    private List<String> boardColorOptions;
    private List<String> playerImages;
    private String userImage;
    private String opponentImage;
    private ImageView userImageChoice;
    private ImageView opponentImageChoice;
    private String boardColorChoice;

    public CustomizationPopUp(Stage stage, int width, int height, String currUserImg, String currOppImg) throws FileNotFoundException {
        super(stage, width, height);
        boardColorOptions = new ArrayList<>();
        playerImages = new ArrayList<>();
        getBoardAndPlayerCustomizationChoices();
        userImage = currUserImg;
        opponentImage = currOppImg;

    }

    @Override
    public void createPopUpContents() {
        VBox customizationContents = new VBox();
        customizationContents.setSpacing(40);
        customizationContents.getChildren().addAll(createPlayerCustomization(),
                createBackgroundCustomization());
        myPopUpContents.getChildren().add(customizationContents);
    }

    private void getBoardAndPlayerCustomizationChoices() {
        String boardColors = popUpScreenData.getString("Colors");
        boardColorOptions = Arrays.asList(boardColors.split(","));

        String imageOptions = popUpScreenData.getString("Images");
        playerImages = Arrays.asList(imageOptions.split(","));
    }

    private VBox createPlayerCustomization() {
        VBox playerCustomization = new VBox();
        playerCustomization.setSpacing(20);

        labelText = popUpScreenData.getJSONObject("Labels");
        HBox topCustomizeContainer = createContainerWithHeadingLabel(labelText.getString("PlayerCustomization"),
                "customizeLabels");

        HBox userChoiceContainer = createChoiceContainerWithComboBox(true,labelText.getString("PlayerChoice"), "Choose Image");
        userImageChoice = setImageView(userImage);
        userChoiceContainer.getChildren().add(userImageChoice);

        HBox opponentChoiceContainer = createChoiceContainerWithComboBox(false,labelText.getString("OpponentChoice"), "Choose Image");
        opponentImageChoice = setImageView(opponentImage);
        opponentChoiceContainer.getChildren().add(opponentImageChoice);

        playerCustomization.getChildren().addAll(topCustomizeContainer,
                userChoiceContainer,
                opponentChoiceContainer);

        return playerCustomization;
    }

    private VBox createBackgroundCustomization() {
        VBox backgroundCustomization = new VBox();
        backgroundCustomization.setSpacing(20);

        buttonText = popUpScreenData.getJSONObject("Buttons");
        HBox customizeBackgroundContainer = createContainerWithHeadingLabel(labelText.getString("BackgroundCustomization"),
                "customizeLabels");
        HBox boardColorContainer = chooseBoardColorContainer(labelText.getString("BackgroundColor"), "customizeLabels");
        HBox backgroundContainer = backgroundChoiceContainer();

        backgroundCustomization.getChildren().addAll(customizeBackgroundContainer,
                boardColorContainer, backgroundContainer);

        return backgroundCustomization;

    }

    private HBox createContainerWithHeadingLabel(String labelName, String style) {
        HBox headingContainer = createHorizontalContainer();
        Label heading = createHeadingLabel(labelName, style);
        headingContainer.setAlignment(Pos.TOP_CENTER);
        headingContainer.getChildren().add(heading);
        return headingContainer;
    }

    private HBox chooseBoardColorContainer(String labelName, String comboBoxName) {
        HBox boardColorContainer = createChoiceContainerWithLabel(labelName);
        ComboBox colorChoice = new ComboBox();
        colorChoice.getItems().addAll(boardColorOptions);
        colorChoice.valueProperty().addListener(((observable, oldValue, newValue) -> {
            boardColorChoice = (String) newValue;
        }));
        boardColorContainer.getChildren().add(colorChoice);
        return boardColorContainer;
    }

    private HBox backgroundChoiceContainer() {
        HBox backgroundMode = createHorizontalContainer();
        backgroundMode.setAlignment(Pos.CENTER);
        Button lightMode = createButton(buttonText.getString("LightMode"));
        Button darkMode = createButton(buttonText.getString("DarkMode"));
        backgroundMode.getChildren().addAll(lightMode, darkMode);
        return backgroundMode;
    }

    private Button createButton(String buttonName) {
        Button b = new Button(buttonName);
        b.getStyleClass().add("gameButton");
        b.setMinWidth(popUpWidth/3.0);
        return b;
    }

    private HBox createHorizontalContainer() {
        HBox container = new HBox();
        container.setPadding(new Insets(20,20,0,20));
        container.setSpacing(30);
        return container;
    }

    private HBox createChoiceContainerWithLabel(String labelName) {
        HBox choiceCustomizeContainer = createHorizontalContainer();
        Label playerLabel = createLabel(labelName);
        choiceCustomizeContainer.getChildren().add(playerLabel);
        return choiceCustomizeContainer;
    }

    private HBox createChoiceContainerWithComboBox(boolean isUser, String labelName, String comboBoxName) {
        HBox choiceCustomizeContainer = createChoiceContainerWithLabel(labelName);
        ComboBox playerImageChoice = new ComboBox();
        playerImageChoice.getItems().addAll(playerImages);
        playerImageChoice.setPromptText(comboBoxName);
        playerImageChoice.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (isUser) {
                userImage = (String) newValue + ".png";
                updateImageView(userImageChoice, userImage);
            }
            else {
                opponentImage = (String) newValue + ".png";
                updateImageView(opponentImageChoice, opponentImage);
            }
        }));
        choiceCustomizeContainer.getChildren().add(playerImageChoice);
        return choiceCustomizeContainer;
    }

    private void updateImageView(ImageView imgView, String newImg) {
        imgView.setImage(new Image(PIECES_RESOURCES + newImg));
    }


    private ImageView setImageView(String imageName) {
        Image img = new Image(PIECES_RESOURCES + imageName);
        ImageView playerIconChoice = new ImageView(img);
        playerIconChoice.setFitWidth(30);
        playerIconChoice.setPreserveRatio(true);
        return playerIconChoice;
    }


    private Label createHeadingLabel(String labelName, String styleName) {
        Label heading = createLabel(labelName);
        heading.setMinWidth(popUpWidth);
        heading.getStyleClass().add(styleName);
        return heading;
    }


    private Label createLabel(String labelName) {
        Label label = new Label();
        label.setText(labelName);
        label.setAlignment(Pos.CENTER);
        return label;
    }



}
