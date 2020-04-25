package ooga.view.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.List;

/**
 * This class creates a combo box with a label and prompt
 * It also can be used to get the combo box directly or just get
 * the value/see if it is not empty
 */
public class GameDropDown {
    public static final int SPACING = 10;
    private ComboBox<String> myDropDown;

    /**
     * @param position - where the box should be positions
     * @param options - the options to include in the drop down
     * @param prompt - the prompt to include before opening the drop down
     * @param label - the text to use as the label
     * @return an HBox containing a container featuring the dropdown and a label
     */
    public HBox createDropDownContainer(Pos position, List<String> options, String prompt, String label){
        HBox container = new HBox(SPACING);
        Text dropDownLabel = new Text(label);
        myDropDown = new ComboBox<>();
        ObservableList<String> obList = FXCollections.observableList(options);
        myDropDown.setItems(obList);
        myDropDown.setId(prompt.replaceAll("\\s", ""));
        myDropDown.setPromptText(prompt);
        container.getChildren().addAll(dropDownLabel, myDropDown);
        container.setAlignment(position);
        return container;
    }

    /**
     * @return the combo box, for instances when need to add event listeners
     */
    public ComboBox<String> getComboBox(){
        return myDropDown;
    }

    /**
     * @return whether an option has been selected
     */
    public boolean isNotEmpty(){
        return !myDropDown.getSelectionModel().isEmpty();
    }

    /**
     * @return the value that has been selected
     */
    public String getValue(){
        return myDropDown.getValue();
    }

}
