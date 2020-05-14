package Main;

import DataBaseUtil.DBOrder;
import DataBaseUtil.DatabaseUtil;
import DataBaseUtil.SerialNum;
import Util.AlertBox;
import Util.ConfirmBox;
import Util.HandleError;
import Util.PasswordBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntryFormController {

    private double xOffset = 0;
    private double yOffset = 0;

    public Label topLabel;
    public ImageView backButton;
    public JFXTextField nameTextField;
    public JFXTextField emailTextField;
    public JFXTextArea suggestionsTextArea;
    public JFXButton resetButton;
    public JFXButton submitButton;
    public AnchorPane root;

    Stage stage;
    String title;

    public void initData(Stage stage, String title) {
        this.stage = stage;
        this.title = title;
        root.setPrefSize(700, 417);
        initialize();
    }

    public void initialize() {

        topLabel.setText("Check In Form for " + title);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // setting the back button image
        backButton.setImage(new Image(FinalConstants.backWhite.toString()));
        backButton.setOnMouseEntered(event -> {
            backButton.setImage(new Image(FinalConstants.backBlack.toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        backButton.setOnMouseExited(event -> {
            backButton.setImage(new Image(FinalConstants.backWhite.toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        backButton.setOnMouseClicked(event -> backToMain());
        resetButton.setOnMouseClicked(event -> clearScreen(false));
        submitButton.setOnMouseClicked(event -> addEntry());

    }

    /**
     * Clear the screen
     * @param system true if system is calling, false if user is calling
     */
    private void clearScreen(boolean system) {

        if (nameTextField.getText().equals("") && emailTextField.getText().equals("") && suggestionsTextArea.getText().equals(""))
            return;

        if (system || ConfirmBox.display("Confirm",
                "Are you sure you want to clear the screen? Your response will not be recorded.",
                "Yes", "No")) {
            nameTextField.clear();
            emailTextField.clear();
            suggestionsTextArea.clear();
        }
    }

    /**
     * Add entry to database
     */
    private void addEntry() {

        if (nameTextField.getText().equals("")) {
            AlertBox.display("Error", "Please enter your name.");
            return;
        }

        Entry entry = new Entry(SerialNum.getSerialNum(DBOrder.ENTRIES));
        entry.setName(nameTextField.getText());
        entry.setEmail(emailTextField.getText());
        entry.setSuggestions(suggestionsTextArea.getText());
        entry.setCheckInDate(LocalDate.now());
        entry.setTitle(title);
        try {
            DatabaseUtil.AddEntry(entry);
            clearScreen(true);
            AlertBox.display("Success", "Thank you for your time! Your response has been recorded.");
        } catch (SQLException e) {
            AlertBox.display("Error", "Could not recorded response.");
        }
    }

    private void backToMain() {
        if (!PasswordBox.display())
            return;

        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream fileInputStream = getClass().getResourceAsStream(Main.fxmlPath + "MainScreen.fxml");
            Parent parent = loader.load(fileInputStream);

            MainScreen mainScreen = loader.getController();
            mainScreen.initData(stage);

            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            AlertBox.display("Error", "Loading Window Error！");
            new HandleError(Main.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        }
    }
}
