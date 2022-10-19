package ec;

import javax.ejb.Local;

@Local
public interface StatsEJBSingletonLocal {
	public void addData(double data);
	public StatsSummary getData();
	public void setData(StatsSummary sm);
	public double getCount();
	public void stats();
	public void saveModel();
}