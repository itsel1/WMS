/**
 * ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.microsoft.schemas._2003._10.Serialization.Arrays;

public class ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY  implements java.io.Serializable {
    private java.lang.String key;

    private net.aramex.ws.ShippingTrackAPI.v1.TrackingResult[] value;

    public ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY() {
    }

    public ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY(
           java.lang.String key,
           net.aramex.ws.ShippingTrackAPI.v1.TrackingResult[] value) {
           this.key = key;
           this.value = value;
    }


    /**
     * Gets the key value for this ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.
     * 
     * @return key
     */
    public java.lang.String getKey() {
        return key;
    }


    /**
     * Sets the key value for this ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.
     * 
     * @param key
     */
    public void setKey(java.lang.String key) {
        this.key = key;
    }


    /**
     * Gets the value value for this ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.
     * 
     * @return value
     */
    public net.aramex.ws.ShippingTrackAPI.v1.TrackingResult[] getValue() {
        return value;
    }


    /**
     * Sets the value value for this ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.
     * 
     * @param value
     */
    public void setValue(net.aramex.ws.ShippingTrackAPI.v1.TrackingResult[] value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY)) return false;
        ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY other = (ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              java.util.Arrays.equals(this.value, other.getValue())));
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
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getValue() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValue());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValue(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", ">ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpY>KeyValueOfstringArrayOfTrackingResultmFAkxlpY"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingTrackAPI/v1/", "TrackingResult"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingTrackAPI/v1/", "TrackingResult"));
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
