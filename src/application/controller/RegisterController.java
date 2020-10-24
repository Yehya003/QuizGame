package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.utils.RegexUtils;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class RegisterController {

    private ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
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
    private Label lbRegisterUsername, lbRegisterEmail, lbRegisterPass, lbConfirmPass;

    public void registerButtonPressed(ActionEvent event) {

        DatabaseConnector myConnection = new DatabaseConnector();
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        String email = tfEmail.getText();
        String confirmPass = pfConfirmPassword.getText();
        boolean is_admin = false;

        if (username.trim().equals("") && password.trim().equals("") && email.trim().equals("") && confirmPass.trim().equals("")) {
            lbRegisterUsername.setText("Fill the username! ");
            lbRegisterPass.setText("Fill the password! ");
            lbRegisterEmail.setText("Fill the email! ");
            lbConfirmPass.setText("Confirm the password! ");
        } else if (username.trim().equals("")) {
            lbRegisterUsername.setText("Fill the username! ");
        } else if (password.trim().equals("") || confirmPass.trim().equals("")) {
            lbRegisterPass.setText("Fill the password! ");
        } else if (!email.matches(RegexUtils.EMAIL_REGEX)) {
            lbRegisterEmail.setText("Invalid email!");
        } else if (!password.equals(confirmPass)) {
            lbConfirmPass.setText("Wrong password! ");
        } else {
            if (myConnection.checkIfUserAlreadyExists(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User with this username already exists!");
                alert.showAndWait();
            } else {
                myConnection.saveRegistration(username, password, email, is_admin);
                String welcomeMessage = "Dear:" + username + "\n\n Your registration has been successfully completed!" +
                        " \n\n Username: " + username + "\n\n Password:" + password;
                sendRegisterConfirmation(email, welcomeMessage);
                StageManager.getInstance().getMainMenu();
            }
        }
    }


    public void exitButtonPress(ActionEvent event) {
        StageManager.getInstance().getLogin();
    }

    public void hoverOverAnchorPane() {
        effect.setNode(registerPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }

    private void sendRegisterConfirmation(String email, String messageText) {
        try {
            String host = "smtp.gmail.com";
            String user = "yehyaalkhatib70@gmail.com";
            String pass = "0728466958y";
            String from = "yehyaalkhatib70@gmail.com";
            String subject = "Welcome To HQR Quiz";
            // String messageText = "Your new password :"; // here we need to send the password
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            //java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(email)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.WARNING, "Email not sent." +
                    "\nCheck if you entered a valid email.");
            a.showAndWait();
        }
    }

}
