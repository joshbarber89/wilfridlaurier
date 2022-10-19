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
 * Servlet implementation class GeneralServlet
 */
@WebServlet("/GeneralServlet")
public class GeneralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@Inject
    private EcuserLocal validation;   
	
    @EJB
    private ModelEJBRemote model;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneralServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	
		if (validation.valid() && validation.role() <= 3) {
        	
        	String value = request.getParameter("stats");
        	if (value != null) {
        		StatsSummary sm = model.getObject();
        		if (sm != null) {
	        		switch (value) {
	        		case "getCount":
	        			out.print(sm.count);
	        			break;
	        		case "getMin":
	        			out.print(sm.min);
	        			break;
	        		case "getMax":
	        			out.print(sm.max);
	        			break;
	        		case "getMean":
	        			out.print(sm.mean);
	        			break;
	        		case "getStd":
	        			out.print(sm.std);
	        			break;
	        		}
        		}

        	} else {
	        	String html = 
	        			"<form action = \"/stats-ejb-web/GeneralServlet\" method = \"POST\">\r\n" + 
	        			"		<select name = \"stats\">\r\n" +
	        			"           <option value =\"getCount\">Get Count</option>\r\n" +	
	        			"           <option value =\"getMin\">Get Min</option>\r\n" +
	        			"           <option value =\"getMax\">Get Max</option>\r\n" +
	        			"           <option value =\"getMean\">Get Mean</option>\r\n" +
	        			"           <option value =\"getStd\">Get Std</option>\r\n" +
	        			"       </select>\r\n" +
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
