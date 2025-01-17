package com.chabiamin.dicomalbumsmanager.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class albumManagement implements Initializable {

    @FXML
    Button chooseDirectoryButton ;
    @FXML
    TextField directoryPathField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chooseDirectoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
                directoryPathField.setText(selectedDirectory.getAbsolutePath());


            }
        });


    }
}
