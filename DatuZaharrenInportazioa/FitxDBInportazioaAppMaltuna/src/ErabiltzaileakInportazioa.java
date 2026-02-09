import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ErabiltzaileakInportazioa {

    public void inportatu() {

        // Inportatu nahi den fitxategia:
        String erabiltzaileakFitxategia = "../Fitxategiak_DatuZaharrak/erabiltzaileak.csv";
        String emaitzaLoga = "../Fitxategiak_DatuZaharrak/log/erabiltzaileak.log";

        String[] errenkada;
        int id_erabiltzailea, kont = 0, kontInport = 0;
        String lerroa, izena, abizena, nan, helbidea, postaKodea, emaila, jaiotze_data, alta_data;
        boolean errenkadaInportatua = false;
        // DBKonexioa klaseko objektu bat sortzen da.
        DBKonexioa dataBkonexioa = new DBKonexioa();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(emaitzaLoga));

            try {

                BufferedReader br = new BufferedReader(new FileReader(erabiltzaileakFitxategia));

                // konektatu() metodoari deitzen zaio.
                // Metodo honek Connection motako objektu bat itzultzen du,
                // eta SQLException jaurtitzen du errorea gertatzen bada.
                Connection konexioa = dataBkonexioa.konektatu();

                // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
                if (konexioa != null && !konexioa.isClosed()) {
                    bw.write(LocalDateTime.now() + " INFO: Komunikazio kanala irekita dago.\n");
                    //System.out.println("Komunikazio kanala irekita dago.");
                    lerroa = br.readLine();
                    while (lerroa != null) {
                        kont++;
                        errenkada = lerroa.split(",");
                        if (errenkada.length == 9) {
                            try {
                                id_erabiltzailea = Integer.parseInt(errenkada[0]);
                                izena = errenkada[1];
                                abizena = errenkada[2];
                                nan = errenkada[3];
                                helbidea = errenkada[4];
                                postaKodea = errenkada[5];
                                emaila = errenkada[6];
                                jaiotze_data = errenkada[7];
                                alta_data = errenkada[8];

                                // **INSERT INTO ERABILTZAILEA** SQL kontsulta prestatzen da
                                String kontsulta = "INSERT INTO ERABILTZAILEA (ID_ERABILTZAILEA,IZENA,ABIZENA1,NAN,HELBIDEA,POSTA_KODEA,EMAILA,JAIOTZE_DATA,ALTA_DATA) VALUES(?,?,?,?,?,?,?,?,?)";
                                PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                                // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                                agindua.setInt(1, id_erabiltzailea);
                                agindua.setString(2, izena);
                                agindua.setString(3, abizena);
                                agindua.setString(4, nan);
                                agindua.setString(5, helbidea);
                                agindua.setString(6, postaKodea);
                                agindua.setString(7, emaila);
                                agindua.setDate(8, java.sql.Date.valueOf(jaiotze_data));
                                agindua.setDate(9, java.sql.Date.valueOf(alta_data));

                                // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                                // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                                int emaitza = agindua.executeUpdate();

                                // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                                if (emaitza == 1) {
                                    bw.write(LocalDateTime.now() + " INFO: " + kont + " Errenkada inportatua: " + lerroa + "\n");
                                    //System.out.println(kont + " Errenkada inportatua: " + lerroa);
                                    kontInport++;
                                    errenkadaInportatua = true;
                                }
                            } catch (NumberFormatException e) {
                                bw.write(LocalDateTime.now() + " ERROR: " + kont + " Arazoak eman ditu String to int castinarekin! Fitxategian irakurritako lerroa okerra da: " + lerroa + "\n");
                                //System.out.println(kont+ " Arazoak eman ditu String to double castinarekin! Fitxategian irakurritako lerroa okerra da: "+ lerroa);
                            }
                        } else {
                            bw.write(LocalDateTime.now() + " ERROR: " + kont + " Fitxategian irakurritako lerroa okerra da: " + lerroa + "\n");
                            //System.out.println(kont + " Fitxategian irakurritako lerroa okerra da: " + lerroa);
                        }
                        lerroa = br.readLine();
                    }
                    // Datu-basearekin konektatutako kanala itxi egiten da,
                    // memoria eta baliabideak askatzeko.
                    konexioa.close();
                    bw.write(LocalDateTime.now() + " INFO: Konexioa itxi da.\n");
                    //System.out.println("Konexioa itxi da.");
                }
                br.close();
                if (errenkadaInportatua) {
                    bw.write(LocalDateTime.now() + " INFO: " + kontInport + " errenkada inportatu dira!\n");
                    //System.out.println(kontInport + " errenkada inportatu dira!");
                } else {
                    bw.write(LocalDateTime.now() + " ERROR: Akatsak egon dira, ezin izan da " + erabiltzaileakFitxategia + " fitxategia inportatu!\n");
                    //System.out.println( "Akatsak egon dira, ezin izan da " + erabiltzaileakFitxategia + " fitxategia inportatu!");
                }
            } catch (FileNotFoundException e) {
                bw.write(LocalDateTime.now() + " ERROR: Ezin izan da " + erabiltzaileakFitxategia + " fitxategia aurkitu!\n");
                //System.out.println("Ezin izan da " + erabiltzaileakFitxategia + " fitxategia aurkitu!");
            } catch (IOException e) {
                bw.write(LocalDateTime.now() + " ERROR: Ezin izan da " + erabiltzaileakFitxategia + " fitxategia irakurri!\n");
               //System.out.println("Ezin izan da " + erabiltzaileakFitxategia + " fitxategia irakurri!");
            } catch (SQLException e) {
                // Salbuespen bat sortzen da SQL aginduak exekutatzen direnean errore bat
                // gertatuz gero.
                // Adibidez, datuak sartzeko parametroak okerrak izan daitezke edo taula ez
                // egon.
                bw.write(LocalDateTime.now() + " ERROR: Errorea erabiltzailearen datuak sortzean: " + e.getMessage() + " \n");
               //System.out.println("Errorea piezaren datuak sortzean.");
                //e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
            }
            bw.close();
        } catch (IOException e1) {
            System.out.println("Ezin izan da log fitxategia sortu edo idatzi!");
            e1.printStackTrace();
        }
    }

}
