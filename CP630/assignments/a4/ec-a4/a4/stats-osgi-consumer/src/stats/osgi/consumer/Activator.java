package stats.osgi.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import stats.osgi.StatsOSGiI;

public class Activator implements BundleActivator {
    private ServiceTracker tracker;
    private StatsOSGiI service;	


	public void start(BundleContext bundleContext) throws Exception {
        tracker = new ServiceTracker(bundleContext, StatsOSGiI.class.getName(), null);
        tracker.open();
        service = (StatsOSGiI) tracker.getService();
        System.out.println("Count: " + service.getCount());
        System.out.println("Min: "+ service.getMin());
        System.out.println("Max: "+ service.getMax());
        System.out.println("Mean "+ service.getMean());
        System.out.println("STD "+ service.getSTD());
	}

	public void stop(BundleContext bundleContext) throws Exception {
        tracker.close();
        tracker = null;
        service = null;
        System.out.println("consumer stop");
	}

}
