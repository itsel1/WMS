/**
 * Service_1_0_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingTrackAPI.v1;

public interface TrackingService_1_0_PortType extends java.rmi.Remote {
    public net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentResponse trackShipments(net.aramex.ws.ShippingTrackAPI.v1.TrackingShipmentRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingTrackAPI.v1.TrackingPickupResponse trackPickup(net.aramex.ws.ShippingTrackAPI.v1.TrackingPickupRequest parameters) throws java.rmi.RemoteException;
}
