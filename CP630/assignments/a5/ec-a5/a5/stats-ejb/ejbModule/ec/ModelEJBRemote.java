package ec;

import javax.ejb.Remote;

@Remote
public interface ModelEJBRemote {
	public StatsSummary getObject();
	public boolean saveObject(String name, double data);
	public StatsSummary getObjectByName(String name);
}
