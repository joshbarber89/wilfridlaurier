package ec.osgi.web;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import stats.osgi.StatsOSGiI;


public class Activator implements BundleActivator {

    private ServiceTracker httpServiceTracker;
    private ServiceTracker tracker;
    private StatsOSGiI service;
    
    @Override
	public void start(BundleContext bundleContext) throws Exception {
        tracker = new ServiceTracker(bundleContext, StatsOSGiI.class.getName(), null);
        tracker.open();
        service = (StatsOSGiI) tracker.getService();
        
        httpServiceTracker = new ServiceTracker(bundleContext, HttpService.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference ref) {
                HttpService httpService = (HttpService) bundleContext.getService(ref);
                try {
                   httpService.registerServlet("/stats-osgi-web/statsSummary", new OSGiServlet(service), null, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return httpService;
            }
            public void removedService(ServiceReference ref, Object service) {
                try {
                    ((HttpService) service).unregister("/stats-osgi-web/statsSummary");
               
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        httpServiceTracker.open();
        System.out.println("stats-osgi-web started");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
        httpServiceTracker.close();
        tracker.close();
        tracker = null;
        System.out.println("stats-osgi-web closed");
	}

}
