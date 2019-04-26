package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Question {

    private SimpleIntegerProperty question_id = new SimpleIntegerProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty difficulty = new SimpleStringProperty();
    private SimpleStringProperty question = new SimpleStringProperty();
    private SimpleStringProperty answer = new SimpleStringProperty();
    private SimpleStringProperty incorrect_answer1 = new SimpleStringProperty();
    private SimpleStringProperty incorrect_answer2 = new SimpleStringProperty();
    private SimpleStringProperty incorrect_answer3 = new SimpleStringProperty();

    public Question() {

    }

    public Question(int question_id, String category, String difficulty, String question, String answer, String incorrect_answer1, String incorrect_answer2, String incorrect_answer3) {
        this.question_id = new SimpleIntegerProperty(question_id);
        this.category = new SimpleStringProperty(category);
        this.difficulty = new SimpleStringProperty(difficulty);
        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
        this.incorrect_answer1 = new SimpleStringProperty(incorrect_answer1);
        this.incorrect_answer2 = new SimpleStringProperty(incorrect_answer2);
        this.incorrect_answer3 = new SimpleStringProperty(incorrect_answer3);
    }

    public int getQuestion_id() {
        return question_id.get();
    }

    public void setQuestion_id(int question_id) {
        this.question_id.set(question_id);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getQuestion() {
        return question.get();
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getAnswer() {
        return answer.get();
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
    }

    public String getIncorrect_answer1() {
        return incorrect_answer1.get();
    }

    public void setIncorrect_answer1(String incorrect_answer1) {
        this.incorrect_answer1.set(incorrect_answer1);
    }

    public String getIncorrect_answer2() {
        return incorrect_answer2.get();
    }

    public void setIncorrect_answer2(String incorrect_answer2) {
        this.incorrect_answer2.set(incorrect_answer2);
    }

    public String getIncorrect_answer3() {
        return incorrect_answer3.get();
    }

    public void setIncorrect_answer3(String incorrect_answer3) {
        this.incorrect_answer3.set(incorrect_answer3);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", category=" + category +
                ", difficulty=" + difficulty +
                ", question=" + question +
                ", answer=" + answer +
                ", incorrect_answer1=" + incorrect_answer1 +
                ", incorrect_answer2=" + incorrect_answer2 +
                ", incorrect_answer3=" + incorrect_answer3 +
                '}';
    }
}

