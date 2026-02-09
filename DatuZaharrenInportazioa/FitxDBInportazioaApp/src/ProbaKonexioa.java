
// JDBC rekin lanean aritzeko, inportatu beharrekoak:
import java.sql.Connection;      // Datu baserekin lotzeko "komunikazio kanala" da.
import java.sql.SQLException;   // Konexioa egiteaz arduratzen da.

public class ProbaKonexioa {

    public static void main(String[] args) {

        DBKonexioa dataBkonexioa = new DBKonexioa();
        Connection konexioa = null;

        try {
            // konektatu() metodoak SQLException jaurti dezake
            konexioa= dataBkonexioa.konektatu();

            // konexioa existitzen dela eta irekita dagoela konprobatzen du
            if (konexioa != null && !konexioa.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");

                // Konexioa itxi egiten da
                konexioa.close();
                System.out.println("Konexioa itxi da.");
            }

        } catch (SQLException e) {
            System.out.println("Errorea datu-basera konektatzean");
            e.printStackTrace();
        }
    }
}
