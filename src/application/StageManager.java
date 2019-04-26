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
        getScene("login", "Login");
    }

    public void getForgetPass() {
        getScene("forgotPassword", "Forgot Password");
    }

    public void getRegister() {
        getScene("register", "Create Account");
    }

    public void getMainMenu() {
        getScene("mainMenu", "Main Menu");
    }

    public void getAdminScene() {
        getScene("admin", "Admin Menu");
    }

    public void getGameSetup() {
        getScene("gameSetup", "Setup Game");
    }

    public void getGame() {
        getScene("game", "Quiz");
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

    private void getScene(String fxmlFile, String stageTitle) {
        Stage stage = createStage("view/" + fxmlFile + ".fxml");
        stage.setTitle(stageTitle);
        stages.add(stage);
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
