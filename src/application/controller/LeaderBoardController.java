package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.model.Quiz;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {

    @FXML
    private TableView<Quiz> tableView;
    @FXML
    private TableColumn<Quiz, String> username;

    @FXML
    private TableColumn<Quiz, Integer> score;

    @FXML
    private TableColumn<Quiz, String> category;

    @FXML
    private TableColumn<Quiz, Integer> duration;

    @FXML
    private JFXButton back;

    public void backBtnPressed() {
        StageManager.getInstance().getMainMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //establishing connection with the database class
        DatabaseConnector connector = new DatabaseConnector();
        // instructing the columns to read the values from these quiz class specific attributes
        // that will happen after we set the items inside the tableView by we must instruct first the columns and tell
        // them where to read from.
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        //Setting the column content alignment to be in the center
        username.setStyle("-fx-alignment: CENTER;");
        score.setStyle("-fx-alignment: CENTER;");
        category.setStyle("-fx-alignment: CENTER;");
        duration.setStyle("-fx-alignment: CENTER;");

        try {
            //setting the items inside the table by getting the returning value from the database method that
            // retrieves them from the database
            tableView.setItems(connector.getTheHighestScores());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
