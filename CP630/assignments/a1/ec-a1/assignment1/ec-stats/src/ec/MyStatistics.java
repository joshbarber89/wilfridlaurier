package ec;

public class MyStatistics {
	public static void main(String[] args) {
		ECStatistics statistics = new ECStatistics();
		for (int i = 1; i <= 10000; i++) {
			statistics.addData(i);
			statistics.getCount();
			statistics.getMin();
			statistics.getMax();
			statistics.getMean();
			statistics.getSTD();
			statistics.stats();
		}
	}
}
