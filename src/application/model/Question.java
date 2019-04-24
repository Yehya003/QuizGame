package application.model;

public class Question {

    private int question_id;
    private String category;
    private String difficulty;
    private String question;
    private String answer;
    private String incorrect_answer1;
    private String incorrect_answer2;
    private String incorrect_answer3;

    public Question(int question_id, String category, String difficulty, String question, String answer, String incorrect_answer1, String incorrect_answer2, String incorrect_answer3) {
        this.question_id = question_id;
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.answer = answer;
        this.incorrect_answer1 = incorrect_answer1;
        this.incorrect_answer2 = incorrect_answer2;
        this.incorrect_answer3 = incorrect_answer3;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getIncorrect_answer1() {
        return incorrect_answer1;
    }

    public String getIncorrect_answer2() {
        return incorrect_answer2;
    }

    public String getIncorrect_answer3() {
        return incorrect_answer3;
    }

    public int getQuestion_id() {
        return question_id;
    }
}

