package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        //root.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //primaryStage.setTitle("Quiz me");
        //primaryStage.setScene(new Scene(root));
        //primaryStage.setResizable(true);
        //primaryStage.show();
        StageManager.getInstance().getLogin();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
