package ec;

import javax.ejb.Local;
import javax.jms.JMSException;
import javax.naming.NamingException;

@Local
public interface StatsEJBJMSStatelessLocal {
	public void produce(String message) throws NamingException, JMSException;
	public void publish(String message) throws Exception;
}
