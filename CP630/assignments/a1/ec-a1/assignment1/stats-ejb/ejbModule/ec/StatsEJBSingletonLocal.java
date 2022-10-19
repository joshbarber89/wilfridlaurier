package ec;

import javax.ejb.Local;

@Local
public interface StatsEJBSingletonLocal {
	public void addData(double data);
	public double getCount();
	public void stats();
	public void saveModel();
}