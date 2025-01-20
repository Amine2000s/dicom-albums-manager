package com.chabiamin.dicomalbumsmanager.Controller;

import com.chabiamin.dicomalbumsmanager.Model.DicomAlbum;
import com.chabiamin.dicomalbumsmanager.Model.DicomData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.*;
import static com.chabiamin.dicomalbumsmanager.utils.viewUtils.refreshListView;

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

    @FXML
    Label albumNameLabel ;

    @FXML
    Label albumDescLabel;

    @FXML
    Label albumImageCountLabel ;




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
                /*i can put these 2 instruction under a function called reload listview
                * first argument is the list view , second is the selected direcotry (file)
                * */
                /*ArrayList<File> list = getAlbumsWithMetadata(selectedDirectory);
                albumListView.getItems().addAll(list);*/
                refreshListView(albumListView,selectedDirectory);
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
                try {
                    Map<String, Object> info = getAlbumMetadata(selectedFile);
                    albumNameLabel.setText(info.get("album_name").toString());
                    albumDescLabel.setText(info.get("description").toString());
                    File[] files = selectedFile.listFiles(File::isFile); // Only count regular files
                    int no_of_files = files != null ? (files.length - 1) : 0;
                    albumImageCountLabel.setText(String.valueOf(no_of_files));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        createButton.setOnAction(event -> {

                Stage popupStage = new Stage();
                popupStage.setTitle("Create Album");

                VBox vbox = new VBox(10);
                vbox.setPadding(new javafx.geometry.Insets(20));

                // Album name input
                Label albumNameLabel = new Label("Album Name:");
                TextField albumNameField = new TextField();
                albumNameField.setPromptText("Enter album name");

                // Description input
                Label descriptionLabel = new Label("Description:");
                TextField descriptionField = new TextField();
                descriptionField.setPromptText("Enter album description");

                // Username input
                Label usernameLabel = new Label("Username:");
                TextField usernameField = new TextField();
                usernameField.setPromptText("Enter your username");

                // Create Album Button
                Button createAlbumButton = new Button("Create Album");

                // Cancel Button
                Button cancelButton = new Button("Cancel");

                vbox.getChildren().addAll(albumNameLabel, albumNameField, descriptionLabel, descriptionField, usernameLabel, usernameField, createAlbumButton, cancelButton);

                createAlbumButton.setOnAction(subevent -> {
                    String albumName = albumNameField.getText();
                    String description = descriptionField.getText();
                    String username = usernameField.getText();

                    if (albumName.isEmpty() || description.isEmpty() || username.isEmpty()) {
                        showAlert("Error", "Please fill in all fields!");
                        return;
                    }
                    File albumDirectory = new File(directoryPathField.getText(), albumName);
                    if (!albumDirectory.exists()) {
                        albumDirectory.mkdirs();  // Create directory if it doesn't exist
                    }

                    // Prepare album metadata
                    JSONObject albumMetadata = new JSONObject();
                    albumMetadata.put("album_name", albumName);
                    albumMetadata.put("description", description);
                    albumMetadata.put("username", username);
                    albumMetadata.put("creation_date", System.currentTimeMillis());  // Example: creation date as timestamp
                    //albumMetadata.put("dicom_files", dicomFiles);  // List of files, might want to store paths or file info

                    // Save metadata to JSON file
                    File metadataFile = new File(albumDirectory, "metadata.json");
                    try (FileWriter writer = new FileWriter(metadataFile)) {
                        writer.write(albumMetadata.toString());
                    } catch (IOException e) {
                        showAlert("Error", "Failed to save album metadata!");
                        return;
                    }
                    showAlert("Success", "Album created successfully!");
                    popupStage.close();  // Close the popup after successful creation
                    refreshListView(albumListView, new File(directoryPathField.getText()));
                });

                cancelButton.setOnAction(e -> popupStage.close());
                Scene scene = new Scene(vbox, 400, 300);
                popupStage.setScene(scene);
                popupStage.show();
            });
    }


    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
