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

public class PiezakEtaPiezaMotakInportazioa {

  public void inportatu() {

    // Inportatu nahi den fitxategia:
    String piezakEtaPiezaMotakFitxategia = "../Fitxategiak_DatuZaharrak/piezak_eta_pieza_motak.csv";
    // Inportazioaren emaitza gordeko da:
    String emaitzaLoga = "../Fitxategiak_DatuZaharrak/log/piezak_eta_pieza_motak.log";

    String[] errenkada;
    int id_pieza, id_pieza_mota, kontUpdate = 0, kont = 0;
    String lerroa;
    boolean errenkadaInportatua = false;
    // DBKonexioa klaseko objektu bat sortzen da.
    DBKonexioa dataBkonexioa = new DBKonexioa();
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(emaitzaLoga));

      try {

        BufferedReader br = new BufferedReader(new FileReader(piezakEtaPiezaMotakFitxategia));

        // konektatu() metodoari deitzen zaio.
        // Metodo honek Connection motako objektu bat itzultzen du,
        // eta SQLException jaurtitzen du errorea gertatzen bada.
        Connection konexioa = dataBkonexioa.konektatu();

        // Konexioa existitzen dela eta irekita dagoela egiaztatzen da.
        if (konexioa != null && !konexioa.isClosed()) {
          bw.write(LocalDateTime.now() + " INFO: Komunikazio kanala irekita dago.\n");
          // System.out.println("Komunikazio kanala irekita dago.");
          lerroa = br.readLine();
          while (lerroa != null) {
            kont++;
            errenkada = lerroa.split(",");
            if (errenkada.length == 2) {
              try {
                id_pieza = Integer.parseInt(errenkada[0]);
                id_pieza_mota = Integer.parseInt(errenkada[1]);

                // **UPDATE PIEZA** SQL kontsulta prestatzen da.
                String kontsulta = "UPDATE PIEZA SET id_pieza_mota = ? WHERE id_pieza = ?";
                PreparedStatement agindua = konexioa.prepareStatement(kontsulta);

                // Parametroak prestatzen dira, bakoitza dagokion zutabera sartzeko
                agindua.setInt(1, id_pieza_mota);
                agindua.setInt(2, id_pieza);

                // SQL agindua exekutatzen da, eta emaitza zenbakira bihurtzen da.
                // executeUpdate() metodoak 1 itzultzen du, kontsulta ondo exekutatzen bada.
                int emaitza = agindua.executeUpdate();

                // Emaitza balioztatzen da: 1 itzultzen bada, datuak sartu dira.
                if (emaitza > 0) {
                  bw.write(
                      LocalDateTime.now()
                          + " INFO: "
                          + kont
                          + " Errenkada inportatua: "
                          + lerroa
                          + "\n");
                  // System.out.println(kont+" Errenkada inportatua: " + lerroa);
                  kontUpdate++;
                  errenkadaInportatua = true;
                }
              } catch (NumberFormatException e) {
                bw.write(
                    LocalDateTime.now()
                        + " ERROR: "
                        + kont
                        + " Arazoak eman ditu String to int castinarekin! Fitxategian irakurritako"
                        + " lerroa okerra da:"
                        + lerroa
                        + "\n");
                // System.out.println(kont+" Arazoak eman ditu String to double castinarekin!
                // Fitxategian irakurritako lerroa okerra da:"+ lerroa);
              }
            } else {
              bw.write(
                  LocalDateTime.now()
                      + " ERROR: "
                      + kont
                      + " Fitxategian irakurritako lerroa okerra da: "
                      + lerroa
                      + "\n");
              // System.out.println(kont+" Fitxategian irakurritako lerroa okerra da: " +
              // lerroa);
            }
            lerroa = br.readLine();
          }
          // Datu-basearekin konektatutako kanala itxi egiten da,
          // memoria eta baliabideak askatzeko.
          konexioa.close();
          bw.write(LocalDateTime.now() + " INFO: Konexioa itxi da.\n");
          // System.out.println("Konexioa itxi da.");
        }
        br.close();
        if (errenkadaInportatua) {
          bw.write(LocalDateTime.now() + " INFO: " + kontUpdate + " errenkada inportatu dira!\n");
          // System.out.println(kontUpdate + " errenkada inportatu dira!");
        } else {
          bw.write(
              LocalDateTime.now()
                  + " ERROR: Akatsak egon dira, ezin izan da "
                  + piezakEtaPiezaMotakFitxategia
                  + " fitxategia inportatu!\n");
          // System.out.println("Akatsak egon dira, ezin izan da " + piezakEtaPiezaMotakFitxategia
          // + " fitxategia inportatu!");
        }

      } catch (FileNotFoundException e) {
        bw.write(
            LocalDateTime.now()
                + " ERROR: Ezin izan da "
                + piezakEtaPiezaMotakFitxategia
                + " fitxategia aurkitu!\n");
        // System.out.println("Ezin izan da " + piezakEtaPiezaMotakFitxategia + " fitxategia
        // aurkitu!");
      } catch (IOException e) {
        bw.write(
            LocalDateTime.now()
                + " ERROR: Ezin izan da "
                + piezakEtaPiezaMotakFitxategia
                + " fitxategia irakurri!\n");
        // System.out.println("Ezin izan da " + piezakEtaPiezaMotakFitxategia + " fitxategia
        // irakurri!");
      } catch (SQLException e) {
        // Salbuespen bat sortzen da SQL aginduak exekutatzen direnean errore bat
        // gertatuz gero.
        // Adibidez, datuak sartzeko parametroak okerrak izan daitezke edo taula ez
        // egon.
        bw.write(
            LocalDateTime.now()
                + " ERROR: Errorea pieza motaren datuak sortzean: "
                + e.getMessage()
                + "\n");
        // System.out.println("Errorea pieza motaren datuak sortzean.");
        // e.printStackTrace(); // Errorea aztertzeko informazioa bistaratzeko
      }
      bw.close();
    } catch (IOException e) {
      System.out.println("Ezin izan da log fitxategia sortu edo itxi!");
    }
  }
}
