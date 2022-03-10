module com.fantastic_knight {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    exports com.fantastic_knight;
    exports com.fantastic_knight.model;
    exports com.fantastic_knight.view;
    exports com.fantastic_knight.controller;
    opens com.fantastic_knight to javafx.fxml;
    opens com.fantastic_knight.controller to javafx.fxml;
    exports com.fantastic_knight.levelMaker;
    exports com.fantastic_knight.playerMaker;
    opens com.fantastic_knight.levelMaker to javafx.fxml;
    exports com.fantastic_knight.player;
    opens com.fantastic_knight.player to javafx.fxml;
    opens com.fantastic_knight.model to javafx.fxml;
    exports com.fantastic_knight.animation;
    opens com.fantastic_knight.animation to javafx.fxml;
    exports com.fantastic_knight.menu;
    opens com.fantastic_knight.menu to javafx.fxml;
    exports com.fantastic_knight.items;
    opens com.fantastic_knight.items to javafx.fxml;
}
