package ec;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class StatsEJBStateful
 */
@Stateless
public class StatsEJBStateful implements StatsEJBStatefulRemote, StatsEJBStatefulLocal {

    @EJB
    private StatsEJBSingletonLocal singleton;
    
    @EJB
    private StatsEJBStatelessLocal stateless;
    /**
     * Default constructor. 
     */
    public StatsEJBStateful() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void insertData(double value) {
    	singleton.addData(value);
    };
    
    @Override
	public void createModel() {
    	singleton.saveModel();
    };
    
    @Override
	public String getStats() {
    	return stateless.toString();
    }; 
}
