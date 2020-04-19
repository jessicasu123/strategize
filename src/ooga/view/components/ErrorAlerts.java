package ooga.view.components;

import javafx.scene.control.Alert;
import org.json.JSONArray;

public class ErrorAlerts {

    public static final int TITLE_INDEX = 0;
    public static final int HEADER_INDEX = 1;
    public static final int MESSAGE_INDEX = 2;
    public static final String ERROR = "Error";

    public ErrorAlerts(JSONArray alertInfo){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertInfo.getString(TITLE_INDEX));
        alert.setHeaderText(alertInfo.getString(HEADER_INDEX));
        alert.setContentText(alertInfo.getString(MESSAGE_INDEX));
        alert.showAndWait();
    }

    public ErrorAlerts(String errorType, String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(errorType);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
