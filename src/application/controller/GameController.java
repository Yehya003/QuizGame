package application.controller;

import application.model.Question;
import application.model.Quiz;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {



    @FXML
    ToggleGroup answers;
    @FXML
    RadioButton rb1;
    @FXML
    RadioButton rb2;
    @FXML
    RadioButton rb3;
    @FXML
    RadioButton rb4;
    @FXML
    private JFXButton animals;
    @FXML
    private JFXButton history;
    @FXML
    private JFXButton sports;
    @FXML
    private JFXButton next;
    @FXML
    private JFXButton previous;
    @FXML
    private JFXButton finish;
    @FXML
    private Label questionLabel;
    @FXML
    private Label finalScore;
    @FXML
    private JFXProgressBar progressBar;

    private Quiz quiz;
    private int quizCounter = 0;
    private ArrayList<Question> questions;
    private boolean isCorrectAnswerSelected;

    /*
    public void scoreKeeping(boolean rightOrWrong){
        if(rightOrWrong){
            quiz.setScore(quiz.getScore()+1);
        }
    }
    */
    public void finishGame() {
        if(isAnswerCorrect()){
            quiz.setScore(quiz.getScore()+1);
        }
        int score = quiz.getScore();
        finalScore.setText("Final Score: "+score+"/"+quiz.getQuestions().size());
    }

    public void nextOrPreviousQuestion(ActionEvent event) {
        //System.out.println(event.getSource());
        //System.out.println(event.toString());
        if (isAnswerCorrect()) {
            quiz.setScore(quiz.getScore() + 1);
        }
        if (event.getSource().equals(next)) {
            if (quizCounter == questions.size() - 1) {
                return;
                //next.setText("Finish");
            } else {
                quizCounter++;
            }
        } else {
            if (quizCounter == 0) {
                return;
            } else {
                quizCounter--;
            }
        }
        displayQuestion(quizCounter);
        try {
            answers.getSelectedToggle().setSelected(false);
        } catch (Exception e) {

        }
    }

    public boolean isAnswerCorrect() {
        String selectedAnswer = questions.get(quizCounter).getAnswer();
        //System.out.println(answers.selectedToggleProperty());
        try {
            isCorrectAnswerSelected = answers.getSelectedToggle().toString().contains(selectedAnswer);
        } catch (Exception e) {

        }
        //System.out.println(answers.getSelectedToggle().toString().contains(selectedAnswer));
        return isCorrectAnswerSelected;
    }

    public void displayQuestion(int question_id) {
        progressBar.setProgress((double) question_id / (questions.size() - 1));
        questionLabel.setText(questions.get(question_id).getQuestion());
        rb1.setText(questions.get(question_id).getAnswer());
        rb2.setText(questions.get(question_id).getIncorrect_answer1());
        rb3.setText(questions.get(question_id).getIncorrect_answer2());
        rb4.setText(questions.get(question_id).getIncorrect_answer3());
    }

    /*
    public void populateQuiz(ActionEvent event) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        String category;
        if (event.getSource().equals(history)) {
            category = "history";
        } else if (event.getSource().equals(animals)) {
            category = "animals";
        } else {
            category = "sports";
        }
        quiz = new Quiz(category, databaseConnector.QuizFill(category));
        quizCounter = 0;
        questions = quiz.getQuestions();
        displayQuestion(quizCounter);
    }
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quiz = application.controller.MainMenuController.quiz;
        quizCounter = 0;
        questions = quiz.getQuestions();
        displayQuestion(quizCounter);

    }
}
