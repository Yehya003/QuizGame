package sample.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.DBConnection;
import sample.StageManager;
import java.sql.SQLException;
import java.util.Random;

public class RegisterController  {

    // Handel Registration
    ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
    @FXML
    private AnchorPane registerPane;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXPasswordField pfPassword;
    @FXML
    private JFXPasswordField pfConfirmPassword;
    @FXML
    private Label lbRegisterUsername,lbRegisterEmail,lbRegisterPass,lbConfirmPass;


    public void initialize (){

    }


    public void registerButtonPressed (ActionEvent event) throws SQLException {

        DBConnection myConnection = new DBConnection();
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        String email = tfEmail.getText();
        String confirmPass = pfConfirmPassword.getText();
        boolean is_admin = false;

        if (username.trim().equals("") && password.trim().equals("") && email.trim().equals("") && confirmPass.trim().equals("")){
            lbRegisterUsername.setText("Fill the username! ");
            lbRegisterPass.setText("Fill the password! ");
            lbRegisterEmail.setText("Fill the email! ");
            lbConfirmPass.setText("Confirm the password! ");
        }
        else if (username.trim().equals("") ){
            lbRegisterUsername.setText("Fill the username! ");
        }
        else if (password.trim().equals("") || confirmPass.trim().equals("") ){
            lbRegisterPass.setText("Fill the password! ");
        }
        else if (!email.matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$") || email.trim().equals("")){
            lbRegisterEmail.setText("Invalid email!");
        }
        else if (!password.equals(confirmPass)){
            lbConfirmPass.setText("Wrong password! ");
        }
        else {

            myConnection.saveRegistration(username,password,email,is_admin);
            StageManager.getInstance().getMainMenu();
        }
    }


    public void exitBtnPress(ActionEvent event){

        StageManager.getInstance().getLogin();
    }

    public void hooverOverAnchorpane() {
        effect.setNode(registerPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }

}
