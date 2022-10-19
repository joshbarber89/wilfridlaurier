package ec;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ec.StatsEJBSingletonRemote;
import ec.StatsEJBStatefulRemote;
import ec.StatsEJBStatelessRemote;;

public class EJBClient {
    public static void main(String[] args) throws NamingException {
        StatsEJBSingletonRemote sbsingleton = (StatsEJBSingletonRemote) InitialContext
                .doLookup("stats-ejb/StatsEJBSingleton!ec.StatsEJBSingletonRemote");
        for (int i = 1; i <= 10; i++) {
        	sbsingleton.addData(i);
        }
        System.out.println(sbsingleton.getCount());
        sbsingleton.saveModel();
        
    	StatsEJBStatelessRemote sbstateless = (StatsEJBStatelessRemote) InitialContext
                .doLookup("stats-ejb/StatsEJBStateless!ec.StatsEJBStatelessRemote"); 
        System.out.println(sbstateless.getCount());
        System.out.println(sbstateless.getMean());

        StatsEJBStatefulRemote sbstateful = (StatsEJBStatefulRemote) InitialContext
                .doLookup("stats-ejb/StatsEJBStateful!ec.StatsEJBStatefulRemote");
        for (int i = 11; i <= 100; i++) {
        	sbstateful.insertData(i);;
        }
        sbstateful.createModel();
        System.out.println(sbstateful.getStats());
    }
}