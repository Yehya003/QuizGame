package sample.Controller;

import javafx.event.ActionEvent;
import sample.StageManager;

public class AdminController {



    public void updateQustion(ActionEvent event){

    }

    public void exitAdminMenu(ActionEvent event){

        StageManager.getInstance().getLogin();
    }
}
