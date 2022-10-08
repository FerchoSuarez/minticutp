package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.Gson;
import beans.Usuario;
import connection.DBConnection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController implements IUsuarioController {

    @Override
    public String login(String username, String contrasena) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Select * from usuario where username = '" + username
                + "' and contrasena = '" + contrasena + "'";
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String ciudad_origen = rs.getString("ciudad_origen");
                String email = rs.getString("email");
                String celular = rs.getString("celular");
                Boolean premium = rs.getBoolean("premium");

                Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, ciudad_origen, email, celular, premium);
                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String register(String username, String contrasena, String nombre, String apellidos, String ciudad_origen, String email, String celular, boolean premium) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Insert into usuario values('" + username + "', '" + contrasena + "', '" + nombre
                + "', '" + apellidos + "', '" + ciudad_origen + "', '" + email + "', '" + celular + "', " + premium + ")";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, ciudad_origen, email, celular, premium);

            st.close();

            return gson.toJson(usuario);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {
            con.desconectar();
        }

        return "false";

    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public String pedir(String username) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from usuario where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String contrasena = rs.getString("contrasena");                
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String ciudad_origen = rs.getString("ciudad_origen");
                String email = rs.getString("email");
                String celular = rs.getString("celular");
                Boolean premium = rs.getBoolean("premium");
                

                Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, ciudad_origen, email, celular, premium);

                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

 
       
    @Override
    public String modificar(String username, String nuevaContrasena,
            String nuevoNombre, String nuevosApellidos, String nuevaCiudad_Origen,
            String nuevoEmail, String nuevoCelular, boolean nuevoPremium) {

        DBConnection con = new DBConnection();

        String sql = "Update usuario set contrasena = '" + nuevaContrasena
                + "', nombre = '" + nuevoNombre + "', "
                + "apellidos = '" + nuevosApellidos + "', ciudad_origen = '"
                + nuevaCiudad_Origen + "', email = '" + nuevoEmail + "', celular = " + nuevoCelular + ", premium = ";

        if (nuevoPremium == true) {
            sql += " 1 ";
        } else {
            sql += " 0 ";
        }

        sql += " where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public String verDisponible(String username) {

        DBConnection con = new DBConnection();
        String sql = "Select id_habitacion,count(*) as num_disponible from reservar where username = '"
                + username + "' group by id_habitacion;";

        Map<Integer, Integer> disponible = new HashMap<Integer, Integer>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_habitacion = rs.getInt("id_habitacion");
                int num_disponible = rs.getInt("disponible");

                disponible.put(id_habitacion, num_disponible);
            }

            devolverHabitacion(username, disponible);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String devolverHabitacion(String username, Map<Integer, Integer> disponible) {

        DBConnection con = new DBConnection();

        try {
            for (Map.Entry<Integer, Integer> habitaciones : disponible.entrySet()) {
                int id_habitacion = habitaciones.getKey();
                int num_disponible = habitaciones.getValue();

                String sql = "Update habitacion set disponible = (Select disponible + " + num_disponible
                        + " from habitacion where id_habitacion = " + id_habitacion + ") where id_habitacion = " + id_habitacion;

                Statement st = con.getConnection().createStatement();
                st.executeUpdate(sql);

            }

            this.eliminar(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }

    @Override
    public String eliminar(String username) {

        DBConnection con = new DBConnection();

        String sql1 = "Delete from reservar where username = '" + username + "'";
        String sql2 = "Delete from usuario where username = '" + username + "'";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
}
