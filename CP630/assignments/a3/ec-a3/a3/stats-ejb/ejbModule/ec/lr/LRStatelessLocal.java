package ec.lr;

import javax.ejb.Local;

@Local
public interface LRStatelessLocal {
	public String predict(String[] args) throws Exception;
}
