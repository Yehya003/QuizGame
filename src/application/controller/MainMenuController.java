package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends ToggleGroup implements Initializable {

    @FXML
    private JFXComboBox<String> gameMode;

    @FXML
    private JFXComboBox<String> difficulty;
    @FXML
    private JFXButton art;

    @FXML
    private JFXButton tvSeries;

    @FXML
    private JFXButton movies;

    @FXML
    private JFXButton music;

    @FXML
    private JFXButton politics;

    @FXML
    private JFXButton animal;

    @FXML
    private JFXButton cars;

    @FXML
    private JFXButton computerScience;
    @FXML
    private JFXButton random;

    @FXML
    private JFXButton sports;

    @FXML
    private JFXButton vGAme;

    @FXML
    private JFXButton history;

    private JFXButton[] myButtons;
    private String previouslySelected, selectedCategory, theGameMode, theDifficulty;

    public void setSelection(ActionEvent event) {
        // getting the name of the button to send it later to the next seen for game play

        Glow glow = new Glow();
        JFXButton btn = (JFXButton) event.getSource();
        if (!btn.getId().equals(previouslySelected)) {
            for (JFXButton button : myButtons) {
                if (btn.getId().equals(button.getId())) {
                    button.setEffect(glow);
                }
                if (previouslySelected != null) {
                    if (previouslySelected.equals(button.getId()))
                        button.setEffect(null);
                }
            }
            previouslySelected = btn.getId();
            selectedCategory = previouslySelected;
        }
    }


    public void unSelect(MouseEvent event) {
        if (event.getClickCount() == 1) {
            for (JFXButton button : myButtons) {
                button.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myButtons = new JFXButton[]{history, vGAme, sports, random, computerScience,
                cars, animal, politics, music, movies, tvSeries, art};

        //Adding Items in combo boxes
        gameMode.getItems().add("Time based");
        gameMode.getItems().add("Exit when one wrong");
        difficulty.getItems().add("Easy");
        difficulty.getItems().add("Hard");

        //Some field validators for the combo boxes
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Required..");
        gameMode.getValidators().add(validator);
        difficulty.getValidators().add(validator);
        gameMode.getValidators();
        difficulty.getValidators();

    }

    public void getSelectedFromComboBox() {
        //getting the comboBoxes selected items and depositing it's value with the field variables
        theGameMode = gameMode.getSelectionModel().getSelectedItem();
        theDifficulty = difficulty.getSelectionModel().getSelectedItem();
    }

    public void nextBtnPressed() {
        //checking if all the required information are given by the user otherwise show validators
        if (!(selectedCategory == null || theGameMode == null || theDifficulty == null)) {
            System.out.println();
            // call a method from playScene to pass info
            // call stageManager and move to playScene
        } else {
            difficulty.validate();
            gameMode.validate();
            gameMode.valueProperty().addListener((Observable, oldValue, newValue) -> gameMode.resetValidation());
            difficulty.valueProperty().addListener((Observable, oldValue, newValue) -> difficulty.resetValidation());
            // add validators on the buttons so they show or even an emotion to indicate that they need to be chosen
            //preliminary 
        }
    }
}