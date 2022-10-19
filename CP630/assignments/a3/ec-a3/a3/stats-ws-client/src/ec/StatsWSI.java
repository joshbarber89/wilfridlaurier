/**
 * StatsWSI.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ec;

public interface StatsWSI extends java.rmi.Remote {
    public double getMax() throws java.rmi.RemoteException;
    public double getCount() throws java.rmi.RemoteException;
    public double getSTD() throws java.rmi.RemoteException;
    public double getMin() throws java.rmi.RemoteException;
    public double getMean() throws java.rmi.RemoteException;
}
