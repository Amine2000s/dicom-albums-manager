package com.chabiamin.dicomalbumsmanager.Controller;

import com.chabiamin.dicomalbumsmanager.Model.DicomData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.json.JSONObject;

import static com.chabiamin.dicomalbumsmanager.Controller.fileManagement.dicomDataList;
import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.getAlbumsWithMetadata;
import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.getAvailableDirectories;

public class addToAlbumsPopUp implements Initializable {

    @FXML
    Button browseButton;
    @FXML
    TextField directoryPathField;

    @FXML
    ListView albumsListView ;

    @FXML
    Button addToAlbumButton;
    @FXML
    Button createAlbumButton;

    private TableView<DicomData> resultsTable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // needs to filter and make sure the files with meta data (json file are only showen )
        browseButton.setOnAction(event -> {


            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
                directoryPathField.setText(selectedDirectory.getAbsolutePath());
                ArrayList<File> list = getAlbumsWithMetadata(selectedDirectory);
                albumsListView.getItems().addAll(list); // Load directories
                albumsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    // Update the TextField with the selected directory
                    if (newValue != null) {
                        directoryPathField.setText(newValue.toString());
                        System.out.println(newValue);
                    }
                });
            }
        });


        addToAlbumButton.setOnAction(event -> {
            String selectedDirectory = directoryPathField.getText();
            if (!selectedDirectory.isEmpty()) {
                // Here we assume you have an ArrayList of DICOM files already
                ArrayList<DicomData> dicomFiles = dicomDataList;
                ArrayList<DicomData> selectedFiles = new ArrayList<>();

                // Iterate through each DicomData object in the TableView
                for (DicomData dicomData : resultsTable.getItems()) {
                    if (dicomData.isSelectedProperty().get()) {
                        selectedFiles.add(dicomData);
                    }
                }

                if (selectedFiles.isEmpty()) {
                    showAlert("Error", "No files selected!");
                    return;
                }

                // Perform the logic to add DICOM files to the album directory
                // (this is just a placeholder for your logic)
                addDICOMFilesToDirectory(dicomFiles, selectedDirectory);

                // Optionally: Show confirmation
                showConfirmation("DICOM files have been added to the album!");


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                // Handle the case where no directory was selected
                showConfirmation("Please select a directory first.");
            }

        });

        createAlbumButton.setOnAction(event -> {

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

                // Optionally: Move or copy the DICOM files to the album directory
            });

            cancelButton.setOnAction(e -> popupStage.close());
            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        });



    }


    private void addDICOMFilesToDirectory(ArrayList<DicomData> dicomFiles, String directoryPath) {
        // Logic for copying the DICOM files to the selected directory and/or creating an album
        // This could involve copying the files, creating album metadata, etc.
        System.out.println("Adding DICOM files to: " + directoryPath);
        for (DicomData Dicomfile : dicomFiles) {
            // Actual file copying logic here (e.g., FileUtils.copy(file, new File(directoryPath)));
            System.out.println("Copying: " + Dicomfile.getFileName());
            File dicomFile = Dicomfile.getFile();  // Assuming your DicomData object has a method `getFile()`

            if (dicomFile.exists() && dicomFile.isFile()) {
                Path destinationPath = Paths.get(directoryPath, dicomFile.getName());

                try {
                    Files.copy(dicomFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Successfully copied: " + dicomFile.getName());
                } catch (IOException e) {
                    System.out.println("erro while copying fie "+Dicomfile.getFile().toString());
                }
            } else {
                System.err.println("Invalid DICOM file: " + dicomFile.getName());
            }
        }
    }

    // Method to show a confirmation message (could be a simple alert dialog)
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

    public void setResultsTable(TableView<DicomData> resultsTable) {
        this.resultsTable = resultsTable;
    }
}
