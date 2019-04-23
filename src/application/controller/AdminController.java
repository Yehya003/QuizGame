package application.controller;

import javafx.event.ActionEvent;
import application.StageManager;

public class AdminController {



    public void updateQustion(ActionEvent event){

    }

    public void exitAdminMenu(ActionEvent event){

        StageManager.getInstance().getLogin();
    }
}
