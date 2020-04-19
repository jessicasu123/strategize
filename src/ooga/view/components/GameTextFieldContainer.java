package ooga.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class GameTextFieldContainer {
    public static final int SPACING = 10;
    private TextField myTextField;

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

    public String getText(){
        return myTextField.getText();
    }

    public boolean isNotEmpty(){
        return myTextField.getText() != null && !myTextField.getText().trim().isEmpty();
    }
}
