package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.view.components.GameDropDown;
import ooga.view.components.GameIcon;
import org.json.JSONObject;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import javax.swing.event.ChangeListener;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Responsible for allowing the user to customize the piece images
 * for both his/her player and the opponent's player, as well
 * as the background of the board & mode settings (dark or light mode).
 * These customization selections are finalized when the user
 * clicks on the "SET PREFERENCES" button and reflected
 * in the view.
 *
 * @author Jessica Su
 */
public class CustomizationPopUp extends GamePopUp{

    public static final String LIGHT_MODE_BOARD_OUTLINE = "black";
    public static final String DARK_MODE_BOARD_OUTLINE = "white";
    public static final String LIGHT_MODE_BOARD_FILL = "white";
    public static final String DARK_MODE_BOARD_FILL = "black";
    public static final String LIGHT_MODE = "light mode";
    public static final String DARK_MODE = "dark mode";
    public static final String IMG_EXTENSION = ".png";
    public static final double DIVIDE_FACTOR = 3.0;

    private JSONObject labelText;
    private JSONObject buttonInfo;
    private List<String> boardColorOptions;
    private List<String> playerImages;
    private String userImage;
    private String opponentImage;
    private ImageView userImageChoice;
    private ImageView opponentImageChoice;
    private String boardColorChoice;
    private List<String> modeOptions;
    private String userModeChoice;
    private ComboBox<String> colorChoice;

    public CustomizationPopUp(Stage stage, int width, int height, String fileName,
                              String currUserImg, String currOppImg,
                              String currColor, GameButtonManager buttonManager) throws FileNotFoundException {
        super(stage, width, height, fileName, buttonManager);
        setUpJSONReader();
        boardColorOptions = new ArrayList<>();
        playerImages = new ArrayList<>();
        modeOptions = new ArrayList<>(List.of(LIGHT_MODE, DARK_MODE));
        userModeChoice = LIGHT_MODE;
        userImage = currUserImg;
        opponentImage = currOppImg;
        getBoardAndPlayerCustomizationChoices();
        playerImages.add(userImage.split("\\.")[0]);
        playerImages.add(opponentImage.split("\\.")[0]);
        boardColorChoice = currColor;
    }

    /**
     * For the CustomizationPopUp, the content includes the ability
     * to customize the pieces for the player and opponent,
     * as well as options to change the color of the board background
     * or switch to dark/light mode.
     */
    @Override
    public void createPopUpContents() {
        VBox customizationContents = new VBox();
        customizationContents.setSpacing(SPACING);
        customizationContents.getChildren().addAll(createPlayerCustomization(),
                createBackgroundCustomization(),
                createSetPreferencesContainer());
        myPopUpContents.getChildren().add(customizationContents);
    }

    /**
     * Tells the view in which this pop-up is created what the user's piece choice was.
     * @return - the image that the user has chosen to represent his/her piece
     */
    protected List<String> getUserImageChoice() {
        return List.of(userImage.split(",")); }

    /**
     * Tells the view in which this pop-up is created what the opponent's piece should be.
     * @return - the image the user selected for the opponent's piece
     */
    protected List<String> getOpponentImageChoice() { return List.of(opponentImage.split(",")); }

    /**
     * Tells the view in which this pop-up is created what the board color should be.
     * @return - the desired board color
     */
    protected String getBoardColorChoice() {
        if (colorChoice.getValue()==null) {
            if (userModeChoice.equals(DARK_MODE)) {
                return DARK_MODE_BOARD_FILL;
            }else {
                return LIGHT_MODE_BOARD_FILL;
            }
        }
        return boardColorChoice; }


    /**
     * Tells the view in which this pop-up is created whether the user chose
     * dark mode or light mode.
     * @return - the desired mode setting (dark or light)
     */
    protected boolean isLightMode() {return userModeChoice.equals(LIGHT_MODE);}

    /**
     * Tells the view in which this pop-up is created what the board outline color
     * should be. Relevant for switching to dark or light mode.
     * @return - the board outline color depending on the mode
     */
    protected String getBoardOutlineColor() {
        if (userModeChoice.equals(LIGHT_MODE)) return LIGHT_MODE_BOARD_OUTLINE;
        else return DARK_MODE_BOARD_OUTLINE;
    }

    private void getBoardAndPlayerCustomizationChoices() {
        String boardColors = popUpScreenData.getString("Colors");
        boardColorOptions.addAll(Arrays.asList(boardColors.split(",")));

        String imageOptions = popUpScreenData.getString("Images");
        playerImages.addAll(Arrays.asList(imageOptions.split(",")));
    }

    private VBox createPlayerCustomization() {
        VBox playerCustomization = new VBox();
        playerCustomization.setSpacing(SPACING/2);

        labelText = popUpScreenData.getJSONObject("Labels");
        HBox topCustomizeContainer = createContainerWithHeadingLabel(labelText.getString("PlayerCustomization"),
                "customizeLabels");

        HBox userChoiceContainer = createChoiceContainerWithComboBox(true,
                labelText.getString("PlayerChoice").split(",")[0],
                labelText.getString("PlayerChoice").split(",")[1]);
        userImageChoice = setImageView(userImage);
        userChoiceContainer.getChildren().add(userImageChoice);

        HBox opponentChoiceContainer = createChoiceContainerWithComboBox(false,
                labelText.getString("OpponentChoice").split(",")[0],
                labelText.getString("OpponentChoice").split(",")[1]);
        opponentImageChoice = setImageView(opponentImage);
        opponentChoiceContainer.getChildren().add(opponentImageChoice);

        playerCustomization.getChildren().addAll(topCustomizeContainer, userChoiceContainer, opponentChoiceContainer);

        return playerCustomization;
    }

    private VBox createBackgroundCustomization() {
        VBox backgroundCustomization = new VBox();
        backgroundCustomization.setSpacing(SPACING/2);

        buttonInfo = popUpScreenData.getJSONObject("Buttons");
        HBox customizeBackgroundContainer = createContainerWithHeadingLabel(labelText.getString("BackgroundCustomization"),
                "customizeLabels");
        HBox boardColorContainer = chooseBoardColorContainer(labelText.getString("BackgroundColor").split(",")[0],
                labelText.getString("BackgroundColor").split(",")[1]);

        HBox modeContainer = chooseModeContainer(labelText.getString("ModeChoice").split(",")[0],
                labelText.getString("ModeChoice").split(",")[1]);

        backgroundCustomization.getChildren().addAll(customizeBackgroundContainer,
                boardColorContainer, modeContainer);

        return backgroundCustomization;
    }

    private HBox createSetPreferencesContainer() {
        HBox setPref = createHorizontalContainer();
        setPref.setAlignment(Pos.CENTER);
        Button setPreferencesButton = popUpGameButtonManager.createButton("SET PREFERENCES",
                buttonInfo.getString("SET PREFERENCES"),
                popUpWidth/DIVIDE_FACTOR);
        setPref.getChildren().add(setPreferencesButton);
        return setPref;
    }

    private HBox createContainerWithHeadingLabel(String labelName, String style) {
        HBox headingContainer = createHorizontalContainer();
        Label heading = createHeadingLabel(labelName, style);
        headingContainer.setAlignment(Pos.TOP_CENTER);
        headingContainer.getChildren().add(heading);
        return headingContainer;
    }

    private HBox chooseBoardColorContainer(String labelName, String comboBoxName) {
        GameDropDown colorDropDown = new GameDropDown();
        HBox boardColorContainer = colorDropDown.createDropDownContainer(Pos.CENTER,boardColorOptions,comboBoxName,labelName);
        colorChoice = colorDropDown.getComboBox();
        colorChoice.valueProperty().addListener(((observable, oldValue, newValue) -> boardColorChoice = newValue));
        return boardColorContainer;
    }

    private HBox createChoiceContainerWithComboBox(boolean isUser, String labelName, String comboBoxName) {
        GameDropDown playerImageDropDown = new GameDropDown();
        HBox choiceCustomizeContainer = playerImageDropDown.createDropDownContainer(Pos.CENTER,playerImages,comboBoxName,labelName);
        ComboBox<String> playerImageChoice = playerImageDropDown.getComboBox();
        playerImageChoice.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (isUser) {
                userImage = newValue + IMG_EXTENSION;
                updateImageView(userImageChoice, userImage);
            }
            else {
                opponentImage = newValue + IMG_EXTENSION;
                updateImageView(opponentImageChoice, opponentImage);
            }
        }));

        return choiceCustomizeContainer;
    }

    private HBox chooseModeContainer(String labelName, String comboBoxName) {
        GameDropDown modeDropDown = new GameDropDown();
        HBox modeContainer = modeDropDown.createDropDownContainer(Pos.CENTER, modeOptions, comboBoxName, labelName);
        ComboBox<String> modeChoice = modeDropDown.getComboBox();
        modeChoice.valueProperty().addListener((observable, oldValue, newValue) -> userModeChoice = newValue);
        return modeContainer;
    }

    private void updateImageView(ImageView imgView, String newImg) {
        imgView.setImage(new Image(PIECES_RESOURCES + newImg));
    }

    private ImageView setImageView(String imageName) {
        return new GameIcon().createGameIcon(PIECES_RESOURCES+imageName);
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

    private HBox createHorizontalContainer() {
        HBox container = new HBox();
        container.setPadding(new Insets(20,SPACING/2,0,SPACING/2));
        container.setSpacing(SPACING-10);
        return container;
    }



}
