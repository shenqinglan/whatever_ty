
package com.whty.efs.webservice.es.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>SignatureType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SignatureType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SignedInfo"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureValue"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyInfo" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Object" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureType", namespace = "http://www.w3.org/2000/09/xmldsig#", propOrder = {
    "signedInfo",
    "signatureValue",
    "keyInfo",
    "object"
})
public class SignatureType {

    @XmlElement(name = "SignedInfo", required = true)
    protected SignedInfoType signedInfo;
    @XmlElement(name = "SignatureValue", required = true)
    protected SignatureValueType signatureValue;
    @XmlElement(name = "KeyInfo")
    protected KeyInfoType keyInfo;
    @XmlElement(name = "Object")
    protected List<ObjectType> object;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * 获取signedInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SignedInfoType }
     *     
     */
    public SignedInfoType getSignedInfo() {
        return signedInfo;
    }

    /**
     * 设置signedInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SignedInfoType }
     *     
     */
    public void setSignedInfo(SignedInfoType value) {
        this.signedInfo = value;
    }

    /**
     * 获取signatureValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SignatureValueType }
     *     
     */
    public SignatureValueType getSignatureValue() {
        return signatureValue;
    }

    /**
     * 设置signatureValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureValueType }
     *     
     */
    public void setSignatureValue(SignatureValueType value) {
        this.signatureValue = value;
    }

    /**
     * 获取keyInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link KeyInfoType }
     *     
     */
    public KeyInfoType getKeyInfo() {
        return keyInfo;
    }

    /**
     * 设置keyInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link KeyInfoType }
     *     
     */
    public void setKeyInfo(KeyInfoType value) {
        this.keyInfo = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the object property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectType }
     * 
     * 
     */
    public List<ObjectType> getObject() {
        if (object == null) {
            object = new ArrayList<ObjectType>();
        }
        return this.object;
    }

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
