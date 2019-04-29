package application.controller;

import application.DatabaseConnector;
import application.RegexUtils;
import application.StageManager;
import application.model.Account;
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
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class ForgotPasswordController {

    private ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
    @FXML
    private AnchorPane forgetPassPane;
    @FXML
    private JFXTextField tfUsername, tfEmail;
    @FXML
    private Label lbUsername, lbEmail;

    public void initialize() {

    }

    public void sendPassword(ActionEvent event) throws SQLException {

        String username = tfUsername.getText();
        String email = tfEmail.getText();
        DatabaseConnector myConnection = new DatabaseConnector();

        if (username.trim().equals("") && email.trim().equals("")) {
            lbUsername.setText("Fill The Username! ");
            lbEmail.setText("Fill The Email! ");
        } else if (!email.matches(RegexUtils.EMAIL_REGEX) || email.trim().equals("")) {
            lbEmail.setText("Invalid Email!");
        } else {
            lbUsername.setText("");
            lbEmail.setText("");

            myConnection.checkValidateEmail(username);

            if (Account.getInstance().getEmail().equals(email)) {

                String password = "Your Password: " + Account.getInstance().getPassword();
                sendNewPassEmail(email, password);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("The password was send! ");
                alert.showAndWait();

                tfUsername.clear();
                tfEmail.clear();

                StageManager.getInstance().getLogin();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The email entered not exist! ");
                alert.showAndWait();
            }
        }
    }

    public void backButtonPress(ActionEvent event) {
        StageManager.getInstance().getLogin();
    }

    private void sendNewPassEmail(String email, String messageText) {
        try {
            String host = "smtp.gmail.com";
            String user = "yehyaalkhatib70@gmail.com";
            String pass = "0728466958y";
            String from = "yehyaalkhatib70@gmail.com";
            String subject = "Retrieve Password";
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

    public void hooverOverAnchorPane() {
        effect.setNode(forgetPassPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }
}