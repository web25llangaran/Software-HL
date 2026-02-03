package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import programa.App;
public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}