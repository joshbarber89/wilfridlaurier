package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StatsEJBSingletonServlet")
public class StatsEJBSingletonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	@EJB
    private StatsEJBSingletonRemote singleton;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {
            double value = Double.parseDouble(request.getParameter("value"));
            singleton.addData(value);
            singleton.saveModel();
            out.println(value + " added, model saved");
    
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}
