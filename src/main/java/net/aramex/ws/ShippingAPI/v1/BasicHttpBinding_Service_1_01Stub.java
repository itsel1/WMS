/**
 * BasicHttpBinding_Service_1_01Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingAPI.v1;

public class BasicHttpBinding_Service_1_01Stub extends org.apache.axis.client.Stub implements net.aramex.ws.ShippingAPI.v1.Service_1_0_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CreateShipments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentCreationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentCreationRequest"), net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentCreationResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentCreationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PrintLabel");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelPrintingRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LabelPrintingRequest"), net.aramex.ws.ShippingAPI.v1.LabelPrintingRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LabelPrintingResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelPrintingResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CreatePickup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupCreationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCreationRequest"), net.aramex.ws.ShippingAPI.v1.PickupCreationRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCreationResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.PickupCreationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupCreationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelPickup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupCancelationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCancelationRequest"), net.aramex.ws.ShippingAPI.v1.PickupCancelationRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCancelationResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupCancelationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReserveShipmentNumberRange");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ReserveRangeRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ReserveRangeRequest"), net.aramex.ws.ShippingAPI.v1.ReserveRangeRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ReserveRangeResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ReserveRangeResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLastShipmentsNumbersRange");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LastReservedShipmentNumberRangeRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LastReservedShipmentNumberRangeRequest"), net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LastReservedShipmentNumberRangeResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LastReservedShipmentNumberRangeResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ScheduleDelivery");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDeliveryRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ScheduledDeliveryRequest"), net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ScheduledDeliveryResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDeliveryResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("HoldShipments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "HoldCreationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationRequest"), net.aramex.ws.ShippingAPI.v1.HoldCreationRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.HoldCreationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "HoldCreationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AddShipmentAttachment");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentAttachmentAdditionRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentAttachmentAdditionRequest"), net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentAttachmentAdditionResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentAttachmentAdditionResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ValidatePickup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupValidationRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupValidationRequest"), net.aramex.ws.ShippingAPI.v1.PickupValidationRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupValidationResponse"));
        oper.setReturnClass(net.aramex.ws.ShippingAPI.v1.PickupValidationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupValidationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    public BasicHttpBinding_Service_1_01Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_Service_1_01Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_Service_1_01Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.HoldCreationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.HoldCreationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LabelPrintingRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.LabelPrintingRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LabelPrintingResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LastReservedShipmentNumberRangeRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LastReservedShipmentNumberRangeResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCancelationRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupCancelationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCancelationResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCreationRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupCreationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupCreationResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupCreationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupValidationRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupValidationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupValidationResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupValidationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ReserveRangeRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ReserveRangeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ReserveRangeResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ScheduledDeliveryRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ScheduledDeliveryResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentAttachmentAdditionRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentAttachmentAdditionResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentCreationRequest");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentCreationResponse");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperty");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.AdditionalProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Address");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfAdditionalProperty");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.AdditionalProperty[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperty");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperty");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfAttachment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Attachment[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfDimensions");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Dimensions[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfExistingShipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ExistingShipment[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipment");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipment");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfNotification");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Notification[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfPickupItemDetail");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupItemDetail[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfProcessedShipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipment[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfProcessedShipmentAttachment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentAttachment[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachment");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachment");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfProcessedShipmentHold");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentHold[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHold");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHold");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfShipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Shipment[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfShipmentHoldDetails");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentHoldDetails[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHoldDetails");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHoldDetails");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ArrayOfShipmentItem");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem");
            qName2 = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Attachment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AttachmentInfo");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.AttachmentInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ClientInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Contact");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Contact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DeliveryInstructions");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.DeliveryInstructions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Dimensions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ExistingShipment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelInfo");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.LabelInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Money.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Notification.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Party");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Party.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Pickup");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Pickup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.PickupItemDetail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedPickup");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedPickup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentAttachment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachmentType");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentAttachmentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentDetails");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHold");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ProcessedShipmentHold.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDelivery");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ScheduledDelivery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Shipment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentDetails");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHoldDetails");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentHoldDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentLabel");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.ShipmentLabel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Transaction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Volume");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Volume.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight");
            cachedSerQNames.add(qName);
            cls = net.aramex.ws.ShippingAPI.v1.Weight.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse createShipments(net.aramex.ws.ShippingAPI.v1.ShipmentCreationRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/CreateShipments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CreateShipments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.ShipmentCreationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse printLabel(net.aramex.ws.ShippingAPI.v1.LabelPrintingRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/PrintLabel");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PrintLabel"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.LabelPrintingResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.PickupCreationResponse createPickup(net.aramex.ws.ShippingAPI.v1.PickupCreationRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/CreatePickup");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CreatePickup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.PickupCreationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.PickupCreationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.PickupCreationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse cancelPickup(net.aramex.ws.ShippingAPI.v1.PickupCancelationRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/CancelPickup");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CancelPickup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.PickupCancelationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse reserveShipmentNumberRange(net.aramex.ws.ShippingAPI.v1.ReserveRangeRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/ReserveShipmentNumberRange");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ReserveShipmentNumberRange"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.ReserveRangeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse getLastShipmentsNumbersRange(net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/GetLastShipmentsNumbersRange");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "GetLastShipmentsNumbersRange"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.LastReservedShipmentNumberRangeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse scheduleDelivery(net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/ScheduleDelivery");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ScheduleDelivery"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.ScheduledDeliveryResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.HoldCreationResponse holdShipments(net.aramex.ws.ShippingAPI.v1.HoldCreationRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/HoldShipments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "HoldShipments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.HoldCreationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.HoldCreationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.HoldCreationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse addShipmentAttachment(net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/AddShipmentAttachment");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "AddShipmentAttachment"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.ShipmentAttachmentAdditionResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public net.aramex.ws.ShippingAPI.v1.PickupValidationResponse validatePickup(net.aramex.ws.ShippingAPI.v1.PickupValidationRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://ws.aramex.net/ShippingAPI/v1/Service_1_0/ValidatePickup");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ValidatePickup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (net.aramex.ws.ShippingAPI.v1.PickupValidationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (net.aramex.ws.ShippingAPI.v1.PickupValidationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, net.aramex.ws.ShippingAPI.v1.PickupValidationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
