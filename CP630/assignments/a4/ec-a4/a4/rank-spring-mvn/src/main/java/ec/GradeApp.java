package ec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GradeApp {
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("GradeBeans.xml");

		Grade Grade = (Grade) context.getBean("grade-bean");
		
		
		/*int count = 0;
		for (int i = 66; i <= 100; i++) {
			String grade = Grade.getLetterGrade(i);
			String lineBreak = "";
			if (++count % 5 == 0) {
				lineBreak = "\n";
			}
			System.out.print(i+":"+grade+ " "+lineBreak);
			
		}*/ 
		
		
		int count = 0;
		for (int i = 46; i <= 100; i++) {
			String grade = Grade.getLetterGrade(i);
			String lineBreak = "";
			if (++count % 5 == 0) {
				lineBreak = "\n";
			}
			System.out.print(i+":"+grade+ " "+lineBreak);
			
		}
	}
}
