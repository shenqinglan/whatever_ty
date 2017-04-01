
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
 *         &lt;element name="CurrentSMSRid" type="{}ObjectIdentifierType"/&gt;
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
    "currentSMSRid"
})
@XmlRootElement(name = "ES4-PrepareSMSRChangeRequest")
public class ES4PrepareSMSRChangeRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "CurrentSMSRid", required = true)
    protected String currentSMSRid;

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
     * 获取currentSMSRid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentSMSRid() {
        return currentSMSRid;
    }

    /**
     * 设置currentSMSRid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentSMSRid(String value) {
        this.currentSMSRid = value;
    }

}
