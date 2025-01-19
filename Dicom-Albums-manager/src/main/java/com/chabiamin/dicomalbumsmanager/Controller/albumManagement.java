package com.chabiamin.dicomalbumsmanager.Controller;

import com.chabiamin.dicomalbumsmanager.Model.DicomAlbum;
import com.chabiamin.dicomalbumsmanager.Model.DicomData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.fetchDicomData;
import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.getAlbumsWithMetadata;

public class albumManagement implements Initializable {

    ArrayList<DicomAlbum> DicomAlbumsList=new ArrayList<>();
    ArrayList<DicomData> DicomDataList=new ArrayList<>();
    ObservableList<DicomAlbum> albumsObservableList = FXCollections.observableArrayList(DicomAlbumsList);
    ObservableList<DicomData> DicomDataObservableList = FXCollections.observableArrayList(DicomDataList);

    @FXML
    Button chooseDirectoryButton ;
    @FXML
    TextField directoryPathField;
    @FXML
    ListView albumListView ;

    @FXML
    TableView albumImagesTable ;

    @FXML
    TableColumn<DicomData, String> fileNameColumn ;
    @FXML
    TableColumn<DicomData, String> modalityColumn ;
    @FXML
    TableColumn<DicomData, String> patientIdColumn ;
    @FXML
    Button createButton ;
    @FXML
    Button deleteButton ;
    @FXML
    Button exportButton ;

    @FXML
    Button removeSelectedImages ;
    @FXML
    Button viewImage ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFileName()));
        patientIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatientId()));
        modalityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModality()));

        chooseDirectoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
                directoryPathField.setText(selectedDirectory.getAbsolutePath());
                ArrayList<File> list = getAlbumsWithMetadata(selectedDirectory);
                albumListView.getItems().addAll(list);
                //albumsObservableList =
              /*  dicomDataObservableListList = fetchDicomData(directoryPathField.getText());
                resultsTable.setItems(dicomDataObservableListList);
                albumListView.setItems();
**/
            }
        });

        albumListView.setOnMouseClicked((MouseEvent event) -> {
            // Get the selected item
            File selectedFile = (File) albumListView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                DicomDataObservableList = fetchDicomData(selectedFile.getAbsolutePath());
                albumImagesTable.setItems(DicomDataObservableList);

            }
        });

    }
}
