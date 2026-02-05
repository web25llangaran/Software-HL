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
import programa.model.erabiltzailea;
import programa.model.makina;
import programa.model.makinaErabiltzailea;
import programa.model.piezaMota;

public class MaltunaBrandController {

    // ********************************************************************************************************************
    // MAKINA
    // ********************************************************************************************************************
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

    // ********************************************************************************************************************
    // ERABILTZAILEA
    // ********************************************************************************************************************

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
    private ListView<erabiltzailea> id_erabiltzaileaZerrenda;
    @FXML
    private ListView<erabiltzailea> id_erabiltzaileaZerrenda1;
    @FXML
    private AnchorPane id_erabiltzaileaInfoPantaila;
    @FXML
    private Label id_label_erabiltzaileaZerrenda;
    @FXML
    private Button id_button_ErabiltzaileaBilatu;
    @FXML
    private Button id_button_ErabiltzaileaBilatu1;

    @FXML
    void gehituErabiltzaileaPantaila(ActionEvent event) {
        id_imageErabiltzailea.setVisible(false);
        id_erabiltzaileaKenduPantaila.setVisible(false);
        id_erabiltzaileaAldatuPantaila.setVisible(false);
        id_erabiltzaileaGehituPantaila.setVisible(true);
        id_erabiltzaileaZerrendatuPantaila.setVisible(false);
        id_label_erabiltzaileaZerrenda.setVisible(false);
        ObservableList<erabiltzailea> erabiltzaileak = FXCollections.observableArrayList();
        // Pantaila garbitu, textfieldak
        id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
        id_sartuIDErabiltzailea.setText("");
        id_sartuIzenaErabiltzailea.setText("");
        id_sartuAbizena1Erabiltzailea.setText("");
        id_sartuNANErabiltzailea.setText("");
        id_sartuHelbideaErabiltzailea.setText("");
        id_sartuPostaKodeaErabiltzailea.setText("");
        id_sartuEmailaErabiltzailea.setText("");
        id_sartuJaiotzeDataErabiltzailea.setValue(null);
        id_sartuAltaDataErabiltzailea.setValue(null);
    }

    @FXML
    void gehituErabiltzailea(ActionEvent event) {
        // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean erabiltzaileaTxertatua = false;
        String id = id_sartuIDErabiltzailea.getText();
        String izena = id_sartuIzenaErabiltzailea.getText();
        String abizena1 = id_sartuAbizena1Erabiltzailea.getText();
        String nan = id_sartuNANErabiltzailea.getText();
        String helbidea = id_sartuHelbideaErabiltzailea.getText();
        String posta_kodea = id_sartuPostaKodeaErabiltzailea.getText();
        String emaila = id_sartuEmailaErabiltzailea.getText();
        LocalDate jaiotzeData = id_sartuJaiotzeDataErabiltzailea.getValue();
        LocalDate altaData = id_sartuAltaDataErabiltzailea.getValue();
        // Jaiotze data eta alta data Stringera pasa
        String jaiotze_data = "";
        String alta_data = "";
        if (jaiotzeData != null) {
            jaiotze_data = jaiotzeData.toString();
        }
        if (altaData != null) {
            alta_data = altaData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !abizena1.isEmpty() && !nan.isEmpty() && !helbidea.isEmpty()
                && !posta_kodea.isEmpty()
                && !emaila.isEmpty() && !jaiotze_data.isEmpty() && !alta_data.isEmpty()) {
            if (posta_kodea.length() == 5) {
                if (emaila.contains("@")) {
                    // erabiltzaileBerria objetua sortu
                    try {
                        erabiltzailea erabiltzaileBerria = new erabiltzailea(Integer.parseInt(id), izena, abizena1, nan,
                                helbidea, Integer.parseInt(posta_kodea), emaila, jaiotze_data, alta_data);
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
                                String kontsulta = "INSERT INTO ERABILTZAILEA (ID_ERABILTZAILEA,IZENA,ABIZENA1,NAN,HELBIDEA,POSTA_KODEA,EMAILA,JAIOTZE_DATA,ALTA_DATA) VALUES(?,?,?,?,?,?,?,?,?)";
                                System.out.println(kontsulta);
                                PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                                // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                                agindua.setInt(1, erabiltzaileBerria.getId_erabiltzailea());
                                agindua.setString(2, erabiltzaileBerria.getIzena());
                                agindua.setString(3, erabiltzaileBerria.getAbizena1());
                                agindua.setString(4, erabiltzaileBerria.getNan());
                                agindua.setString(5, erabiltzaileBerria.getHelbidea());
                                agindua.setString(6, Integer.toString(erabiltzaileBerria.getPosta_kodea()));
                                agindua.setString(7, erabiltzaileBerria.getEmaila());
                                agindua.setDate(8, java.sql.Date.valueOf(erabiltzaileBerria.getJaiotze_data()));
                                agindua.setDate(9, java.sql.Date.valueOf(erabiltzaileBerria.getAlta_data()));

                                // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                                // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                                int emaitza = agindua.executeUpdate();

                                // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                                if (emaitza == 1) {
                                    System.out.println("Erabiltzaile berria datu basean txertatu da");
                                    erabiltzaileaTxertatua = true;
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
                        if (erabiltzaileaTxertatua) {
                            alerta.setTitle("INFO");
                            alerta.setHeaderText(null);
                            alerta.setContentText("Erabiltzaile berria txertatuta!");
                            alerta.showAndWait();
                            id_label_erabiltzaileaZerrenda.setVisible(true);
                            ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
                            id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
                            id_erabiltzaileaInfoPantaila.setVisible(true);
                        } else {
                            alerta.setTitle("ADI !");
                            alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                            alerta.setContentText("Ezin izan da erabiltzaile berria datu basean txertatu");
                            alerta.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        alerta.setTitle("ADI !");
                        alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                        alerta.setContentText("Erabiltzailearen IDa eta posta kodea zenbaki osoak izan behar dira");
                        alerta.showAndWait();
                    }
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                    alerta.setContentText("Erabiltzailearen emaila ez da egokia");
                    alerta.showAndWait();
                }
            } else {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Erabiltzailearen posta kodeak bost digitu izan behar ditu");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_sartuIDErabiltzailea.setText("");
        id_sartuIzenaErabiltzailea.setText("");
        id_sartuAbizena1Erabiltzailea.setText("");
        id_sartuNANErabiltzailea.setText("");
        id_sartuHelbideaErabiltzailea.setText("");
        id_sartuPostaKodeaErabiltzailea.setText("");
        id_sartuEmailaErabiltzailea.setText("");
        id_sartuJaiotzeDataErabiltzailea.setValue(null);
        id_sartuAltaDataErabiltzailea.setValue(null);
    }

    @FXML
    void kenduErabiltzaileaPantaila(ActionEvent event) {
        id_imageErabiltzailea.setVisible(false);
        id_erabiltzaileaKenduPantaila.setVisible(true);
        id_erabiltzaileaAldatuPantaila.setVisible(false);
        id_erabiltzaileaGehituPantaila.setVisible(false);
        id_erabiltzaileaZerrendatuPantaila.setVisible(false);
        id_erabiltzaileaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_kenduIDErabiltzailea.setText("");
        id_kenduIzenaErabiltzailea.setText("");
        id_kenduAbizena1Erabiltzailea.setText("");
        id_kenduNANErabiltzailea.setText("");
        id_kenduHelbideaErabiltzailea.setText("");
        id_kenduPostaKodeaErabiltzailea.setText("");
        id_kenduEmailaErabiltzailea.setText("");
        id_kenduJaiotzeDataErabiltzailea.setValue(null);
        id_kenduAltaDataErabiltzailea.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        id_kenduIzenaErabiltzailea.setDisable(true);
        id_kenduAbizena1Erabiltzailea.setDisable(true);
        id_kenduNANErabiltzailea.setDisable(true);
        id_kenduHelbideaErabiltzailea.setDisable(true);
        id_kenduPostaKodeaErabiltzailea.setDisable(true);
        id_kenduEmailaErabiltzailea.setDisable(true);
        id_kenduJaiotzeDataErabiltzailea.setDisable(true);
        id_kenduAltaDataErabiltzailea.setDisable(true);
        // Erabiltzailearen zerrenda bistaratu:
        id_label_erabiltzaileaZerrenda.setVisible(true);
        ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
        id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
        // Aukeratutako erabiltzailea jaso
        id_erabiltzaileaZerrenda.getSelectionModel().selectedItemProperty()
                .addListener((obs, aurrekoAukera, aukeraBerria) -> {
                    id_kenduIDErabiltzailea.setText(Integer.toString(aukeraBerria.getId_erabiltzailea()));
                    id_kenduIzenaErabiltzailea.setText(aukeraBerria.getIzena());
                    id_kenduAbizena1Erabiltzailea.setText(aukeraBerria.getAbizena1());
                    id_kenduNANErabiltzailea.setText(aukeraBerria.getNan());
                    id_kenduHelbideaErabiltzailea.setText(aukeraBerria.getHelbidea());
                    id_kenduPostaKodeaErabiltzailea.setText(Integer.toString(aukeraBerria.getPosta_kodea()));
                    id_kenduEmailaErabiltzailea.setText(aukeraBerria.getEmaila());
                    id_kenduJaiotzeDataErabiltzailea.setValue(LocalDate.parse(aukeraBerria.getJaiotze_data()));
                    id_kenduAltaDataErabiltzailea.setValue(LocalDate.parse(aukeraBerria.getAlta_data()));
                });
        // Erabiltzailearen IDa sartzean (textField en fokua aldatzean) datu basean
        // aurkitu
        // makina existitzen den
        id_kenduIDErabiltzailea.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_kenduIDErabiltzailea.setText("");
                id_kenduIzenaErabiltzailea.setText("");
                id_kenduAbizena1Erabiltzailea.setText("");
                id_kenduNANErabiltzailea.setText("");
                id_kenduHelbideaErabiltzailea.setText("");
                id_kenduPostaKodeaErabiltzailea.setText("");
                id_kenduEmailaErabiltzailea.setText("");
                id_kenduJaiotzeDataErabiltzailea.setValue(null);
                id_kenduAltaDataErabiltzailea.setValue(null);
            }
        });

    }

    @FXML
    void kenduErabiltzailea(ActionEvent event) {
        // escenetik erabiltzailearen datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean erabiltzaileaEzabatua = false;
        String id = id_kenduIDErabiltzailea.getText();
        String izena = id_kenduIzenaErabiltzailea.getText();
        String abizena1 = id_kenduAbizena1Erabiltzailea.getText();
        String nan = id_kenduNANErabiltzailea.getText();
        String helbidea = id_kenduHelbideaErabiltzailea.getText();
        String posta_kodea = id_kenduPostaKodeaErabiltzailea.getText();
        String emaila = id_kenduEmailaErabiltzailea.getText();
        LocalDate jaiotzeData = id_kenduJaiotzeDataErabiltzailea.getValue();
        LocalDate altaData = id_kenduAltaDataErabiltzailea.getValue();
        // Jaiotze data eta alta data Stringera pasa
        String jaiotze_data = "";
        String alta_data = "";
        if (jaiotzeData != null) {
            jaiotze_data = jaiotzeData.toString();
        }
        if (altaData != null) {
            alta_data = altaData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !abizena1.isEmpty() && !nan.isEmpty() && !helbidea.isEmpty()
                && !posta_kodea.isEmpty()
                && !emaila.isEmpty() && !jaiotze_data.isEmpty() && !alta_data.isEmpty()) {
            try {
                erabiltzailea kentzekoErabiltzailea = new erabiltzailea(Integer.parseInt(id), izena, abizena1, nan,
                        helbidea, Integer.parseInt(posta_kodea), emaila, jaiotze_data, alta_data);
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

                        // **DELETE FROM ERABILTZAILEA** SQL kontsulta prestatzen da.
                        String kontsulta = "DELETE FROM ERABILTZAILEA WHERE ID_ERABILTZAILEA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroa prestatzen da, "id_erabiltzailea" balioa sartzeko.
                        agindua.setInt(1, kentzekoErabiltzailea.getId_erabiltzailea());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo langilerik ezabatu.
                        // > 0 itzultzen bada, **langilea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Erabiltzailea ezabatu da");
                            erabiltzaileaEzabatua = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea erabiltzailearen datuak ezabatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (erabiltzaileaEzabatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Erabiltzailea, ezabatu da datu basetik!");
                    alerta.showAndWait();
                    id_label_erabiltzaileaZerrenda.setVisible(true);
                    ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
                    id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da erabiltzailea ezabatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Erabiltzailearen IDa eta posta kodea zenbaki osoak izan behar dira");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_kenduIDErabiltzailea.setText("");
        id_kenduIzenaErabiltzailea.setText("");
        id_kenduAbizena1Erabiltzailea.setText("");
        id_kenduNANErabiltzailea.setText("");
        id_kenduHelbideaErabiltzailea.setText("");
        id_kenduPostaKodeaErabiltzailea.setText("");
        id_kenduEmailaErabiltzailea.setText("");
        id_kenduJaiotzeDataErabiltzailea.setValue(null);
        id_kenduAltaDataErabiltzailea.setValue(null);
    }

    @FXML
    void ErabiltzaileaBilatuKendun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_kenduIDErabiltzailea.getText();
        boolean erabiltzaileaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
            for (erabiltzailea konzidituMakina : erabiltzaileak) {
                if (konzidituMakina.getId_erabiltzailea() == idZenb) {
                    id_kenduIzenaErabiltzailea.setText(konzidituMakina.getIzena());
                    id_kenduAbizena1Erabiltzailea.setText(konzidituMakina.getAbizena1());
                    id_kenduNANErabiltzailea.setText(konzidituMakina.getNan());
                    id_kenduHelbideaErabiltzailea.setText(konzidituMakina.getHelbidea());
                    id_kenduPostaKodeaErabiltzailea.setText(Integer.toString(konzidituMakina.getPosta_kodea()));
                    id_kenduEmailaErabiltzailea.setText(konzidituMakina.getEmaila());
                    id_kenduJaiotzeDataErabiltzailea.setValue(LocalDate.parse(konzidituMakina.getJaiotze_data()));
                    id_kenduAltaDataErabiltzailea.setValue(LocalDate.parse(konzidituMakina.getAlta_data()));
                    erabiltzaileaAurkitua = true;
                    return;
                }
            }
            if (!erabiltzaileaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun erabiltzailea ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_kenduIDErabiltzailea.setText("");
                id_kenduIzenaErabiltzailea.setText("");
                id_kenduAbizena1Erabiltzailea.setText("");
                id_kenduNANErabiltzailea.setText("");
                id_kenduHelbideaErabiltzailea.setText("");
                id_kenduPostaKodeaErabiltzailea.setText("");
                id_kenduEmailaErabiltzailea.setText("");
                id_kenduJaiotzeDataErabiltzailea.setValue(null);
                id_kenduAltaDataErabiltzailea.setValue(null);
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Erabiltzailearen IDa eta posta kodea zenbaki osoak izan behar dira");
            alerta.showAndWait();
        }
    }

    @FXML
    void aldatuErabiltzaileaPantaila(ActionEvent event) {
        id_imageErabiltzailea.setVisible(false);
        id_erabiltzaileaKenduPantaila.setVisible(false);
        id_erabiltzaileaAldatuPantaila.setVisible(true);
        id_erabiltzaileaGehituPantaila.setVisible(false);
        id_erabiltzaileaZerrendatuPantaila.setVisible(false);
        id_erabiltzaileaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_aldatuIDErabiltzailea.setText("");
        id_aldatuIzenaErabiltzailea.setText("");
        id_aldatuAbizena1Erabiltzailea.setText("");
        id_aldatuNANErabiltzailea.setText("");
        id_aldatuHelbideaErabiltzailea.setText("");
        id_aldatuPostaKodeaErabiltzailea.setText("");
        id_aldatuEmailaErabiltzailea.setText("");
        id_aldatuJaiotzeDataErabiltzailea.setValue(null);
        id_aldatuAltaDataErabiltzailea.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        id_aldatuIzenaErabiltzailea.setDisable(true);
        id_aldatuAbizena1Erabiltzailea.setDisable(true);
        id_aldatuNANErabiltzailea.setDisable(true);
        id_aldatuHelbideaErabiltzailea.setDisable(true);
        id_aldatuPostaKodeaErabiltzailea.setDisable(true);
        id_aldatuEmailaErabiltzailea.setDisable(true);
        id_aldatuJaiotzeDataErabiltzailea.setDisable(true);
        id_aldatuAltaDataErabiltzailea.setDisable(true);
        // Erabiltzaile zerrenda bistaratu:
        id_label_erabiltzaileaZerrenda.setVisible(true);
        ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
        id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
        // Aukeratutako erabiltzailea jaso
        id_erabiltzaileaZerrenda.getSelectionModel().selectedItemProperty()
                .addListener((obs, aurrekoAukera, aukeraBerria) -> {
                    id_aldatuIDErabiltzailea.setText(Integer.toString(aukeraBerria.getId_erabiltzailea()));
                    id_aldatuIzenaErabiltzailea.setText(aukeraBerria.getIzena());
                    id_aldatuAbizena1Erabiltzailea.setText(aukeraBerria.getAbizena1());
                    id_aldatuNANErabiltzailea.setText(aukeraBerria.getNan());
                    id_aldatuHelbideaErabiltzailea.setText(aukeraBerria.getHelbidea());
                    id_aldatuPostaKodeaErabiltzailea.setText(Integer.toString(aukeraBerria.getPosta_kodea()));
                    id_aldatuEmailaErabiltzailea.setText(aukeraBerria.getEmaila());
                    id_aldatuJaiotzeDataErabiltzailea.setValue(LocalDate.parse(aukeraBerria.getJaiotze_data()));
                    id_aldatuAltaDataErabiltzailea.setValue(LocalDate.parse(aukeraBerria.getAlta_data()));
                    id_aldatuIzenaErabiltzailea.setDisable(false);
                    id_aldatuAbizena1Erabiltzailea.setDisable(false);
                    id_aldatuNANErabiltzailea.setDisable(false);
                    id_aldatuHelbideaErabiltzailea.setDisable(false);
                    id_aldatuPostaKodeaErabiltzailea.setDisable(false);
                    id_aldatuEmailaErabiltzailea.setDisable(false);
                    id_aldatuJaiotzeDataErabiltzailea.setDisable(false);
                    id_aldatuAltaDataErabiltzailea.setDisable(false);
                });
        // Erabiltzailearen IDa sartzean (textField en fokua aldatzean) datu basean
        // aurkitu
        // erabiltzailea existitzen den
        id_aldatuIDErabiltzailea.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_aldatuIDErabiltzailea textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_aldatuIDErabiltzailea.setText("");
                id_aldatuIzenaErabiltzailea.setText("");
                id_aldatuAbizena1Erabiltzailea.setText("");
                id_aldatuNANErabiltzailea.setText("");
                id_aldatuHelbideaErabiltzailea.setText("");
                id_aldatuPostaKodeaErabiltzailea.setText("");
                id_aldatuEmailaErabiltzailea.setText("");
                id_aldatuJaiotzeDataErabiltzailea.setValue(null);
                id_aldatuAltaDataErabiltzailea.setValue(null);
                id_aldatuIzenaErabiltzailea.setDisable(false);
                id_aldatuAbizena1Erabiltzailea.setDisable(false);
                id_aldatuNANErabiltzailea.setDisable(false);
                id_aldatuHelbideaErabiltzailea.setDisable(false);
                id_aldatuPostaKodeaErabiltzailea.setDisable(false);
                id_aldatuEmailaErabiltzailea.setDisable(false);
                id_aldatuJaiotzeDataErabiltzailea.setDisable(false);
                id_aldatuAltaDataErabiltzailea.setDisable(false);
            }
        });
    }

    @FXML
    void aldatuErabiltzailea(ActionEvent event) {
        // escenetik erabiltzailearen datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean erabiltzaileAldatuta = false;
        String id = id_aldatuIDErabiltzailea.getText();
        String izena = id_aldatuIzenaErabiltzailea.getText();
        String abizena1 = id_aldatuAbizena1Erabiltzailea.getText();
        String nan = id_aldatuNANErabiltzailea.getText();
        String helbidea = id_aldatuHelbideaErabiltzailea.getText();
        String posta_kodea = id_aldatuPostaKodeaErabiltzailea.getText();
        String emaila = id_aldatuEmailaErabiltzailea.getText();
        LocalDate jaiotzeData = id_aldatuJaiotzeDataErabiltzailea.getValue();
        LocalDate altaData = id_aldatuAltaDataErabiltzailea.getValue();
        // Jaiotze data eta alta data Stringera pasa
        String jaiotze_data = "";
        String alta_data = "";
        if (jaiotzeData != null) {
            jaiotze_data = jaiotzeData.toString();
        }
        if (altaData != null) {
            alta_data = altaData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty() && !abizena1.isEmpty() && !nan.isEmpty() && !helbidea.isEmpty()
                && !posta_kodea.isEmpty()
                && !emaila.isEmpty() && !jaiotze_data.isEmpty() && !alta_data.isEmpty()) {
            try {
                erabiltzailea aldatzekoErabiltzailea = new erabiltzailea(Integer.parseInt(id), izena, abizena1, nan,
                        helbidea, Integer.parseInt(posta_kodea), emaila, jaiotze_data, alta_data);
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

                        // **UPDATE ERABILTZAILEA SET...** SQL kontsulta prestatzen da.
                        String kontsulta = "UPDATE ERABILTZAILEA SET IZENA=?, ABIZENA1=?, NAN=?, HELBIDEA=?, POSTA_KODEA=?, EMAILA=?, JAIOTZE_DATA=?, ALTA_DATA=? WHERE ID_ERABILTZAILEA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                        agindua.setString(1, aldatzekoErabiltzailea.getIzena());
                        agindua.setString(2, aldatzekoErabiltzailea.getAbizena1());
                        agindua.setString(3, aldatzekoErabiltzailea.getNan());
                        agindua.setString(4, aldatzekoErabiltzailea.getHelbidea());
                        agindua.setString(5, Integer.toString(aldatzekoErabiltzailea.getPosta_kodea()));
                        agindua.setString(6, aldatzekoErabiltzailea.getEmaila());
                        agindua.setDate(7, java.sql.Date.valueOf(aldatzekoErabiltzailea.getJaiotze_data()));
                        agindua.setDate(8, java.sql.Date.valueOf(aldatzekoErabiltzailea.getAlta_data()));
                        agindua.setInt(9, aldatzekoErabiltzailea.getId_erabiltzailea());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // > 0 itzultzen bada, **erabilltzailea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Erabiltzailea aldatu da");
                            erabiltzaileAldatuta = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea erabiltzailearen datuak aldatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (erabiltzaileAldatuta) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Erabiltzailea aldatu da!");
                    alerta.showAndWait();
                    id_label_makinaZerrenda.setVisible(true);
                    ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
                    id_erabiltzaileaZerrenda.setItems(erabiltzaileak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da erabiltzailea aldatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Erabiltzailearen IDa eta kode postala zenbaki osoak izan behar dira");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_aldatuIDErabiltzailea.setText("");
        id_aldatuIzenaErabiltzailea.setText("");
        id_aldatuAbizena1Erabiltzailea.setText("");
        id_aldatuNANErabiltzailea.setText("");
        id_aldatuHelbideaErabiltzailea.setText("");
        id_aldatuPostaKodeaErabiltzailea.setText("");
        id_aldatuEmailaErabiltzailea.setText("");
        id_aldatuJaiotzeDataErabiltzailea.setValue(null);
        id_aldatuAltaDataErabiltzailea.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        id_aldatuIzenaErabiltzailea.setDisable(false);
        id_aldatuAbizena1Erabiltzailea.setDisable(false);
        id_aldatuNANErabiltzailea.setDisable(false);
        id_aldatuHelbideaErabiltzailea.setDisable(false);
        id_aldatuPostaKodeaErabiltzailea.setDisable(false);
        id_aldatuEmailaErabiltzailea.setDisable(false);
        id_aldatuJaiotzeDataErabiltzailea.setDisable(false);
        id_aldatuAltaDataErabiltzailea.setDisable(false);
    }

    @FXML
    void ErabiltzaileaBilatuAldatun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_aldatuIDErabiltzailea.getText();
        boolean erabiltzaileaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
            for (erabiltzailea konzidituErabiltzailea : erabiltzaileak) {
                if (konzidituErabiltzailea.getId_erabiltzailea() == idZenb) {
                    id_aldatuIzenaErabiltzailea.setText(konzidituErabiltzailea.getIzena());
                    id_aldatuAbizena1Erabiltzailea.setText(konzidituErabiltzailea.getAbizena1());
                    id_aldatuNANErabiltzailea.setText(konzidituErabiltzailea.getNan());
                    id_aldatuHelbideaErabiltzailea.setText(konzidituErabiltzailea.getHelbidea());
                    id_aldatuPostaKodeaErabiltzailea.setText(Integer.toString(konzidituErabiltzailea.getPosta_kodea()));
                    id_aldatuEmailaErabiltzailea.setText(konzidituErabiltzailea.getEmaila());
                    id_aldatuJaiotzeDataErabiltzailea
                            .setValue(LocalDate.parse(konzidituErabiltzailea.getJaiotze_data()));
                    id_aldatuAltaDataErabiltzailea.setValue(LocalDate.parse(konzidituErabiltzailea.getAlta_data()));
                    id_aldatuIzenaErabiltzailea.setDisable(false);
                    id_aldatuAbizena1Erabiltzailea.setDisable(false);
                    id_aldatuNANErabiltzailea.setDisable(false);
                    id_aldatuHelbideaErabiltzailea.setDisable(false);
                    id_aldatuPostaKodeaErabiltzailea.setDisable(false);
                    id_aldatuEmailaErabiltzailea.setDisable(false);
                    id_aldatuJaiotzeDataErabiltzailea.setDisable(false);
                    id_aldatuAltaDataErabiltzailea.setDisable(false);
                    erabiltzaileaAurkitua = true;
                    return;
                }
            }
            if (!erabiltzaileaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun erabiltzailea ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_aldatuIDErabiltzailea.setText("");
                id_aldatuIzenaErabiltzailea.setText("");
                id_aldatuAbizena1Erabiltzailea.setText("");
                id_aldatuNANErabiltzailea.setText("");
                id_aldatuHelbideaErabiltzailea.setText("");
                id_aldatuPostaKodeaErabiltzailea.setText("");
                id_aldatuEmailaErabiltzailea.setText("");
                id_aldatuJaiotzeDataErabiltzailea.setValue(null);
                id_aldatuAltaDataErabiltzailea.setValue(null);
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Erabiltzaileare IDa eta posta kodea zenbaki osoak izan behar dira");
            alerta.showAndWait();
        }
    }

    @FXML
    void zerrendatuErabiltzaileakPantaila(ActionEvent event) {
        id_imageErabiltzailea.setVisible(false);
        id_erabiltzaileaKenduPantaila.setVisible(false);
        id_erabiltzaileaAldatuPantaila.setVisible(true);
        id_erabiltzaileaGehituPantaila.setVisible(false);
        id_erabiltzaileaZerrendatuPantaila.setVisible(true);
        id_erabiltzaileaInfoPantaila.setVisible(false);

        // Erabiltzaile zerrenda bistaratu:
        ObservableList<erabiltzailea> erabiltzaileak = erabiltzaileZerrenda();
        id_erabiltzaileaZerrenda1.setItems(erabiltzaileak);
    }

    public ObservableList<erabiltzailea> erabiltzaileZerrenda() {

        ObservableList<erabiltzailea> erabiltzaileak = FXCollections.observableArrayList();
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
                // String motako kontsulta aldagaian "INSERT INTO ERABILTZAILEA
                // "SELECT ID_ERABILTZAILEA, IZENA, ABIZENA1, NAN, HELBIDEA, POSTA_KODEA,
                // EMAILA, JAIOTZE_DATA, ALTA_DATA FROM ERABILTZAILEA"
                String kontsulta = "SELECT ID_ERABILTZAILEA, IZENA, ABIZENA1, NAN, HELBIDEA, POSTA_KODEA, EMAILA, JAIOTZE_DATA, ALTA_DATA FROM ERABILTZAILEA";
                // Adierazitako kontsulta prestatzen da
                PreparedStatement agindua = cn.prepareStatement(kontsulta);
                // Prestatutako kontsulta exekutatzen da
                ResultSet emaitza = agindua.executeQuery();

                // ResultSet: SQL kontsultaren emaitzak jasotzen ditu.
                // "ResultSet" objektuak datu-baseko erantzunaren edukia gordetzen du.
                while (emaitza.next()) {
                    erabiltzailea irakurritakoErabiltzailea = new erabiltzailea(emaitza.getInt("id_erabiltzailea"),
                            emaitza.getString("izena"),
                            emaitza.getString("abizena1"), emaitza.getString("nan"), emaitza.getString("helbidea"),
                            emaitza.getInt("posta_kodea"), emaitza.getString("emaila"),
                            emaitza.getString("jaiotze_data"),
                            emaitza.getString("alta_data"));
                    erabiltzaileak.add(irakurritakoErabiltzailea);
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
        return erabiltzaileak;
    }

    // ********************************************************************************************************************
    // PIEZA
    // ********************************************************************************************************************

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

    // ********************************************************************************************************************
    // MAKINA - ERABILTZAILEA
    // ********************************************************************************************************************

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
    private ListView<makinaErabiltzailea> id_makinaErabiltzaileaZerrenda;
    @FXML
    private ListView<makinaErabiltzailea> id_makinaErabiltzaileaZerrenda1;
    @FXML
    private AnchorPane id_makinaErabiltzaileaInfoPantaila;
    @FXML
    private Label id_label_makinaErabiltzaileaZerrenda;
    @FXML
    private Button id_button_makinaErabiltzaileaBilatu;
    // @FXML
    // private Button id_button_makinaErabiltzaileaBilatu1;
    @FXML
    private Button id_button_makinaErabiltzaileaBilatu2;
    // @FXML
    // private Button id_button_makinaErabiltzaileaBilatu3;

    @FXML
    void gehituMakinaErabiltzaileaPantaila(ActionEvent event) {
        id_imageMakinaErabiltzailea.setVisible(false);
        id_makinaErabiltzaileaKenduPantaila.setVisible(false);
        id_makinaErabiltzaileaAldatuPantaila.setVisible(false);
        id_makinaErabiltzaileaGehituPantaila.setVisible(true);
        id_makinaErabiltzaileaZerrendatuPantaila.setVisible(false);
        id_label_makinaErabiltzaileaZerrenda.setVisible(false);
        ObservableList<makinaErabiltzailea> makinaErabiltzaileak = FXCollections.observableArrayList();
        // Pantaila garbitu, textfieldak
        id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
        id_sartuIDErabiltzailea1.setText("");
        id_sartuIDMakina1.setText("");
        id_sartuHasieraDataMakinaErabiltzailea.setValue(null);
        id_sartuAmaieraDataMakinaErabiltzailea.setValue(null);
        id_sartuHasieraDataMakinaErabiltzailea.getEditor().clear();
        id_sartuAmaieraDataMakinaErabiltzailea.getEditor().clear();
    }

    @FXML
    void gehituMakinaErabiltzailea(ActionEvent event) {
        // escenetik makinaErabiltzailearen datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaErabiltzaileaTxertatua = false;
        String id_erabiltzailea = id_sartuIDErabiltzailea1.getText();
        String id_makina = id_sartuIDMakina1.getText();
        LocalDate hasData = id_sartuHasieraDataMakinaErabiltzailea.getValue();
        LocalDate amaiData = id_sartuAmaieraDataMakinaErabiltzailea.getValue();
        // Instalakuntza data Stringera pasa
        String hasieraData = "";
        String amaieraData = "";
        if (hasData != null) {
            hasieraData = hasData.toString();
        }
        if (amaiData != null) {
            amaieraData = amaiData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id_erabiltzailea.isEmpty() && !id_makina.isEmpty() && !hasieraData.isEmpty() && !amaieraData.isEmpty()) {
            // makinaErabiltzaileBerria objetua sortu
            try {
                makinaErabiltzailea makinaErabiltzaileBerria = new makinaErabiltzailea(
                        Integer.parseInt(id_erabiltzailea), Integer.parseInt(id_makina), hasieraData, amaieraData);
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
                        // **INSERT INTO MAKINA_ERABILTZAILEA** SQL kontsulta prestatzen da
                        String kontsulta = "INSERT INTO MAKINA_ERABILTZAILEA (ID_ERABILTZAILEA,ID_MAKINA,HASIERA_DATA, AMAIERA_DATA) VALUES(?,?,?,?)";
                        PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                        agindua.setInt(1, makinaErabiltzaileBerria.getId_erabiltzailea());
                        agindua.setInt(2, makinaErabiltzaileBerria.getId_makina());
                        agindua.setDate(3, java.sql.Date.valueOf(makinaErabiltzaileBerria.getHasiera_data()));
                        agindua.setDate(4, java.sql.Date.valueOf(makinaErabiltzaileBerria.getAmaiera_data()));

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                        if (emaitza == 1) {
                            System.out.println("Makina_Erabiltzaile berria datu basean txertatu da");
                            makinaErabiltzaileaTxertatua = true;
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
                    System.out.println("Errorea makina erabiltzailearen datuak sortzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaErabiltzaileaTxertatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina erabiltzaile berria txertatuta!");
                    alerta.showAndWait();
                    id_label_makinaErabiltzaileaZerrenda.setVisible(true);
                    ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
                    id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
                    id_makinaErabiltzaileaZerrendatuPantaila.setVisible(true);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina erabiltzaile berria datu basean txertatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makinaren eta erabiltzailearen IDak zenbaki osoak izan behar dira");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_sartuIDErabiltzailea1.setText("");
        id_sartuIDMakina1.setText("");
        id_sartuHasieraDataMakinaErabiltzailea.setValue(null);
        id_sartuAmaieraDataMakinaErabiltzailea.setValue(null);
        id_sartuHasieraDataMakinaErabiltzailea.getEditor().clear();
        id_sartuAmaieraDataMakinaErabiltzailea.getEditor().clear();
    }

    @FXML
    void kenduMakinaErabiltzaileaPantaila(ActionEvent event) {
        id_imageMakinaErabiltzailea.setVisible(false);
        id_makinaErabiltzaileaKenduPantaila.setVisible(true);
        id_makinaErabiltzaileaAldatuPantaila.setVisible(false);
        id_makinaErabiltzaileaGehituPantaila.setVisible(false);
        id_makinaErabiltzaileaZerrendatuPantaila.setVisible(false);
        id_makinaErabiltzaileaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_kenduIDMakina1.setText("");
        id_kenduIDErabiltzailea1.setText("");
        id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
        id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
        // Sarreran ez utzi idazten usuarioari
        // id_kenduIDMakina.setDisable(true);
        id_kenduHasieraDataMakinaErabiltzailea.setDisable(true);
        id_kenduAmaieraDataMakinaErabiltzailea.setDisable(true);
        // Makina erabiltzaileen zerrenda bistaratu:
        id_label_makinaErabiltzaileaZerrenda.setVisible(true);
        ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
        id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
        // Aukeratutako makina jaso
        id_makinaErabiltzaileaZerrenda.getSelectionModel().selectedItemProperty()
                .addListener((obs, aurrekoAukera, aukeraBerria) -> {
                    if (aukeraBerria != null) {
                        id_kenduIDMakina1.setText(Integer.toString(aukeraBerria.getId_makina()));
                        id_kenduIDErabiltzailea1.setText(Integer.toString(aukeraBerria.getId_erabiltzailea()));
                        id_kenduHasieraDataMakinaErabiltzailea
                                .setValue((LocalDate.parse(aukeraBerria.getHasiera_data())));
                        id_kenduAmaieraDataMakinaErabiltzailea
                                .setValue((LocalDate.parse(aukeraBerria.getAmaiera_data())));
                    }
                });
        // Makina erabiltzailearen ID_erabiltzailea sartzean (textField en fokua
        // aldatzean) datu basean aurkitu
        // makina existitzen den
        id_kenduIDMakina1.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
                id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
                id_kenduHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_kenduAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        });
        // Makina erabiltzailearen ID_makina sartzean (textField en fokua aldatzean)
        // datu basean aurkitu
        // makina existitzen den
        id_kenduIDErabiltzailea1.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria && !id_kenduIDMakina1.getText().isEmpty() && !id_kenduIDErabiltzailea1.getText().isEmpty()) {
                id_kenduIDErabiltzailea1.setText("");
                id_kenduIDMakina1.setText("");
                id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
                id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
                id_kenduHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_kenduAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        });
        id_kenduIDMakina1.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria && !id_kenduIDMakina1.getText().isEmpty() && !id_kenduIDErabiltzailea1.getText().isEmpty()) {
                id_kenduIDErabiltzailea1.setText("");
                id_kenduIDMakina1.setText("");
                id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
                id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
                id_kenduHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_kenduAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        });
    }

    @FXML
    void kenduMakinaErabiltzailea(ActionEvent event) {
        // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaErabiltzaileaEzabatua = false;
        String id_erabiltzailea = id_kenduIDErabiltzailea1.getText();
        String id_makina = id_kenduIDMakina1.getText();
        LocalDate hasData = id_kenduHasieraDataMakinaErabiltzailea.getValue();
        LocalDate amaiData = id_kenduAmaieraDataMakinaErabiltzailea.getValue();
        // Instalakuntza data Stringera pasa
        String hasieraData = "";
        String amaieraData = "";
        if (hasData != null) {
            hasieraData = hasData.toString();
        }
        if (amaiData != null) {
            amaieraData = amaiData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id_erabiltzailea.isEmpty() && !id_makina.isEmpty() && !hasieraData.isEmpty() && !amaieraData.isEmpty()) {
            // makinaErabiltzaileBerria objetua sortu
            try {
                makinaErabiltzailea kentzekoMakinaErabiltzailea = new makinaErabiltzailea(
                        Integer.parseInt(id_erabiltzailea), Integer.parseInt(id_makina), hasieraData, amaieraData);
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
                        // **DELETE FROM MAKINA_ERABILTZAILEA** SQL kontsulta prestatzen da.
                        String kontsulta = "DELETE FROM MAKINA_ERABILTZAILEA WHERE ID_ERABILTZAILEA = ? AND ID_MAKINA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);
                        System.out.println(kontsulta);
                        agindua.setInt(1, kentzekoMakinaErabiltzailea.getId_erabiltzailea());
                        agindua.setInt(2, kentzekoMakinaErabiltzailea.getId_makina());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo langilerik ezabatu.
                        // > 0 itzultzen bada, **langilea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("MakinaErabiltzailea ezabatu da");
                            makinaErabiltzaileaEzabatua = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea makina erabiltzailearen datuak ezabatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaErabiltzaileaEzabatua) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina_Erabiltzailea ezabatu da datu basetik!");
                    alerta.showAndWait();
                    id_label_makinaZerrenda.setVisible(true);
                    ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
                    id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina ezabatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makina eta erabiltzailearen IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_kenduIDErabiltzailea1.setText("");
        id_kenduIDMakina1.setText("");
        id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
        id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
        id_kenduHasieraDataMakinaErabiltzailea.getEditor().clear();
        id_kenduAmaieraDataMakinaErabiltzailea.getEditor().clear();
    }

    @FXML
    void makinaErabiltzaileaBilatuKendun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id_erabiltzailea = id_kenduIDErabiltzailea1.getText();
        String id_makina = id_kenduIDMakina1.getText();
        boolean makinaErabiltzaileaAurkitua = false;
        try {
            int idErabiltzailea = Integer.parseInt(id_erabiltzailea);
            int idMakina = Integer.parseInt(id_makina);
            ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
            for (makinaErabiltzailea konzidituMakinaErabiltzailea : makinaErabiltzaileak) {
                if ((konzidituMakinaErabiltzailea.getId_erabiltzailea() == idErabiltzailea)
                        && (konzidituMakinaErabiltzailea.getId_makina() == idMakina)) {
                    id_kenduAmaieraDataMakinaErabiltzailea
                            .setValue((LocalDate.parse(konzidituMakinaErabiltzailea.getHasiera_data())));
                    id_kenduHasieraDataMakinaErabiltzailea
                            .setValue((LocalDate.parse(konzidituMakinaErabiltzailea.getAmaiera_data())));
                    makinaErabiltzaileaAurkitua = true;
                    return;
                }
            }
            if (!makinaErabiltzaileaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        idMakina + idErabiltzailea + " ID dun makina edo erabiltzailea ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_kenduIDErabiltzailea1.setText("");
                id_kenduIDMakina1.setText("");
                id_kenduHasieraDataMakinaErabiltzailea.setValue(null);
                id_kenduAmaieraDataMakinaErabiltzailea.setValue(null);
                id_kenduHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_kenduAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Makina eta erabiltzailearen IDak zenbaki osoak izan behar dira");
            alerta.showAndWait();
        }
    }

    @FXML
    void aldatuMakinaErabiltzaileaPantaila(ActionEvent event) {
        id_imageMakinaErabiltzailea.setVisible(false);
        id_makinaErabiltzaileaKenduPantaila.setVisible(false);
        id_makinaErabiltzaileaAldatuPantaila.setVisible(true);
        id_makinaErabiltzaileaGehituPantaila.setVisible(false);
        id_makinaErabiltzaileaZerrendatuPantaila.setVisible(false);
        id_makinaErabiltzaileaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_aldatuIDErabiltzailea1.setText("");
        id_aldatuIDMakina1.setText("");
        id_aldatuHasieraDataMakinaErabiltzailea.setValue(null);
        id_aldatuAmaieraDataMakinaErabiltzailea.setValue(null);
        id_aldatuHasieraDataMakinaErabiltzailea.getEditor().clear();
        id_aldatuAmaieraDataMakinaErabiltzailea.getEditor().clear();
        // Sarreran ez utzi idazten usuarioari
        id_aldatuHasieraDataMakinaErabiltzailea.setDisable(true);
        id_aldatuAmaieraDataMakinaErabiltzailea.setDisable(true);
        // Makinen zerrenda bistaratu:
        id_label_makinaErabiltzaileaZerrenda.setVisible(true);
        ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
        id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
        // Aukeratutako makina jaso
        id_makinaErabiltzaileaZerrenda.getSelectionModel().selectedItemProperty().addListener((obs, aurrekoAukera, aukeraBerria) -> {
            if (aukeraBerria != null) {
                id_aldatuIDMakina1.setText(Integer.toString(aukeraBerria.getId_makina()));
                id_aldatuIDErabiltzailea1.setText(Integer.toString(aukeraBerria.getId_erabiltzailea()));
                id_aldatuHasieraDataMakinaErabiltzailea
                        .setValue((LocalDate.parse(aukeraBerria.getHasiera_data())));
                id_aldatuAmaieraDataMakinaErabiltzailea
                        .setValue((LocalDate.parse(aukeraBerria.getAmaiera_data())));
            }
        });
        // Makinaren IDa sartzean (textField en fokua aldatzean) datu basean aurkitu
        // makina existitzen den
        id_aldatuIDErabiltzailea1.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria && !id_aldatuIDMakina1.getText().isEmpty() && !id_aldatuIDErabiltzailea1.getText().isEmpty()) {
                id_aldatuIDErabiltzailea1.setText("");
                id_aldatuIDMakina1.setText("");
                id_aldatuHasieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuAmaieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_aldatuAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        });
        id_aldatuIDMakina1.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_kenduIDMakina textField etik irten bada egin ondorengoa
            if (focusBerria && !id_aldatuIDMakina1.getText().isEmpty() && !id_aldatuIDErabiltzailea1.getText().isEmpty()) {
                id_aldatuIDErabiltzailea1.setText("");
                id_aldatuIDMakina1.setText("");
                id_aldatuHasieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuAmaieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_aldatuAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        });
    }
//lllllllllll
    @FXML
    void aldatuMakinaErabiltzailea(ActionEvent event) {
         // escenetik makinaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean makinaErabiltzaileaAldatuta = false;
        String id_erabiltzailea = id_aldatuIDErabiltzailea1.getText();
        String id_makina = id_aldatuIDMakina1.getText();
        LocalDate hasData = id_aldatuHasieraDataMakinaErabiltzailea.getValue();
        LocalDate amaiData = id_aldatuAmaieraDataMakinaErabiltzailea.getValue();
        // Instalakuntza data Stringera pasa
        String hasieraData = "";
        String amaieraData = "";
        if (hasData != null) {
            hasieraData = hasData.toString();
        }
        if (amaiData != null) {
            amaieraData = amaiData.toString();
        }
        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id_erabiltzailea.isEmpty() && !id_makina.isEmpty() && !hasieraData.isEmpty() && !amaieraData.isEmpty()) {
            // makinaErabiltzaileBerria objetua sortu
            try {
                makinaErabiltzailea aldatzekokoMakinaErabiltzailea = new makinaErabiltzailea(
                        Integer.parseInt(id_erabiltzailea), Integer.parseInt(id_makina), hasieraData, amaieraData);
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
                        String kontsulta = "UPDATE MAKINA_ERABILTZAILEA SET HASIERA_DATA=?, AMAIERA_DATA=? WHERE  ID_ERABILTZAILEA = ? AND ID_MAKINA = ?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, balioaksartzeko.
                        agindua.setDate(1, java.sql.Date.valueOf(aldatzekokoMakinaErabiltzailea.getHasiera_data()));
                        agindua.setDate(2, java.sql.Date.valueOf(aldatzekokoMakinaErabiltzailea.getAmaiera_data()));
                        agindua.setInt(3, aldatzekokoMakinaErabiltzailea.getId_erabiltzailea());
                        agindua.setInt(4, aldatzekokoMakinaErabiltzailea.getId_makina());
                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // 0 itzultzen bada, ez da inongo langilerik ezabatu.
                        // > 0 itzultzen bada, **langilea ezabatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Makina erabiltzailea aldatu da");
                            makinaErabiltzaileaAldatuta = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea makina erabiltzailearen datuak aldatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (makinaErabiltzaileaAldatuta) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Makina_Erabiltzailea aldatu da!");
                    alerta.showAndWait();
                    id_label_makinaErabiltzaileaZerrenda.setVisible(true);
                    ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
                    id_makinaErabiltzaileaZerrenda.setItems(makinaErabiltzaileak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da makina aldatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Makina eta erabiltzaile IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:        
        id_aldatuIDErabiltzailea1.setText("");
        id_aldatuIDMakina1.setText("");
        id_aldatuHasieraDataMakinaErabiltzailea.setValue(null);
        id_aldatuAmaieraDataMakinaErabiltzailea.setValue(null);
        id_aldatuHasieraDataMakinaErabiltzailea.getEditor().clear();
        id_aldatuAmaieraDataMakinaErabiltzailea.getEditor().clear();
        // Sarreran ez utzi idazten usuarioari
        // id_kenduIDMakina.setDisable(true);
        id_aldatuHasieraDataMakinaErabiltzailea.setDisable(true);
        id_aldatuAmaieraDataMakinaErabiltzailea.setDisable(true);
    }

    @FXML
    void makinaErabiltzaileaBilatuAldatun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id_erabiltzailea = id_aldatuIDErabiltzailea1.getText();
        String id_makina = id_aldatuIDMakina1.getText();
        boolean makinaErabiltzaileaAurkitua = false;
        try {
            int idErabiltzailea = Integer.parseInt(id_erabiltzailea);
            int idMakina = Integer.parseInt(id_makina);
            ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
            for (makinaErabiltzailea konzidituMakinaErabiltzailea : makinaErabiltzaileak) {
                if ((konzidituMakinaErabiltzailea.getId_erabiltzailea() == idErabiltzailea)
                        && (konzidituMakinaErabiltzailea.getId_makina() == idMakina)) {
                    id_aldatuAmaieraDataMakinaErabiltzailea
                            .setValue((LocalDate.parse(konzidituMakinaErabiltzailea.getHasiera_data())));
                    id_aldatuHasieraDataMakinaErabiltzailea
                            .setValue((LocalDate.parse(konzidituMakinaErabiltzailea.getAmaiera_data())));
                    makinaErabiltzaileaAurkitua = true;
                    return;
                }
            }
            if (!makinaErabiltzaileaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        idMakina + idErabiltzailea + " ID dun makina edo erabiltzailea ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_aldatuIDErabiltzailea1.setText("");
                id_aldatuIDMakina1.setText("");
                id_aldatuHasieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuAmaieraDataMakinaErabiltzailea.setValue(null);
                id_aldatuHasieraDataMakinaErabiltzailea.getEditor().clear();
                id_aldatuAmaieraDataMakinaErabiltzailea.getEditor().clear();
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Makina eta erabiltzailearen IDak zenbaki osoak izan behar dira");
            alerta.showAndWait();
        }
    }

    @FXML
    void zerrendatuMakinaErabiltzaileakPantaila(ActionEvent event) {
        id_imageMakinaErabiltzailea.setVisible(false);
        id_makinaErabiltzaileaKenduPantaila.setVisible(false);
        id_makinaErabiltzaileaAldatuPantaila.setVisible(false);
        id_makinaErabiltzaileaGehituPantaila.setVisible(false);
        id_makinaErabiltzaileaZerrendatuPantaila.setVisible(true);
        id_makinaErabiltzaileaInfoPantaila.setVisible(false);

        // Makina erabiltzaileen zerrenda bistaratu:
        ObservableList<makinaErabiltzailea> makinaErabiltzaileak = makinaErabiltzaileakZerrenda();
        id_makinaErabiltzaileaZerrenda1.setItems(makinaErabiltzaileak);
    }

    public ObservableList<makinaErabiltzailea> makinaErabiltzaileakZerrenda() {

        ObservableList<makinaErabiltzailea> makinaErabiltzaileak = FXCollections.observableArrayList();
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
                // String motako kontsulta aldagaian "SELECT ID_ERABILTZAILEA, ID_MAKINA,
                // HASIERA_DATA, AMAIERA_DATA FROM MAKINA_ERABILTZAILEA" agindua gordetzen da
                String kontsulta = "SELECT ID_ERABILTZAILEA, ID_MAKINA, HASIERA_DATA, AMAIERA_DATA FROM MAKINA_ERABILTZAILEA";
                // Adierazitako kontsulta prestatzen da
                PreparedStatement agindua = cn.prepareStatement(kontsulta);
                // Prestatutako kontsulta exekutatzen da
                ResultSet emaitza = agindua.executeQuery();

                // ResultSet: SQL kontsultaren emaitzak jasotzen ditu.
                // "ResultSet" objektuak datu-baseko erantzunaren edukia gordetzen du.
                while (emaitza.next()) {
                    makinaErabiltzailea irakurritakoMakinaErabiltzailea = new makinaErabiltzailea(
                            emaitza.getInt("id_erabiltzailea"), emaitza.getInt("id_makina"),
                            emaitza.getString("hasiera_data"), emaitza.getString("amaiera_data"));
                    makinaErabiltzaileak.add(irakurritakoMakinaErabiltzailea);
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
        return makinaErabiltzaileak;
    }

 
    // ********************************************************************************************************************
    // PIEZA MOTA
    // ********************************************************************************************************************

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
    private Button id_button_piezaMotaBilatu1;

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
        id_piezaMotaZerrenda.getSelectionModel().selectedItemProperty()
                .addListener((obs, aurrekoAukera, aukeraBerria) -> {
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
        id_imagePiezaMotak.setVisible(false);
        id_piezaMotaKenduPantaila.setVisible(false);
        id_PiezaMotaAldatuPantaila.setVisible(true);
        id_piezaMotaGehituPantaila.setVisible(false);
        id_piezaMotaZerrendatuPantaila.setVisible(false);
        id_piezaMotaInfoPantaila.setVisible(true);
        // Pantaila garbitu, textfieldak
        id_aldatuIDPiezaMota.setText("");
        id_aldatuIzenaPiezaMota.setText("");
        // Sarreran ez utzi idazten usuarioari
        id_aldatuIzenaPiezaMota.setDisable(true);
        // Pieza moten zerrenda bistaratu:
        id_label_piezaMotaZerrenda.setVisible(true);
        ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
        id_piezaMotaZerrenda.setItems(piezaMotak);
        // Aukeratutako pieza mota jaso
        id_piezaMotaZerrenda.getSelectionModel().selectedItemProperty()
                .addListener((obs, aurrekoAukera, aukeraBerria) -> {
                    id_aldatuIDPiezaMota.setText(Integer.toString(aukeraBerria.getId_piezaMota()));
                    id_aldatuIzenaPiezaMota.setText(aukeraBerria.getIzena());
                    id_aldatuIzenaPiezaMota.setDisable(false);
                });
        // Pieza motaren IDa sartzean (textField en fokua aldatzean) datu basean aurkitu
        // pieza mota existitzen den
        id_aldatuIDPiezaMota.focusedProperty().addListener((obs, focusZaharra, focusBerria) -> {
            // fokoa galdu, textField etik irten: focusBerria=false - textField era sartu:
            // focusBerria=true
            // id_aldatuIDPiezaMota textField etik irten bada egin ondorengoa
            if (focusBerria) {
                id_aldatuIDPiezaMota.setText("");
                id_aldatuIzenaPiezaMota.setText("");
                id_aldatuIzenaPiezaMota.setDisable(true);
            }
        });
    }

    @FXML
    void aldatuPiezaMota(ActionEvent event) {
        // escenetik pieza motaren datuak jaso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        boolean piezaMotaAldatuta = false;
        String id = id_aldatuIDPiezaMota.getText();
        String izena = id_aldatuIzenaPiezaMota.getText();

        // Escenetik jasotako daturen bat hutsik badago abixatu
        if (!id.isEmpty() && !izena.isEmpty()) {
            // aldatzekoPiezaMota objetua sortu
            try {
                piezaMota aldatzekoPiezaMota = new piezaMota(Integer.parseInt(id), izena);
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

                        // **UPDATE PIEZA_MOTA** SQL kontsulta prestatzen da.
                        String kontsulta = "UPDATE PIEZA_MOTA SET IZENA=? WHERE ID_PIEZA_MOTA=?";
                        PreparedStatement agindua = cn.prepareStatement(kontsulta);

                        // Parametroak prestatzen dira, balioaksartzeko.
                        agindua.setString(1, aldatzekoPiezaMota.getIzena());
                        agindua.setInt(2, aldatzekoPiezaMota.getId_piezaMota());

                        // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                        // executeUpdate() metodoak zenbakia itzultzen du, eragiketa zenbat aldiz egin
                        // den adieraziz.
                        int emaitza = agindua.executeUpdate();

                        // Emaitza balioztatu egiten da:
                        // > 0 itzultzen bada, **pieza mota aldatu dela** adierazten du.
                        if (emaitza > 0) {
                            System.out.println("Pieza mota aldatu da");
                            piezaMotaAldatuta = true;
                        }

                        // Datu-basearekin konektatutako kanala itxi egiten da,
                        // memoria eta baliabideak askatzeko.
                        cn.close();
                        System.out.println("Konexioa itxi da.");
                    }
                } catch (SQLException e) {
                    // SQL agindua exekutatzen edo konexioa egitean errore bat gertatzen bada,
                    // salbuespena jasotzen da.
                    System.out.println("Errorea pieza motaren datuak aldatzean.");
                    e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
                }
                if (piezaMotaAldatuta) {
                    alerta.setTitle("INFO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Pieza mota aldatu da!");
                    alerta.showAndWait();
                    id_label_piezaMotaZerrenda.setVisible(true);
                    ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
                    id_piezaMotaZerrenda.setItems(piezaMotak);
                } else {
                    alerta.setTitle("ADI !");
                    alerta.setHeaderText("Arazoak izan dira datu basearekin.");
                    alerta.setContentText("Ezin izan da pieza mota aldatu");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                alerta.setTitle("ADI !");
                alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
                alerta.setContentText("Pieza motaren IDa zenbaki osoa izan behar da");
                alerta.showAndWait();
            }
        } else {
            alerta.setTitle("ADI !");
            alerta.setHeaderText(null);
            alerta.setContentText("Ziurtatu eremu guztiak modu egokian bete direla!");
            alerta.showAndWait();
        }
        // Sarrerako datuak garbitu:
        id_aldatuIDPiezaMota.setText("");
        id_aldatuIzenaPiezaMota.setText("");
        // Sarreran ez utzi idazten usuarioari
        id_aldatuIzenaPiezaMota.setDisable(true);
    }

    @FXML
    void piezaMotaBilatuAldatun(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        String id = id_aldatuIDPiezaMota.getText();
        boolean piezaMotaAurkitua = false;
        try {
            int idZenb = Integer.parseInt(id);
            ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
            for (piezaMota konzidituPiezaMota : piezaMotak) {
                if (konzidituPiezaMota.getId_piezaMota() == idZenb) {
                    id_aldatuIzenaPiezaMota.setText(konzidituPiezaMota.getIzena());
                    id_aldatuIzenaPiezaMota.setDisable(false);
                    piezaMotaAurkitua = true;
                    return;
                }
            }
            if (!piezaMotaAurkitua) {
                alerta.setTitle("INFO");
                alerta.setHeaderText(null);
                alerta.setContentText(id + " ID dun pieza mota ez da datu basean aurkitzen!");
                alerta.showAndWait();
                id_aldatuIDPiezaMota.setText("");
                id_aldatuIzenaPiezaMota.setText("");
            }
        } catch (NumberFormatException e) {
            alerta.setTitle("ADI !");
            alerta.setHeaderText("Arreta jarri sartu dituzun datuengan");
            alerta.setContentText("Pieaza motaren IDa zenbaki osoa izan behar da");
            alerta.showAndWait();
        }
    }

    @FXML
    void zerrendatuPiezaMotakPantaila(ActionEvent event) {
        id_imagePiezaMotak.setVisible(false);
        id_piezaMotaKenduPantaila.setVisible(false);
        id_PiezaMotaAldatuPantaila.setVisible(false);
        id_piezaMotaGehituPantaila.setVisible(false);
        id_piezaMotaZerrendatuPantaila.setVisible(true);
        id_piezaMotaInfoPantaila.setVisible(false);

        // Makinen zerrenda bistaratu:
        ObservableList<piezaMota> piezaMotak = piezaMotaZerrenda();
        id_piezaMotaZerrenda1.setItems(piezaMotak);
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
                    piezaMota irakurritakoPiezaMota = new piezaMota(emaitza.getInt("id_pieza_mota"),
                            emaitza.getString("izena"));
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
