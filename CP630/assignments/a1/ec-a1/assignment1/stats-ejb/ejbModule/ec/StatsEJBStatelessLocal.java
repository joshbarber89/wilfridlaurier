package ec;

import javax.ejb.Local;

@Local
public interface StatsEJBStatelessLocal {
	public double getCount();
	public double getMin(); 
	public double getMax();
	public double getMean();
	public double getSTD();
	public String toString();
}
