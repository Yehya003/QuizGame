package application.controller;

import application.DatabaseConnector;
import application.StageManager;
import application.model.Question;
import application.model.Quiz;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
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
    private JFXButton next;
    @FXML
    private JFXButton previous;
    @FXML
    private Label questionLabel;
    @FXML
    private Label timeTakenLabel;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private JFXRadioButton question1isCorrectRadio;
    @FXML
    private JFXRadioButton question2isCorrectRadio;
    @FXML
    private JFXRadioButton question3isCorrectRadio;
    @FXML
    private JFXRadioButton question4isCorrectRadio;
    @FXML
    private JFXRadioButton question5isCorrectRadio;
    @FXML
    private JFXRadioButton question6isCorrectRadio;
    @FXML
    private JFXRadioButton question7isCorrectRadio;
    @FXML
    private JFXRadioButton question8isCorrectRadio;
    @FXML
    private JFXRadioButton question9isCorrectRadio;
    @FXML
    private JFXRadioButton question10isCorrectRadio;
    @FXML
    private AnchorPane endGameAnchor;
    @FXML
    private AnchorPane gameAnchor;

    private Quiz quiz;
    private int quizCounter = 0;
    private ArrayList<Question> questions;
    private boolean isCorrectAnswerSelected;
    private Instant quizStart;
    private Instant quizEnd;
    private Duration quizDuration;
    SecureRandom random = new SecureRandom();

    public void scoreKeeping(boolean isCorrect) { //adds 1 to score if correct answer is selected
        if (isCorrect) {
            quiz.setScore(quiz.getScore() + 1);
        }
    }

    public String calculateQuizDuration() { //Checks duration of quiz
        quizEnd = Instant.now();
        quizDuration = Duration.between(quizStart, quizEnd);
        long quizDurationLong = quizDuration.getSeconds();
        quiz.setDuration(quizDurationLong); //saves duration in seconds to quiz object
        return quizDurationLong / 60 + ":" + quizDurationLong % 60; // returns string in MINUTES:SECONDS
    }

    public int getNewQuizID() { //Gets the highest quiz id from database and adds 1 for new quiz
        DatabaseConnector connector = new DatabaseConnector();
        int quizID = connector.getLastestQuizID()+1;
        return quizID;
    }

    public void toMainMenu(){
        StageManager.getInstance().getMainMenu();  //returns to main menu
    }

    public void setEndGameDisplay(int quizCounter,boolean isCorrectAnswerSelected){
        switch (quizCounter){
            case 0:
                question1isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 1:
                question2isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 2:
                question3isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 3:
                question4isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 4:
                question5isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 5:
                question6isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 6:
                question7isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 7:
                question8isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 8:
                question9isCorrectRadio.setSelected(isCorrectAnswerSelected);
            case 9:
                question10isCorrectRadio.setSelected(isCorrectAnswerSelected);
            default:

        }
    }

    public void finishGame() { //Ends the game
        String duration = calculateQuizDuration();
        int score = quiz.getScore();
        quiz.setQuiz_id(getNewQuizID()); //Saves latest quiz_id to object, will later also save to database
        timeTakenLabel.setText("Final Score: " + score + "/" + quiz.getQuestions().size() + " with duration: " + duration);
        gameAnchor.toBack();
        endGameAnchor.toFront();
        //StageManager.getInstance().getMainMenu();  //returns to main menu
    }

    public void nextOrPreviousQuestion(ActionEvent event) {
        boolean isAnswerCorrect = isAnswerCorrect();
        scoreKeeping(isAnswerCorrect); //checks if selected answer is correct
        setEndGameDisplay(quizCounter,isAnswerCorrect);//calls to set the end game radio buttons selected or not
        /*  Previous button disabled, no need to check event as of now
        if (event.getSource().equals(next)) {
            if (quizCounter == questions.size() - 2) {
                next.setText("Finish");
                quizCounter++;
            } else if (quizCounter == questions.size() - 1) {
                finishGame();
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
        */
        if (quizCounter == questions.size() - 2) {
            next.setText("Finish"); //changes NEXT button text to finish for the final question
            quizCounter++;
        } else if (quizCounter == questions.size() - 1) {
            finishGame(); //terminates game
        } else {
            quizCounter++;
        }
        displayQuestion(quizCounter);
        try {
            answers.getSelectedToggle().setSelected(false);
        } catch (Exception e) {
            System.out.println("No answer Selected");
        }
    }

    public boolean isAnswerCorrect() {
        String selectedAnswer = questions.get(quizCounter).getAnswer();
        try {
            isCorrectAnswerSelected = answers.getSelectedToggle().toString().contains(selectedAnswer);
            //If answer is selected will return true if correct, false if incorrect
        } catch (Exception e) {
            //if no answer is selected, make sure it will return false
            isCorrectAnswerSelected = false;
            System.out.println("No answer Selected");
        }
        return isCorrectAnswerSelected;
    }

    public void displayQuestion(int question_id) {
        progressBar.setProgress((double) question_id / (questions.size() - 1));
        questionLabel.setText(questions.get(question_id).getQuestion());
        ArrayList<String> answers = new ArrayList<>();
        answers.add(questions.get(question_id).getAnswer());
        answers.add(questions.get(question_id).getIncorrect_answer1());
        answers.add(questions.get(question_id).getIncorrect_answer2());
        answers.add(questions.get(question_id).getIncorrect_answer3());
        for (int i = 0; i <4; i++) {
            int number = random.nextInt(4-i);
            switch (i){
                case 0:
                    rb1.setText(answers.get(number));
                case 1:
                    rb2.setText(answers.get(number));
                case 2:
                    rb3.setText(answers.get(number));
                case 3:
                    rb4.setText(answers.get(number));
            }
            answers.remove(number);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quiz = application.controller.MainMenuController.quiz;
        quizCounter = 0;
        questions = quiz.getQuestions();
        displayQuestion(quizCounter);
        quizStart = Instant.now();

    }
}
