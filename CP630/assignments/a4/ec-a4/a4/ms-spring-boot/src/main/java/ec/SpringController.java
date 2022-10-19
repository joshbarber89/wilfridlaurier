package ec;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SpringController {
    @Autowired
    private GradeI grade;
    
    @Autowired
    private RankI rank;
    
	@RequestMapping(value ="/grade/{number}")
	public String gradeURI(@PathVariable String number, Model model) {
		String g = grade.getLetterGrade(Integer.parseInt(number));	
		model.addAttribute("grade", g);
		model.addAttribute("score", number);
        return "grade";

	}

	
	@RequestMapping(value = "/rank/{number}")
	public String rank(@PathVariable String number, Model model) {
		int r = rank.getRank(Integer.parseInt(number));	
		model.addAttribute("rank", r);
		model.addAttribute("score", number);
        return "rank";
	}
	
	@RequestMapping(value = "/grade_rank/{number}")
	public String grade_rank(@PathVariable String number, Model model) {
		int r = rank.getRank(Integer.parseInt(number));	
		String g = rank.getGrade(Integer.parseInt(number));
		model.addAttribute("rank", r);
		model.addAttribute("grade", g);
		model.addAttribute("score", number);
        return "grade_rank";
	}
	
}
