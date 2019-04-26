package application;

import application.model.Question;

public class DatabaseUpdater implements Runnable {

    private DatabaseConnector databaseConnector;

    private Question questionBeingEdited;
    private String columnText;
    private String newText;

    private enum Running {QUESTION_UPDATE, QUESTION_FETCH}
    private Running currentRun = null;

    public DatabaseUpdater() {
    }

    public void prepareQuestionUpdate(Question questionBeingEdited, String columnText, String newText) {
        currentRun = Running.QUESTION_UPDATE;

        this.questionBeingEdited = questionBeingEdited;
        this.columnText = columnText;
        this.newText = newText;
    }

    @Override
    public void run() {
        switch (currentRun) {
            case QUESTION_UPDATE:
                String query = "UPDATE question " +
                        "SET " + columnText + " = '" + newText + "' " +
                        "WHERE Question = '" + questionBeingEdited.getQuestion() + "'";

                databaseConnector = new DatabaseConnector();
                databaseConnector.updateDatabase(query);
                break;
            case QUESTION_FETCH:
                //For future thread running
                break;
            default:
                System.out.println("Nothing initialized to run on the thread");
                break;

        }
    }
}
