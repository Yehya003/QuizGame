package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private String username;

    public void hideAllOpen() {
        for (Stage s : stages) {
            s.hide();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public void getGame() {
        getScene("game", "Quiz");
    }

    public void getLeaderBoard() {
    getScene("leaderBoard","Leader Board");
    }

    public void getEditPlayer() {
        getScene("edit","Update Account");
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
    /*public void getMeAnotherScene(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondScene.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add((getClass().getResource("MyStyle.css")).toExternalForm());

        Scene scene = new Scene(root);
        // stage.setScene(scene);
        // stage.setTitle("Second one");
        stage.show();
    }
*/
}
