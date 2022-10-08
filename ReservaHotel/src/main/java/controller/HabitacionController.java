package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import beans.Habitacion;
import connection.DBConnection;
import java.sql.Timestamp;

public class HabitacionController implements IHabitacionController {
    
     @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from habitacion";

        if (ordenar == true) {
            sql += " order by piso " + orden;
        }

        List<String> habitacion = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id_habitacion = rs.getInt("id_habitacion");
                String piso = rs.getString("piso");
                String tipo = rs.getString("tipo");
                String ciudad = rs.getString("ciudad");
                int disponible = rs.getInt("disponible");
                boolean novedad = rs.getBoolean("novedad");

                Habitacion habitaciones = new Habitacion(id_habitacion, piso, tipo, ciudad, disponible, novedad);

                habitacion.add(gson.toJson(habitaciones));

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return gson.toJson(habitacion);

    }

    
       
    @Override
    public String reservar(int id_habitacion, String username) {
        
        Timestamp fecha = new Timestamp(new Date().getTime());
        DBConnection con = new DBConnection();
        String sql = "Insert into reservar values ('" + id_habitacion + "', '" + username + "', '" + fecha + "')";
                
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            String modificar = modificar(id_habitacion);

            if (modificar.equals("true")) {
                return "true";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    
    @Override
    public String modificar(int id_habitacion) {

        DBConnection con = new DBConnection();
        String sql = "Update habitacion set disponible = (disponible - 1) where id_habitacion = " + id_habitacion;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";

    }
    
    @Override
    public String devolver(int id_habitacion, String username) {

        DBConnection con = new DBConnection();
        String sql = "Delete from reservar where id_habitacion= " + id_habitacion + " and username = '"
                + username + "' limit 1";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            this.sumarCantidad(id_habitacion);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
    
     @Override
    public String sumarCantidad(int id_habitacion) {

        DBConnection con = new DBConnection();

        String sql = "Update habitacion set disponible= (Select disponible from habitacion where id_habitacion = " 
                + id_habitacion + ") + 1 where id_habitacion = " + id_habitacion;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";

    }
   
}
