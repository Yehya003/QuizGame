package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.model.Quiz;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    @FXML
    private LineChart<?, ?> lineChart;
    XYChart.Series<?,?> myChart = new XYChart.Series<>();

    public void backBtnPressed() {
        StageManager.getInstance().getMainMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnector connector = new DatabaseConnector();
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        username.setStyle("-fx-alignment: CENTER;");
        score.setStyle("-fx-alignment: CENTER;");
        category.setStyle("-fx-alignment: CENTER;");
        duration.setStyle("-fx-alignment: CENTER;");

        try {
            tableView.setItems(connector.getTheHighestScores());
            tableView.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
