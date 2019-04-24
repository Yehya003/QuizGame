package application.controller;

import application.StageManager;
import javafx.event.ActionEvent;

public class AdminController {

    public void updateQuestion(ActionEvent event) {

    }

    public void exitAdminMenu(ActionEvent event) {
        StageManager.getInstance().getLogin();
    }
}
