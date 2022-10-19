package ec;

import javax.ejb.Remote;

@Remote
public interface StatsEJBStatefulRemote {
	public void insertData(double value);
	public void createModel();
	public String getStats();  
}
