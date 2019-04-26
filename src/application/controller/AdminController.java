package application.controller;

import application.DatabaseConnector;
import application.DatabaseUpdater;
import application.StageManager;
import application.model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableView<Question> tableView;
    @FXML
    private TableColumn<Question, String> categoryColumn;
    @FXML
    private TableColumn<Question, String> difficultyColumn;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, String> answerColumn;
    @FXML
    private TableColumn<Question, String> incorrectAnswer1Column;
    @FXML
    private TableColumn<Question, String> incorrectAnswer2Column;
    @FXML
    private TableColumn<Question, String> incorrectAnswer3Column;
    @FXML
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonExit.setOnAction(e -> exitAdminMenu(e));

        configureTable();
        configureColumns();
        populateTable();
    }

    private void configureTable() {
        tableView.setEditable(true);
    }

    private void configureColumns() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        incorrectAnswer1Column.setCellValueFactory(new PropertyValueFactory<>("incorrect_answer1"));
        incorrectAnswer2Column.setCellValueFactory(new PropertyValueFactory<>("incorrect_answer2"));
        incorrectAnswer3Column.setCellValueFactory(new PropertyValueFactory<>("incorrect_answer3"));

        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        difficultyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        questionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        answerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        incorrectAnswer1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        incorrectAnswer2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        incorrectAnswer3Column.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryColumn.setEditable(false);
        difficultyColumn.setEditable(false);

        categoryColumn.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setCategory(e.getNewValue());
        });
        difficultyColumn.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setDifficulty(e.getNewValue());
        });
        questionColumn.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setQuestion(e.getNewValue());
        });
        answerColumn.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setAnswer(e.getNewValue());
        });
        incorrectAnswer1Column.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setIncorrect_answer1(e.getNewValue());
        });
        incorrectAnswer2Column.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setIncorrect_answer2(e.getNewValue());
        });
        incorrectAnswer3Column.setOnEditCommit(e -> {
            onEditCommit(e);
            e.getRowValue().setIncorrect_answer3(e.getNewValue());
        });
    }

    /**
     * Triggered every time there is an edit on the table view that is finished and committed.
     */
    private void onEditCommit(TableColumn.CellEditEvent<Question, String> productStringCellEditEvent) {
        //If the new value is different than the old value
        if (!productStringCellEditEvent.getNewValue().equals(productStringCellEditEvent.getOldValue())) {
            Question questionBeingEdited = productStringCellEditEvent.getRowValue(); //Original object
            String columnText = productStringCellEditEvent.getTableColumn().getText(); //Column that is being changed
            String newText = productStringCellEditEvent.getNewValue(); //New text that we want to save

            DatabaseUpdater updater = new DatabaseUpdater(); //Database updater that implements Runnable object
            updater.prepareQuestionUpdate(questionBeingEdited, columnText, newText); //Prepare for this action
            Thread updaterThread = new Thread(updater);
            updaterThread.start();
        }
    }

    private void populateTable() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        ArrayList<Question> questionArrayList = databaseConnector.getAllQuestions();
        ObservableList<Question> observableList = FXCollections.observableList(questionArrayList);

        tableView.setItems(observableList);
    }

    private void viewAllPlayers() {

    }

    private void addQuestion() {

    }

    private Question getQuestionPicked() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public void exitAdminMenu(ActionEvent event) {
        StageManager.getInstance().getLogin();
    }
}
