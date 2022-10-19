package ec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RankApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("RankBeans.xml");
	
		Rank Rank = (Rank) context.getBean("rank-bean");
		
		for (int i = 0; i < Rank.getScores().length; i++) {
			System.out.println("score: "+ Rank.getScores()[i] + " rank: "+ Rank.getRank(Rank.getScores()[i]) + " grade: " + Rank.getGrade(Rank.getScores()[i]));
		}
		int score = 98;
		int rank = Rank.getRank(score);
		String grade = Rank.getGrade(score);
		
		System.out.println("Prediction for score: "+score+ " rank: " + rank + " grade: " + grade);
		
		
	}
}
