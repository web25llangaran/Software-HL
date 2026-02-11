package programa.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TxirbilDBController {

    @FXML
    private Button button_itxi;

    @FXML
    private void leihoaItxi(ActionEvent event) throws IOException {
        Stage stage = (Stage) button_itxi.getScene().getWindow();
        stage.close();
    }

}
