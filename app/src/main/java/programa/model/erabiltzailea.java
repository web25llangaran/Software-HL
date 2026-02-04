package programa.model;

public class erabiltzailea {
    int id_erabiltzailea;
    String izena;
    String abizena1;
    String nan;
    String helbidea;
    int posta_kodea;
    String emaila;
    String jaiotze_data;
    String alta_data;


    public erabiltzailea(int id_erabiltzailea, String izena, String abizena1, String nan, String helbidea,
            int posta_kodea, String emaila, String jaiotze_data, String alta_data) {
        this.id_erabiltzailea = id_erabiltzailea;
        this.izena = izena;
        this.abizena1 = abizena1;
        this.nan = nan;
        this.helbidea = helbidea;
        this.posta_kodea = posta_kodea;
        this.emaila = emaila;
        this.jaiotze_data = jaiotze_data;
        this.alta_data = alta_data;
    }


    public int getId_erabiltzailea() {
        return id_erabiltzailea;
    }


    public void setId_erabiltzailea(int id_erabiltzailea) {
        this.id_erabiltzailea = id_erabiltzailea;
    }


    public String getIzena() {
        return izena;
    }


    public void setIzena(String izena) {
        this.izena = izena;
    }


    public String getAbizena1() {
        return abizena1;
    }


    public void setAbizena1(String abizena1) {
        this.abizena1 = abizena1;
    }


    public String getNan() {
        return nan;
    }


    public void setNan(String nan) {
        this.nan = nan;
    }


    public String getHelbidea() {
        return helbidea;
    }


    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }


    public int getPosta_kodea() {
        return posta_kodea;
    }


    public void setPosta_kodea(int posta_kodea) {
        this.posta_kodea = posta_kodea;
    }


    public String getEmaila() {
        return emaila;
    }


    public void setEmaila(String emaila) {
        this.emaila = emaila;
    }


    public String getJaiotze_data() {
        return jaiotze_data;
    }


    public void setJaiotze_data(String jaiotze_data) {
        this.jaiotze_data = jaiotze_data;
    }


    public String getAlta_data() {
        return alta_data;
    }


    public void setAlta_data(String alta_data) {
        this.alta_data = alta_data;
    }


    @Override
    public String toString() {
        return "erabiltzailea [id_erabiltzailea=" + id_erabiltzailea + ", izena=" + izena + ", abizena1=" + abizena1
                + ", nan=" + nan + ", helbidea=" + helbidea + ", posta_kodea=" + posta_kodea + ", emaila=" + emaila
                + ", jaiotze_data=" + jaiotze_data + ", alta_data=" + alta_data + "]";
    }

    
    

}
