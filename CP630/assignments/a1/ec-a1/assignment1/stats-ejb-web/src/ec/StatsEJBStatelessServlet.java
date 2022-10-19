package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StatsEJBStatelessServlet")
public class StatsEJBStatelessServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
    private StatsEJBStatelessRemote stateless;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {
        	out.println("Count: " + stateless.getCount());
        	out.println("Min: " + stateless.getMin());  
        	out.println("Max: " + stateless.getMax());  
        	out.println("Mean: " + stateless.getMean());  
        	out.println("STD: " + stateless.getSTD());  
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}
