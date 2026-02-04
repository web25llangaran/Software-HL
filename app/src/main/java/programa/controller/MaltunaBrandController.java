package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import programa.DBKonexioa;
import programa.model.makina;
import programa.model.piezaMota;





public class MaltunaBrandController {

//********************************************************************************************************************  
//                                                     MAKINA  
//********************************************************************************************************************
    @FXML
    private TextArea id_aldatuDeskribapenaMakina;
    @FXML
    private TextField id_aldatuIDMakina;
    @FXML
    private DatePicker id_aldatuInstalakuntzaDataMakina;
    @FXML
    private TextField id_aldatuIzenaMakina;
    @FXML
    private TextField id_aldatuPotentziaMakina;
    @FXML
    private Button id_gehituMakina;
    @FXML
    private Button id_button_aldatuMakina;
    @FXML
    private Button id_aldatuMakina;
    @FXML
    private Button id_button_gehituMakina;
    @FXML
    private Button id_button_kenduMakina;
    @FXML
    private Button id_kenduMakina;
    @FXML
    private Button id_button_zerrendatuMakina;
    @FXML
    private VBox id_ezkerMenua_makina;
    @FXML
    private AnchorPane id_footerra;
    @FXML
    private ImageView id_imageMakina;
    @FXML
    private TextArea id_kenduDeskribapenaMakina;
    @FXML
    private TextField id_kenduIDMakina;
    @FXML
    private DatePicker id_kenduInstalakuntzaDataMakina;
    @FXML
    private TextField id_kenduIzenaMakina;
    @FXML
    private TextField id_kenduPotentziaMakina;
    @FXML
    private TitledPane id_makinaAldatuPantaila;
    @FXML
    private AnchorPane id_makinaEkintzaPantaila;
    @FXML
    private TitledPane id_makinaGehituPantaila;
    @FXML
    private AnchorPane id_makinaInfoPantaila;
    @FXML
    private TitledPane id_makinaKenduPantaila;
    @FXML
    private ListView<makina> id_makinaZerrenda;
    @FXML
    private ListView<makina> id_makinaZerrenda1;
    @FXML
    private AnchorPane id_oinarria;
    @FXML
    private TextArea id_sartuDeskribapenaMakina;
    @FXML
    private TextField id_sartuIDMakina;
    @FXML
    private DatePicker id_sartuInstalakuntzaDataMakina;
    @FXML
    private TextField id_sartuIzenaMakina;
    @FXML
    private TextField id_sartuPotentziaMakina;
    @FXML
    private TabPane id_tabPane_elementuak;
    @FXML
    private Label id_label_makinaZerrenda;
    @FXML
    private Button id_button_makinaBilatu;
    @FXML
    private Button id_button_makinaBilatu1;
    @FXML
    private TitledPane id_makinaZerrendatuPantaila;

    @FXML
    void gehituMakinaPantaila(ActionEvent event) {
        id_imageMakina.setVisible(false);
        id_makinaKenduPantaila.setVisible(false);
        id_makinaAldatuPantaila.setVisible(false);
        id_makinaGehituPantaila.setVisible(true);
        id_makinaZerrendatuPantaila.setVisible(false);
        id_label_makinaZerrenda.setVisible(false);
        ObservableList<makina> makinak = FXCollections.observableArrayList();
        // Pantaila garbitu, textfieldak
        id_makinaZerrenda.setItems(makinak);
        id_sartuIDMakina.setText("");
        id_sartuIzenaMakina.setText("");
        id_sartuDeskribapenaMakina.setText("");
        id_sartuPotentziaMakina.setText("");
        id_sartuInstalakuntzaDataMakina.setValue(null);
    }

    @FXML
    void gehituMakina(ActionEvent event) throws IOException {

        // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaTxertatua = false;
        String id = id_sartuIDMakina.getText();
        String izena = id_sartuIzenaMakina.getText();
        String deskribapena = id_sartuDeskribapenaMakina.getText();
        String potentzia = id_sartuPotentziaMakina.getText();
        LocalDate instalData = id_sartuInstalakuntzaDataMakina.getValue();
        // Instalakuntza data Stringera pasa
        String instalakuntzaData = "";
        if (instalData != null) {
            instalakuntzaData = instalData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !deskribapena.isEmpty() && !potentzia.isEmpty()
                && !instalakuntzaData.isEmpty()) {
            // makinaBerria objetua sortu
            try {
                makina makinaBerria = new makina(Integer.parseInt(id), izena, deskribapena, Integer.parseInt(potentzia),
                        instalakuntzaData);
                // DBKonexioa klaseko objektu bat sortzen da, datu-basearekin konexioa
                // kudeatzeko.
                DBKonexioa dataBkonexioa = new DBKonexioa();

                try {
                    // konektatu() metodoari deitzen zaio.
                    // Metodo honek Connection motako objektu bat itzultzen du,
                    // eta SQLException jaurtitzen du errorea gertatzen bada.
                    Connection konexioa = dataBkonexioa.konektatu();
                    // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                    if (konexioa != null && !konexioa.isClosed()) {
                        System.out.println("Komunikazio kanala irekita dago.");
                        // **INSERT INTO ERABILTZAILEA** SQL kontsulta prestatzen da
                        String kontsulta = "INSERT INTO MAKINA (ID_MAKINA,IZENA,DESKRIBAPENA,POTENTZIA,INSTALAKUNTZA_DATA) VALUES(?,?,?,?,?)";
                        PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                        agindua.setInt(1, makinaBerria.getId());
                        agindua.setString(2, makinaBerria.getIzena());
                        agindua.setString(3, makinaBerria.getDeskribapena());
                        agindua.setInt(4, makinaBerria.getPotentzia());
                        agindua.setDate(5, java.sql.Date.valueOf(makinaBerria.getInstalakuntzaData()));

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                        if (emaitza == 1) {
                            System.out.println("Makina berria datu basean txertatu da");
                            makinaTxertatua = true;
                        }
                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        konexioa.close();
                    }
                } catch (SQLException e) {
                    // Salbuespen bat sortzen da SQL aginduak exekutatzen direnean errore bat
                    // gertatuz gero.
                    // Adibidez, datuak sartzeko parametroak okerrak izan daitezke edo taula ez
                    // egon.
                    System.out.println("Errorea makinaren datuak sortzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaTxertatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina berria txertatuta!");
                    alerta.showAndWait();
                    id_label_makinaZerrenda.setVisible(true);
                    ObservableList<makina> makinak = makinaZerrenda();
                    id_makinaZerrenda.setItems(makinak);
                    id_makinaInfoPantaila.setVisible(true);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina berria datu basean txertatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren IDa eta POTENTZIA zenbaki osoak izan behar dira");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_sartuIDMakina.setText("");
        id_sartuIzenaMakina.setText("");
        id_sartuDeskribapenaMakina.setText("");
        id_sartuPotentziaMakina.setText("");
        id_sartuInstalakuntzaDataMakina.setValue(null);
    }

    @FXML
    void kenduMakinaPantaila(ActionEvent event) {
        id_imageMakina.setVisible(false);
        id_makinaKenduPantaila.setVisible(true);
        id_makinaAldatuPantaila.setVisible(false);
        id_makinaGehituPantaila.setVisible(false);
        id_makinaZerrendatuPantaila.setVisible(false);
        id_makinaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_kenduIDMakina.setText("");
        id_kenduIzenaMakina.setText("");
        id_kenduDeskribapenaMakina.setText("");
        id_kenduPotentziaMakina.setText("");
        id_kenduInstalakuntzaDataMakina.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        // id_kenduIDMakina.setDisable(true);
        id_kenduIzenaMakina.setDisable(true);
        id_kenduDeskribapenaMakina.setDisable(true);
        id_kenduPotentziaMakina.setDisable(true);
        id_kenduInstalakuntzaDataMakina.setDisable(true);
        // Makinen zerrenda bistaratu:
        id_label_makinaZerrenda.setVisible(true);
        ObservableList<makina> makinak = makinaZerrenda();
        id_makinaZerrenda.setItems(makinak);
        // Aukeratutako makina jaso
        id_makinaZerrenda.getSelectionModel().selectedItemProperty().addListener((obs, aurrekoAukera, aukeraBerria) -> {
            id_kenduIDMakina.setText(Integer.toString(aukeraBerria.getId()));
            id_kenduIzenaMakina.setText(aukeraBerria.getIzena());
            id_kenduDeskribapenaMakina.setText(aukeraBerria.getDeskribapena());
            id_kenduPotentziaMakina.setText(Integer.toString(aukeraBerria.getPotentzia()));
            id_kenduInstalakuntzaDataMakina.setValue((LocalDate.parse(aukeraBerria.getInstalakuntzaData())));
        });
        // Makinaren IDa sartzean (textField en fokua aldatzean) datu basean aurkitu
        // makina existitzen den
        id_kenduIDMakina.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_kenduIDMakina.setText("");
                id_kenduIzenaMakina.setText("");
                id_kenduDeskribapenaMakina.setText("");
                id_kenduPotentziaMakina.setText("");
                id_kenduInstalakuntzaDataMakina.setValue(null);
            }
        });
    }

    @FXML
    void kenduMakina(ActionEvent event) {
        // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaEzabatua = false;
        String id = id_kenduIDMakina.getText();
        String izena = id_kenduIzenaMakina.getText();
        String deskribapena = id_kenduDeskribapenaMakina.getText();
        String potentzia = id_kenduPotentziaMakina.getText();
        LocalDate instalData = id_kenduInstalakuntzaDataMakina.getValue();
        // Instalakuntza data Stringera pasa
        String instalakuntzaData = "";
        if (instalData != null) {
            instalakuntzaData = instalData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !deskribapena.isEmpty() && !potentzia.isEmpty()
                && !instalakuntzaData.isEmpty()) {
            // makinaBerria objetua sortu
            try {
                makina kentzekoMakina = new makina(Integer.parseInt(id), izena, deskribapena,
                        Integer.parseInt(potentzia), instalakuntzaData);
                // DBKonexioa klaseko objektu bat sortzen da, datu-basearekin konexioa
                // kudeatzeko.
                DBKonexioa dataBkonexioa = new DBKonexioa();

                try {
                    // konektatu() metodoari deitzen zaio.
                    // Metodo honek Connection motako objektu bat itzultzen du,
                    // eta SQLException jaurtitzen du errorea gertatzen bada.
                    Connection cn = dataBkonexioa.konektatu();

                    // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                    // "cn != null" konexioaren balio egokia den ziurtatzeko
                    // eta "cn.isClosed()" konexioa irekita dagoen egiaztatzeko.
                    if (cn != null && !cn.isClosed()) {
                        System.out.println("Komunikazio kanala irekita dago.");

                        // **DELETE FROM EMPLEADOS** SQL kontsulta prestatzen da.
                        String kontsulta = "DELETE FROM MAKINA WHERE ID_MAKINA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroa prestatzen da, "id_makina" balioa sartzeko.
                        agindua.setInt(1, kentzekoMakina.getId());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo langilerik ezabatu.
                        // > 0 itzultzen bada, **langilea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Makina ezabatu da");
                            makinaEzabatua = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea makinaren datuak ezabatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaEzabatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina ezabatu da datu basetik!");
                    alerta.showAndWait();
                    id_label_makinaZerrenda.setVisible(true);
                    ObservableList<makina> makinak = makinaZerrenda();
                    id_makinaZerrenda.setItems(makinak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina ezabatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_kenduIDMakina.setText("");
        id_kenduIzenaMakina.setText("");
        id_kenduDeskribapenaMakina.setText("");
        id_kenduPotentziaMakina.setText("");
        id_kenduInstalakuntzaDataMakina.setValue(null);
    }

    @FXML
    void makinaBilatuKendun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_kenduIDMakina.getText();
        boolean makinaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<makina> makinak = makinaZerrenda();
            for (makina konzidituMakina : makinak) {
                if (konzidituMakina.getId() == idZenb) {
                    id_kenduIzenaMakina.setText(konzidituMakina.getIzena());
                    id_kenduDeskribapenaMakina.setText(konzidituMakina.getDeskribapena());
                    id_kenduPotentziaMakina.setText(Integer.toString(konzidituMakina.getPotentzia()));
                    id_kenduInstalakuntzaDataMakina.setValue((LocalDate.parse(konzidituMakina.getInstalakuntzaData())));
                    makinaAurkitua = true;
                    return;
                }
            }
            if (!makinaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun makina ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_kenduIDMakina.setText("");
                id_kenduIzenaMakina.setText("");
                id_kenduDeskribapenaMakina.setText("");
                id_kenduPotentziaMakina.setText("");
                id_kenduInstalakuntzaDataMakina.setValue(null);
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
            alerta.showAndWait();
        }
    }

    @FXML
    void aldatuMakinaPantaila(ActionEvent event) {
        id_imageMakina.setVisible(false);
        id_makinaKenduPantaila.setVisible(false);
        id_makinaAldatuPantaila.setVisible(true);
        id_makinaGehituPantaila.setVisible(false);
        id_makinaZerrendatuPantaila.setVisible(false);
        id_makinaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_aldatuIDMakina.setText("");
        id_aldatuIzenaMakina.setText("");
        id_aldatuDeskribapenaMakina.setText("");
        id_aldatuPotentziaMakina.setText("");
        id_aldatuInstalakuntzaDataMakina.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        // id_kenduIDMakina.setDisable(true);
        id_aldatuIzenaMakina.setDisable(true);
        id_aldatuDeskribapenaMakina.setDisable(true);
        id_aldatuPotentziaMakina.setDisable(true);
        id_aldatuInstalakuntzaDataMakina.setDisable(true);
        // Makinen zerrenda bistaratu:
        id_label_makinaZerrenda.setVisible(true);
        ObservableList<makina> makinak = makinaZerrenda();
        id_makinaZerrenda.setItems(makinak);
        // Aukeratutako makina jaso
        id_makinaZerrenda.getSelectionModel().selectedItemProperty().addListener((obs, aurrekoAukera, aukeraBerria) -> {
            id_aldatuIDMakina.setText(Integer.toString(aukeraBerria.getId()));
            id_aldatuIzenaMakina.setText(aukeraBerria.getIzena());
            id_aldatuDeskribapenaMakina.setText(aukeraBerria.getDeskribapena());
            id_aldatuPotentziaMakina.setText(Integer.toString(aukeraBerria.getPotentzia()));
            id_aldatuInstalakuntzaDataMakina.setValue((LocalDate.parse(aukeraBerria.getInstalakuntzaData())));
            id_aldatuIzenaMakina.setDisable(false);
            id_aldatuDeskribapenaMakina.setDisable(false);
            id_aldatuPotentziaMakina.setDisable(false);
            id_aldatuInstalakuntzaDataMakina.setDisable(false);
        });
        // Makinaren IDa sartzean (textField en fokua aldatzean) datu basean aurkitu
        // makina existitzen den
        id_aldatuIDMakina.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_aldatuIDMakina.setText("");
                id_aldatuIzenaMakina.setText("");
                id_aldatuDeskribapenaMakina.setText("");
                id_aldatuPotentziaMakina.setText("");
                id_aldatuInstalakuntzaDataMakina.setValue(null);
                id_aldatuIzenaMakina.setDisable(true);
                id_aldatuDeskribapenaMakina.setDisable(true);
                id_aldatuPotentziaMakina.setDisable(true);
                id_aldatuInstalakuntzaDataMakina.setDisable(true);
            }
        });
    }

    @FXML
    void aldatuMakina(ActionEvent event) {
        // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaAldatuta = false;
        String id = id_aldatuIDMakina.getText();
        String izena = id_aldatuIzenaMakina.getText();
        String deskribapena = id_aldatuDeskribapenaMakina.getText();
        String potentzia = id_aldatuPotentziaMakina.getText();
        LocalDate instalData = id_aldatuInstalakuntzaDataMakina.getValue();
        // Instalakuntza data Stringera pasa
        String instalakuntzaData = "";
        if (instalData != null) {
            instalakuntzaData = instalData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !deskribapena.isEmpty() && !potentzia.isEmpty()
                && !instalakuntzaData.isEmpty()) {
            // makinaBerria objetua sortu
            try {
                makina aldatzekoMakina = new makina(Integer.parseInt(id), izena, deskribapena,
                        Integer.parseInt(potentzia), instalakuntzaData);
                // DBKonexioa klaseko objektu bat sortzen da, datu-basearekin konexioa
                // kudeatzeko.
                DBKonexioa dataBkonexioa = new DBKonexioa();

                try {
                    // konektatu() metodoari deitzen zaio.
                    // Metodo honek Connection motako objektu bat itzultzen du,
                    // eta SQLException jaurtitzen du errorea gertatzen bada.
                    Connection cn = dataBkonexioa.konektatu();

                    // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                    // "cn != null" konexioaren balio egokia den ziurtatzeko
                    // eta "cn.isClosed()" konexioa irekita dagoen egiaztatzeko.
                    if (cn != null && !cn.isClosed()) {
                        System.out.println("Komunikazio kanala irekita dago.");

                        // **DELETE FROM EMPLEADOS** SQL kontsulta prestatzen da.
                        String kontsulta = "UPDATE MAKINA SET IZENA=?, DESKRIBAPENA=?, POTENTZIA=?, INSTALAKUNTZA_DATA=? WHERE ID_MAKINA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, balioaksartzeko.
                        agindua.setString(1, aldatzekoMakina.getIzena());
                        agindua.setString(2, aldatzekoMakina.getDeskribapena());
                        agindua.setInt(3, aldatzekoMakina.getPotentzia());
                        agindua.setDate(4, java.sql.Date.valueOf(aldatzekoMakina.getInstalakuntzaData()));
                        agindua.setInt(5, aldatzekoMakina.getId());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo langilerik ezabatu.
                        // > 0 itzultzen bada, **langilea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Makina aldatu da");
                            makinaAldatuta = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea makinaren datuak aldatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaAldatuta) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina aldatu da!");
                    alerta.showAndWait();
                    id_label_makinaZerrenda.setVisible(true);
                    ObservableList<makina> makinak = makinaZerrenda();
                    id_makinaZerrenda.setItems(makinak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina aldatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_aldatuIDMakina.setText("");
        id_aldatuIzenaMakina.setText("");
        id_aldatuDeskribapenaMakina.setText("");
        id_aldatuPotentziaMakina.setText("");
        id_aldatuInstalakuntzaDataMakina.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        // id_kenduIDMakina.setDisable(true);
        id_aldatuIzenaMakina.setDisable(true);
        id_aldatuDeskribapenaMakina.setDisable(true);
        id_aldatuPotentziaMakina.setDisable(true);
        id_aldatuInstalakuntzaDataMakina.setDisable(true);
    }

    @FXML
    void makinaBilatuAldatun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_aldatuIDMakina.getText();
        boolean makinaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<makina> makinak = makinaZerrenda();
            for (makina konzidituMakina : makinak) {
                if (konzidituMakina.getId() == idZenb) {
                    id_aldatuIzenaMakina.setText(konzidituMakina.getIzena());
                    id_aldatuDeskribapenaMakina.setText(konzidituMakina.getDeskribapena());
                    id_aldatuPotentziaMakina.setText(Integer.toString(konzidituMakina.getPotentzia()));
                    id_aldatuInstalakuntzaDataMakina
                            .setValue((LocalDate.parse(konzidituMakina.getInstalakuntzaData())));
                    id_aldatuIzenaMakina.setDisable(false);
                    id_aldatuDeskribapenaMakina.setDisable(false);
                    id_aldatuPotentziaMakina.setDisable(false);
                    id_aldatuInstalakuntzaDataMakina.setDisable(false);
                    makinaAurkitua = true;
                    return;
                }
            }
            if (!makinaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun makina ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_aldatuIDMakina.setText("");
                id_aldatuIzenaMakina.setText("");
                id_aldatuDeskribapenaMakina.setText("");
                id_aldatuPotentziaMakina.setText("");
                id_aldatuInstalakuntzaDataMakina.setValue(null);
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
            alerta.showAndWait();
        }
    }


    @FXML
    void zerrendatuMakinakPantaila(ActionEvent event) {
        
        id_imageMakina.setVisible(false);
        id_makinaKenduPantaila.setVisible(false);
        id_makinaAldatuPantaila.setVisible(false);
        id_makinaGehituPantaila.setVisible(false);
        id_makinaZerrendatuPantaila.setVisible(true);
        id_makinaInfoPantaila.setVisible(false);
        
        // Makinen zerrenda bistaratu:
        ObservableList<makina> makinak = makinaZerrenda();
        id_makinaZerrenda1.setItems(makinak);
    }


    public ObservableList<makina> makinaZerrenda() {

        ObservableList<makina> makinak = FXCollections.observableArrayList();
        // DBKonexioa klaseko objektu bat sortzen da,
        // datu-basearekin konexioa kudeatzeko.
        DBKonexioa konex = new DBKonexioa();

        try {
            // konektatu() metodoari deitzen zaio.
            // Metodo honek Connection motako objektu bat bueltatzen du,
            // eta SQLException jaurti dezake errorea gertatzen bada.
            Connection cn = konex.konektatu();

            // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
            if (cn != null && !cn.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");

                // PreparedStatement: SQL kontsulta bat prestatzen du
                // eta exekutatzeko prest dagoen objektu bat sortzen da.
                // "Prepared" deitzen da, izan ere, SQL aginduak lehenago prestatu etaondoren
                // exekutatzen direlako.
                // String motako kontsulta aldagaian "SELECT ID_MAKINA, IZENA, DESKRIBAPENA,
                // POTENTZIA, INSTALAKUNTZA_DATA FROM MAKINA" agindua gordetzen da
                String kontsulta = "SELECT ID_MAKINA, IZENA, DESKRIBAPENA, POTENTZIA, INSTALAKUNTZA_DATA FROM MAKINA";
                // Adierazitako kontsulta prestatzen da
                PreparedStatement agindua = cn.prepareStatement(kontsulta);
                // Prestatutako kontsulta exekutatzen da
                ResultSet emaitza = agindua.executeQuery();

                // ResultSet: SQL kontsultaren emaitzak jasotzen ditu.
                // "ResultSet" objektuak datu-baseko erantzunaren edukia gordetzen du.
                while (emaitza.next()) { // next() metodoak hurrengo errenkadako balioak irakurtzen ditu.
                    // makinaren id: "id_makina" zutabean dagoen balioa irakurriko da
                    // makina izena: "izena" zutabean dagoen balioa irakurriko da
                    // makina deskribapena: "deskribapena" zutabean dagoen balioa irakurriko da
                    // makina potentzia: "potentzia" zutabean dagoen balioa irakurriko da
                    // makina instalakuntzaData: "instalakuntza_data" zutabean dagoen balioa
                    // irakurriko da
                    makina irakurritakoMakina = new makina(emaitza.getInt("id_makina"), emaitza.getString("izena"),
                            emaitza.getString("deskribapena"), emaitza.getInt("potentzia"),
                            emaitza.getString("instalakuntza_data"));
                    makinak.add(irakurritakoMakina);
                }
                // ResultSet objektua itxi egiten da, memoria baliabideak askatuz.
                emaitza.close();
                // Datu-basearekiko konexioa itxi egiten da.
                cn.close();
                System.out.println("Konexioa itxi da.");
            }
        } catch (SQLException e) {
            // Salbuespena harrapatzen da kontsultan edo konexioan
            // arazoren bat gertatu bada.
            System.out.println("Errorea kontsulta exekutatzean");
            e.printStackTrace();
        }
        return makinak;
    }

//********************************************************************************************************************  
//                                                     ERABILTZAILEA  
//********************************************************************************************************************



    @FXML
    private Button id_button_aldatuErabiltzailea;
    @FXML
    private Button id_button_gehituErabiltzailea;
    @FXML
    private Button id_button_kenduErabiltzailea;
    @FXML
    private Button id_button_zerrendatuErabiltzailea;
    @FXML
    private VBox id_ezkerMenua_erabiltzailea;
    @FXML
    private ImageView id_imageErabiltzailea;
    @FXML
    private TextField id_sartuAbizena1Erabiltzailea;
    @FXML
    private DatePicker id_sartuAltaDataErabiltzailea;
    @FXML
    private TextField id_sartuEmailaErabiltzailea;
    @FXML
    private TextField id_sartuHelbideaErabiltzailea;
    @FXML
    private TextField id_sartuIDErabiltzailea;
    @FXML
    private TextField id_sartuIzenaErabiltzailea;
    @FXML
    private DatePicker id_sartuJaiotzeDataErabiltzailea;
    @FXML
    private TextField id_sartuNANErabiltzailea;
    @FXML
    private TextField id_sartuPostaKodeaErabiltzailea;
    @FXML
    private Button id_gehituErabiltzailea;
    @FXML
    private TitledPane id_erbiltzaileaGehituPantaila;   
    @FXML
    private TextField id_kenduIDErabiltzailea;
    @FXML
    private TextField id_kenduIzenaErabiltzailea;
    @FXML
    private TextField id_kenduAbizena1Erabiltzailea;
    @FXML
    private TextField id_kenduNANErabiltzailea;
    @FXML
    private TextField id_kenduHelbideaErabiltzailea;
    @FXML
    private TextField id_kenduPostaKodeaErabiltzailea;
    @FXML
    private TextField id_kenduEmailaErabiltzailea;
    @FXML
    private DatePicker id_kenduJaiotzeDataErabiltzailea;
    @FXML
    private DatePicker id_kenduAltaDataErabiltzailea;
    @FXML
    private Button id_kenduErabiltzailea;
    @FXML
    private TitledPane id_erabiltzaileaAldatuPantaila;
    @FXML
    private TitledPane id_erabiltzaileaGehituPantaila;
    @FXML
    private TitledPane id_erabiltzaileaKenduPantaila;
    @FXML
    private TextField id_aldatuIDErabiltzailea;
    @FXML
    private TextField id_aldatuIzenaErabiltzailea;
    @FXML
    private TextField id_aldatuAbizena1Erabiltzailea;
    @FXML
    private TextField id_aldatuNANErabiltzailea;
    @FXML
    private TextField id_aldatuHelbideaErabiltzailea;
    @FXML
    private TextField id_aldatuPostaKodeaErabiltzailea;
    @FXML
    private TextField id_aldatuEmailaErabiltzailea;
    @FXML
    private DatePicker id_aldatuJaiotzeDataErabiltzailea;
    @FXML
    private DatePicker id_aldatuAltaDataErabiltzailea;
    @FXML
    private Button id_aldatuErabiltzailea;
    @FXML
    private TitledPane id_erabiltzaileaZerrendatuPantaila;
    @FXML
    private ListView<makina> id_erabiltzaileaZerrenda;
    @FXML
    private ListView<makina> id_erabiltzaileaZerrenda1;
    @FXML
    private AnchorPane id_erabiltzaileaInfoPantaila;
    @FXML
    private Label id_label_erabiltzaileaZerrenda;

    @FXML
    void gehituErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void gehituErabiltzailea(ActionEvent event) {
    }
    @FXML
    void kenduErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void kenduErabiltzailea(ActionEvent event) {
    }
    @FXML
    void aldatuErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void aldatuErabiltzailea(ActionEvent event) {
    }
    @FXML
    void zerrendatuErabiltzaileakPantaila(ActionEvent event) {
    }
    




//********************************************************************************************************************  
//                                                     PIEZA  
//********************************************************************************************************************
    
    @FXML
    private VBox id_ezkerMenua_pieza;
    @FXML
    private Button id_button_gehituPieza;
    @FXML
    private Button id_button_kenduPieza;
    @FXML
    private Button id_button_aldatuPieza;
    @FXML
    private Button id_button_zerrendatuPieza;
    @FXML
    private ImageView id_imagePieza;
    @FXML
    private TextField id_sartuIDPiezaMotaPieza;
    @FXML
    private TextField id_sartuIDMakinaPieza;
    @FXML
    private TextField id_sartuIzenaPieza;
    @FXML
    private TextField id_sartuIDPieza;
    @FXML
    private TextField id_sartuPisuaPieza;
    @FXML
    private TextArea id_sartuDeskribapenaPieza;
    @FXML
    private TextField id_sartuPrezioaPieza;
    @FXML
    private TextField id_sartuStockPieza;
    @FXML
    private Button id_gehituPieza;
    @FXML
    private TitledPane id_piezaGehituPantaila;   
    @FXML
    private TextField id_kenduIDPieza;
    @FXML
    private TextField id_kenduIDPiezaMotaPieza;
    @FXML
    private TextField id_kenduIDMakinaPieza;
    @FXML
    private TextField id_kenduIzenaPieza;
    @FXML
    private TextArea id_kenduDeskribapenaPieza;
    @FXML
    private TextField id_kendPisuaPieza;
    @FXML
    private TextField id_kenduPrezioaPieza;
    @FXML
    private TextField id_kenduStockPieza;
    @FXML
    private Button id_kenduPieza;
    @FXML
    private TitledPane id_piezaKenduPantaila;    
    @FXML
    private TextField id_aldatuIDPieza;
    @FXML
    private TextField id_aldatuIDPiezaMotaPieza;
    @FXML
    private TextField id_aldatuIDMakinaPieza;
    @FXML
    private TextField id_aldatuIzenaPieza;
    @FXML
    private TextArea id_aldatuDeskribapenaPieza;
    @FXML
    private TextField id_aldatuPisuaPieza;
    @FXML
    private TextField id_aldatuPrezioaPieza;
    @FXML
    private TextField id_aldatuStockPieza;
    @FXML
    private Button id_aldatuPieza;
    @FXML
    private TitledPane id_PiezaAldatuPantaila;
     @FXML
    private TitledPane id_PiezaZerrendatuPantaila;
    @FXML
    private ListView<makina> id_piezaZerrenda;
    @FXML
    private ListView<makina> id_piezaZerrenda1;
    @FXML
    private AnchorPane id_piezaInfoPantaila;
    @FXML
    private Label id_label_piezaZerrenda;

    @FXML
    void gehituPiezaPantaila(ActionEvent event) {
    }
    @FXML
    void gehituPieza(ActionEvent event) {
    }
    @FXML
    void kenduPiezaPantaila(ActionEvent event) {
    }
    @FXML
    void kenduPieza(ActionEvent event) {
    }
    @FXML
    void aldatuPiezaPantaila(ActionEvent event) {
    }
    @FXML
    void aldatuPieza(ActionEvent event) {
    }
    @FXML
    void zerrendatuPiezakPantaila(ActionEvent event) {
    }
    


//********************************************************************************************************************  
//                                                    MAKINA - ERABILTZAILEA  
//********************************************************************************************************************


    @FXML
    private Button id_button_aldatuMakinaErabiltzailea;
    @FXML
    private Button id_button_gehituMakinaErabiltzailea;
    @FXML
    private Button id_button_kenduMakinaErabiltzailea;
    @FXML
    private Button id_button_zerrendatuMakinaErabiltzailea;
    @FXML
    private VBox id_ezkerMenua_makinaErabiltzailea;
    @FXML
    private ImageView id_imageMakinaErabiltzailea;
    @FXML
    private TextField id_sartuIDMakina1;
    @FXML
    private TextField id_sartuIDErabiltzailea1;
    @FXML
    private DatePicker id_sartuHasieraDataMakinaErabiltzailea;
    @FXML
    private DatePicker id_sartuAmaieraDataMakinaErabiltzailea;    
    @FXML
    private Button id_gehituMakinaErabiltzailea;
    @FXML
    private TitledPane id_makinaErabiltzaileaGehituPantaila;   
    @FXML
    private TextField id_kenduIDErabiltzailea1;
    @FXML
    private TextField id_kenduIDMakina1;    
    @FXML
    private DatePicker id_kenduHasieraDataMakinaErabiltzailea;
    @FXML
    private DatePicker id_kenduAmaieraDataMakinaErabiltzailea;
    @FXML
    private Button id_kenduMakinaErabiltzailea;
    @FXML
    private TitledPane id_makinaErabiltzaileaAldatuPantaila;
    @FXML
    private TitledPane id_makinaErabiltzaileaKenduPantaila;
    @FXML
    private TextField id_aldatuIDErabiltzailea1;
    @FXML
    private TextField id_aldatuIDMakina1;
    @FXML
    private DatePicker id_aldatuHasieraDataMakinaErabiltzailea;
    @FXML
    private DatePicker id_aldatuAmaieraDataMakinaErabiltzailea;
    @FXML
    private Button id_aldatuMakinaErabiltzailea;
    @FXML
    private TitledPane id_makinaErabiltzaileaZerrendatuPantaila;
    @FXML
    private ListView<makina> id_makinaErabiltzaileaZerrenda;
    @FXML
    private ListView<makina> id_makinaErabiltzaileaZerrenda1;
    @FXML
    private AnchorPane id_makinaErabiltzaileaInfoPantaila;
    @FXML
    private Label id_label_makinaErabiltzaileaZerrenda;

    @FXML
    void gehituMakinaErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void gehituMakinaErabiltzailea(ActionEvent event) {
    }
    @FXML
    void kenduMakinaErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void kenduMakinaErabiltzailea(ActionEvent event) {
    }
    @FXML
    void aldatuMakinaErabiltzaileaPantaila(ActionEvent event) {
    }
    @FXML
    void aldatuMakinaErabiltzailea(ActionEvent event) {
    }
    @FXML
    void zerrendatuMakinaErabiltzaileakPantaila(ActionEvent event) {
    }
    


    

//********************************************************************************************************************  
//                                                    PIEZA MOTA  
//********************************************************************************************************************


    @FXML
    private Button id_button_aldatuPiezaMota;
    @FXML
    private Button id_button_gehituPiezaMota;
    @FXML
    private Button id_button_kenduPiezaMota;
    @FXML
    private Button id_button_zerrendatuPiezaMota;
    @FXML
    private VBox id_ezkerMenua_piezaMota;
    @FXML
    private ImageView id_imagePiezaMotak;
    @FXML
    private TextField id_sartuIzenaPiezaMota;
    @FXML
    private TextField id_sartuIDPiezaMota;   
    @FXML
    private Button id_gehituPiezaMota;
    @FXML
    private TitledPane id_piezaMotaGehituPantaila;   
    @FXML
    private TextField id_kenduIDPiezaMota;
    @FXML
    private TextField id_kenduIzenaPiezaMota;
    @FXML
    private Button id_KenduPiezaMota;
    @FXML
    private TitledPane id_PiezaMotaAldatuPantaila;
    @FXML
    private TitledPane id_piezaMotaKenduPantaila;
    @FXML
    private TextField id_aldatuIDPiezaMota;
    @FXML
    private TextField id_aldatuIzenaPiezaMota;
    @FXML
    private Button id_aldatuPiezaMota;
    @FXML
    private TitledPane id_piezaMotaZerrendatuPantaila;
    @FXML
    private ListView<piezaMota> id_piezaMotaZerrenda;
    @FXML
    private ListView<piezaMota> id_piezaMotaZerrenda1;
    @FXML
    private AnchorPane id_piezaMotaInfoPantaila;
    @FXML
    private Label id_label_piezaMotaZerrenda;
    @FXML
    private Button id_button_piezaMotaBilatu;
    



    @FXML
    void gehituPiezaMotaPantaila(ActionEvent event) {

        id_imagePiezaMotak.setVisible(false);
        id_piezaMotaKenduPantaila.setVisible(false);
        id_PiezaMotaAldatuPantaila.setVisible(false);
        id_piezaMotaGehituPantaila.setVisible(true);
        id_piezaMotaZerrendatuPantaila.setVisible(false);
        id_label_piezaMotaZerrenda.setVisible(false);
        ObservableList<piezaMota> piezaMotak = FXCollections.observableArrayList();
        // Pantaila garbitu, textfieldak
        id_piezaMotaZerrenda.setItems(piezaMotak);
        id_sartuIzenaPiezaMota.setText("");
    }
    @FXML
    void gehituPiezaMota(ActionEvent event) {
        // escenetik pieza motaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean piezaMotaTxertatua = false;
        String id = id_sartuIDPiezaMota.getText();
        String izena = id_sartuIzenaPiezaMota.getText();
        
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty()) {
            // piezaMotaBerria objetua sortu
            try {
                piezaMota piezaMotaBerria = new piezaMota(Integer.parseInt(id), izena);
                // DBKonexioa klaseko objektu bat sortzen da, datu-basearekin konexioa
                // kudeatzeko.
                DBKonexioa dataBkonexioa = new DBKonexioa();

                try {
                    // konektatu() metodoari deitzen zaio.
                    // Metodo honek Connection motako objektu bat itzultzen du,
                    // eta SQLException jaurtitzen du errorea gertatzen bada.
                    Connection konexioa = dataBkonexioa.konektatu();
                    // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                    if (konexioa != null && !konexioa.isClosed()) {
                        System.out.println("Komunikazio kanala irekita dago.");
                        // **INSERT INTO ERABILTZAILEA** SQL kontsulta prestatzen da
                        String kontsulta = "INSERT INTO PIEZA_MOTA (ID_PIEZA_MOTA,IZENA) VALUES(?,?)";
                        PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                        agindua.setInt(1, piezaMotaBerria.getId_piezaMota());
                        agindua.setString(2, piezaMotaBerria.getIzena());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                        if (emaitza == 1) {
                            System.out.println("Pieza mota berria datu basean txertatu da");
                            piezaMotaTxertatua = true;
                        }
                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        konexioa.close();
                    }
                } catch (SQLException e) {
                    // Salbuespen bat sortzen da SQL aginduak exekutatzen direnean errore bat
                    // gertatuz gero.
                    // Adibidez, datuak sartzeko parametroak okerrak izan daitezke edo taula ez
                    // egon.
                    System.out.println("Errorea pieza motaren datuak sortzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (piezaMotaTxertatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Pieza mota berria txertatuta!");
                    alerta.showAndWait();
                    id_label_piezaMotaZerrenda.setVisible(true);
                    ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
                    id_piezaMotaZerrenda.setItems(piezaMotak);
                    id_piezaMotaInfoPantaila.setVisible(true);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da pieza mota berria datu basean txertatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_sartuIDPiezaMota.setText("");
        id_sartuIzenaPiezaMota.setText("");
    }




    @FXML
    void kenduPiezaMotaPantaila(ActionEvent event) {
        id_imagePiezaMotak.setVisible(false);
        id_piezaMotaKenduPantaila.setVisible(true);
        id_PiezaMotaAldatuPantaila.setVisible(false);
        id_piezaMotaGehituPantaila.setVisible(false);
        id_piezaMotaZerrendatuPantaila.setVisible(false);
        id_piezaMotaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_kenduIDPiezaMota.setText("");
        id_kenduIzenaPiezaMota.setText("");
        // Sarreran ez utzi idazten usuarioari
        id_kenduIzenaPiezaMota.setDisable(true);
        // Makinen zerrenda bistaratu:
        id_label_piezaMotaZerrenda.setVisible(true);
        ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
        id_piezaMotaZerrenda.setItems(piezaMotak);
        // Aukeratutako pieza mota jaso
        id_piezaMotaZerrenda.getSelectionModel().selectedItemProperty().addListener((obs, aurrekoAukera, aukeraBerria) -> {
            id_kenduIDPiezaMota.setText(Integer.toString(aukeraBerria.getId_piezaMota()));
            id_kenduIzenaPiezaMota.setText(aukeraBerria.getIzena());
        });
        // Pieza motaren IDa sartzean (textField en fokua aldatzean) datu basean aurkitu
        // pieza mota existitzen den
        id_kenduIDPiezaMota.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDPiezaMota textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_kenduIDPiezaMota.setText("");
                id_kenduIzenaPiezaMota.setText("");
            }
        });
    }





    @FXML
    void kenduPiezaMota(ActionEvent event) {
         // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean piezaMotaEzabatua = false;
        String id = id_kenduIDPiezaMota.getText();
        String izena = id_kenduIzenaPiezaMota.getText();
        
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty()) {
            // piezaMotaBerria objetua sortu
            try {
                piezaMota kentzekoPiezaMota = new piezaMota(Integer.parseInt(id), izena);
                // DBKonexioa klaseko objektu bat sortzen da, datu-basearekin konexioa
                // kudeatzeko.
                DBKonexioa dataBkonexioa = new DBKonexioa();

                try {
                    // konektatu() metodoari deitzen zaio.
                    // Metodo honek Connection motako objektu bat itzultzen du,
                    // eta SQLException jaurtitzen du errorea gertatzen bada.
                    Connection cn = dataBkonexioa.konektatu();

                    // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                    // "cn != null" konexioaren balio egokia den ziurtatzeko
                    // eta "cn.isClosed()" konexioa irekita dagoen egiaztatzeko.
                    if (cn != null && !cn.isClosed()) {
                        System.out.println("Komunikazio kanala irekita dago.");

                        // **DELETE FROM PIEZA_MOTA** SQL kontsulta prestatzen da.
                        String kontsulta = "DELETE FROM PIEZA_MOTA WHERE ID_PIEZA_MOTA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroa prestatzen da, "id_makina" balioa sartzeko.
                        agindua.setInt(1, kentzekoPiezaMota.getId_piezaMota());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo pieza motarik ezabatu.
                        // > 0 itzultzen bada, **pieza mota ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Pieza mota ezabatu da");
                            piezaMotaEzabatua = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea pieza motaren datuak ezabatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (piezaMotaEzabatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Pieza mota ezabatu da datu basetik!");
                    alerta.showAndWait();
                    id_label_piezaMotaZerrenda.setVisible(true);
                    ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
                    id_piezaMotaZerrenda.setItems(piezaMotak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da pieza mota ezabatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_kenduIDPiezaMota.setText("");
        id_kenduIzenaPiezaMota.setText("");
    }


    

    @FXML
    void piezaMotaBilatuKendun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_kenduIDPiezaMota.getText();
        boolean piezaMotaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
            for (piezaMota konzidituPiezaMota : piezaMotak) {
                if (konzidituPiezaMota.getId_piezaMota() == idZenb) {
                    id_kenduIzenaPiezaMota.setText(konzidituPiezaMota.getIzena());
                    piezaMotaAurkitua = true;
                    return;
                }
            }
            if (!piezaMotaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun pieza mota ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_kenduIDPiezaMota.setText("");
                id_kenduIzenaPiezaMota.setText("");
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Pieza motaren IDa zenbaki osoa izan behar da");
            alerta.showAndWait();
        }
    }




    @FXML
    void aldatuPiezaMotaPantaila(ActionEvent event) {
    }
    @FXML
    void aldatuPiezaMota(ActionEvent event) {
    }
    @FXML
    void zerrendatuPiezaMotakPantaila(ActionEvent event) {
    }
    



    public ObservableList<piezaMota> piezaMotaZerrenda() {

        ObservableList<piezaMota> piezaMotak = FXCollections.observableArrayList();
        // DBKonexioa klaseko objektu bat sortzen da,
        // datu-basearekin konexioa kudeatzeko.
        DBKonexioa konex = new DBKonexioa();

        try {
            // konektatu() metodoari deitzen zaio.
            // Metodo honek Connection motako objektu bat bueltatzen du,
            // eta SQLException jaurti dezake errorea gertatzen bada.
            Connection cn = konex.konektatu();

            // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
            if (cn != null && !cn.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");

                // PreparedStatement: SQL kontsulta bat prestatzen du
                // eta exekutatzeko prest dagoen objektu bat sortzen da.
                // "Prepared" deitzen da, izan ere, SQL aginduak lehenago prestatu etaondoren
                // exekutatzen direlako.
                // String motako kontsulta aldagaian "SELECT ID_MAKINA, IZENA, DESKRIBAPENA,
                // POTENTZIA, INSTALAKUNTZA_DATA FROM MAKINA" agindua gordetzen da
                String kontsulta = "SELECT ID_PIEZA_MOTA, IZENA FROM PIEZA_MOTA";
                // Adierazitako kontsulta prestatzen da
                PreparedStatement agindua = cn.prepareStatement(kontsulta);
                // Prestatutako kontsulta exekutatzen da
                ResultSet emaitza = agindua.executeQuery();

                // ResultSet: SQL kontsultaren emaitzak jasotzen ditu.
                // "ResultSet" objektuak datu-baseko erantzunaren edukia gordetzen du.
                while (emaitza.next()) { // next() metodoak hurrengo errenkadako balioak irakurtzen ditu.
                    // makinaren id: "id_makina" zutabean dagoen balioa irakurriko da
                    // makina izena: "izena" zutabean dagoen balioa irakurriko da
                    // makina deskribapena: "deskribapena" zutabean dagoen balioa irakurriko da
                    // makina potentzia: "potentzia" zutabean dagoen balioa irakurriko da
                    // makina instalakuntzaData: "instalakuntza_data" zutabean dagoen balioa
                    // irakurriko da
                    piezaMota irakurritakoPiezaMota = new piezaMota(emaitza.getInt("id_pieza_mota"), emaitza.getString("izena"));
                    piezaMotak.add(irakurritakoPiezaMota);
                }
                // ResultSet objektua itxi egiten da, memoria baliabideak askatuz.
                emaitza.close();
                // Datu-basearekiko konexioa itxi egiten da.
                cn.close();
                System.out.println("Konexioa itxi da.");
            }
        } catch (SQLException e) {
            // Salbuespena harrapatzen da kontsultan edo konexioan
            // arazoren bat gertatu bada.
            System.out.println("Errorea kontsulta exekutatzean");
            e.printStackTrace();
        }
        return piezaMotak;
    }






}
