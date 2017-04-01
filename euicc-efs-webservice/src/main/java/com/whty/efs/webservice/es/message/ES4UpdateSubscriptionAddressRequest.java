
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
 *         &lt;element name="newSubscriptionAddress" type="{}SubscriptionAddressType"/&gt;
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
    "newSubscriptionAddress"
})
@XmlRootElement(name = "ES4-UpdateSubscriptionAddressRequest")
public class ES4UpdateSubscriptionAddressRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "Iccid", required = true)
    protected String iccid;
    @XmlElement(required = true)
    protected SubscriptionAddressType newSubscriptionAddress;

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
     * 获取newSubscriptionAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SubscriptionAddressType }
     *     
     */
    public SubscriptionAddressType getNewSubscriptionAddress() {
        return newSubscriptionAddress;
    }

    /**
     * 设置newSubscriptionAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SubscriptionAddressType }
     *     
     */
    public void setNewSubscriptionAddress(SubscriptionAddressType value) {
        this.newSubscriptionAddress = value;
    }

}
