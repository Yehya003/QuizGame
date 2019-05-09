package application.controller;

import application.DatabaseConnector;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EditInfoController {
    @FXML
    private JFXTextField oldPassword;

    @FXML
    private JFXTextField newPassword;

    @FXML
    private JFXTextField repeatPassword;
    @FXML
    private JFXTextField username;


    public void edit() throws Exception {
        Alert myAlert = new Alert(Alert.AlertType.ERROR);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (newPassword.getText().isEmpty() | repeatPassword.getText().isEmpty() | oldPassword.getText().isEmpty()) {
            myAlert.setContentText("Please fill all the fields!");
            myAlert.showAndWait();
        }
        if (newPassword.getText().equals(repeatPassword.getText())) {
            DatabaseConnector obj = new DatabaseConnector();
            boolean done = obj.updatePassword(username.getText(), oldPassword.getText(), newPassword.getText());
            if (!done) {
                myAlert.setContentText("Database connection problem or we could not find you in our database!");
                myAlert.showAndWait();
            } else {
                alert.setContentText("The password changed successfully!");
                alert.showAndWait();
            }
        } else {
            myAlert.setContentText("The repeated password mismatching the new password!");
            myAlert.showAndWait();
        }
    }
}
