
package com.whty.efs.webservice.es.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>KeyValueType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="KeyValueType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}DSAKeyValue"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}RSAKeyValue"/&gt;
 *         &lt;any processContents='lax' namespace='##other'/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyValueType", namespace = "http://www.w3.org/2000/09/xmldsig#", propOrder = {
    "dSAKeyValue",
    "rSAKeyValue"
})
public class KeyValueType {

    /*@XmlElementRefs({
        @XmlElementRef(name = "DSAKeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class),
        @XmlElementRef(name = "RSAKeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class)
    })
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;*/
    @XmlElement(name = "DSAKeyValue")
    protected DSAKeyValueType dSAKeyValue;
    @XmlElement(name = "RSAKeyValue")
    protected RSAKeyValueType rSAKeyValue;
	public DSAKeyValueType getdSAKeyValue() {
		return dSAKeyValue;
	}
	public void setdSAKeyValue(DSAKeyValueType dSAKeyValue) {
		this.dSAKeyValue = dSAKeyValue;
	}
	public RSAKeyValueType getrSAKeyValue() {
		return rSAKeyValue;
	}
	public void setrSAKeyValue(RSAKeyValueType rSAKeyValue) {
		this.rSAKeyValue = rSAKeyValue;
	}

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
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link DSAKeyValueType }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link RSAKeyValueType }{@code >}
     * {@link Element }
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

}
