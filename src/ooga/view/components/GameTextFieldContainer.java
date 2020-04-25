package ooga.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This class creates a text field using the prompt provided
 * Can be used to get the text from the text field
 */
public class GameTextFieldContainer {
    public static final int SPACING = 10;
    private TextField myTextField;

    /**
     * @param label - the label to put next to the text field
     * @param prompt - the text to use as a prompt for the text field
     * @return an HBox featuring a label and the textfield with the prompt provided
     */
    public HBox createTextFieldContainer(String label, String prompt){
        myTextField = new TextField();
        HBox loadFile = new HBox(SPACING);
        Text loadFileLabel = new Text(label);
        myTextField.setPromptText(prompt);
        myTextField.setId(prompt.replaceAll("\\s", ""));
        loadFile.getChildren().addAll(loadFileLabel, myTextField);
        loadFile.setAlignment(Pos.CENTER);
        return loadFile;
    }

    /**
     * @return the text inside of the text field
     */
    public String getText(){
        return myTextField.getText();
    }

    /**
     * @return whether or not text has been entered into the text field
     */
    public boolean isNotEmpty(){
        return myTextField.getText() != null && !myTextField.getText().trim().isEmpty();
    }
}
