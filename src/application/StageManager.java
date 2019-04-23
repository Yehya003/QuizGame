package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class StageManager {

    private static StageManager stageManager = new StageManager();

    private Stage login;
    private Stage register;
    private Stage mainMenu;
    private Stage admin;
    private Stage forgetPass;

    private ArrayList<Stage> stages;

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        stages = new ArrayList<>();
    }

    public void hideAllOpen() {
        for (Stage s : stages) {
            s.hide();
        }
    }

    public void getLogin() {
        if (login == null) {
            login = createStage("view/login.fxml");
            login.setTitle("Login");
            stages.add(login);
        }
        hideAllOpen();
        login.show();
    }

    public void getForgetPass() {
        if (forgetPass == null) {
            forgetPass = createStage("view/forgotPassword.fxml");
            forgetPass.setTitle("Forget Password");
            stages.add(forgetPass);
        }
        hideAllOpen();
        forgetPass.show();
    }

    public void getRegister() {
        if (register == null) {
            register = createStage("view/register.fxml");
            register.setTitle("Create account");
            stages.add(register);
        }
        hideAllOpen();
        register.show();
    }

    public void getMainMenu() {
        if (mainMenu == null) {
            mainMenu = createStage("view/mainMenu.fxml");
            mainMenu.setTitle("Main Menu");
            stages.add(mainMenu);
        }
        hideAllOpen();
        mainMenu.show();
    }

    public void getAdminScene() {
        if (admin == null) {
            admin = createStage("view/admin.fxml");
            admin.setTitle("Admin Menu");
            stages.add(admin);
        }
        hideAllOpen();
        admin.show();
    }

    public Stage createStage(String stageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(stageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.DECORATED);
        return stage;
    }
}
