package net.aramex.ws.ShippingAPI.v1;

public class Service_1_0Proxy implements net.aramex.ws.ShippingAPI.v1.Service_1_0_PortType {
  private String _endpoint = null;
  private net.aramex.ws.ShippingAPI.v1.Service_1_0_PortType service_1_0_PortType = null;
  
  public Service_1_0Proxy() {
    _initService_1_0Proxy();
  }
  
  public Service_1_0Proxy(String endpoint) {
    _endpoint = endpoint;
    _initService_1_0Proxy();
  }
  
  private void _initService_1_0Proxy() {
    try {
      service_1_0_PortType = (new net.aramex.ws.ShippingAPI.v1.Service_1_0_ServiceLocator()).getBasicHttpBinding_Service_1_01();
      if (service_1_0_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service_1_0_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service_1_0_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service_1_0_PortType != null)
      ((javax.xml.rpc.Stub)service_1_0_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.aramex.ws.ShippingAPI.v1.Service_1_0_PortType getService_1_0_PortType() {
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType;
  }
  
  public net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse createShipments(net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.createShipments(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse printLabel(net.aramex.ws.ShippingAPI.v1.LabelPrintingRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.printLabel(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.PickupCreationResponse createPickup(net.aramex.ws.ShippingAPI.v1.PickupCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.createPickup(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse cancelPickup(net.aramex.ws.ShippingAPI.v1.PickupCancelationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.cancelPickup(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse reserveShipmentNumberRange(net.aramex.ws.ShippingAPI.v1.ReserveRangeRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.reserveShipmentNumberRange(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse getLastShipmentsNumbersRange(net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.getLastShipmentsNumbersRange(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse scheduleDelivery(net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.scheduleDelivery(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.HoldCreationResponse holdShipments(net.aramex.ws.ShippingAPI.v1.HoldCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.holdShipments(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse addShipmentAttachment(net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.addShipmentAttachment(parameters);
  }
  
  public net.aramex.ws.ShippingAPI.v1.PickupValidationResponse validatePickup(net.aramex.ws.ShippingAPI.v1.PickupValidationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.validatePickup(parameters);
  }
  
  
}