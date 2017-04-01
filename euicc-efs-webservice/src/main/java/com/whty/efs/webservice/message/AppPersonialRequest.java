
package com.whty.efs.webservice.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AppPersonialRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AppPersonialRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="partnerOrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AppAID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AppVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardNO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pan" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ats" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cardholderName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identityNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="deviceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rApdu" type="{http://www.tathing.com}Rapdu" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppPersonialRequest", propOrder = {
    "partnerOrgCode",
    "appAID",
    "appVersion",
    "cardNO",
    "pan",
    "ats",
    "cardholderName",
    "identity",
    "identityNumber",
    "phoneNumber",
    "deviceID",
    "rApdu"
})
public class AppPersonialRequest {

    @XmlElement(required = true)
    protected String partnerOrgCode;
    @XmlElement(name = "AppAID", required = true)
    protected String appAID;
    @XmlElement(name = "AppVersion", required = true)
    protected String appVersion;
    @XmlElement(required = true)
    protected String cardNO;
    @XmlElement(required = true)
    protected String pan;
    @XmlElement(required = true)
    protected String ats;
    @XmlElement(required = true)
    protected String cardholderName;
    @XmlElement(required = true)
    protected String identity;
    @XmlElement(required = true)
    protected String identityNumber;
    @XmlElement(required = true)
    protected String phoneNumber;
    @XmlElement(required = true)
    protected String deviceID;
    @XmlElement(nillable = true)
    protected List<Rapdu> rApdu;

    /**
     * ��ȡpartnerOrgCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerOrgCode() {
        return partnerOrgCode;
    }

    /**
     * ����partnerOrgCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerOrgCode(String value) {
        this.partnerOrgCode = value;
    }

    /**
     * ��ȡappAID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppAID() {
        return appAID;
    }

    /**
     * ����appAID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppAID(String value) {
        this.appAID = value;
    }

    /**
     * ��ȡappVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * ����appVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppVersion(String value) {
        this.appVersion = value;
    }

    /**
     * ��ȡcardNO���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNO() {
        return cardNO;
    }

    /**
     * ����cardNO���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNO(String value) {
        this.cardNO = value;
    }

    /**
     * ��ȡpan���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPan() {
        return pan;
    }

    /**
     * ����pan���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPan(String value) {
        this.pan = value;
    }

    /**
     * ��ȡats���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAts() {
        return ats;
    }

    /**
     * ����ats���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAts(String value) {
        this.ats = value;
    }

    /**
     * ��ȡcardholderName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * ����cardholderName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardholderName(String value) {
        this.cardholderName = value;
    }

    /**
     * ��ȡidentity���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * ����identity���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentity(String value) {
        this.identity = value;
    }

    /**
     * ��ȡidentityNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * ����identityNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityNumber(String value) {
        this.identityNumber = value;
    }

    /**
     * ��ȡphoneNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * ����phoneNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * ��ȡdeviceID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * ����deviceID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceID(String value) {
        this.deviceID = value;
    }

    /**
     * Gets the value of the rApdu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rApdu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRApdu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rapdu }
     * 
     * 
     */
    public List<Rapdu> getRApdu() {
        if (rApdu == null) {
            rApdu = new ArrayList<Rapdu>();
        }
        return this.rApdu;
    }

}
