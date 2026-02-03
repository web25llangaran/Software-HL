package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class PrimaryController {

     @FXML
        private Label labela;
           @FXML
    private Label label1;
   
    @FXML
    private void switchToSecondary() throws IOException {
        labela.setText("ia zer gertatzen den");
        //App.setRoot("secondary");
    }

    @FXML
    void iritsi(MouseEvent event) {
       String text= label1.getText();
       if (text=="Bai!"){
        label1.setText("Ez jarrikou!");
       }else label1.setText("Bai!");


       
    }

    
      @FXML
    void oin(DragEvent event) {

        
       // labela.setText("ia zer gertatzen den");
    }
}
