
package com.whty.efs.webservice.es.message;

import java.math.BigInteger;
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
 *         &lt;element name="Mno-id" type="{}ObjectIdentifierType"/&gt;
 *         &lt;element name="RequiredMemory" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="MoreToDo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
    "mnoId",
    "requiredMemory",
    "moreToDo"
})
@XmlRootElement(name = "ES3-CreateISDPRequest")
public class ES3CreateISDPRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "Iccid", required = true)
    protected String iccid;
    @XmlElement(name = "Mno-id", required = true)
    protected String mnoId;
    @XmlElement(name = "RequiredMemory", required = true)
    protected BigInteger requiredMemory;
    @XmlElement(name = "MoreToDo")
    protected Boolean moreToDo;

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
     * 获取requiredMemory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequiredMemory() {
        return requiredMemory;
    }

    /**
     * 设置requiredMemory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequiredMemory(BigInteger value) {
        this.requiredMemory = value;
    }

    /**
     * 获取moreToDo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMoreToDo() {
        return moreToDo;
    }

    /**
     * 设置moreToDo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMoreToDo(Boolean value) {
        this.moreToDo = value;
    }

	public Boolean getMoreToDo() {
		return moreToDo;
	}

}
