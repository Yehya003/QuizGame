package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameSetupController implements Initializable {

    @FXML
    private JFXComboBox<Label> categoryComboBox;
    @FXML
    private JFXButton startGameButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startGameButton.setOnAction(e -> startGame());
        populateComboBox();
    }

    private void populateComboBox() {
        DatabaseConnector databaseConnector = null;
        try {
            databaseConnector = new DatabaseConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> categories = databaseConnector.getCategoryList();
        ArrayList<Label> categoryLabels = new ArrayList<>();

        for (String element : categories) {
            categoryLabels.add(new Label(element));
        }

        categoryComboBox.getItems().addAll(categoryLabels);
    }

    private void startGame() {
        //Todo send information to Game regarding category etc.
        StageManager.getInstance().getGame();
    }
}
