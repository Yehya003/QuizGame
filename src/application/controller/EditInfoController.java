package application.controller;

import application.CurrentAccountSingleton;
import application.DatabaseConnector;
import application.StageManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class EditInfoController implements Initializable {
    @FXML
    private JFXTextField oldPassword;
    @FXML
    private JFXTextField newPassword;
    @FXML
    private JFXTextField repeatPassword;
    @FXML
    private JFXTextField username;


    public void editInfo() {
        Alert myAlert = new Alert(Alert.AlertType.ERROR);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (username.getText().isEmpty() || oldPassword.getText().isEmpty() || repeatPassword.getText().isEmpty() || newPassword.getText().isEmpty()) {
            myAlert.setContentText("Please fill all the fields!");
            myAlert.showAndWait();
        } else if (newPassword.getText().equals(repeatPassword.getText())) {
            DatabaseConnector db = new DatabaseConnector();
            boolean done = db.updatePassword(username.getText(), oldPassword.getText(), newPassword.getText());
            if (!done) {
                myAlert.setContentText("Database connection problem or we could not find you in our database!");
                myAlert.showAndWait();
            } else {
                alert.setContentText("The password changed successfully!");
                alert.showAndWait();
            }
        } else {
            myAlert.setContentText("The repeated password does not match the new password!");
            myAlert.showAndWait();
        }
    }
    public void leaderBoardBtnPressed() {
        StageManager.getInstance().getLeaderBoard();
    }
    public void logOutPressed() {
        StageManager.getInstance().getLogin();
    }
    public void editBtnPressed() {
        StageManager.getInstance().getEditPlayer();
    }
    public void mainMenuPressed(){StageManager.getInstance().getMainMenu();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(CurrentAccountSingleton.getInstance().getAccount().getUsername());

    }
}
