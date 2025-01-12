module com.chabiamin.dicomalbumsmanager {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.chabiamin.dicomalbumsmanager to javafx.fxml;
    exports com.chabiamin.dicomalbumsmanager;
    exports com.chabiamin.dicomalbumsmanager.Controller;
    opens com.chabiamin.dicomalbumsmanager.Controller to javafx.fxml;

}