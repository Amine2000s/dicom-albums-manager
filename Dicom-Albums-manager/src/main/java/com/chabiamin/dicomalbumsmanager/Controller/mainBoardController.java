package com.chabiamin.dicomalbumsmanager.Controller;

import com.chabiamin.dicomalbumsmanager.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainBoardController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    AnchorPane mainPane;
    @FXML
    Button metaDataQueryButton ;

    public void showMetadataQuery() {
        loadView("fileManagment.fxml");
    }

    public void showAlbumManagement() {
        loadView("AlbumManagment.fxml");
    }

    public void showSettings() {
       // loadView("/fxml/Settings.fxml");
    }

    private void loadView(String fxmlPath){
        try{
            Parent view = FXMLLoader.load(HelloApplication.class.getResource(fxmlPath));
            //mainPane.getChildren().setAll(view);
            Stage ST = new Stage();
            Scene sc = new Scene(view);
            ST.setTitle("main-board");
            ST.setScene(sc);
            ST.show();
        }catch( IOException e ){
            e.printStackTrace();
        }

    }
}
