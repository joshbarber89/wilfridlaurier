package ec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Rank implements RankI {
	private String name;
	private Integer[] scores = {71,71,85,70,85,99,70,79,89,83,96,85,82,84,96,77,89,81,71,90,89,71,99,99,84,74,90,75,73,86};
	private int count;
	private Grade grade;
	
	Rank() {
		Arrays.sort(scores, Collections.reverseOrder());
		count = scores.length;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Grade getGrade() {
		return this.grade;
	}
	
	public Integer[] getScores() {
		return this.scores;
	}
	
	/*
	 * Simply returns `grade.getLetterGrade(s)`
	*/	
	public String getGrade(int s) {
		ApplicationContext context = new ClassPathXmlApplicationContext("GradeBeans.xml");

		Grade Grade = (Grade) context.getBean("grade-bean");
		
		return Grade.getLetterGrade(s);
	}
	
	/*
	 * Returns the number of scores bigger than s plus 1. 
	 * Try to use efficient data structure and algorithm.
	*/ 
	public int getRank(int s) {
		int l = 0;
		int r = count - 1;
		int m = (l + r) / 2;
		while (l < r - 1) {
			if (this.scores[l] == s) {
				m = l;
				break;
			} 
			else if (this.scores[r] == s) {
				m = r;
				break;
			}
			
			
			m = (l + r) / 2;
			if (this.scores[m] < s)
				r = m;
			else
				l = m;
		}

		if (l == r - 1) {
			if (this.scores[r] < s && this.scores[l] > s ) {
				m = r;
			} else 
			m = l;
		}
		while (m > 0 && scores[m - 1] == scores[m]) {
			m = m - 1;			
		}
		return m+1;

	}

}
