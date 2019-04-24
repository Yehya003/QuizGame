package application.model;

public class Question {

    private final int question_id = 0;
    private String category;
    private String difficulty;
    private String question;
    private String answer;
    private String incorrect_answer1;
    private String incorrect_answer2;
    private String incorrect_answer3;

    public Question(String category, String difficulty, String question, String answer, String incorrect_answer1, String incorrect_answer2, String incorrect_answer3) {
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
}

