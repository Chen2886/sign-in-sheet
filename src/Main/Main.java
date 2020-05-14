package Main;

import DataBaseUtil.DatabaseUtil;
import DataBaseUtil.SerialNum;
import Util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    public static String fxmlPath = "/";
    public static String styleSheetPath = fxmlPath + "stylesheet.css";
    private Stage mainStage;

    /**
     * Function that initialize before stage shows
     * @param args N/A
     */
    public static void main(String[] args) {

        // Clear error log
        HandleError.clear();

        // init Final Constant
        FinalConstants.init();

        // Initialize Database
        if (!DatabaseUtil.ConnectionInitAndCreate()) {
            AlertBox.display("Error", "Data Base does not exists, and creation failure");
            System.exit(0);
        }

        // Initialize Serial Num
        SerialNum.initSerialNum();

        // start the screen
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;

        Scene scene = loadMainFXML("MainScreen.fxml");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(417);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setOnCloseRequest(event -> {
            Path source = Paths.get("signInEntries.db");
            Path target = Paths.get(System.getProperty("user.home") + "/signInEntries.db");
            try {
                if (Files.exists(target)) Files.delete(target);
                Files.copy(source, target);
            } catch (IOException e) {
                new HandleError(Main.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        e.getMessage(), e.getStackTrace(), false);
            }
            stage.close();
        });

        stage.show();
    }

    public Scene loadMainFXML(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream fileInputStream = getClass().getResourceAsStream(fxmlPath + fxmlName);
            Parent parent = loader.load(fileInputStream);

            MainScreen mainScreen = loader.getController();
            mainScreen.initData(mainStage);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(styleSheetPath);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            AlertBox.display("Error", "Loading Window Error！");
            new HandleError(Main.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            return new Scene(new VBox());
        }
    }
}
