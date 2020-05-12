package Main;

import DataBaseUtil.DBOrder;
import DataBaseUtil.DatabaseUtil;
import DataBaseUtil.SerialNum;
import Util.AlertBox;
import Util.ConfirmBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EntryFormController {

    public ImageView backButton;
    public JFXTextField nameTextField;
    public JFXTextField emailTextField;
    public JFXTextArea suggestionsTextArea;
    public JFXButton resetButton;
    public JFXButton submitButton;

    Stage stage;
    String title;

    public void initData(Stage stage, String title) {
        this.stage = stage;
        this.title = title;
        initialize();
    }

    public void initialize() {

        // setting the back button image
        backButton.setImage(new Image(FinalConstants.backWhite.toURI().toString()));
        backButton.setOnMouseEntered(event -> {
            backButton.setImage(new Image(FinalConstants.backBlack.toURI().toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        backButton.setOnMouseExited(event -> {
            backButton.setImage(new Image(FinalConstants.backWhite.toURI().toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        resetButton.setOnMouseClicked(event -> clearScreen());
        submitButton.setOnMouseClicked(event -> addEntry());

    }

    /**
     * Clear the screen
     */
    private void clearScreen() {
        if (ConfirmBox.display("Confirm",
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
        Entry entry = new Entry(SerialNum.getSerialNum(DBOrder.ENTRIES));
        entry.setName(nameTextField.getText());
        entry.setEmail(emailTextField.getText());
        entry.setSuggestions(suggestionsTextArea.getText());
        entry.setCheckInDate(LocalDate.now());
        entry.setTitle(title);
        try {
            DatabaseUtil.AddEntry(entry);
            AlertBox.display("Success", "Thank you for your time! Your response has been recorded.");
        } catch (SQLException e) {
            AlertBox.display("Error", "Could not recorded response.");
        }
    }
}
