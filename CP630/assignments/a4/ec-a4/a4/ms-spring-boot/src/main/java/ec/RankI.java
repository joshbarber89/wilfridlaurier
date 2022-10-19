package ec;

public interface RankI {
	/*
	 * Simply returns `grade.getLetterGrade(s)`
	*/
	String getGrade(int s);

	/*
	 * Returns the number of scores bigger than s plus 1. 
	 * Try to use efficient data structure and algorithm.
	*/ 
	int getRank(int s);
}
