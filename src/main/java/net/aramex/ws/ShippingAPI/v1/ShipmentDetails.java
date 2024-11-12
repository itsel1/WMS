/**
 * ShipmentDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.aramex.ws.ShippingAPI.v1;

public class ShipmentDetails  implements java.io.Serializable {
    private net.aramex.ws.ShippingAPI.v1.Dimensions dimensions;

    private net.aramex.ws.ShippingAPI.v1.Weight actualWeight;

    private net.aramex.ws.ShippingAPI.v1.Weight chargeableWeight;

    private java.lang.String descriptionOfGoods;

    private java.lang.String goodsOriginCountry;

    private int numberOfPieces;

    private java.lang.String productGroup;

    private java.lang.String productType;

    private java.lang.String paymentType;

    private java.lang.String paymentOptions;

    private net.aramex.ws.ShippingAPI.v1.Money customsValueAmount;

    private net.aramex.ws.ShippingAPI.v1.Money cashOnDeliveryAmount;

    private net.aramex.ws.ShippingAPI.v1.Money insuranceAmount;

    private net.aramex.ws.ShippingAPI.v1.Money cashAdditionalAmount;

    private java.lang.String cashAdditionalAmountDescription;

    private net.aramex.ws.ShippingAPI.v1.Money collectAmount;

    private java.lang.String services;

    private net.aramex.ws.ShippingAPI.v1.ShipmentItem[] items;

    private net.aramex.ws.ShippingAPI.v1.DeliveryInstructions deliveryInstructions;

    private net.aramex.ws.ShippingAPI.v1.AdditionalProperty[] additionalProperties;

    private java.lang.Boolean containsDangerousGoods;

    public ShipmentDetails() {
    }

    public ShipmentDetails(
           net.aramex.ws.ShippingAPI.v1.Dimensions dimensions,
           net.aramex.ws.ShippingAPI.v1.Weight actualWeight,
           net.aramex.ws.ShippingAPI.v1.Weight chargeableWeight,
           java.lang.String descriptionOfGoods,
           java.lang.String goodsOriginCountry,
           int numberOfPieces,
           java.lang.String productGroup,
           java.lang.String productType,
           java.lang.String paymentType,
           java.lang.String paymentOptions,
           net.aramex.ws.ShippingAPI.v1.Money customsValueAmount,
           net.aramex.ws.ShippingAPI.v1.Money cashOnDeliveryAmount,
           net.aramex.ws.ShippingAPI.v1.Money insuranceAmount,
           net.aramex.ws.ShippingAPI.v1.Money cashAdditionalAmount,
           java.lang.String cashAdditionalAmountDescription,
           net.aramex.ws.ShippingAPI.v1.Money collectAmount,
           java.lang.String services,
           net.aramex.ws.ShippingAPI.v1.ShipmentItem[] items,
           net.aramex.ws.ShippingAPI.v1.DeliveryInstructions deliveryInstructions,
           net.aramex.ws.ShippingAPI.v1.AdditionalProperty[] additionalProperties,
           java.lang.Boolean containsDangerousGoods) {
           this.dimensions = dimensions;
           this.actualWeight = actualWeight;
           this.chargeableWeight = chargeableWeight;
           this.descriptionOfGoods = descriptionOfGoods;
           this.goodsOriginCountry = goodsOriginCountry;
           this.numberOfPieces = numberOfPieces;
           this.productGroup = productGroup;
           this.productType = productType;
           this.paymentType = paymentType;
           this.paymentOptions = paymentOptions;
           this.customsValueAmount = customsValueAmount;
           this.cashOnDeliveryAmount = cashOnDeliveryAmount;
           this.insuranceAmount = insuranceAmount;
           this.cashAdditionalAmount = cashAdditionalAmount;
           this.cashAdditionalAmountDescription = cashAdditionalAmountDescription;
           this.collectAmount = collectAmount;
           this.services = services;
           this.items = items;
           this.deliveryInstructions = deliveryInstructions;
           this.additionalProperties = additionalProperties;
           this.containsDangerousGoods = containsDangerousGoods;
    }


    /**
     * Gets the dimensions value for this ShipmentDetails.
     * 
     * @return dimensions
     */
    public net.aramex.ws.ShippingAPI.v1.Dimensions getDimensions() {
        return dimensions;
    }


    /**
     * Sets the dimensions value for this ShipmentDetails.
     * 
     * @param dimensions
     */
    public void setDimensions(net.aramex.ws.ShippingAPI.v1.Dimensions dimensions) {
        this.dimensions = dimensions;
    }


    /**
     * Gets the actualWeight value for this ShipmentDetails.
     * 
     * @return actualWeight
     */
    public net.aramex.ws.ShippingAPI.v1.Weight getActualWeight() {
        return actualWeight;
    }


    /**
     * Sets the actualWeight value for this ShipmentDetails.
     * 
     * @param actualWeight
     */
    public void setActualWeight(net.aramex.ws.ShippingAPI.v1.Weight actualWeight) {
        this.actualWeight = actualWeight;
    }


    /**
     * Gets the chargeableWeight value for this ShipmentDetails.
     * 
     * @return chargeableWeight
     */
    public net.aramex.ws.ShippingAPI.v1.Weight getChargeableWeight() {
        return chargeableWeight;
    }


    /**
     * Sets the chargeableWeight value for this ShipmentDetails.
     * 
     * @param chargeableWeight
     */
    public void setChargeableWeight(net.aramex.ws.ShippingAPI.v1.Weight chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
    }


    /**
     * Gets the descriptionOfGoods value for this ShipmentDetails.
     * 
     * @return descriptionOfGoods
     */
    public java.lang.String getDescriptionOfGoods() {
        return descriptionOfGoods;
    }


    /**
     * Sets the descriptionOfGoods value for this ShipmentDetails.
     * 
     * @param descriptionOfGoods
     */
    public void setDescriptionOfGoods(java.lang.String descriptionOfGoods) {
        this.descriptionOfGoods = descriptionOfGoods;
    }


    /**
     * Gets the goodsOriginCountry value for this ShipmentDetails.
     * 
     * @return goodsOriginCountry
     */
    public java.lang.String getGoodsOriginCountry() {
        return goodsOriginCountry;
    }


    /**
     * Sets the goodsOriginCountry value for this ShipmentDetails.
     * 
     * @param goodsOriginCountry
     */
    public void setGoodsOriginCountry(java.lang.String goodsOriginCountry) {
        this.goodsOriginCountry = goodsOriginCountry;
    }


    /**
     * Gets the numberOfPieces value for this ShipmentDetails.
     * 
     * @return numberOfPieces
     */
    public int getNumberOfPieces() {
        return numberOfPieces;
    }


    /**
     * Sets the numberOfPieces value for this ShipmentDetails.
     * 
     * @param numberOfPieces
     */
    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }


    /**
     * Gets the productGroup value for this ShipmentDetails.
     * 
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this ShipmentDetails.
     * 
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the productType value for this ShipmentDetails.
     * 
     * @return productType
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this ShipmentDetails.
     * 
     * @param productType
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the paymentType value for this ShipmentDetails.
     * 
     * @return paymentType
     */
    public java.lang.String getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this ShipmentDetails.
     * 
     * @param paymentType
     */
    public void setPaymentType(java.lang.String paymentType) {
        this.paymentType = paymentType;
    }


    /**
     * Gets the paymentOptions value for this ShipmentDetails.
     * 
     * @return paymentOptions
     */
    public java.lang.String getPaymentOptions() {
        return paymentOptions;
    }


    /**
     * Sets the paymentOptions value for this ShipmentDetails.
     * 
     * @param paymentOptions
     */
    public void setPaymentOptions(java.lang.String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }


    /**
     * Gets the customsValueAmount value for this ShipmentDetails.
     * 
     * @return customsValueAmount
     */
    public net.aramex.ws.ShippingAPI.v1.Money getCustomsValueAmount() {
        return customsValueAmount;
    }


    /**
     * Sets the customsValueAmount value for this ShipmentDetails.
     * 
     * @param customsValueAmount
     */
    public void setCustomsValueAmount(net.aramex.ws.ShippingAPI.v1.Money customsValueAmount) {
        this.customsValueAmount = customsValueAmount;
    }


    /**
     * Gets the cashOnDeliveryAmount value for this ShipmentDetails.
     * 
     * @return cashOnDeliveryAmount
     */
    public net.aramex.ws.ShippingAPI.v1.Money getCashOnDeliveryAmount() {
        return cashOnDeliveryAmount;
    }


    /**
     * Sets the cashOnDeliveryAmount value for this ShipmentDetails.
     * 
     * @param cashOnDeliveryAmount
     */
    public void setCashOnDeliveryAmount(net.aramex.ws.ShippingAPI.v1.Money cashOnDeliveryAmount) {
        this.cashOnDeliveryAmount = cashOnDeliveryAmount;
    }


    /**
     * Gets the insuranceAmount value for this ShipmentDetails.
     * 
     * @return insuranceAmount
     */
    public net.aramex.ws.ShippingAPI.v1.Money getInsuranceAmount() {
        return insuranceAmount;
    }


    /**
     * Sets the insuranceAmount value for this ShipmentDetails.
     * 
     * @param insuranceAmount
     */
    public void setInsuranceAmount(net.aramex.ws.ShippingAPI.v1.Money insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }


    /**
     * Gets the cashAdditionalAmount value for this ShipmentDetails.
     * 
     * @return cashAdditionalAmount
     */
    public net.aramex.ws.ShippingAPI.v1.Money getCashAdditionalAmount() {
        return cashAdditionalAmount;
    }


    /**
     * Sets the cashAdditionalAmount value for this ShipmentDetails.
     * 
     * @param cashAdditionalAmount
     */
    public void setCashAdditionalAmount(net.aramex.ws.ShippingAPI.v1.Money cashAdditionalAmount) {
        this.cashAdditionalAmount = cashAdditionalAmount;
    }


    /**
     * Gets the cashAdditionalAmountDescription value for this ShipmentDetails.
     * 
     * @return cashAdditionalAmountDescription
     */
    public java.lang.String getCashAdditionalAmountDescription() {
        return cashAdditionalAmountDescription;
    }


    /**
     * Sets the cashAdditionalAmountDescription value for this ShipmentDetails.
     * 
     * @param cashAdditionalAmountDescription
     */
    public void setCashAdditionalAmountDescription(java.lang.String cashAdditionalAmountDescription) {
        this.cashAdditionalAmountDescription = cashAdditionalAmountDescription;
    }


    /**
     * Gets the collectAmount value for this ShipmentDetails.
     * 
     * @return collectAmount
     */
    public net.aramex.ws.ShippingAPI.v1.Money getCollectAmount() {
        return collectAmount;
    }


    /**
     * Sets the collectAmount value for this ShipmentDetails.
     * 
     * @param collectAmount
     */
    public void setCollectAmount(net.aramex.ws.ShippingAPI.v1.Money collectAmount) {
        this.collectAmount = collectAmount;
    }


    /**
     * Gets the services value for this ShipmentDetails.
     * 
     * @return services
     */
    public java.lang.String getServices() {
        return services;
    }


    /**
     * Sets the services value for this ShipmentDetails.
     * 
     * @param services
     */
    public void setServices(java.lang.String services) {
        this.services = services;
    }


    /**
     * Gets the items value for this ShipmentDetails.
     * 
     * @return items
     */
    public net.aramex.ws.ShippingAPI.v1.ShipmentItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this ShipmentDetails.
     * 
     * @param items
     */
    public void setItems(net.aramex.ws.ShippingAPI.v1.ShipmentItem[] items) {
        this.items = items;
    }


    /**
     * Gets the deliveryInstructions value for this ShipmentDetails.
     * 
     * @return deliveryInstructions
     */
    public net.aramex.ws.ShippingAPI.v1.DeliveryInstructions getDeliveryInstructions() {
        return deliveryInstructions;
    }


    /**
     * Sets the deliveryInstructions value for this ShipmentDetails.
     * 
     * @param deliveryInstructions
     */
    public void setDeliveryInstructions(net.aramex.ws.ShippingAPI.v1.DeliveryInstructions deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }


    /**
     * Gets the additionalProperties value for this ShipmentDetails.
     * 
     * @return additionalProperties
     */
    public net.aramex.ws.ShippingAPI.v1.AdditionalProperty[] getAdditionalProperties() {
        return additionalProperties;
    }


    /**
     * Sets the additionalProperties value for this ShipmentDetails.
     * 
     * @param additionalProperties
     */
    public void setAdditionalProperties(net.aramex.ws.ShippingAPI.v1.AdditionalProperty[] additionalProperties) {
        this.additionalProperties = additionalProperties;
    }


    /**
     * Gets the containsDangerousGoods value for this ShipmentDetails.
     * 
     * @return containsDangerousGoods
     */
    public java.lang.Boolean getContainsDangerousGoods() {
        return containsDangerousGoods;
    }


    /**
     * Sets the containsDangerousGoods value for this ShipmentDetails.
     * 
     * @param containsDangerousGoods
     */
    public void setContainsDangerousGoods(java.lang.Boolean containsDangerousGoods) {
        this.containsDangerousGoods = containsDangerousGoods;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentDetails)) return false;
        ShipmentDetails other = (ShipmentDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dimensions==null && other.getDimensions()==null) || 
             (this.dimensions!=null &&
              this.dimensions.equals(other.getDimensions()))) &&
            ((this.actualWeight==null && other.getActualWeight()==null) || 
             (this.actualWeight!=null &&
              this.actualWeight.equals(other.getActualWeight()))) &&
            ((this.chargeableWeight==null && other.getChargeableWeight()==null) || 
             (this.chargeableWeight!=null &&
              this.chargeableWeight.equals(other.getChargeableWeight()))) &&
            ((this.descriptionOfGoods==null && other.getDescriptionOfGoods()==null) || 
             (this.descriptionOfGoods!=null &&
              this.descriptionOfGoods.equals(other.getDescriptionOfGoods()))) &&
            ((this.goodsOriginCountry==null && other.getGoodsOriginCountry()==null) || 
             (this.goodsOriginCountry!=null &&
              this.goodsOriginCountry.equals(other.getGoodsOriginCountry()))) &&
            this.numberOfPieces == other.getNumberOfPieces() &&
            ((this.productGroup==null && other.getProductGroup()==null) || 
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.productType==null && other.getProductType()==null) || 
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) || 
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType()))) &&
            ((this.paymentOptions==null && other.getPaymentOptions()==null) || 
             (this.paymentOptions!=null &&
              this.paymentOptions.equals(other.getPaymentOptions()))) &&
            ((this.customsValueAmount==null && other.getCustomsValueAmount()==null) || 
             (this.customsValueAmount!=null &&
              this.customsValueAmount.equals(other.getCustomsValueAmount()))) &&
            ((this.cashOnDeliveryAmount==null && other.getCashOnDeliveryAmount()==null) || 
             (this.cashOnDeliveryAmount!=null &&
              this.cashOnDeliveryAmount.equals(other.getCashOnDeliveryAmount()))) &&
            ((this.insuranceAmount==null && other.getInsuranceAmount()==null) || 
             (this.insuranceAmount!=null &&
              this.insuranceAmount.equals(other.getInsuranceAmount()))) &&
            ((this.cashAdditionalAmount==null && other.getCashAdditionalAmount()==null) || 
             (this.cashAdditionalAmount!=null &&
              this.cashAdditionalAmount.equals(other.getCashAdditionalAmount()))) &&
            ((this.cashAdditionalAmountDescription==null && other.getCashAdditionalAmountDescription()==null) || 
             (this.cashAdditionalAmountDescription!=null &&
              this.cashAdditionalAmountDescription.equals(other.getCashAdditionalAmountDescription()))) &&
            ((this.collectAmount==null && other.getCollectAmount()==null) || 
             (this.collectAmount!=null &&
              this.collectAmount.equals(other.getCollectAmount()))) &&
            ((this.services==null && other.getServices()==null) || 
             (this.services!=null &&
              this.services.equals(other.getServices()))) &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            ((this.deliveryInstructions==null && other.getDeliveryInstructions()==null) || 
             (this.deliveryInstructions!=null &&
              this.deliveryInstructions.equals(other.getDeliveryInstructions()))) &&
            ((this.additionalProperties==null && other.getAdditionalProperties()==null) || 
             (this.additionalProperties!=null &&
              java.util.Arrays.equals(this.additionalProperties, other.getAdditionalProperties()))) &&
            ((this.containsDangerousGoods==null && other.getContainsDangerousGoods()==null) || 
             (this.containsDangerousGoods!=null &&
              this.containsDangerousGoods.equals(other.getContainsDangerousGoods())));
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
        if (getDimensions() != null) {
            _hashCode += getDimensions().hashCode();
        }
        if (getActualWeight() != null) {
            _hashCode += getActualWeight().hashCode();
        }
        if (getChargeableWeight() != null) {
            _hashCode += getChargeableWeight().hashCode();
        }
        if (getDescriptionOfGoods() != null) {
            _hashCode += getDescriptionOfGoods().hashCode();
        }
        if (getGoodsOriginCountry() != null) {
            _hashCode += getGoodsOriginCountry().hashCode();
        }
        _hashCode += getNumberOfPieces();
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        if (getPaymentOptions() != null) {
            _hashCode += getPaymentOptions().hashCode();
        }
        if (getCustomsValueAmount() != null) {
            _hashCode += getCustomsValueAmount().hashCode();
        }
        if (getCashOnDeliveryAmount() != null) {
            _hashCode += getCashOnDeliveryAmount().hashCode();
        }
        if (getInsuranceAmount() != null) {
            _hashCode += getInsuranceAmount().hashCode();
        }
        if (getCashAdditionalAmount() != null) {
            _hashCode += getCashAdditionalAmount().hashCode();
        }
        if (getCashAdditionalAmountDescription() != null) {
            _hashCode += getCashAdditionalAmountDescription().hashCode();
        }
        if (getCollectAmount() != null) {
            _hashCode += getCollectAmount().hashCode();
        }
        if (getServices() != null) {
            _hashCode += getServices().hashCode();
        }
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeliveryInstructions() != null) {
            _hashCode += getDeliveryInstructions().hashCode();
        }
        if (getAdditionalProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getContainsDangerousGoods() != null) {
            _hashCode += getContainsDangerousGoods().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ActualWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeableWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ChargeableWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionOfGoods");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DescriptionOfGoods"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsOriginCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GoodsOriginCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "NumberOfPieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PaymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PaymentOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsValueAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CustomsValueAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashOnDeliveryAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashOnDeliveryAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insuranceAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "InsuranceAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAdditionalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashAdditionalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAdditionalAmountDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashAdditionalAmountDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CollectAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("services");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Services"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DeliveryInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DeliveryInstructions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalProperties");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperty"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AdditionalProperty"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containsDangerousGoods");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ContainsDangerousGoods"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
