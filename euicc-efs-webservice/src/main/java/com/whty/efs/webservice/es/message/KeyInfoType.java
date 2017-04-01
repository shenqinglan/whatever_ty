
package com.whty.efs.webservice.es.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * <p>KeyInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="KeyInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyName"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyValue"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}RetrievalMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}X509Data"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}PGPData"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SPKIData"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}MgmtData"/&gt;
 *         &lt;any processContents='lax' namespace='##other'/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyInfoType", namespace = "http://www.w3.org/2000/09/xmldsig#", propOrder = {
    "keyName",
    "keyValue",
    "retrevalMethod",
    "x509DataType",
    "pGPData",
    "sPKIData",
    "mgmtData"
})
public class KeyInfoType {

   /* @XmlElementRefs({
        @XmlElementRef(name = "PGPData", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "MgmtData", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "KeyName", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "KeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "SPKIData", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "RetrievalMethod", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class)
    })
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;*/
    @XmlElement(name = "KeyName")
    protected String keyName;
    @XmlElement(name = "KeyValue")
    protected KeyValueType keyValue;
    @XmlElement(name = "RetrievalMethod")
    protected RetrievalMethodType retrevalMethod;
    @XmlElement(name = "X509Data")
    protected X509DataType x509DataType;
    @XmlElement(name = "PGPData")
    protected PGPDataType pGPData;
    @XmlElement(name = "SPKIData")
    protected SPKIDataType sPKIData;
    @XmlElement(name = "MgmtData")
    protected String mgmtData;
    
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link PGPDataType }{@code >}
     * {@link Object }
     * {@link String }
     * {@link JAXBElement }{@code <}{@link X509DataType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link KeyValueType }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link SPKIDataType }{@code >}
     * {@link JAXBElement }{@code <}{@link RetrievalMethodType }{@code >}
     * 
     * 
     */
    /*public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    public void setContent(List<Object> content) {
		this.content = content;
	}*/

	/**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public KeyValueType getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(KeyValueType keyValue) {
		this.keyValue = keyValue;
	}

	public RetrievalMethodType getRetrevalMethod() {
		return retrevalMethod;
	}

	public void setRetrevalMethod(RetrievalMethodType retrevalMethod) {
		this.retrevalMethod = retrevalMethod;
	}

	public X509DataType getX509DataType() {
		return x509DataType;
	}

	public void setX509DataType(X509DataType x509DataType) {
		this.x509DataType = x509DataType;
	}

	public PGPDataType getpGPData() {
		return pGPData;
	}

	public void setpGPData(PGPDataType pGPData) {
		this.pGPData = pGPData;
	}

	public SPKIDataType getsPKIData() {
		return sPKIData;
	}

	public void setsPKIData(SPKIDataType sPKIData) {
		this.sPKIData = sPKIData;
	}

	public String getMgmtData() {
		return mgmtData;
	}

	public void setMgmtData(String mgmtData) {
		this.mgmtData = mgmtData;
	}

	/**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
