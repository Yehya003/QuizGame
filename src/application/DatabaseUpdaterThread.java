package application;

import application.model.Question;

public class DatabaseUpdaterThread implements Runnable {

    private DatabaseConnector databaseConnector;

    private Question questionBeingEdited;
    private Question questionBeingAdded;
    private String columnText;
    private String newText;
    private String username;
    private String password;

    private enum Running {QUESTION_UPDATE, QUESTION_ADDITION, VALIDATE_LOGIN}

    private Running currentRun = null;

    public DatabaseUpdaterThread() {
    }

    public void prepareAddingQuestion(Question questionBeingAdded) {
        currentRun = Running.QUESTION_ADDITION;

        this.questionBeingAdded = questionBeingAdded;
    }

    public void prepareQuestionUpdate(Question questionBeingEdited, String columnText, String newText) {
        currentRun = Running.QUESTION_UPDATE;

        this.questionBeingEdited = questionBeingEdited;
        this.columnText = columnText;
        this.newText = newText;
    }

    public void prepareValidateLogin(String username, String password){
        currentRun = Running.VALIDATE_LOGIN;

        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        databaseConnector = new DatabaseConnector();
        String query;
        switch (currentRun) {
            case QUESTION_UPDATE:
                query = "UPDATE question " +
                        "SET " + columnText + " = '" + newText + "' " +
                        "WHERE Question = '" + questionBeingEdited.getQuestion() + "'";

                databaseConnector.updateDatabase(query);
                break;
            case QUESTION_ADDITION:
                query = "INSERT INTO question (" +
                        "question_id" + ", " +
                        "category" + ", " +
                        "difficulty" + ", " +
                        "question" + ", " +
                        "answer" + ", " +
                        "incorrect_answer1" + ", " +
                        "incorrect_answer2" + ", " +
                        "incorrect_answer3" + ") " +
                        "VALUES " +
                        "(null, " + "'" + //Null to use the auto_increment
                        questionBeingAdded.getCategory() + "', '" +
                        questionBeingAdded.getDifficulty() + "', '" +
                        questionBeingAdded.getQuestion() + "', '" +
                        questionBeingAdded.getAnswer() + "', '" +
                        questionBeingAdded.getIncorrect_answer1() + "', '" +
                        questionBeingAdded.getIncorrect_answer2() + "', '" +
                        questionBeingAdded.getIncorrect_answer3() + "')";

                databaseConnector.addQuestion(query);
                break;
            case VALIDATE_LOGIN:
                databaseConnector.validateLogin(username, password);
                break;
            default:
                break;
        }
        System.out.println("Query done");
    }
}
