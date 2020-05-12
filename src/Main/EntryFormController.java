package Main;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EntryFormController {
    Stage stage;

    @FXML
    ImageView backButton;

    public void initData(Stage stage) {
        this.stage = stage;
        initialize();
    }

    public void initialize() {

        backButton.setImage(new Image(FinalConstants.backWhite.toURI().toString()));

        backButton.setOnMouseEntered(event -> {
            backButton.setImage(new Image(FinalConstants.backBlack.toURI().toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        backButton.setOnMouseExited(event -> {
            backButton.setImage(new Image(FinalConstants.backWhite.toURI().toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });

    }
}
