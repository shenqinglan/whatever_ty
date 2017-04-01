
package com.whty.efs.webservice.es.message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * This Type provides the description of a Security Domain.
 * 
 * <p>SecurityDomainType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SecurityDomainType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Aid" type="{}AIDType"/&gt;
 *         &lt;element name="Tar" type="{}TARType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Sin" type="{}hexBinary16"/&gt;
 *         &lt;element name="Sdin" type="{}hexBinary16"/&gt;
 *         &lt;element name="Role" type="{}SDRoleType"/&gt;
 *         &lt;element name="Keyset" maxOccurs="127"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Version" type="{}KeysetVersionType"/&gt;
 *                   &lt;element name="Type" type="{}KeysetUsageType" minOccurs="0"/&gt;
 *                   &lt;element name="Cntr" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
 *                   &lt;choice maxOccurs="128" minOccurs="0"&gt;
 *                     &lt;element name="Key" type="{}KeyType"/&gt;
 *                     &lt;element name="Certificate" type="{}GPCertificateType"/&gt;
 *                   &lt;/choice&gt;
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
@XmlType(name = "SecurityDomainType", propOrder = {
    "aid",
    "tar",
    "sin",
    "sdin",
    "role",
    "keyset"
})
public class SecurityDomainType {

    @XmlElement(name = "Aid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] aid;
    @XmlElement(name = "Tar", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected List<byte[]> tar;
    @XmlElement(name = "Sin", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] sin;
    @XmlElement(name = "Sdin", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] sdin;
    @XmlElement(name = "Role", required = true)
    @XmlSchemaType(name = "string")
    protected SDRoleType role;
    @XmlElement(name = "Keyset", required = true)
    protected List<SecurityDomainType.Keyset> keyset;

    /**
     * 获取aid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getAid() {
        return aid;
    }

    /**
     * 设置aid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAid(byte[] value) {
        this.aid = value;
    }

    /**
     * Gets the value of the tar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<byte[]> getTar() {
        if (tar == null) {
            tar = new ArrayList<byte[]>();
        }
        return this.tar;
    }

    /**
     * 获取sin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSin() {
        return sin;
    }

    /**
     * 设置sin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSin(byte[] value) {
        this.sin = value;
    }

    /**
     * 获取sdin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSdin() {
        return sdin;
    }

    /**
     * 设置sdin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSdin(byte[] value) {
        this.sdin = value;
    }

    /**
     * 获取role属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SDRoleType }
     *     
     */
    public SDRoleType getRole() {
        return role;
    }

    /**
     * 设置role属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SDRoleType }
     *     
     */
    public void setRole(SDRoleType value) {
        this.role = value;
    }

    /**
     * Gets the value of the keyset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityDomainType.Keyset }
     * 
     * 
     */
    public List<SecurityDomainType.Keyset> getKeyset() {
        if (keyset == null) {
            keyset = new ArrayList<SecurityDomainType.Keyset>();
        }
        return this.keyset;
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
     *         &lt;element name="Version" type="{}KeysetVersionType"/&gt;
     *         &lt;element name="Type" type="{}KeysetUsageType" minOccurs="0"/&gt;
     *         &lt;element name="Cntr" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/&gt;
     *         &lt;choice maxOccurs="128" minOccurs="0"&gt;
     *           &lt;element name="Key" type="{}KeyType"/&gt;
     *           &lt;element name="Certificate" type="{}GPCertificateType"/&gt;
     *         &lt;/choice&gt;
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
        "version",
        "type",
        "cntr",
        "keyOrCertificate"
    })
    public static class Keyset {

        @XmlElement(name = "Version")
        @XmlSchemaType(name = "integer")
        protected int version;
        @XmlElement(name = "Type")
        @XmlSchemaType(name = "string")
        protected KeysetUsageType type;
        @XmlElement(name = "Cntr")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger cntr;
        @XmlElements({
            @XmlElement(name = "Key", type = KeyType.class),
            @XmlElement(name = "Certificate", type = GPCertificateType.class)
        })
        protected List<Object> keyOrCertificate;

        /**
         * 获取version属性的值。
         * 
         */
        public int getVersion() {
            return version;
        }

        /**
         * 设置version属性的值。
         * 
         */
        public void setVersion(int value) {
            this.version = value;
        }

        /**
         * 获取type属性的值。
         * 
         * @return
         *     possible object is
         *     {@link KeysetUsageType }
         *     
         */
        public KeysetUsageType getType() {
            return type;
        }

        /**
         * 设置type属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link KeysetUsageType }
         *     
         */
        public void setType(KeysetUsageType value) {
            this.type = value;
        }

        /**
         * 获取cntr属性的值。
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCntr() {
            return cntr;
        }

        /**
         * 设置cntr属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCntr(BigInteger value) {
            this.cntr = value;
        }

        /**
         * Gets the value of the keyOrCertificate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the keyOrCertificate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKeyOrCertificate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link KeyType }
         * {@link GPCertificateType }
         * 
         * 
         */
        public List<Object> getKeyOrCertificate() {
            if (keyOrCertificate == null) {
                keyOrCertificate = new ArrayList<Object>();
            }
            return this.keyOrCertificate;
        }

    }

}
