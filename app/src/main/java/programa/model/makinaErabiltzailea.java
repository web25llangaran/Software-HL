package programa.model;

public class makinaErabiltzailea {
    int id_erabiltzailea;
    int id_makina;
    String hasiera_data;
    String amaiera_data;
    
    public makinaErabiltzailea(int id_erabiltzailea, int id_makina, String hasiera_data, String amaiera_data) {
        this.id_erabiltzailea = id_erabiltzailea;
        this.id_makina = id_makina;
        this.hasiera_data = hasiera_data;
        this.amaiera_data = amaiera_data;
    }

    public int getId_erabiltzailea() {
        return id_erabiltzailea;
    }

    public void setId_erabiltzailea(int id_erabiltzailea) {
        this.id_erabiltzailea = id_erabiltzailea;
    }

    public int getId_makina() {
        return id_makina;
    }

    public void setId_makina(int id_makina) {
        this.id_makina = id_makina;
    }

    public String getHasiera_data() {
        return hasiera_data;
    }

    public void setHasiera_data(String hasiera_data) {
        this.hasiera_data = hasiera_data;
    }

    public String getAmaiera_data() {
        return amaiera_data;
    }

    public void setAmaiera_data(String amaiera_data) {
        this.amaiera_data = amaiera_data;
    }

    @Override
    public String toString() {
        return "makinaErabiltzailea [id_erabiltzailea=" + id_erabiltzailea + ", id_makina=" + id_makina
                + ", hasiera_data=" + hasiera_data + ", amaiera_data=" + amaiera_data + "]";
    }

}
