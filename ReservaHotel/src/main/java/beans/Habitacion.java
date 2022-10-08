
package beans;

public class Habitacion {

    public static void add(String toJson) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    private int id_habitacion;
    private String piso;
    private String tipo;
    private String ciudad;
    private int disponible;
    private boolean novedad;

    public Habitacion(int id_habitacion, String piso, String tipo, String ciudad, int disponible, boolean novedad) {
        this.id_habitacion = id_habitacion;
        this.piso = piso;
        this.tipo = tipo;
        this.ciudad = ciudad;
        this.disponible = disponible;
        this.novedad = novedad;     
                
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }
    
}