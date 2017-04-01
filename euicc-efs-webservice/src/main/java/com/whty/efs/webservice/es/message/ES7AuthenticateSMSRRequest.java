
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
 *         &lt;element name="Eis" type="{}EISType"/&gt;
 *         &lt;element name="SmsrCertificate" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
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
    "eis",
    "smsrCertificate"
})
@XmlRootElement(name = "ES7-AuthenticateSMSRRequest")
public class ES7AuthenticateSMSRRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eis", required = true)
    protected EISType eis;
    @XmlElement(name = "SmsrCertificate", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] smsrCertificate;

    /**
     * 获取eis属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EISType }
     *     
     */
    public EISType getEis() {
        return eis;
    }

    /**
     * 设置eis属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EISType }
     *     
     */
    public void setEis(EISType value) {
        this.eis = value;
    }

    /**
     * 获取smsrCertificate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSmsrCertificate() {
        return smsrCertificate;
    }

    /**
     * 设置smsrCertificate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsrCertificate(byte[] value) {
        this.smsrCertificate = value;
    }

}
