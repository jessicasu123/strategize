package ooga.view.components;

import javafx.scene.control.Alert;
import org.json.JSONArray;

/**
 * This class creates a pop up to present an error
 * message to let the user know what the problem is
 *
 * @author Holly Ansel
 */
public class ErrorAlerts {

    public static final int TITLE_INDEX = 0;
    public static final int HEADER_INDEX = 1;
    public static final int MESSAGE_INDEX = 2;
    public static final String ERROR = "Error";

    /**
     * @param alertInfo - some alerts are created using custom
     *                  information directly from the datafile
     */
    public ErrorAlerts(JSONArray alertInfo){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertInfo.getString(TITLE_INDEX));
        alert.setHeaderText(alertInfo.getString(HEADER_INDEX));
        alert.setContentText(alertInfo.getString(MESSAGE_INDEX));
        alert.showAndWait();
    }

    /**
     * Alerts can also be created by gathering information from the exception itself
     * @param errorType - the type of error
     * @param errorMessage - the message associated with the error
     */
    public ErrorAlerts(String errorType, String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(errorType);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
