package sample;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class LoginController {
    ScaleTransition effect = new ScaleTransition(Duration.millis(1500));
    @FXML
    private AnchorPane logInPane;

    public void hooverOverAnchorpane() {
        effect.setNode(logInPane);
        effect.setByX(.1);
        effect.setByY(.1);
        effect.setCycleCount(2);
        effect.setAutoReverse(true);
        effect.play();
    }
    // background.fitHeightProperty().bind(mainPane.heightProperty());
    // background.fitWidthProperty().bind(mainPane.widthProperty());
}