package ec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class StatsEJBStateless
 */
@Stateless
public class StatsEJBStateless implements StatsEJBStatelessRemote, StatsEJBStatelessLocal {

    /**
     * Default constructor. 
     */
    public StatsEJBStateless() {
        // TODO Auto-generated constructor stub
    }
    
    public StatsSummary loadModel() {
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
	public double getCount() {
    	StatsSummary ss = this.loadModel();
    	return ss.count;
    };
	
    @Override
	public double getMin() {
    	StatsSummary ss = this.loadModel();
    	return ss.min;
    };
	
    @Override
	public double getMax() {
    	StatsSummary ss = this.loadModel();
    	return ss.max;
    };
	
    @Override
	public double getMean() {
    	StatsSummary ss = this.loadModel();
    	return ss.mean;    	
    };
	
    @Override
	public double getSTD() {
    	StatsSummary ss = this.loadModel();
    	return ss.std;    	
    };
	
    @Override
	public String toString() {
    	StatsSummary ss = this.loadModel();
    	return "Count:"+ ss.count + "\nMin:" + ss.min + "\nMax:" + ss.max + "\nMean:" + ss.mean + "\nSTD:" + ss.std;    	
    };    
}
