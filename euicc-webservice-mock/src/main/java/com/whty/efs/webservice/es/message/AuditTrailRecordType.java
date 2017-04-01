
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This type contains the description of a platform or profile management operations performed by SM-SR or a notification received by SM-SR from the given eUICC.
 * 
 * <p>AuditTrailRecordType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AuditTrailRecordType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eid" type="{}EIDType"/&gt;
 *         &lt;element name="Smsr-id" type="{}ObjectIdentifierType"/&gt;
 *         &lt;element name="OperationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OperationType"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *               &lt;length value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RequesterId" type="{}ObjectIdentifierType" minOccurs="0"/&gt;
 *         &lt;element name="OperationExecutionStatus" type="{}ExecutionStatusType" minOccurs="0"/&gt;
 *         &lt;element name="Isd-p-aid" type="{}AIDType" minOccurs="0"/&gt;
 *         &lt;element name="Iccid" type="{}ICCIDType" minOccurs="0"/&gt;
 *         &lt;element name="Imei" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *         &lt;element name="Meid" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuditTrailRecordType", namespace = "", propOrder = {
    "eid",
    "smsrId",
    "operationDate",
    "operationType",
    "requesterId",
    "operationExecutionStatus",
    "isdPAid",
    "iccid",
    "imei",
    "meid"
})
public class AuditTrailRecordType {

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "Smsr-id", required = true)
    protected String smsrId;
    @XmlElement(name = "OperationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationDate;
    @XmlElement(name = "OperationType", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] operationType;
    @XmlElement(name = "RequesterId")
    protected String requesterId;
    @XmlElement(name = "OperationExecutionStatus")
    protected ExecutionStatusType operationExecutionStatus;
    @XmlElement(name = "Isd-p-aid", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] isdPAid;
    @XmlElement(name = "Iccid")
    protected String iccid;
    @XmlElement(name = "Imei", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] imei;
    @XmlElement(name = "Meid", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] meid;

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
     * 获取smsrId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsrId() {
        return smsrId;
    }

    /**
     * 设置smsrId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsrId(String value) {
        this.smsrId = value;
    }

    /**
     * 获取operationDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperationDate() {
        return operationDate;
    }

    /**
     * 设置operationDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperationDate(XMLGregorianCalendar value) {
        this.operationDate = value;
    }

    /**
     * 获取operationType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getOperationType() {
        return operationType;
    }

    /**
     * 设置operationType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationType(byte[] value) {
        this.operationType = value;
    }

    /**
     * 获取requesterId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequesterId() {
        return requesterId;
    }

    /**
     * 设置requesterId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequesterId(String value) {
        this.requesterId = value;
    }

    /**
     * 获取operationExecutionStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ExecutionStatusType }
     *     
     */
    public ExecutionStatusType getOperationExecutionStatus() {
        return operationExecutionStatus;
    }

    /**
     * 设置operationExecutionStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutionStatusType }
     *     
     */
    public void setOperationExecutionStatus(ExecutionStatusType value) {
        this.operationExecutionStatus = value;
    }

    /**
     * 获取isdPAid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getIsdPAid() {
        return isdPAid;
    }

    /**
     * 设置isdPAid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsdPAid(byte[] value) {
        this.isdPAid = value;
    }

    /**
     * 获取iccid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * 设置iccid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIccid(String value) {
        this.iccid = value;
    }

    /**
     * 获取imei属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getImei() {
        return imei;
    }

    /**
     * 设置imei属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImei(byte[] value) {
        this.imei = value;
    }

    /**
     * 获取meid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getMeid() {
        return meid;
    }

    /**
     * 设置meid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeid(byte[] value) {
        this.meid = value;
    }

}
