package Main;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreen {

    Stage stage;

    public ImageView adminButton;
    public ImageView powerButton;

    public void initData(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {

        adminButton.setImage(new Image(FinalConstants.adminInputWhite.toURI().toString()));
        powerButton.setImage(new Image(FinalConstants.powerInputWhite.toURI().toString()));
        adminButton.getStyleClass().add("adminIcon");
        powerButton.getStyleClass().add("powerIcon");

        adminButton.setOnMouseEntered(event -> {
            adminButton.setImage(new Image(FinalConstants.adminInputBlack.toURI().toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        adminButton.setOnMouseExited(event -> {
            adminButton.setImage(new Image(FinalConstants.adminInputWhite.toURI().toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        powerButton.setOnMouseEntered(event -> {
            powerButton.setImage(new Image(FinalConstants.powerInputBlack.toURI().toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        powerButton.setOnMouseExited(event -> {
            powerButton.setImage(new Image(FinalConstants.powerInputWhite.toURI().toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });

    }
}
