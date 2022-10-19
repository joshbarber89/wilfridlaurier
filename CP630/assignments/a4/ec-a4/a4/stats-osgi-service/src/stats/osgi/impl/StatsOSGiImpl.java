package stats.osgi.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import ec.StatsSummary;
import stats.osgi.StatsOSGiI;

public class StatsOSGiImpl implements StatsOSGiI{
	StatsSummary ss;
	public StatsOSGiImpl() {
		ss = this.loadStatsSummary();		
	}
	
	public StatsSummary loadStatsSummary() {
    	StatsSummary ss = null;
    	try {
			FileInputStream fi = new FileInputStream(new File("C:/enterprise/tmp/model/stats.bin"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			ss = (StatsSummary) oi.readObject();
			
			oi.close();
			fi.close();
			
    	} catch (FileNotFoundException e) {
    		System.out.println("File not found");
    	} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	if (ss != null) {
    		return ss;
    	} else {
    		return ss = new StatsSummary();
    	}
	}

	@Override
	public int getCount() {		
		return (int) ss.count;
	}

	@Override
	public double getMin() {		
		return ss.min;
	}

	@Override
	public double getMax() {
		return ss.max;
	}

	@Override
	public double getMean() {
		return ss.mean;
	}

	@Override
	public double getSTD() {
		return ss.std;
	}
}
