
package controller;

public interface IHabitacionController {
    
    public String listar(boolean ordenar, String orden);
    public String reservar(int id_habitacion, String username);
    public String modificar(int id_habitacion);
    public String devolver(int id_habitacion,String username);
    public String sumarCantidad(int id_habitacion);
    
}
