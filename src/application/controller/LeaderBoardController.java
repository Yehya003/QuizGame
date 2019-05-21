package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.model.Quiz;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {

    @FXML
    private TableView<Quiz> tableView;

    @FXML
    private JFXCheckBox top10;

    @FXML
    private TableColumn<Quiz, String> username;

    @FXML
    private TableColumn<Quiz, Integer> score;

    @FXML
    private TableColumn<Quiz, String> category;

    @FXML
    private TableColumn<Quiz, Integer> duration;
    @FXML
    private AnchorPane filterPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXComboBox<String> from;
    @FXML
    private JFXComboBox<String> to;
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private PieChart pieChart;
    boolean populated = false;
    XYChart.Series<String, Integer> myChart = new XYChart.Series<>();
    DatabaseConnector connector = new DatabaseConnector();

    public void mainMenuPressed() {
        StageManager.getInstance().getMainMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setting the column input that corresponds to the variables of the quiz object
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        //Setting the alignment to the middle
        username.setStyle("-fx-alignment: CENTER;");
        score.setStyle("-fx-alignment: CENTER;");
        category.setStyle("-fx-alignment: CENTER;");
        duration.setStyle("-fx-alignment: CENTER;");

        try {
            tableView.setItems(new DatabaseConnector().getTheHighestScores());
            tableView.setEditable(false);
            //getting the return value of the method and saving it to the XYChart
            myChart = connector.getDataForStatistics();
            //populating the pie chart from the database connector after it returns observable list full of data
            pieChart.setData(connector.getPlayedCategoriesRatio());
            //plugging in the data from the XYChart to view it in the line chart
            lineChart.getData().add(myChart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterPressed() {
        GaussianBlur blur = new GaussianBlur();
        mainPane.setEffect(blur);
        // mainPane.setOpacity(0.71);
        populateComboBox();
        filterPane.setLayoutY(190);
        filterPane.setVisible(true);
        mainPane.setDisable(true);
    }

    public void populateComboBox() {
        if (populated == false) {
            for (int i = 0; i < 11; i++) {
                from.getItems().add(String.valueOf(i));
                to.getItems().add(String.valueOf(i));
                populated = true;
            }
        }
    }

    public void okBtnPressed() {
        try {
            if (top10.isPressed()) {
                tableView.setItems(connector.smartFilterData(from.getSelectionModel().getSelectedItem(), to.getSelectionModel().getSelectedItem(), true));

            } else {
                tableView.setItems(connector.smartFilterData(from.getSelectionModel().getSelectedItem(), to.getSelectionModel().getSelectedItem(), false));
            }
            filterPane.setVisible(false);
            mainPane.setEffect(null);
            mainPane.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancellBtnPressed() {
        filterPane.setVisible(false);
        mainPane.setDisable(false);
        mainPane.setEffect(null);
    }
    public void editBtnPressed() {
        StageManager.getInstance().getEditPlayer();
    }
    public void logOutPressed() {
        StageManager.getInstance().getLogin();
    }


}
