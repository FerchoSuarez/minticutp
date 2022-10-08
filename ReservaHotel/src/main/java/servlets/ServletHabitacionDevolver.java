package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HabitacionController;

/**
 * Servlet implementation class ServletHabitacionDevolver
 */
@WebServlet("/ServletPeliculaDevolver")
public class ServletHabitacionDevolver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletHabitacionDevolver() {
        super();
        // TODO Auto-generated constructor stub
    }

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
		// TODO Auto-generated method stub
		HabitacionController habitacion = new HabitacionController();
		
		String username = request.getParameter("username");
		int id_habitacion = Integer.parseInt(request.getParameter("id_habitacion"));
		
		String libroStr = habitacion.devolver(id_habitacion,username);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(libroStr);
		out.flush();
		out.close();
	}

	
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}