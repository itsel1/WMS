/**
 * LastReservedShipmentNumberRangeRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingAPI.v1;

public class LastReservedShipmentNumberRangeRequest  implements java.io.Serializable {
    private net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo;

    private net.aramex.ws.ShippingAPI.v1.Transaction transaction;

    private java.lang.String entity;

    private java.lang.String productGroup;

    public LastReservedShipmentNumberRangeRequest() {
    }

    public LastReservedShipmentNumberRangeRequest(
           net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo,
           net.aramex.ws.ShippingAPI.v1.Transaction transaction,
           java.lang.String entity,
           java.lang.String productGroup) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.entity = entity;
           this.productGroup = productGroup;
    }


    /**
     * Gets the clientInfo value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @return clientInfo
     */
    public net.aramex.ws.ShippingAPI.v1.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @param clientInfo
     */
    public void setClientInfo(net.aramex.ws.ShippingAPI.v1.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @return transaction
     */
    public net.aramex.ws.ShippingAPI.v1.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @param transaction
     */
    public void setTransaction(net.aramex.ws.ShippingAPI.v1.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the entity value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @return entity
     */
    public java.lang.String getEntity() {
        return entity;
    }


    /**
     * Sets the entity value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @param entity
     */
    public void setEntity(java.lang.String entity) {
        this.entity = entity;
    }


    /**
     * Gets the productGroup value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this LastReservedShipmentNumberRangeRequest.
     * 
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LastReservedShipmentNumberRangeRequest)) return false;
        LastReservedShipmentNumberRangeRequest other = (LastReservedShipmentNumberRangeRequest) obj;
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
            ((this.entity==null && other.getEntity()==null) || 
             (this.entity!=null &&
              this.entity.equals(other.getEntity()))) &&
            ((this.productGroup==null && other.getProductGroup()==null) || 
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup())));
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
        if (getEntity() != null) {
            _hashCode += getEntity().hashCode();
        }
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LastReservedShipmentNumberRangeRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">LastReservedShipmentNumberRangeRequest"));
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
        elemField.setFieldName("entity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Entity"));
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
