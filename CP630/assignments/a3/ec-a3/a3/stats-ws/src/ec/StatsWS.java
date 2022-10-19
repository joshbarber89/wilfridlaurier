package ec;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import ec.StatsEJBStatelessLocal;

@WebService(endpointInterface = "ec.StatsWSI")
public class StatsWS implements StatsWSI{
    @EJB
    StatsEJBStatelessLocal stats;
            
    @WebMethod
    public Double getCount() {    	
    	System.out.println("value=" + stats.getCount());
    	return stats.getCount();
    }
    
    @WebMethod
    public Double getMin() {
    	System.out.println("value=" + stats.getMin());
    	return stats.getMin();
    }
    
    @WebMethod
    public Double getMax() {
    	System.out.println("value=" + stats.getMax());
    	return stats.getMax();
    }
    
    @WebMethod
    public Double getMean() {
    	System.out.println("value=" + stats.getMean());
    	return stats.getMean();
    }
    
    @WebMethod
    public Double getSTD() {
    	System.out.println("value=" + stats.getSTD());
    	return stats.getSTD();
    }
}
