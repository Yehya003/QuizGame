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
        getScene(login, "view/login.fxml", "Login");
    }

    public void getForgetPass() {
        getScene(forgetPass, "view/forgotPassword.fxml", "Forgot Password");
    }

    public void getRegister() {
        getScene(register, "view/register.fxml", "Create Account");
    }

    public void getMainMenu() {
        getScene(mainMenu, "view/mainMenu.fxml", "Main Menu");
    }

    public void getAdminScene() {
        getScene(admin, "view/admin.fxml", "Admin Menu");
    }

    public void getGameSetup() {
        //TODO game setup scene
    }

    public void getPlayerStatistics() {
        //TODO player statistics scene
    }

    public void getLeaderboards() {
        //TODO leaderboards scene
    }

    public void getEditPlayer() {
        //TODO edit player scene
    }

    private void getScene(Stage stage, String fxmlFile, String stageTitle) {
        if (stage == null) {
            stage = createStage(fxmlFile);
            stage.setTitle(stageTitle);
            stages.add(stage);
        }
        hideAllOpen();
        stage.show();
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
