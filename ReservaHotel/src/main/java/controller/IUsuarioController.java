
package controller;

import java.util.Map;

public interface IUsuarioController {
    public String login(String username, String contrasena);    
    public String register(String username, String contrasena, String nombre, String apellidos, String ciudad_origen, String email, String celular, boolean premium);
    
    
 public String pedir(String username);
 public String modificar(String username, String nuevaContrasena,
            String nuevoNombre, String nuevosApellidos, String nuevaCiudad_Origen,
            String nuevoEmail, String nuevoCelular, boolean nuevoPremium);

 public String verDisponible(String username);
 public String devolverHabitacion(String username, Map<Integer, Integer> disponible);
 public String eliminar(String username);
}
