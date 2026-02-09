import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        ErabiltzaileakInportazioa erabiltzaileak = new ErabiltzaileakInportazioa();
        MakinakInportazioa makinak = new MakinakInportazioa();
        ErabiltzaileakEtaMakinakInportazioa erabiltzaileaMakinaLotura = new ErabiltzaileakEtaMakinakInportazioa();
        PiezakInportazioa piezak = new PiezakInportazioa();
        PiezaMotakInportazioa piezaMotak = new PiezaMotakInportazioa();
        PiezakEtaPiezaMotakInportazioa piezaMotakLotura = new PiezakEtaPiezaMotakInportazioa();
        PiezaEtaMakinakLoturaInportazioa piezaMakinakLotura = new PiezaEtaMakinakLoturaInportazioa();

        System.out.println("Idatzi -inportatu- Datu basera fitxeroen inportaziorako");
        String scanner = sc.nextLine();
        if (scanner.equalsIgnoreCase("inportatu")) {
            try {
                File logDir = new File("../Fitxategiak_DatuZaharrak/log");
                if (!logDir.exists()) {
                    logDir.mkdir();
                    System.out.println("log karpeta sortu da, inportazioaren emaitza gordetzeko!");
                } 
                erabiltzaileak.inportatu();
                makinak.inportatu();
                erabiltzaileaMakinaLotura.inportatu();
                piezak.inportatu();
                piezaMotak.inportatu();
                piezaMotakLotura.inportatu();
                piezaMakinakLotura.inportatu();
                System.out.println("Inportazioa burutu da.");
                System.out.println("Inportazioari buruzko informazioa, *.log fitxategietan eskura duzu.");
            } catch (SecurityException e) {
                System.out.println("Ez daukazu horretarako baimenik!");
            }
        }else System.out.println("Beste batetan izango da.");
        sc.close();
    }
}
