package application.controller;

import application.DatabaseConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSetupController implements Initializable {

    @FXML
    private JFXComboBox<?> categoryComboBox;
    @FXML
    private JFXButton startGameButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
