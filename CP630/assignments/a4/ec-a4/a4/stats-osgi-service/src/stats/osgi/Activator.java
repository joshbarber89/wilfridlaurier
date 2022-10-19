package stats.osgi;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import stats.osgi.StatsOSGiI;
import stats.osgi.impl.StatsOSGiImpl;

public class Activator implements BundleActivator {

	private StatsOSGiI statsSummary;


	public void start(BundleContext bundleContext) throws Exception {
		statsSummary = new StatsOSGiImpl();
		Hashtable props = new Hashtable();
		bundleContext.registerService(StatsOSGiI.class.getName(), statsSummary, props);
        System.out.println("Stats Summary service start");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		statsSummary = null;
        System.out.println("service stop");
	}

}
