package ec;

import java.io.Serializable;

public class StatsSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	public double count;
	public double min;
	public double max;
	public double mean;
	public double std;
	public StatsSummary(
			double count,
			double min,
			double max,
			double mean,
			double std
	) {
		this.count = count;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.std = std;
	}
	public StatsSummary() {}
}
