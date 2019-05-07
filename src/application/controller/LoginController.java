package application.controller;

import application.DatabaseConnector;
import application.DatabaseUpdaterThread;
import application.StageManager;
import application.model.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
    @FXML
    private AnchorPane logInPane;
    @FXML
    private JFXTextField tfAccountLogin;
    @FXML
    private JFXPasswordField pfPasswordLogin;
    @FXML
    private Label lbUsernameLogin;
    @FXML
    private Label lbPasswordLogin;
    @FXML
    private JFXCheckBox bxRememberMe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfAccountLogin.setText("ste");
        pfPasswordLogin.setText("ste");
    }

    public void loginButtonPressed() {
        try {
            String username = tfAccountLogin.getText();
            String password = pfPasswordLogin.getText();
            if (username.trim().equals("") && password.trim().equals("")) {
                lbUsernameLogin.setText("Fill The Username!");
                lbPasswordLogin.setText("Fill The Password!");
            } else if (username.trim().equals("")) {
                lbUsernameLogin.setText("Fill The Username!");
            } else if (password.trim().equals("")) {
                lbPasswordLogin.setText("Fill The Password!");
            } else {
                lbUsernameLogin.setText("");
                lbPasswordLogin.setText("");

                //TODO add animation for waiting after trying to login
                DatabaseUpdaterThread updater = new DatabaseUpdaterThread();
                updater.prepareValidateLogin(username, password);
                new Thread(updater).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassPressed(ActionEvent event) {
        StageManager.getInstance().getForgetPass();
    }

    public void registerBtnPress(ActionEvent event) {
        StageManager.getInstance().getRegister();
    }

    /*public void loadAccount() {
        try {
            String username = tfAccountLogin.getText();
            DatabaseConnector myConnection = new DatabaseConnector();
            myConnection.getRole(username);

            if (Account.getInstance().isAdmin()) {
                StageManager.getInstance().getAdminScene();
            } else if(!Account.getInstance().isAdmin()) {
                StageManager.getInstance().getMainMenu();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The User Not Register!  ");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void hooverOverAnchorPane() {
        effect.setNode(logInPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }
}