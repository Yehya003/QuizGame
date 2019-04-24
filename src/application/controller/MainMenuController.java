package application.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private JFXButton playGameButton;
    @FXML
    private JFXButton playerStatisticsButton;
    @FXML
    private JFXButton leaderboardsButton;
    @FXML
    private JFXButton editPlayerButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playGameButton.setOnAction(e -> playGame());
        playerStatisticsButton.setOnAction(e -> openPlayerStatistics());
        leaderboardsButton.setOnAction(e -> openLeaderboards());
        editPlayerButton.setOnAction(e -> openEditPlayer());
    }

    private void playGame() {

    }

    private void openPlayerStatistics() {

    }

    private void openLeaderboards() {

    }

    private void openEditPlayer() {

    }

}