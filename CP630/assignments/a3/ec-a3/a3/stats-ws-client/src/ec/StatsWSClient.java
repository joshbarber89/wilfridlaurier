package ec;

public class StatsWSClient {
    public static void main(String[] args) {
        StatsWSServiceLocator locator = new StatsWSServiceLocator();
        
        StatsWSI service;
        try {
            service = locator.getStatsWSPort();
            double count = service.getCount();
            double min = service.getMin();
            double max = service.getMax();
            double mean = service.getMean();
            double std = service.getSTD();
            
            String out = "Count: " + count + " Min: " + min + " Max: " + max + " Mean: " + mean + " STD: " + std;
            System.out.println(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
