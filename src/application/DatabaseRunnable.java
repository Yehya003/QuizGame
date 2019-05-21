package application;

import application.model.Question;
import application.utils.FileUtils;
import javafx.application.Platform;
import javafx.stage.Stage;

public class DatabaseRunnable implements Runnable {

    private enum Running {QUESTION_UPDATE, QUESTION_ADDITION, VALIDATE_LOGIN}
    private DatabaseConnector databaseConnector;
    private Question questionBeingEdited;
    private Question questionBeingAdded;
    private String columnText;
    private String newText;
    private String username;
    private String password;
    private boolean rememberMe;

    private Stage currentStage;

    private Running currentRun = null;

    public DatabaseRunnable() {
        this.currentStage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
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

    public void prepareValidateLogin(String username, String password, boolean rememberMe) {
        currentRun = Running.VALIDATE_LOGIN;

        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            StageManager.getInstance().showProgressBar(currentStage);
        });
        databaseConnector = new DatabaseConnector();
        String query;
        switch (currentRun) {
            case QUESTION_UPDATE: {
                query = "UPDATE question " +
                        "SET " + columnText + " = '" + newText + "' " +
                        "WHERE Question = '" + questionBeingEdited.getQuestion() + "'";

                databaseConnector.updateDatabase(query);
                break;
            }
            case QUESTION_ADDITION: {
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
            }
            case VALIDATE_LOGIN: {
                boolean successfulLogin = databaseConnector.validateLogin(username, password);
                if (successfulLogin && rememberMe) {
                    FileUtils.writeObject(FileUtils.accountFilePath, CurrentAccountSingleton.getInstance().getAccount());
                }
                break;
            }
            default:
                break;
        }
        Platform.runLater(() -> {
            StageManager.getInstance().stopProgressBar();
        });
        System.out.println("Query done");
    }
}
