package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sample.DBConnection;
import sample.Model.Question;
import sample.Model.Quiz;

import java.sql.SQLException;
import java.util.ArrayList;

public class GameController {
    public DBConnection connector = new DBConnection();

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
    Label questionLabel;


    private Quiz quiz;
    private int quizCounter = 0;
    private ArrayList<Question> questions;

    public GameController() throws SQLException {
    }

    public void nextOrPreviousQuestion(ActionEvent event) {

        System.out.println(event.getSource());
        System.out.println(event.toString());

        if (event.getSource().equals(next)) {
            if (quizCounter == questions.size() - 1) {

                next.setText("Finish");
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


        questionLabel.setText(questions.get(quizCounter).getQuestion());
        rb1.setText(questions.get(quizCounter).getAnswer());
        rb2.setText(questions.get(quizCounter).getIncorrect_answer1());
        rb3.setText(questions.get(quizCounter).getIncorrect_answer2());
        rb4.setText(questions.get(quizCounter).getIncorrect_answer3());


    }

    public void populateQuiz(ActionEvent event) {
        String category;
        if (event.getSource().equals(history)) {
            category = "history";
        } else if (event.getSource().equals(animals)) {
            category = "animals";
        } else {
            category = "sports";
        }

        quiz = new Quiz(category, connector.QuizFill(category));
        quizCounter = 0;

        questions = quiz.getQuestions();

        questionLabel.setText(questions.get(quizCounter).getQuestion());
        rb1.setText(questions.get(quizCounter).getAnswer());
        rb2.setText(questions.get(quizCounter).getIncorrect_answer1());
        rb3.setText(questions.get(quizCounter).getIncorrect_answer2());
        rb4.setText(questions.get(quizCounter).getIncorrect_answer3());


    }


}
