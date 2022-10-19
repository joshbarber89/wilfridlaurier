package ec;

import java.util.ArrayList;

public class ECStatistics implements Statistics {
	private ArrayList<Double> data = new ArrayList<Double>();
	private double count = 0;
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	private double mean = 0;
	private double std = 0;
	
	@Override
	public void addData(double value) {
		this.data.add(value);		
		if (value < this.min) {
			this.min = value;
		}
		if (value > this.max) {
			this.max = value;
		}
		double std = Math.pow(value - this.mean, 2);
		this.std = Math.sqrt(((Math.pow(this.std, 2) * this.count) + std) / (this.count + 1));	
		this.mean = ((this.mean * this.count) + value) / (this.count + 1);	
		this.count++;
	}

	@Override
	public double getCount() {
		String message = "\ngetCount:"+this.count;
		System.out.println(message);
		return this.count;
	}

	@Override
	public double getMin() {
		String message = "\ngetMin:"+this.min;
		System.out.println(message);
		return this.min;
	}

	@Override
	public double getMax() {
		String message = "\ngetMax:"+this.max;
		System.out.println(message);
		return this.max;
	}

	@Override
	public double getMean() {
		String message = "\ngetMean:"+this.mean;
		System.out.println(message);
		return this.mean;
	}

	@Override
	public double getSTD() {
		String message = "\ngetSTD:"+this.std;
		System.out.println(message);
		return this.std;
	}

	@Override
	public void stats() {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE; 
		double count = 0;
		double total = 0;
		double total2 = 0;
		double variance = 0;
		double mean = 0;
		double std = 0;
		for (int i = 0; i < data.size(); i++) {
			count++;
			total += data.get(i);	
			total2 += data.get(i) * data.get(i);
			if (data.get(i) < min ) {
				min = data.get(i);
			}
			if (data.get(i) > max ) {
				max = data.get(i);
			}
			if (data.get(i) > max ) {
				max = data.get(i);
			}
		}
		mean = total / count;
		variance = (count * total2 - total * total) / (count * count);
		std = Math.sqrt(variance);
		
		this.count = count;
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.std = std;
		
		
		String message = "\n\n\nStats Call:\nCount:"+this.count+"\nMin:"+this.min+"\nMax:"+this.max+"\nMean:"+this.mean+"\nStd:"+this.std;
		System.out.println(message);
	}
}
