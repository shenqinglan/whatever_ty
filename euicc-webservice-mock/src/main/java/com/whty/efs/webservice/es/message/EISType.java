
package com.whty.efs.webservice.es.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * eUICC Information Set. The content may be only partial information depending of the function where it is used. 
 * 
 * <p>EISType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="EISType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EumSignedInfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Eid" type="{}EIDType"/&gt;
 *                   &lt;element name="Eum-id" type="{}ObjectIdentifierType"/&gt;
 *                   &lt;element name="ProductionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *                   &lt;element name="PlatformType" type="{}String100"/&gt;
 *                   &lt;element name="PlatformVersion" type="{}ThreeDigitVersion"/&gt;
 *                   &lt;element name="Isd-p-loadfile-aid" type="{}AIDType"/&gt;
 *                   &lt;element name="Isd-p-module-aid" type="{}AIDType"/&gt;
 *                   &lt;element name="Ecasd" type="{}SecurityDomainType"/&gt;
 *                   &lt;element name="EuiccCapabilities"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="CattpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="CattpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
 *                             &lt;element name="HttpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="HttpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
 *                             &lt;element name="SecurePacketVersion" type="{}ThreeDigitVersion"/&gt;
 *                             &lt;element name="RemoteProvisioningVersion" type="{}ThreeDigitVersion"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EumSignature" type="{http://www.w3.org/2000/09/xmldsig#}SignatureType"/&gt;
 *         &lt;element name="RemainingMemory" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *         &lt;element name="AvailableMemoryForProfiles" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *         &lt;element name="LastAuditDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Smsr-id" type="{}ObjectIdentifierType"/&gt;
 *         &lt;element name="ProfileInfo" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Iccid" type="{}ICCIDType"/&gt;
 *                   &lt;element name="Isd-p-aid" type="{}AIDType"/&gt;
 *                   &lt;element name="Mno-id" type="{}ObjectIdentifierType"/&gt;
 *                   &lt;element name="FallbackAttribute" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="SubscriptionAddress" type="{}SubscriptionAddressType"/&gt;
 *                   &lt;element name="State" type="{}ProfileStateType"/&gt;
 *                   &lt;element name="Smdp-id" type="{}ObjectIdentifierType" minOccurs="0"/&gt;
 *                   &lt;element name="ProfileType" type="{}String100" minOccurs="0"/&gt;
 *                   &lt;element name="AllocatedMemory" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *                   &lt;element name="FreeMemory" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *                   &lt;element name="pol2" type="{}POL2Type"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Isd-r" type="{}SecurityDomainType"/&gt;
 *         &lt;element name="AuditTrail" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Record" type="{}AuditTrailRecordType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AdditionalProperties" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Property" type="{}PropertyType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EISType",  propOrder = {
    "eumSignedInfo",
    "eumSignature",
    "remainingMemory",
    "availableMemoryForProfiles",
    "lastAuditDate",
    "smsrId",
    "profileInfo",
    "isdR",
    "auditTrail",
    "additionalProperties"
})
public class EISType {

    @XmlElement(name = "EumSignedInfo", required = true)
    protected EISType.EumSignedInfo eumSignedInfo;
    @XmlElement(name = "EumSignature", required = true)
    protected SignatureType eumSignature;
    @XmlElement(name = "RemainingMemory", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger remainingMemory;
    @XmlElement(name = "AvailableMemoryForProfiles", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger availableMemoryForProfiles;
    @XmlElement(name = "LastAuditDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastAuditDate;
    @XmlElement(name = "Smsr-id", required = true)
    protected String smsrId;
    @XmlElement(name = "ProfileInfo")
    protected List<EISType.ProfileInfo> profileInfo;
    @XmlElement(name = "Isd-r", required = true)
    protected SecurityDomainType isdR;
    @XmlElement(name = "AuditTrail")
    protected EISType.AuditTrail auditTrail;
    @XmlElement(name = "AdditionalProperties")
    protected EISType.AdditionalProperties additionalProperties;

    /**
     * 获取eumSignedInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EISType.EumSignedInfo }
     *     
     */
    public EISType.EumSignedInfo getEumSignedInfo() {
        return eumSignedInfo;
    }

    /**
     * 设置eumSignedInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EISType.EumSignedInfo }
     *     
     */
    public void setEumSignedInfo(EISType.EumSignedInfo value) {
        this.eumSignedInfo = value;
    }

    /**
     * 获取eumSignature属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getEumSignature() {
        return eumSignature;
    }

    /**
     * 设置eumSignature属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setEumSignature(SignatureType value) {
        this.eumSignature = value;
    }

    /**
     * 获取remainingMemory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRemainingMemory() {
        return remainingMemory;
    }

    /**
     * 设置remainingMemory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRemainingMemory(BigInteger value) {
        this.remainingMemory = value;
    }

    /**
     * 获取availableMemoryForProfiles属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableMemoryForProfiles() {
        return availableMemoryForProfiles;
    }

    /**
     * 设置availableMemoryForProfiles属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableMemoryForProfiles(BigInteger value) {
        this.availableMemoryForProfiles = value;
    }

    /**
     * 获取lastAuditDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastAuditDate() {
        return lastAuditDate;
    }

    /**
     * 设置lastAuditDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastAuditDate(XMLGregorianCalendar value) {
        this.lastAuditDate = value;
    }

    /**
     * 获取smsrId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsrId() {
        return smsrId;
    }

    /**
     * 设置smsrId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsrId(String value) {
        this.smsrId = value;
    }

    /**
     * Gets the value of the profileInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profileInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfileInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EISType.ProfileInfo }
     * 
     * 
     */
    public List<EISType.ProfileInfo> getProfileInfo() {
        if (profileInfo == null) {
            profileInfo = new ArrayList<EISType.ProfileInfo>();
        }
        return this.profileInfo;
    }

    public void setProfileInfo(List<EISType.ProfileInfo> profileInfo) {
		this.profileInfo = profileInfo;
	}

	/**
     * 获取isdR属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SecurityDomainType }
     *     
     */
    public SecurityDomainType getIsdR() {
        return isdR;
    }

    /**
     * 设置isdR属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityDomainType }
     *     
     */
    public void setIsdR(SecurityDomainType value) {
        this.isdR = value;
    }

    /**
     * 获取auditTrail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EISType.AuditTrail }
     *     
     */
    public EISType.AuditTrail getAuditTrail() {
        return auditTrail;
    }

    /**
     * 设置auditTrail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EISType.AuditTrail }
     *     
     */
    public void setAuditTrail(EISType.AuditTrail value) {
        this.auditTrail = value;
    }

    /**
     * 获取additionalProperties属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EISType.AdditionalProperties }
     *     
     */
    public EISType.AdditionalProperties getAdditionalProperties() {
        return additionalProperties;
    }

    /**
     * 设置additionalProperties属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EISType.AdditionalProperties }
     *     
     */
    public void setAdditionalProperties(EISType.AdditionalProperties value) {
        this.additionalProperties = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Property" type="{}PropertyType" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "property"
    })
    public static class AdditionalProperties {

        @XmlElement(name = "Property",  required = true)
        protected List<PropertyType> property;

        /**
         * Gets the value of the property property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProperty().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PropertyType }
         * 
         * 
         */
        public List<PropertyType> getProperty() {
            if (property == null) {
                property = new ArrayList<PropertyType>();
            }
            return this.property;
        }

    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Record" type="{}AuditTrailRecordType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "record"
    })
    public static class AuditTrail {

        @XmlElement(name = "Record")
        protected List<AuditTrailRecordType> record;

        /**
         * Gets the value of the record property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the record property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecord().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AuditTrailRecordType }
         * 
         * 
         */
        public List<AuditTrailRecordType> getRecord() {
            if (record == null) {
                record = new ArrayList<AuditTrailRecordType>();
            }
            return this.record;
        }

    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Eid" type="{}EIDType"/&gt;
     *         &lt;element name="Eum-id" type="{}ObjectIdentifierType"/&gt;
     *         &lt;element name="ProductionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
     *         &lt;element name="PlatformType" type="{}String100"/&gt;
     *         &lt;element name="PlatformVersion" type="{}ThreeDigitVersion"/&gt;
     *         &lt;element name="Isd-p-loadfile-aid" type="{}AIDType"/&gt;
     *         &lt;element name="Isd-p-module-aid" type="{}AIDType"/&gt;
     *         &lt;element name="Ecasd" type="{}SecurityDomainType"/&gt;
     *         &lt;element name="EuiccCapabilities"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="CattpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="CattpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
     *                   &lt;element name="HttpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="HttpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
     *                   &lt;element name="SecurePacketVersion" type="{}ThreeDigitVersion"/&gt;
     *                   &lt;element name="RemoteProvisioningVersion" type="{}ThreeDigitVersion"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "eid",
        "eumId",
        "productionDate",
        "platformType",
        "platformVersion",
        "isdPLoadfileAid",
        "isdPModuleAid",
        "ecasd",
        "euiccCapabilities"
    })
    public static class EumSignedInfo {

        @XmlElement(name = "Eid",  required = true, type = String.class)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] eid;
        @XmlElement(name = "Eum-id",  required = true)
        protected String eumId;
        @XmlElement(name = "ProductionDate",  required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar productionDate;
        @XmlElement(name = "PlatformType",  required = true)
        protected String platformType;
        @XmlElement(name = "PlatformVersion",  required = true)
        protected String platformVersion;
        @XmlElement(name = "Isd-p-loadfile-aid",  required = true, type = String.class)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] isdPLoadfileAid;
        @XmlElement(name = "Isd-p-module-aid",  required = true, type = String.class)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] isdPModuleAid;
        @XmlElement(name = "Ecasd",  required = true)
        protected SecurityDomainType ecasd;
        @XmlElement(name = "EuiccCapabilities",  required = true)
        protected EISType.EumSignedInfo.EuiccCapabilities euiccCapabilities;

        /**
         * 获取eid属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getEid() {
            return eid;
        }

        /**
         * 设置eid属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEid(byte[] value) {
            this.eid = value;
        }

        /**
         * 获取eumId属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEumId() {
            return eumId;
        }

        /**
         * 设置eumId属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEumId(String value) {
            this.eumId = value;
        }

        /**
         * 获取productionDate属性的值。
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getProductionDate() {
            return productionDate;
        }

        /**
         * 设置productionDate属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setProductionDate(XMLGregorianCalendar value) {
            this.productionDate = value;
        }

        /**
         * 获取platformType属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlatformType() {
            return platformType;
        }

        /**
         * 设置platformType属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlatformType(String value) {
            this.platformType = value;
        }

        /**
         * 获取platformVersion属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlatformVersion() {
            return platformVersion;
        }

        /**
         * 设置platformVersion属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlatformVersion(String value) {
            this.platformVersion = value;
        }

        /**
         * 获取isdPLoadfileAid属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getIsdPLoadfileAid() {
            return isdPLoadfileAid;
        }

        /**
         * 设置isdPLoadfileAid属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsdPLoadfileAid(byte[] value) {
            this.isdPLoadfileAid = value;
        }

        /**
         * 获取isdPModuleAid属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getIsdPModuleAid() {
            return isdPModuleAid;
        }

        /**
         * 设置isdPModuleAid属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsdPModuleAid(byte[] value) {
            this.isdPModuleAid = value;
        }

        /**
         * 获取ecasd属性的值。
         * 
         * @return
         *     possible object is
         *     {@link SecurityDomainType }
         *     
         */
        public SecurityDomainType getEcasd() {
            return ecasd;
        }

        /**
         * 设置ecasd属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link SecurityDomainType }
         *     
         */
        public void setEcasd(SecurityDomainType value) {
            this.ecasd = value;
        }

        /**
         * 获取euiccCapabilities属性的值。
         * 
         * @return
         *     possible object is
         *     {@link EISType.EumSignedInfo.EuiccCapabilities }
         *     
         */
        public EISType.EumSignedInfo.EuiccCapabilities getEuiccCapabilities() {
            return euiccCapabilities;
        }

        /**
         * 设置euiccCapabilities属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link EISType.EumSignedInfo.EuiccCapabilities }
         *     
         */
        public void setEuiccCapabilities(EISType.EumSignedInfo.EuiccCapabilities value) {
            this.euiccCapabilities = value;
        }


        /**
         * <p>anonymous complex type的 Java 类。
         * 
         * <p>以下模式片段指定包含在此类中的预期内容。
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="CattpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="CattpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
         *         &lt;element name="HttpSupport" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="HttpVersion" type="{}ThreeDigitVersion" minOccurs="0"/&gt;
         *         &lt;element name="SecurePacketVersion" type="{}ThreeDigitVersion"/&gt;
         *         &lt;element name="RemoteProvisioningVersion" type="{}ThreeDigitVersion"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "cattpSupport",
            "cattpVersion",
            "httpSupport",
            "httpVersion",
            "securePacketVersion",
            "remoteProvisioningVersion"
        })
        public static class EuiccCapabilities {

            @XmlElement(name = "CattpSupport")
            protected boolean cattpSupport;
            @XmlElement(name = "CattpVersion")
            protected String cattpVersion;
            @XmlElement(name = "HttpSupport")
            protected boolean httpSupport;
            @XmlElement(name = "HttpVersion")
            protected String httpVersion;
            @XmlElement(name = "SecurePacketVersion",  required = true)
            protected String securePacketVersion;
            @XmlElement(name = "RemoteProvisioningVersion",  required = true)
            protected String remoteProvisioningVersion;

            /**
             * 获取cattpSupport属性的值。
             * 
             */
            public boolean isCattpSupport() {
                return cattpSupport;
            }

            /**
             * 设置cattpSupport属性的值。
             * 
             */
            public void setCattpSupport(boolean value) {
                this.cattpSupport = value;
            }

            /**
             * 获取cattpVersion属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCattpVersion() {
                return cattpVersion;
            }

            /**
             * 设置cattpVersion属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCattpVersion(String value) {
                this.cattpVersion = value;
            }

            /**
             * 获取httpSupport属性的值。
             * 
             */
            public boolean isHttpSupport() {
                return httpSupport;
            }

            /**
             * 设置httpSupport属性的值。
             * 
             */
            public void setHttpSupport(boolean value) {
                this.httpSupport = value;
            }

            /**
             * 获取httpVersion属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHttpVersion() {
                return httpVersion;
            }

            /**
             * 设置httpVersion属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHttpVersion(String value) {
                this.httpVersion = value;
            }

            /**
             * 获取securePacketVersion属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSecurePacketVersion() {
                return securePacketVersion;
            }

            /**
             * 设置securePacketVersion属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSecurePacketVersion(String value) {
                this.securePacketVersion = value;
            }

            /**
             * 获取remoteProvisioningVersion属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRemoteProvisioningVersion() {
                return remoteProvisioningVersion;
            }

            /**
             * 设置remoteProvisioningVersion属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRemoteProvisioningVersion(String value) {
                this.remoteProvisioningVersion = value;
            }

        }

    }


    /**
     * Type for a Profile in the EIS as the representation a profile loaded on the eUICC
     * 
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Iccid" type="{}ICCIDType"/&gt;
     *         &lt;element name="Isd-p-aid" type="{}AIDType"/&gt;
     *         &lt;element name="Mno-id" type="{}ObjectIdentifierType"/&gt;
     *         &lt;element name="FallbackAttribute" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="SubscriptionAddress" type="{}SubscriptionAddressType"/&gt;
     *         &lt;element name="State" type="{}ProfileStateType"/&gt;
     *         &lt;element name="Smdp-id" type="{}ObjectIdentifierType" minOccurs="0"/&gt;
     *         &lt;element name="ProfileType" type="{}String100" minOccurs="0"/&gt;
     *         &lt;element name="AllocatedMemory" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
     *         &lt;element name="FreeMemory" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
     *         &lt;element name="pol2" type="{}POL2Type"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "iccid",
        "isdPAid",
        "mnoId",
        "fallbackAttribute",
        "subscriptionAddress",
        "state",
        "smdpId",
        "profileType",
        "allocatedMemory",
        "freeMemory",
        "pol2"
    })
    public static class ProfileInfo {

        @XmlElement(name = "Iccid",  required = true)
        protected String iccid;
        @XmlElement(name = "Isd-p-aid",  required = true, type = String.class)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] isdPAid;
        @XmlElement(name = "Mno-id",  required = true)
        protected String mnoId;
        @XmlElement(name = "FallbackAttribute")
        protected boolean fallbackAttribute;
        @XmlElement(name = "SubscriptionAddress",  required = true)
        protected SubscriptionAddressType subscriptionAddress;
        @XmlElement(name = "State",  required = true)
        @XmlSchemaType(name = "string")
        protected ProfileStateType state;
        @XmlElement(name = "Smdp-id")
        protected String smdpId;
        @XmlElement(name = "ProfileType")
        protected String profileType;
        @XmlElement(name = "AllocatedMemory",  required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger allocatedMemory;
        @XmlElement(name = "FreeMemory")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger freeMemory;
        @XmlElement( required = true)
        protected POL2Type pol2;

        /**
         * 获取iccid属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIccid() {
            return iccid;
        }

        /**
         * 设置iccid属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIccid(String value) {
            this.iccid = value;
        }

        /**
         * 获取isdPAid属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getIsdPAid() {
            return isdPAid;
        }

        /**
         * 设置isdPAid属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIsdPAid(byte[] value) {
            this.isdPAid = value;
        }

        /**
         * 获取mnoId属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMnoId() {
            return mnoId;
        }

        /**
         * 设置mnoId属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMnoId(String value) {
            this.mnoId = value;
        }

        /**
         * 获取fallbackAttribute属性的值。
         * 
         */
        public boolean isFallbackAttribute() {
            return fallbackAttribute;
        }

        /**
         * 设置fallbackAttribute属性的值。
         * 
         */
        public void setFallbackAttribute(boolean value) {
            this.fallbackAttribute = value;
        }

        /**
         * 获取subscriptionAddress属性的值。
         * 
         * @return
         *     possible object is
         *     {@link SubscriptionAddressType }
         *     
         */
        public SubscriptionAddressType getSubscriptionAddress() {
            return subscriptionAddress;
        }

        /**
         * 设置subscriptionAddress属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link SubscriptionAddressType }
         *     
         */
        public void setSubscriptionAddress(SubscriptionAddressType value) {
            this.subscriptionAddress = value;
        }

        /**
         * 获取state属性的值。
         * 
         * @return
         *     possible object is
         *     {@link ProfileStateType }
         *     
         */
        public ProfileStateType getState() {
            return state;
        }

        /**
         * 设置state属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link ProfileStateType }
         *     
         */
        public void setState(ProfileStateType value) {
            this.state = value;
        }

        /**
         * 获取smdpId属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSmdpId() {
            return smdpId;
        }

        /**
         * 设置smdpId属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSmdpId(String value) {
            this.smdpId = value;
        }

        /**
         * 获取profileType属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProfileType() {
            return profileType;
        }

        /**
         * 设置profileType属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProfileType(String value) {
            this.profileType = value;
        }

        /**
         * 获取allocatedMemory属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getAllocatedMemory() {
            return allocatedMemory;
        }

        /**
         * 设置allocatedMemory属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setAllocatedMemory(BigInteger value) {
            this.allocatedMemory = value;
        }

        /**
         * 获取freeMemory属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getFreeMemory() {
            return freeMemory;
        }

        /**
         * 设置freeMemory属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setFreeMemory(BigInteger value) {
            this.freeMemory = value;
        }

        /**
         * 获取pol2属性的值。
         * 
         * @return
         *     possible object is
         *     {@link POL2Type }
         *     
         */
        public POL2Type getPol2() {
            return pol2;
        }

        /**
         * 设置pol2属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link POL2Type }
         *     
         */
        public void setPol2(POL2Type value) {
            this.pol2 = value;
        }

    }

}
