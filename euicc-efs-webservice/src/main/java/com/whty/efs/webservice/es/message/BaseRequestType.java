
package com.whty.efs.webservice.es.message;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.whty.efs.packets.message.Body;

/**
 * The base type for a Request types.
 * 
 * <p>BaseRequestType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BaseRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FunctionCallIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValidityPeriod" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRequestType", propOrder = {
    "functionCallIdentifier",
    "validityPeriod"
})
@XmlSeeAlso({
    ES2UpdateSubscriptionAddressRequest.class,
    ES2DisableProfileRequest.class,
    ES2EnableProfileRequest.class,
    ES2UpdatePolicyRulesRequest.class,
    ES2DownloadProfileRequest.class,
    ES2GetEISRequest.class,
    ES2DeleteProfileRequest.class
})
public class BaseRequestType implements Body{

    @XmlElement(name = "FunctionCallIdentifier", required = true)
    protected String functionCallIdentifier;
    @XmlElement(name = "ValidityPeriod")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger validityPeriod;

    /**
     * 获取functionCallIdentifier属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionCallIdentifier() {
        return functionCallIdentifier;
    }

    /**
     * 设置functionCallIdentifier属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionCallIdentifier(String value) {
        this.functionCallIdentifier = value;
    }

    /**
     * 获取validityPeriod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * 设置validityPeriod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setValidityPeriod(BigInteger value) {
        this.validityPeriod = value;
    }

}
