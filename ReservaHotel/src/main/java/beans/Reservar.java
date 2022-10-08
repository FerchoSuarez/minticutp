package beans;

import java.sql.Date;

public class Reservar {

    private int id_habitacion;
    private String username;
    private Date fecha_ingreso;
    private boolean novedad;
    private String piso;
    private String tipo;

    public Reservar(int id_habitacion, String username, Date fecha_ingreso, boolean novedad, String piso, String tipo) {
        this.id_habitacion = id_habitacion;
        this.username = username;
        this.fecha_ingreso = fecha_ingreso;
        this.novedad = novedad;
        this.piso = piso;
        this.tipo = tipo;
    }

    public Reservar(int id_habitacion, Date fecha_ingreso, boolean novedad, String piso, String tipo) {
        this.id_habitacion = id_habitacion;
        this.fecha_ingreso = fecha_ingreso;
        this.novedad = novedad;
        this.piso = piso;
        this.tipo = tipo;
    }
    
    
    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
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

}