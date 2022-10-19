package ec;

import org.springframework.stereotype.Service;

@Service
public class Grade implements GradeI {
	private String name;
	//private int[] gradeBoundary = { 100, 90, 85, 80, 77, 73, 70, 0 };
	private int[] gradeBoundary = {100, 90, 85, 80, 77, 73, 70, 67, 63, 60, 57, 53, 50, 0 };
	//private String[] gradeLetter = { "A+", "A", "A-", "B+", "B", "B-", "F" };
	private String[] gradeLetter = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};
	//private int count = 8;
	private int count = 14;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getLetterGrade(int numerical_grade) {
		String grade = "";
		for (int i = 0; i < count; i++) {
			if (numerical_grade >= gradeBoundary[i]) {
				int gradIndex = (i == 0) ? 0 : i - 1; 
				grade = gradeLetter[gradIndex];
				break;
			}
		}
		return grade;
	}
}
