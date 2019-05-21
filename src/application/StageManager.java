package application;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    private void hideAllOpen() {
        for (Stage s : stages) {
            s.hide();
        }
    }

    public void getLogin() {
        changeIntoNewScene("login", "Login");
    }

    public void getForgetPass() {
        changeIntoNewScene("forgotPassword", "Forgot Password");
    }

    public void getRegister() {
        changeIntoNewScene("register", "Create Account");
    }

    public void getMainMenu() {
        changeIntoNewScene("mainMenu", "Main Menu");
    }

    public void getAdminScene() {
        changeIntoNewScene("admin", "Admin Menu");
    }

    public void getGame() {
        changeIntoNewScene("game", "Quiz");
    }

    public void getPlayerStatistics() {
        //TODO player statistics scene
    }

    public void getLeaderBoard() {
        changeIntoNewScene("leaderBoard", "Leader Board");
    }

    public void getEditPlayer() {
        changeIntoNewScene("editInfo", "Update Account");
    }

    private void changeIntoNewScene(String fxmlFile, String stageTitle) {
        //Handles the changing of the scene in the Main thread as it is illegal to do it in a separate Thread
        Platform.runLater(() -> {
            Stage stage = createStage("view/" + fxmlFile + ".fxml");
            stage.setTitle(stageTitle);
            stages.add(stage);
            hideAllOpen();
            stage.show();
        });
    }

    private Stage createStage(String stageName) {
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

    private Stage progressStage;

    public void showProgressBar(Stage currentStage) {
        try {
            Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("view/progressBar.fxml")));
            progressStage = new Stage(); //Window
            progressStage.setScene(newScene);
            progressStage.initStyle(StageStyle.UNDECORATED);
            progressStage.initModality(Modality.WINDOW_MODAL);
            //Set to the middle of the screen //TODO make this more beautiful
            progressStage.setX(((currentStage.getX() + (currentStage.getX() + currentStage.getWidth())) / 2) - 100);
            progressStage.setY(((currentStage.getY() + (currentStage.getY() + currentStage.getHeight())) / 2) - 10);
            progressStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void stopProgressBar() {
        progressStage.close();
    }
}
