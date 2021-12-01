module com.fantastic_knight {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.fantastic_knight to javafx.fxml;
    exports com.fantastic_knight.controller;
    exports com.fantastic_knight.model;
    exports com.fantastic_knight.view;
    exports com.fantastic_knight;
}