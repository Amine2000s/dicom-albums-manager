package com.chabiamin.dicomalbumsmanager.Controller;

import com.chabiamin.dicomalbumsmanager.HelloApplication;
import com.chabiamin.dicomalbumsmanager.Model.DicomData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.*;

public class fileManagement implements Initializable {

    ////////////// directory Components////////
    @FXML
    Button chooseDirectoryButton ;
    @FXML
    TextField directoryPathField;
    ////////////////////////////////////
    ///////////// Table view related  Components///////
    public static ArrayList<DicomData> dicomDataList = new ArrayList<>(); // represent the actual Data list in the table view
    ObservableList<DicomData> dicomDataObservableListList = FXCollections.observableArrayList(dicomDataList); ; // represent the availlable list in the table view

    @FXML
    TableView resultsTable ;
    @FXML
    TableColumn<DicomData, Boolean> selectColumn ;
    @FXML
    TableColumn<DicomData, String> fileNameColumn ;
    @FXML
    TableColumn<DicomData, String> patientIdColumn ;
    @FXML
    TableColumn<DicomData, String> modalityColumn ;
    @FXML
    TableColumn<DicomData, String> studyDateColumn ;
    @FXML
    TableColumn<DicomData, String> details ;
    @FXML
    CheckBox selectAllCheckbox ;
    ////////////////////////////////////////////////////
    ///////////// Filter and query related Components//////////
    @FXML
    TextField patientIdField ;
    @FXML
    TextField modalityField ;
    @FXML
    DatePicker studyDatePicker ;
    @FXML
    TextField ageField ;
    @FXML
    DatePicker birthDatePicker ;
    @FXML
    TextField studyInstanceField ;
    @FXML
    Button searchButton ;
    @FXML
    Button clearButton ;
    //////////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsTable.setEditable(true);

        selectColumn.setCellValueFactory(param -> param.getValue().isselectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);

        fileNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFileName()));
        patientIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatientId()));
        modalityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModality()));
        studyDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudyDate()));
        details.setCellFactory(param -> new TableCell<DicomData, String>() {
            private final Button detailsButton = new Button("Details");

            {
                // Set the action on button click
                detailsButton.setOnAction(event -> {
                    DicomData dicomData = getTableView().getItems().get(getIndex());
                    showDetailsPopup(dicomData); // Call the function to show the details for the clicked item
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                }
            }
        });
        /*details.setCellFactory(col -> new TableCell() {
                    private final Button detailsButton = new Button("info");

                        URL imageURL = HelloApplication.class.getResource("icons/info.jpg");
                        Image image = new Image(imageURL.toExternalForm());

                        detailsButton.setGraphic((new ImageView(image)));
                        detailsButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

                        detailsButton.setOnAction(event -> {
                            DicomData dicomData = (DicomData) getTableView().getItems().get(getIndex());
                            showDetailsPopup(dicomData);
                        });

        });
*/
        chooseDirectoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if(selectedDirectory!= null){
                directoryPathField.setText(selectedDirectory.getAbsolutePath());
                dicomDataObservableListList = fetchDicomData(directoryPathField.getText());
                resultsTable.setItems(dicomDataObservableListList);
            }
        });

        selectAllCheckbox.selectedProperty().addListener((obs, oldValue, newValue) -> {
            for (Object item : resultsTable.getItems()) {
                DicomData temp = (DicomData)item;
                temp.setSelected(newValue);
            }
        });

        // Track individual row selection changes to update "Select All"
        for (Object item : resultsTable.getItems()) {
            DicomData temp = (DicomData) item;
            temp.isselectedProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    selectAllCheckbox.setSelected(false); // Uncheck "Select All" if any row is unchecked
                } else if (resultsTable.getItems().stream().allMatch(temp::isSelected)) {
                    selectAllCheckbox.setSelected(true); // Check "Select All" if all rows are checked
                }
            });
        }

        searchButton.setOnAction(event ->{
            // retrieve all necessary informations from filters
            // make an iteration for all files , and if all attributes exists , add it to a new list ,
            //then affect a new list
            ArrayList<DicomData> filtredList = filterDicomData(
                    dicomDataList,
                    patientIdField.getText(),
                    modalityField.getText(),
                    studyDatePicker.getValue(),
                    ageField.getText(),
                    birthDatePicker.getValue(),
                    studyInstanceField.getText());

            dicomDataObservableListList.clear();
            dicomDataObservableListList = FXCollections.observableArrayList(filtredList);
            resultsTable.setItems(dicomDataObservableListList);
        });


        clearButton.setOnAction(event -> {
                patientIdField.clear();
                modalityField.clear();
                studyDatePicker.setValue(null);
                ageField.clear();
                birthDatePicker.setValue(null);
                studyInstanceField.clear();
        });
    }


    private void showDetailsPopup(DicomData dicomData) {
        // Create a new Stage (popup window)
        Stage detailsStage = new Stage();
        detailsStage.setTitle("File Details");

        // Create a layout to display details
        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10));
        detailsLayout.getChildren().addAll(
                new Label("File Name: " + dicomData.getFileName()),
                new Label("Patient ID: " + dicomData.getPatientId()),
                new Label("Modality: " + dicomData.getModality()),
                new Label("Study Date: " + dicomData.getStudyDate()),
                new Label("others can be added by preference ")
                // Add other attributes as needed
        );

        // Add a close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> detailsStage.close());
        detailsLayout.getChildren().add(closeButton);

        // Set the scene and show the popup
        Scene scene = new Scene(detailsLayout, 300, 200);
        detailsStage.setScene(scene);
        detailsStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
        detailsStage.show();
    }

    public void handleAddToAlbum() throws IOException {

        Stage popupStage = new Stage();
        popupStage.setTitle("select album directory ");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addToAlbumsPopUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        addToAlbumsPopUp popupController = fxmlLoader.getController();

        // Pass the TableView reference to the pop-up controller
        popupController.setResultsTable(resultsTable);
        popupStage.setScene(scene);
        popupStage.show();
    }


}




