package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DeveloperServlet
 */
@WebServlet("/DeveloperServlet")
public class DeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
    private EcuserLocal validation;   
	
    @EJB
    private ModelEJBRemote model;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeveloperServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
		if (validation.valid() && validation.role() <= 2) {

        	
        	String value = request.getParameter("value");
        	if (value != null && !Double.isNaN(Double.parseDouble(value))) {
        		boolean saved = model.saveObject("stats", Double.parseDouble(value));
        		if (saved) {
        			out.print("Saved!");
        		} else {
        			out.print("Did not save!");
        		}
        	} else {
	        	String html = 
	        			"<form action = \"/stats-ejb-web/DeveloperServlet\" method = \"POST\">\r\n" + 
	        			"		<input type = \"text\" placeholder = \"Enter data\" name = \"value\"/>\r\n" +
	        			"	    <input type = \"submit\" value = \"Submit\"/>\r\n" + 
	        			"</form>";
	        	out.print(html);
        	}
		} else {
			out.print("Not Authorized");
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
