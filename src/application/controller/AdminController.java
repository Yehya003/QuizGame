package application.controller;

import application.DatabaseConnector;
import application.DatabaseUpdaterThread;
import application.StageManager;
import application.model.Question;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
    private JFXComboBox<String> categoryComboBox;
    @FXML
    private JFXComboBox<String> difficultyComboBox;
    @FXML
    private JFXTextField questionTextField;
    @FXML
    private JFXTextField answerTextField;
    @FXML
    private JFXTextField wrongAnswer1TextField;
    @FXML
    private JFXTextField wrongAnswer2TextField;
    @FXML
    private JFXTextField wrongAnswer3TextField;
    @FXML
    private Button buttonAddQuestion;
    @FXML
    private Button buttonDeleteSelected;
    @FXML
    private Button buttonExit;

    ObservableList<Question> observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonAddQuestion.setOnAction(e -> addQuestion());
        buttonDeleteSelected.setOnAction(e -> deleteQuestion());
        buttonExit.setOnAction(e -> exitAdminMenu(e));
        configureTable();
        configureColumns();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                populateTable();
                populateComboBoxes();
            }
        });
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

            DatabaseUpdaterThread updater = new DatabaseUpdaterThread(); //Database updater that implements Runnable object
            updater.prepareQuestionUpdate(questionBeingEdited, columnText, newText); //Prepare for this action
            Thread updaterThread = new Thread(updater);
            updaterThread.start();
        }
    }

    private void populateTable() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        ArrayList<Question> questionArrayList = databaseConnector.getAllQuestions();
        this.observableList = FXCollections.observableList(questionArrayList);

        tableView.setItems(this.observableList);
    }

    private void populateComboBoxes() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        categoryComboBox.getItems().addAll(databaseConnector.getUniqueCategoryList());
        difficultyComboBox.getItems().addAll(databaseConnector.getUniqueDifficultyList());
    }

    private void viewAllPlayers() {

    }

    private void addQuestion() {
        String question = questionTextField.getText();
        String answer = answerTextField.getText();
        String wrong1 = wrongAnswer1TextField.getText();
        String wrong2 = wrongAnswer2TextField.getText();
        String wrong3 = wrongAnswer3TextField.getText();

        String category = categoryComboBox.getValue();
        String difficulty = difficultyComboBox.getValue();

        Question questionObject = new Question(
                category,
                difficulty,
                question,
                answer,
                wrong1,
                wrong2,
                wrong3
        );
        observableList.add(questionObject);

        DatabaseUpdaterThread updater = new DatabaseUpdaterThread(); //Database updater that implements Runnable object
        updater.prepareAddingQuestion(questionObject); //Prepare for this action
        Thread updaterThread = new Thread(updater);
        updaterThread.start();
    }

    private void deleteQuestion() {
        //Will delete only locally because we don't want to accidentally delete any database questions.
        try {
            Question selectedQuestion = getQuestionPicked();
            observableList.remove(selectedQuestion);
            System.out.println("Removed: " + selectedQuestion);
        } catch (Exception e) {
            System.out.println("Removing element went wrong");
            e.printStackTrace();
        }
    }

    private Question getQuestionPicked() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public void exitAdminMenu(ActionEvent event) {
        StageManager.getInstance().getLogin();
    }
}
