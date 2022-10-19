package ec;

import javax.ejb.Local;

@Local
public interface ModelEJBLocal {
	public StatsSummary getObject();
	public boolean saveObject(String name, double data);
	public StatsSummary getObjectByName(String name);
}
