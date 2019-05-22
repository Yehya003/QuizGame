package application.model;

import application.CurrentAccountSingleton;

import java.sql.Date;
import java.util.ArrayList;

public class Quiz {

    private int quiz_id;
    private String category;
    private int score;
    private int duration;
    private Date date;
    private String userName;
    private ArrayList<Question> questions;

    public Quiz(String category, ArrayList<Question> questions) {
        this.category = category;
        this.questions = questions;
        this.userName = CurrentAccountSingleton.getInstance().getAccount().getUsername();
    }

    public Quiz(String userName, int score, String category, int duration) {
        this.userName = userName;
        this.score = score;
        this.category = category;
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDuration() { return duration; }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quiz_id=" + quiz_id +
                ", category='" + category + '\'' +
                ", score=" + score +
                ", duration=" + duration +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                ", questions=" + questions +
                '}';
    }
}

