
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Eis" type="{}EISType" minOccurs="0"/&gt;
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
    "eis"
})
@XmlRootElement(name = "ES2-GetEISResponse")
public class ES2GetEISResponse
    extends BaseResponseType
{

    @XmlElement(name = "Eis")
    protected EISType eis;

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

}
