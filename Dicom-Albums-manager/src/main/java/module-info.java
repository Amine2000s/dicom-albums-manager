module com.chabiamin.dicomalbumsmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires dcm4che.core;
    requires org.json;


    opens com.chabiamin.dicomalbumsmanager to javafx.fxml;
    exports com.chabiamin.dicomalbumsmanager;
    exports com.chabiamin.dicomalbumsmanager.Controller;
    opens com.chabiamin.dicomalbumsmanager.Controller to javafx.fxml;

}