package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.model.Question;
import application.model.Quiz;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController extends ToggleGroup implements Initializable {

    @FXML
    private JFXComboBox<String> gameMode;
    @FXML
    private JFXComboBox<String> difficulty;
    @FXML
    private JFXButton history;
    @FXML
    private JFXButton videoGames;
    @FXML
    private JFXButton art;
    @FXML
    private JFXButton politics;
    @FXML
    private JFXButton tvSeries;
    @FXML
    private JFXButton random;
    @FXML
    private JFXButton computerScience;
    @FXML
    private JFXButton sports;
    @FXML
    private JFXButton geography;
    @FXML
    private JFXButton animals;
    @FXML
    private JFXButton movies;
    @FXML
    private JFXButton music;
    @FXML
    private JFXButton cars;

    private JFXButton[] myButtons;
    private String previouslySelected, selectedCategory, theGameMode, theDifficulty;
    public static Quiz quiz;
    private ArrayList<Question> possibleQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myButtons = new JFXButton[]{cars, music, movies, animals, art, computerScience, history
                , politics, videoGames, tvSeries, sports, geography, random}; //

        //Adding Items in combo boxes
        gameMode.getItems().add("Time based");
        gameMode.getItems().add("Exit when one wrong");
        difficulty.getItems().add("Easy");
        difficulty.getItems().add("Hard");

        //Some field validators for the combo boxes
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Required..");
        gameMode.getValidators().add(validator);
        difficulty.getValidators().add(validator);
        gameMode.getValidators();
        difficulty.getValidators();

    }

    public void setSelection(ActionEvent event) {
        // getting the name of the button to send it later to the next seen for game play
        Glow glow = new Glow();
        JFXButton btn = (JFXButton) event.getSource();
        if (!btn.getId().equals(previouslySelected)) {
            for (JFXButton button : myButtons) {
                if (btn.getId().equals(button.getId())) {
                    button.setEffect(glow);
                }
                if (previouslySelected != null) {
                    if (previouslySelected.equals(button.getId())) {
                        button.setEffect(null);
                    }
                }
            }
            previouslySelected = btn.getId();
            selectedCategory = previouslySelected;
        }
    }

    public void unSelect(MouseEvent event) {
        if (event.getClickCount() == 1) {
            for (JFXButton button : myButtons) {
                button.setDisable(false);
            }
        }
    }

    public void getSelectedFromComboBox() {
        //getting the comboBoxes selected items and depositing its value with the field variables
        theGameMode = gameMode.getSelectionModel().getSelectedItem();
        theDifficulty = difficulty.getSelectionModel().getSelectedItem();
    }

    public void populateQuiz() { //Selects 10 random questions from the corresponding possible questions
        SecureRandom random = new SecureRandom();
        possibleQuestions = getPossibleQuestions();
        ArrayList<Question> questionsForQuiz = new ArrayList<>();
        int quizAmountOfQuestions = 10;
        for (int i = 0; i < quizAmountOfQuestions; i++) {
            int rand = random.nextInt(possibleQuestions.size()); //nextInt has exclusive upper bound so no need for -1
            Question questionToAdd = possibleQuestions.get(rand);
            if (questionsForQuiz.contains(questionToAdd)) {
                i--;//If it has already been picked, do not add it and run the iteration of the loop again
            } else {
                questionsForQuiz.add(possibleQuestions.get(rand));
                possibleQuestions.remove(rand);
            }
        }
        quiz = new Quiz(selectedCategory, questionsForQuiz);
    }

    public ArrayList<Question> getPossibleQuestions() {   //Gets all possible, corresponding questions from database
        DatabaseConnector connector = new DatabaseConnector();
        if (selectedCategory.equals("random")) {
            selectedCategory = "computerScience";
        }//TODO actually random
        possibleQuestions = connector.getQuestionsFromDB(selectedCategory, theDifficulty);
        return possibleQuestions;
    }

    public void nextBtnPressed() {
        //checking if all the required information are given by the user otherwise show validators
        if (!(selectedCategory == null || theGameMode == null || theDifficulty == null)) {
            System.out.println();
            // call a method from playScene to pass info
            // call stageManager and move to playScene
            startGame();
        } else {
            difficulty.validate();
            gameMode.validate();
            gameMode.valueProperty().addListener((Observable, oldValue, newValue) -> gameMode.resetValidation());
            difficulty.valueProperty().addListener((Observable, oldValue, newValue) -> difficulty.resetValidation());
            // add validators on the buttons so they show or even an emotion to indicate that they need to be chosen
            //preliminary 
        }
    }

    private void startGame() {
        //Use gameMode as a way to access the current stage
        new Thread(() -> {
            Platform.runLater(() -> StageManager.getInstance().showProgressBar((Stage) gameMode.getScene().getWindow()));
            populateQuiz();
            StageManager.getInstance().getGame();
            Platform.runLater(() -> StageManager.getInstance().stopProgressBar());
        }).start();
    }

    public void leaderBoardBtnPressed() {
        StageManager.getInstance().getLeaderBoard();
    }

    public void editBtnPressed() {
        StageManager.getInstance().getEditPlayer();
    }

    public void logOutPressed() {
        StageManager.getInstance().getLogin();
    }
}