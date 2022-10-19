package ec;

public class StatsWSIProxy implements ec.StatsWSI {
  private String _endpoint = null;
  private ec.StatsWSI statsWSI = null;
  
  public StatsWSIProxy() {
    _initStatsWSIProxy();
  }
  
  public StatsWSIProxy(String endpoint) {
    _endpoint = endpoint;
    _initStatsWSIProxy();
  }
  
  private void _initStatsWSIProxy() {
    try {
      statsWSI = (new ec.StatsWSServiceLocator()).getStatsWSPort();
      if (statsWSI != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)statsWSI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)statsWSI)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (statsWSI != null)
      ((javax.xml.rpc.Stub)statsWSI)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ec.StatsWSI getStatsWSI() {
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI;
  }
  
  public double getMax() throws java.rmi.RemoteException{
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI.getMax();
  }
  
  public double getCount() throws java.rmi.RemoteException{
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI.getCount();
  }
  
  public double getSTD() throws java.rmi.RemoteException{
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI.getSTD();
  }
  
  public double getMin() throws java.rmi.RemoteException{
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI.getMin();
  }
  
  public double getMean() throws java.rmi.RemoteException{
    if (statsWSI == null)
      _initStatsWSIProxy();
    return statsWSI.getMean();
  }
  
  
}