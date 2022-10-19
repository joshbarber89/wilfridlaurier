package ec;

import javax.ejb.Remote;

@Remote
public interface StatsEJBSingletonRemote {
	public void addData(double data);
	public double getCount();
	public void stats();
	public void saveModel();
}
