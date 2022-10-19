/**
 * StatsWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ec;

public interface StatsWSService extends javax.xml.rpc.Service {
    public java.lang.String getStatsWSPortAddress();

    public ec.StatsWSI getStatsWSPort() throws javax.xml.rpc.ServiceException;

    public ec.StatsWSI getStatsWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
