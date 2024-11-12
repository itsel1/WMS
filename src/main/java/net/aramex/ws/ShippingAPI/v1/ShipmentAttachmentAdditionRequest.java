/**
 * ShipmentAttachmentAdditionRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingAPI.v1;

public class ShipmentAttachmentAdditionRequest  implements java.io.Serializable {
    private net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo;

    private net.aramex.ws.ShippingAPI.v1.Transaction transaction;

    private java.lang.String shipmentNumber;

    private java.lang.String productGroup;

    private java.lang.String originEntity;

    private net.aramex.ws.ShippingAPI.v1.AttachmentInfo attachmentInfo;

    public ShipmentAttachmentAdditionRequest() {
    }

    public ShipmentAttachmentAdditionRequest(
           net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo,
           net.aramex.ws.ShippingAPI.v1.Transaction transaction,
           java.lang.String shipmentNumber,
           java.lang.String productGroup,
           java.lang.String originEntity,
           net.aramex.ws.ShippingAPI.v1.AttachmentInfo attachmentInfo) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.shipmentNumber = shipmentNumber;
           this.productGroup = productGroup;
           this.originEntity = originEntity;
           this.attachmentInfo = attachmentInfo;
    }


    /**
     * Gets the clientInfo value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return clientInfo
     */
    public net.aramex.ws.ShippingAPI.v1.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param clientInfo
     */
    public void setClientInfo(net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return transaction
     */
    public net.aramex.ws.ShippingAPI.v1.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param transaction
     */
    public void setTransaction(net.aramex.ws.ShippingAPI.v1.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the shipmentNumber value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return shipmentNumber
     */
    public java.lang.String getShipmentNumber() {
        return shipmentNumber;
    }


    /**
     * Sets the shipmentNumber value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param shipmentNumber
     */
    public void setShipmentNumber(java.lang.String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }


    /**
     * Gets the productGroup value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the originEntity value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return originEntity
     */
    public java.lang.String getOriginEntity() {
        return originEntity;
    }


    /**
     * Sets the originEntity value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param originEntity
     */
    public void setOriginEntity(java.lang.String originEntity) {
        this.originEntity = originEntity;
    }


    /**
     * Gets the attachmentInfo value for this ShipmentAttachmentAdditionRequest.
     * 
     * @return attachmentInfo
     */
    public net.aramex.ws.ShippingAPI.v1.AttachmentInfo getAttachmentInfo() {
        return attachmentInfo;
    }


    /**
     * Sets the attachmentInfo value for this ShipmentAttachmentAdditionRequest.
     * 
     * @param attachmentInfo
     */
    public void setAttachmentInfo(net.aramex.ws.ShippingAPI.v1.AttachmentInfo attachmentInfo) {
        this.attachmentInfo = attachmentInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentAttachmentAdditionRequest)) return false;
        ShipmentAttachmentAdditionRequest other = (ShipmentAttachmentAdditionRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientInfo==null && other.getClientInfo()==null) || 
             (this.clientInfo!=null &&
              this.clientInfo.equals(other.getClientInfo()))) &&
            ((this.transaction==null && other.getTransaction()==null) || 
             (this.transaction!=null &&
              this.transaction.equals(other.getTransaction()))) &&
            ((this.shipmentNumber==null && other.getShipmentNumber()==null) || 
             (this.shipmentNumber!=null &&
              this.shipmentNumber.equals(other.getShipmentNumber()))) &&
            ((this.productGroup==null && other.getProductGroup()==null) || 
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.originEntity==null && other.getOriginEntity()==null) || 
             (this.originEntity!=null &&
              this.originEntity.equals(other.getOriginEntity()))) &&
            ((this.attachmentInfo==null && other.getAttachmentInfo()==null) || 
             (this.attachmentInfo!=null &&
              this.attachmentInfo.equals(other.getAttachmentInfo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getClientInfo() != null) {
            _hashCode += getClientInfo().hashCode();
        }
        if (getTransaction() != null) {
            _hashCode += getTransaction().hashCode();
        }
        if (getShipmentNumber() != null) {
            _hashCode += getShipmentNumber().hashCode();
        }
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getOriginEntity() != null) {
            _hashCode += getOriginEntity().hashCode();
        }
        if (getAttachmentInfo() != null) {
            _hashCode += getAttachmentInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentAttachmentAdditionRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentAttachmentAdditionRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originEntity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "OriginEntity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachmentInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AttachmentInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AttachmentInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
