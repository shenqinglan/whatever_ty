
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
 *     &lt;extension base="{}BaseResponseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RandomChallenge" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
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
    "randomChallenge"
})
@XmlRootElement(name = "ES7-AuthenticateSMSRResponse")
public class ES7AuthenticateSMSRResponse
    extends BaseResponseType
{

    @XmlElement(name = "RandomChallenge", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] randomChallenge;

    /**
     * 获取randomChallenge属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getRandomChallenge() {
        return randomChallenge;
    }

    /**
     * 设置randomChallenge属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRandomChallenge(byte[] value) {
        this.randomChallenge = value;
    }

}
