package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;

@WebServlet("/StatsEJBJMSDataServlet")
public class StatsEJBJMSDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	@Inject
    private StatsEJBJMSStatelessLocal stateless;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {        	
            String value = request.getParameter("value");
            stateless.publish(value);
            out.println("Published message:" + value);
    
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}
