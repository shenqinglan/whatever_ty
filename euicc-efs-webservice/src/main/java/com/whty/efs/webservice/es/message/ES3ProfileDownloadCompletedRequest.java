
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}BaseRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eid" type="{}EIDType"/&gt;
 *         &lt;element name="Iccid" type="{}ICCIDType"/&gt;
 *         &lt;element name="ProfileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SubscriptionAddress" type="{}SubscriptionAddressType" minOccurs="0"/&gt;
 *         &lt;element name="pol2" type="{}POL2Type" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eid",
    "iccid",
    "profileType",
    "subscriptionAddress",
    "pol2"
})
@XmlRootElement(name = "ES3-ProfileDownloadCompletedRequest")
public class ES3ProfileDownloadCompletedRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "Iccid", required = true)
    protected String iccid;
    @XmlElement(name = "ProfileType")
    protected String profileType;
    @XmlElement(name = "SubscriptionAddress")
    protected SubscriptionAddressType subscriptionAddress;
    protected POL2Type pol2;

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
