package programa.model;

public class makina {
    int id;
    String izena;
    String deskribapena;
    int potentzia;
    String instalakuntzaData;
    public makina(int id, String izena, String deskribapena, int potentzia, String instalakuntzaData) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.potentzia = potentzia;
        this.instalakuntzaData = instalakuntzaData;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIzena() {
        return izena;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public String getDeskribapena() {
        return deskribapena;
    }
    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }
    public int getPotentzia() {
        return potentzia;
    }
    public void setPotentzia(int potentzia) {
        this.potentzia = potentzia;
    }
    public String getInstalakuntzaData() {
        return instalakuntzaData;
    }
    public void setInstalakuntzaData(String instalakuntzaData) {
        this.instalakuntzaData = instalakuntzaData;
    }
    @Override
    public String toString() {
        return "makina [id=" + id + ", izena=" + izena + ", deskribapena=" + deskribapena + ", potentzia=" + potentzia
                + ", instalakuntzaData=" + instalakuntzaData + "]";
    }
}
