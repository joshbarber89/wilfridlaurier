package stats.ec;

import java.io.Serializable;

public class StatsSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	private double count;
	private double min;
	private double max;
	private double mean;
	private double std;
	
	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
	
	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStd() {
		return std;
	}

	public void setStd(double std) {
		this.std = std;
	}

	public StatsSummary(double count, double min, double max, double mean, double std) {
		this.setCount(count);
		this.setMin(min);
		this.setMax(max);
		this.setMean(mean);
		this.setStd(std);
	}

	public StatsSummary() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("count:" + count + "\n");
		sb.append("min:" + min + "\n");
		sb.append("max:" + max + "\n");
		sb.append("mean:" + mean + "\n");
		sb.append("std:" + std + "\n");
		return sb.toString();
	}
	
}
