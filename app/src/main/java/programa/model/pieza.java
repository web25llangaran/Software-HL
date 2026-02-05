package programa.model;

public class pieza {
    private int id_pieza;
    private int id_pieza_mota;
    private int id_makina;
    private String izena;
    private String deskribapena;
    private int pisua;
    private double prezioa;
    private int stock;

    public pieza(int id_pieza, int id_pieza_mota, int id_makina, String izena, String deskribapena, int pisua,
            double prezioa, int stock) {
        this.id_pieza = id_pieza;
        this.id_pieza_mota = id_pieza_mota;
        this.deskribapena = deskribapena;
        this.id_makina = id_makina;
        this.izena = izena;
        this.pisua = pisua;
        this.prezioa = prezioa;
        this.stock = stock;
    }

    public int getId_pieza() {
        return id_pieza;
    }

    public void setId_pieza(int id_pieza) {
        this.id_pieza = id_pieza;
    }

    public int getId_pieza_mota() {
        return id_pieza_mota;
    }

    public void setId_pieza_mota(int id_pieza_mota) {
        this.id_pieza_mota = id_pieza_mota;
    }

    public int getId_makina() {
        return id_makina;
    }

    public void setId_makina(int id_makina) {
        this.id_makina = id_makina;
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

    public int getPisua() {
        return pisua;
    }

    public void setPisua(int pisua) {
        this.pisua = pisua;
    }

    public double getPrezioa() {
        return prezioa;
    }

    public void setPrezioa(double prezioa) {
        this.prezioa = prezioa;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "pieza [id_pieza=" + id_pieza + ", id_pieza_mota=" + id_pieza_mota + ", id_makina=" + id_makina
                + ", izena=" + izena + ", deskribapena=" + deskribapena + ", pisua=" + pisua + ", prezioa=" + prezioa
                + ", stock=" + stock + "]";
    }

}
