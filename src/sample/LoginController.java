package sample;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class LoginController {
    ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
    @FXML
    private AnchorPane logInPane;
    // Handel Login
    @FXML
    private JFXTextField tfAccountLogin;
    @FXML
    private JFXPasswordField pfPasswordLogin;
    @FXML
    private Label lbUsernameLogin, lbPasswordLogin;
    @FXML
    private JFXCheckBox bxRememberMe;


    public void initialize() {

    }

    public void loginButtonPressed(ActionEvent event) throws SQLException {

        String username = tfAccountLogin.getText();
        String password = pfPasswordLogin.getText();
        DBConnection myConnection = new DBConnection();

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
            myConnection.validateLogin(username, password);
            loadAccount();

            if (bxRememberMe.isSelected()) {
                tfAccountLogin.getText();
                pfPasswordLogin.getText();
            } else {
                tfAccountLogin.clear();
                pfPasswordLogin.clear();
            }

        }

    }

    public void forgotPassPressed(ActionEvent event) {

        StageManager.getInstance().getForgetPass();

    }

    public void registerBtnPress(ActionEvent event) {

        StageManager.getInstance().getRegister();
    }

    public void loadAccount() throws SQLException {

        String username = tfAccountLogin.getText();
        DBConnection myConnection = new DBConnection();
        myConnection.getRole(username);

        if (Account.getInstance().getIs_admin() == true) {

            StageManager.getInstance().getAdminScene();

        } else {

            StageManager.getInstance().getMainMenu();
        }
    }

    public void hooverOverAnchorpane() {
        effect.setNode(logInPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }
    // background.fitHeightProperty().bind(mainPane.heightProperty());
    // background.fitWidthProperty().bind(mainPane.widthProperty());
}