package ec;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.inject.Inject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	@Inject
    private EcuserLocal stateless;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
        
        try {        	
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            	
            Boolean valid = stateless.validate(username, password);
            
            if (valid) {
            	Integer role = stateless.role();
	            switch (role) {
	            	case 1:
	            		response.sendRedirect("/stats-ejb-web/AdminServlet");
	            		break;
	            	case 2:
	            		response.sendRedirect("/stats-ejb-web/DeveloperServlet");
	            		break;
	            	case 3:
	            		response.sendRedirect("/stats-ejb-web/GeneralServlet");
	            		break;
	            }           
            }
    
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {
      doGet(request, response);
   }
}
