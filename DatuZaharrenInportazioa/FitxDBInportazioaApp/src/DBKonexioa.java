
// JDBC rekin lanean aritzeko, inportatu beharrekoak:
import java.sql.Connection;      // Datu baserekin lotzeko "komunikazio kanala" da.
import java.sql.DriverManager;   // Konexioa egiteaz arduratzen da.
import java.sql.SQLException;    // SKL egoera bereziak/erroreak kudeatzeko aukera ematen du

// Datu-basearekin konexioa sortuko duen klasea.
public class DBKonexioa {

    // === KONFIGURAZIO DATUAK ===
	// jdbc:JDBC motako konexio bat dela adierazten du. JDBC, Javak, datu-base batera konektatzeko erabiltzen duen protokoloa da.
	// mysql:JDBC protokoloari esaten zaio konektatu nahi dugun datu-basea MySQL motakoa dela.
    // localhost → zerbitzari lokala
    // 3306 →  MySQLren defektuzko portua
    // tiendadb → datu-basearen izena
	
	// 3 konstante definitzen dira: URL, USER eta PASS.
	// Gogoratu konstanteak aldagaiak bezalakoak direla, baina haien balioa beti berdin mantentzen da.
    private static final String URL = "jdbc:mysql://localhost:3306/maltunadb";

    // datu-basearen erabiltzailea
    private static final String USER = "root";

    // datu-basera konektatzeko pasahitza
    private static final String PASS = "";


    //konektatu() --> metodoaren izena da.
    // Connection --> metodoak Connection motako objektu bat bueltatuko du.

    public Connection konektatu() throws SQLException {

    	// throws erabiliz, salbuespena, metodoa deitu duen programara hedatzen da.
    	// DriverManager.getConnection() metodoak bere barruan SQLException jaurtitzen du (throw) salbuespena gertatzen bada.
        return  DriverManager.getConnection(URL, USER, PASS);
    }
}
