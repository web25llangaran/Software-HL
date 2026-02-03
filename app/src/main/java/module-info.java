module programa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens programa to javafx.fxml;
    exports programa;
    opens programa.controller to javafx.fxml;
    exports programa.controller;
    opens programa.model to javafx.fxml;
    exports programa.model;
}
