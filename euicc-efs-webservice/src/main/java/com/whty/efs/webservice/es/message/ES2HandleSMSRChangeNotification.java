
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}BaseNotificationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eis" type="{}EISType"/&gt;
 *         &lt;element name="CompletionTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
    "completionTimestamp"
})
@XmlRootElement(name = "ES2-HandleSMSRChangeNotification")
public class ES2HandleSMSRChangeNotification
    extends BaseNotificationType
{

    @XmlElement(name = "Eis", required = true)
    protected EISType eis;
    @XmlElement(name = "CompletionTimestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completionTimestamp;

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
     * 获取completionTimestamp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompletionTimestamp() {
        return completionTimestamp;
    }

    /**
     * 设置completionTimestamp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompletionTimestamp(XMLGregorianCalendar value) {
        this.completionTimestamp = value;
    }

}
