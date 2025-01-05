module com.chabiamin.dicomalbumsmanager {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.chabiamin.dicomalbumsmanager to javafx.fxml;
    exports com.chabiamin.dicomalbumsmanager;
}