module com.fantastic_knight {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports com.fantastic_knight;
    exports com.fantastic_knight.model;
    exports com.fantastic_knight.view;
    exports com.fantastic_knight.controller;
    opens com.fantastic_knight to javafx.fxml;
    opens com.fantastic_knight.controller to javafx.fxml;
}
