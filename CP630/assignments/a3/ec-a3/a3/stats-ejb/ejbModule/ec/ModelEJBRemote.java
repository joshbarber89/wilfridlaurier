package ec;

import javax.ejb.Remote;

import weka.classifiers.functions.LinearRegression;

@Remote
public interface ModelEJBRemote {
	public StatsSummary getObject();
	public LinearRegression getLRObject();
	public boolean saveObject(String name, double data);
}
