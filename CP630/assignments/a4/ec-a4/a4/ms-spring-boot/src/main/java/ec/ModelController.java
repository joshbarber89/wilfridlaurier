package ec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ModelController {
	
    @Autowired
    private ModelBean modelBean;
    
    @RequestMapping(value = "/prediction", method = RequestMethod.POST)
	public String predict(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	String[] values = request.getParameter("value").split(",");
    	if (values.length == 6) {
	    	String result = modelBean.predict(values);
			model.addAttribute("predict", result);
    	} else {        	
    		model.addAttribute("predict", "Has to be 6 numbers long comma delimited");
    	}
        return "predict";
	}
}
