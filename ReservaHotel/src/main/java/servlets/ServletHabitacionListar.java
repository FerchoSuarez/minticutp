
package servlets;

import controller.HabitacionController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ServletHabitacionListar", urlPatterns = {"/ServletHabitacionListar"})
public class ServletHabitacionListar extends HttpServlet {

    
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HabitacionController habitacion = new HabitacionController();
        boolean ordenar = Boolean.parseBoolean(request.getParameter("ordenar"));
        String orden = request.getParameter("orden");
        String habitacionStr = habitacion.listar(ordenar, orden);
        response.setContentType("text-html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(habitacionStr);
        out.flush();
        out.close();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        
    }

    
   

}
