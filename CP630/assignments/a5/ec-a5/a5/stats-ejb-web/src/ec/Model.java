package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Model
 */
@WebServlet("/Model")
public class Model extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    @EJB
    private ModelEJBRemote model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Model() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	
     	StatsSummary sm = model.getObjectByName("hdstats");

     	if (sm != null) {
	    	String html = 
	    			"<p>Count: " + sm.count + "</p>" + 
	    			"<p>Mean: " + sm.mean + "</p>" +
	    			"<p>Min: " + sm.min + "</p>" +
	    			"<p>Max: " + sm.max + "</p>" +
	    			"<p>STD: " + sm.std + "</p>";
	    	out.print(html);
     	} else {
     		out.print("No model");
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
