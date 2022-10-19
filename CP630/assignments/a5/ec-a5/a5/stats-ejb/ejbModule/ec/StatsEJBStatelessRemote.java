package ec;

import javax.ejb.Remote;

@Remote
public interface StatsEJBStatelessRemote {
	public double getCount();
	public double getMin(); 
	public double getMax();
	public double getMean();
	public double getSTD();
	public String toString();
}
