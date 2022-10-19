package ec.osgi.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stats.osgi.StatsOSGiI;

import java.io.IOException;
import java.io.PrintWriter;

public class OSGiServlet extends HttpServlet {
    private StatsOSGiI tservice;
    
    public OSGiServlet() {
         tservice = null;
    }

    public OSGiServlet(StatsOSGiI service) {
        tservice = service;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String method = request.getParameter("method");
        double value = 0;
    	switch (method) {
    	case "count":
    		value = tservice.getCount();
    		break;
    	case "mean":
    		value = tservice.getMean();
    		break;
    	case "min":
    		value = tservice.getMin();
    		break;
    	case "max":
    		value = tservice.getMax();
    		break;
    	case "std":
    		value = tservice.getSTD();
    		break;
    	}
        try (PrintWriter writer = response.getWriter()) {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>OSGi web component</title>");
            writer.println("</head>");
            writer.println("<body align='center'>");
            writer.println("<h1>OSGi Servlet</h1>");
            writer.println("<h2>"+method+": "+value+"</h2>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }
}
