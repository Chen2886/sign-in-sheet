package Main;

import DataBaseUtil.DatabaseUtil;
import Util.AlertBox;
import Util.ConfirmBox;
import Util.HandleError;
import Util.SimpleAnswerBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainScreen {
    Stage stage;

    public TableView<Entry> historyTable;

    public AnchorPane root;

    public ImageView createButton;
    public ImageView exportButton;
    public ImageView powerButton;

    private double xOffset = 0;
    private double yOffset = 0;

    ObservableList<Entry> allEntry;

    public void initData(Stage stage) {
        this.stage = stage;
        root.setPrefSize(700, 417);
        initialize();
    }

    public void initialize() {

        // pictures
        powerButton.setImage(new Image(FinalConstants.powerInputWhite.toString()));
        createButton.setImage(new Image(FinalConstants.createWhite.toString()));
        exportButton.setImage(new Image(FinalConstants.exportWhite.toString()));
        powerButton.setOnMouseEntered(event -> {
            powerButton.setImage(new Image(FinalConstants.powerInputBlack.toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        powerButton.setOnMouseExited(event -> {
            powerButton.setImage(new Image(FinalConstants.powerInputWhite.toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        createButton.setOnMouseEntered(event -> {
            createButton.setImage(new Image(FinalConstants.createBlack.toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        createButton.setOnMouseExited(event -> {
            createButton.setImage(new Image(FinalConstants.createWhite.toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        createButton.setOnMouseClicked(event -> {
            String title = SimpleAnswerBox.display();
            if (title.equals("")) AlertBox.display("Error", "Please enter a title!");
            else if (title.equals("CANCELLED THE TITLE SCREEN")) {
            }
            else loadEntryForm(title);
        });
        exportButton.setOnMouseEntered(event -> {
            exportButton.setImage(new Image(FinalConstants.exportBlack.toString()));
            stage.getScene().setCursor(Cursor.HAND);
        });
        exportButton.setOnMouseExited(event -> {
            exportButton.setImage(new Image(FinalConstants.exportWhite.toString()));
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        exportButton.setOnMouseClicked(event -> {
            GenerateExcel();
        });


        // dragging
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // closing
        powerButton.setOnMouseClicked(event -> {
            Path source = Paths.get("signInEntries.db");
            Path target = Paths.get(System.getProperty("user.home") + "/signInEntries.db");
            try {
                if (Files.exists(target)) Files.delete(target);
                Files.copy(source, target);
            } catch (IOException e) {
                new HandleError(Main.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        e.getMessage(), e.getStackTrace(), false);
            }
            if (ConfirmBox.display("Confirm", "Are you sure you want to quit?", "Yes", "No"))
                stage.close();
        });

        populateScrollView();
    }

    private void populateScrollView() {
        ObservableList<TableColumn<Entry, ?>> columnArrayList = FXCollections.observableArrayList();
        try {
            allEntry = DatabaseUtil.GetAllEntry();
        } catch (SQLException e) {
            allEntry = FXCollections.observableArrayList();
        }

        for (int i = 0; i < FinalConstants.entryHeader.length; i++) {
            if (i == 0) {
                TableColumn<Entry, LocalDate> newColumn = new TableColumn<>(FinalConstants.entryHeader[i]);
                newColumn.setCellValueFactory(new PropertyValueFactory<>(FinalConstants.entryPropertyHeader[i]));
                newColumn.setStyle("-fx-alignment: CENTER;");
                columnArrayList.add(newColumn);
            } else {
                TableColumn<Entry, String> newColumn = new TableColumn<>(FinalConstants.entryHeader[i]);
                newColumn.setCellValueFactory(new PropertyValueFactory<>(FinalConstants.entryPropertyHeader[i]));
                newColumn.setStyle("-fx-alignment: CENTER;");
                columnArrayList.add(newColumn);
            }
        }

        historyTable.getColumns().clear();
        historyTable.getColumns().addAll(columnArrayList);
        historyTable.getItems().setAll(allEntry);

        historyTable.setOnKeyPressed(event -> {
            if ((event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) &&
                    (historyTable.getSelectionModel().getSelectedItems() != null)) {
                try {
                    if (ConfirmBox.display("Confirm", "Are you sure you want to delte this row?", "Yes", "No")) {
                        DatabaseUtil.deleteEntry(historyTable.getSelectionModel().getSelectedItem());
                        historyTable.getItems().clear();
                        historyTable.getItems().setAll(DatabaseUtil.GetAllEntry());
                        historyTable.refresh();
                    }
                } catch (SQLException e) {
                    AlertBox.display("Error", "Could not delete!");
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        });

    }


    private void loadEntryForm(String title) {
        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream fileInputStream = getClass().getResourceAsStream(Main.fxmlPath + "CheckInForm.fxml");
            Parent parent = loader.load(fileInputStream);

            EntryFormController entryFormController = loader.getController();
            entryFormController.initData(stage, title);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Main.styleSheetPath);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            AlertBox.display("Error", "Loading Window Error！");
            new HandleError(Main.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        }
    }

    private void GenerateExcel() {

        String fileName = "Check In Form.xlsx";
        String sheetName = "All History";

        try {
            ObservableList<Entry> selectedData = DatabaseUtil.GetAllEntry();

            XSSFWorkbook workbook;
            XSSFSheet sheet;
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File excelFile = new File(desktopPath + fileName);

            if (excelFile.exists()) {
                if (!excelFile.delete()) AlertBox.display("Error", "Can not delete current file.");
                if (!excelFile.createNewFile()) AlertBox.display("Error", "Can not create new file.");
            }

            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(sheetName);

            FileOutputStream fileOutputStream = new FileOutputStream(excelFile);

            int rowNum = 1;
            int cellNum = 0;

            Row headerRow = sheet.createRow(0);

            CellStyle cellStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font boldFont = workbook.createFont();
            boldFont.setFontName("Calibri");
            boldFont.setBold(true);
            boldFont.setFontHeightInPoints((short) 24);

            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 16);
            font.setFontName("Calibri");
            cellStyle.setFont(font);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            for (int i = 0; i < FinalConstants.entryHeader.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(cellNum++);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setFont(boldFont);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(FinalConstants.entryHeader[i]);
            }

            cellStyle.setFont(font);

            for (int i = 0; i < selectedData.size(); i++) {
                Row row = sheet.createRow(rowNum++);
                cellNum = 0;
                String[] orderObjArr = selectedData.get(i).toString().split(",");
                for (int j = 0; j < orderObjArr.length; j++) {
                    Cell cell = row.createCell(cellNum++);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(orderObjArr[j]);
                }
            }

            for (int i = 0; i < FinalConstants.entryHeader.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            AlertBox.display("Success", "Generation successful!");

        } catch (SQLException e) {
            AlertBox.display("Error", "Error reading from database!");
            new HandleError(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        } catch (IOException e) {
            AlertBox.display("Error", "Writing to file failure!");
            new HandleError(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        } catch (Exception e) {
            AlertBox.display("Error", "Try deleting the previous file!");
            new HandleError(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        }
    }
}
