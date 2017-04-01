
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Indicates whether the execution of a processing has been completed correctly or not and provides information to give details on the processing result (status code, status data, status message)
 * 
 * <p>ExecutionStatusType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ExecutionStatusType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Status" type="{}StatusType"/&gt;
 *         &lt;element name="StatusCodeData" type="{}StatusCodeDataType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecutionStatusType", namespace = "", propOrder = {
    "status",
    "statusCodeData"
})
public class ExecutionStatusType {

    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected StatusType status;
    @XmlElement(name = "StatusCodeData")
    protected StatusCodeDataType statusCodeData;

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * 获取statusCodeData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link StatusCodeDataType }
     *     
     */
    public StatusCodeDataType getStatusCodeData() {
        return statusCodeData;
    }

    /**
     * 设置statusCodeData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCodeDataType }
     *     
     */
    public void setStatusCodeData(StatusCodeDataType value) {
        this.statusCodeData = value;
    }

}
