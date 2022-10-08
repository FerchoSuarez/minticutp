package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import beans.Reservar;
import connection.DBConnection;

public class ReservarController implements IReservarController {

    
    @Override
    public String listarReservas(String username) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Select l.id_habitacion, l.piso, l.tipo, l.novedad, a.fecha_ingreso from habitacion l "
                + "inner join reservar a on l.id_habitacion = a.id_habitacion inner join usuario u on a.username = u.username "
                + "where a.username = '" + username + "'";

        List<String> reservar = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                
                int id_habitacion = rs.getInt("id_habitacion");
                String piso = rs.getString("piso");
                String tipo = rs.getString("tipo");
                boolean novedad = rs.getBoolean("novedad");
                Date fecha_ingreso = rs.getDate("fecha_ingreso");

                Reservar reservas = new Reservar(id_habitacion, fecha_ingreso, novedad, piso, tipo);
                
                reservar.add(gson.toJson(reservas));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return gson.toJson(reservar);
    }
}

