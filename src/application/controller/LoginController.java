package application.controller;

import application.CurrentAccountSingleton;
import application.DatabaseRunnable;
import application.StageManager;
import application.model.Account;
import application.utils.FileUtils;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        try (ObjectInputStream x = new ObjectInputStream(new FileInputStream(FileUtils.accountFilePath))) {
            Account accountFromFile = (Account) x.readObject();
            if (accountFromFile != null) {
                CurrentAccountSingleton.getInstance().setAccount(accountFromFile);
                bxRememberMe.setSelected(true);
                tfAccountLogin.setText(accountFromFile.getUsername());
                pfPasswordLogin.setText(accountFromFile.getPassword());
            }
        } catch (EOFException e) {
            System.out.println("There is no account saved yet");
        } catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("File did not contain an account");
            e.printStackTrace();
        }
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
                DatabaseRunnable runnable = new DatabaseRunnable();
                runnable.prepareValidateLogin(username, password, bxRememberMe.isSelected());
                new Thread(runnable).start();
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

    public void hooverOverAnchorPane() {
        effect.setNode(logInPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }
}