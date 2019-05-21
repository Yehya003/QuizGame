package application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageManager {

    private Stage progressStage;
    private static StageManager stageManager = new StageManager();

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
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

    public void getLeaderBoard() {
        changeIntoNewScene("leaderBoard", "Leader Board");
    }

    public void getEditPlayer() {
        changeIntoNewScene("editInfo", "Update Account");
    }

    private void changeIntoNewScene(String fxmlFile, String stageTitle) {
        //Handles the changing of the scene in the Main thread as it is illegal to do it in a separate Thread
        Platform.runLater(() -> {
            Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("view/" + fxmlFile + ".fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle(stageTitle);
            stage.show();
        });
    }

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
