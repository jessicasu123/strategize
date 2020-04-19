package ooga.view.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.List;

public class GameDropDown {
    public static final int SPACING = 10;
    private ComboBox<String> myDropDown;

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

    public ComboBox<String> getComboBox(){
        return myDropDown;
    }

    public boolean isNotEmpty(){
        return !myDropDown.getSelectionModel().isEmpty();
    }

    public String getValue(){
        return myDropDown.getValue();
    }

}
