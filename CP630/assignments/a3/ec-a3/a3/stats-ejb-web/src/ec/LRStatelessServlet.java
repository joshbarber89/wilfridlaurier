package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.lr.LRStateless;

/**
 * Servlet implementation class LRStatelessServlet
 */
@WebServlet("/LRStatelessServlet")
public class LRStatelessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
    private LRStateless lrStateless ;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LRStatelessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {        	
            String value = request.getParameter("value");
            
            String[] attr = value.split(",");
            
            if (attr.length == 6) {
            	String predict = lrStateless.predict(attr);
            
            	out.println("Predict Next Number: " + predict);

            } else {
            	out.println("Must have 6 numbers comma delimited. No spaces");
            }
            
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
