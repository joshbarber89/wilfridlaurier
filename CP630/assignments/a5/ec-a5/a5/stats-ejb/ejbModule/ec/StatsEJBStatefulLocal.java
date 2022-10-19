package ec;

import javax.ejb.Local;

@Local
public interface StatsEJBStatefulLocal {
	public void insertData(double value);
	public void createModel();
	public String getStats(); 
}
