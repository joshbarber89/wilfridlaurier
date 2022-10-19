package ec;

import java.io.Serializable;
import java.util.ArrayList;

public class StatsSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	public ArrayList<Double> data = new ArrayList<Double>();
	public double count;
	public double min;
	public double max;
	public double mean;
	public double std;
	public StatsSummary(
			ArrayList<Double> data,
			double count,
			double min,
			double max,
			double mean,
			double std
	) {
		this.data = data;
		this.count = count;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.std = std;
	}
	public StatsSummary() {}
}
