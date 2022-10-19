package ec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.ejb.Singleton;
import org.jboss.logging.Logger;

/**
 * Session Bean implementation class StatsEJBSingleton
 */
@Singleton
public class StatsEJBSingleton implements StatsEJBSingletonRemote, StatsEJBSingletonLocal {
    private static final Logger LOGGER = Logger.getLogger(StatsEJBSingleton.class);
    
    private ArrayList<Double> data = new ArrayList<Double>();
	private double count = 0;
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	private double mean = 0;
	private double std = 0;
    /**
     * Default constructor. 
     */
    public StatsEJBSingleton() {}
    
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
    };
    
    @Override
	public double getCount() {
		String message = "\ngetCount:"+this.count;
		return this.count;
    };
    
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
    };
    
    @Override
	public void saveModel() {
		try {

			FileOutputStream f = new FileOutputStream(new File("C:/enterprise/tmp/model/stats.bin"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			StatsSummary ss = new StatsSummary(this.count, this.min, this.max, this.mean, this.std);
			
			o.writeObject(ss);

			o.close();
			f.close();
		
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
    };

}
