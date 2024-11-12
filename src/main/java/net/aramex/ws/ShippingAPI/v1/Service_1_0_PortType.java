/**
 * Service_1_0_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingAPI.v1;

public interface Service_1_0_PortType extends java.rmi.Remote {
    public net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse createShipments(net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse printLabel(net.aramex.ws.ShippingAPI.v1.LabelPrintingRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.PickupCreationResponse createPickup(net.aramex.ws.ShippingAPI.v1.PickupCreationRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse cancelPickup(net.aramex.ws.ShippingAPI.v1.PickupCancelationRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse reserveShipmentNumberRange(net.aramex.ws.ShippingAPI.v1.ReserveRangeRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse getLastShipmentsNumbersRange(net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse scheduleDelivery(net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.HoldCreationResponse holdShipments(net.aramex.ws.ShippingAPI.v1.HoldCreationRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse addShipmentAttachment(net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionRequest parameters) throws java.rmi.RemoteException;
    public net.aramex.ws.ShippingAPI.v1.PickupValidationResponse validatePickup(net.aramex.ws.ShippingAPI.v1.PickupValidationRequest parameters) throws java.rmi.RemoteException;
}
