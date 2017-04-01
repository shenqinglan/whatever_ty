
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
 *         &lt;element name="Sd-aid" type="{}AIDType"/&gt;
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
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
    "sdAid",
    "data",
    "moreToDo"
})
@XmlRootElement(name = "ES3-SendDataRequest")
public class ES3SendDataRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "Sd-aid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] sdAid;
    @XmlElement(name = "Data", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] data;
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
     * 获取sdAid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSdAid() {
        return sdAid;
    }

    /**
     * 设置sdAid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSdAid(byte[] value) {
        this.sdAid = value;
    }

    /**
     * 获取data属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 设置data属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(byte[] value) {
        this.data = value;
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
