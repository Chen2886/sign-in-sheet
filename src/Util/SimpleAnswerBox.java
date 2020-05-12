package Util;

// from my other packages

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SimpleAnswerBox {

	//Create variable
	static String answer;
	private static final String mediumFont = "-fx-font: 18 arial;";

	public static String display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Set Title");
		window.setMinWidth(250);

		Label label = new Label();
		label.setText("Enter a Title for your new display form:");
		label.setPadding(new Insets(10, 10, 10, 10));
		label.setStyle(mediumFont);

		JFXTextField answerTextField = new JFXTextField();
		answerTextField.setPadding(new Insets(10, 10, 10, 10));
		answerTextField.setPromptText("Password");

		//Create two buttons
		JFXButton yesButton = new JFXButton("Confirm");
		JFXButton noButton = new JFXButton("Cancel");

		HBox buttonHBox = new HBox(yesButton, noButton);
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		buttonHBox.setPadding(new Insets(10, 10, 10, 10));

		//Clicking will set answer and close window
		answerTextField.setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.ENTER)) {
				answer = answerTextField.getText();
				window.close();
			}
		});
		yesButton.setOnMouseClicked(keyEvent -> {
			answer = answerTextField.getText();
			window.close();
		});
		yesButton.setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.ENTER)) {
				answer = answerTextField.getText();
				window.close();
			}
		});
		noButton.setOnAction(e -> {
			answer = "CANCELLED THE TITLE SCREEN";
			window.close();
		});

		VBox layout = new VBox(10);

		//Add buttons
		layout.getChildren().addAll(label, answerTextField, buttonHBox);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		//Make sure to return answer
		return answer;
	}

}