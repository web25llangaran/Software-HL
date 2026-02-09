package programa.model;

public class piezaMota {
    int id_piezaMota;
    String izena;


    public piezaMota(int id_piezaMota, String izena) {
        this.id_piezaMota = id_piezaMota;
        this.izena = izena;
    }


    public int getId_piezaMota() {
        return id_piezaMota;
    }


    public void setId_piezaMota(int id_piezaMota) {
        this.id_piezaMota = id_piezaMota;
    }


    public String getIzena() {
        return izena;
    }


    public void setIzena(String izena) {
        this.izena = izena;
    }


    @Override
    public String toString() {
        return "piezaMota [id_piezaMota=" + id_piezaMota + ", izena=" + izena + "]";
    }

    


}
