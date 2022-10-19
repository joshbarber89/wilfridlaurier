package ec;

import javax.ejb.Local;

import weka.classifiers.functions.LinearRegression;

@Local
public interface ModelEJBLocal {
	public StatsSummary getObject();
	public LinearRegression getLRObject();
	public boolean saveObject(String name, double data);
}
