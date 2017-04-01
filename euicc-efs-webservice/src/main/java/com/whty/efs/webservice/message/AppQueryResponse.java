
package com.whty.efs.webservice.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>AppQueryResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AppQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rspnMsg" type="{http://www.tathing.com}RspnMsg"/&gt;
 *         &lt;element name="appAID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="seID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appDesc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="providerID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="onlineDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="icoUrl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="logoUrl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppQueryResponse", propOrder = {
    "rspnMsg",
    "appAID",
    "appVersion",
    "seID",
    "appName",
    "appType",
    "appDesc",
    "providerID",
    "providerName",
    "onlineDate",
    "icoUrl",
    "logoUrl"
})
public class AppQueryResponse {

    @XmlElement(required = true)
    protected RspnMsg rspnMsg;
    @XmlElement(required = true)
    protected String appAID;
    @XmlElement(required = true)
    protected String appVersion;
    @XmlElement(required = true)
    protected String seID;
    @XmlElement(required = true)
    protected String appName;
    @XmlElement(required = true)
    protected String appType;
    @XmlElement(required = true)
    protected String appDesc;
    @XmlElement(required = true)
    protected String providerID;
    @XmlElement(required = true)
    protected String providerName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar onlineDate;
    @XmlElement(required = true)
    protected String icoUrl;
    @XmlElement(required = true)
    protected String logoUrl;

    /**
     * ��ȡrspnMsg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RspnMsg }
     *     
     */
    public RspnMsg getRspnMsg() {
        return rspnMsg;
    }

    /**
     * ����rspnMsg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RspnMsg }
     *     
     */
    public void setRspnMsg(RspnMsg value) {
        this.rspnMsg = value;
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
     * ��ȡseID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeID() {
        return seID;
    }

    /**
     * ����seID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeID(String value) {
        this.seID = value;
    }

    /**
     * ��ȡappName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppName() {
        return appName;
    }

    /**
     * ����appName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppName(String value) {
        this.appName = value;
    }

    /**
     * ��ȡappType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppType() {
        return appType;
    }

    /**
     * ����appType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppType(String value) {
        this.appType = value;
    }

    /**
     * ��ȡappDesc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * ����appDesc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppDesc(String value) {
        this.appDesc = value;
    }

    /**
     * ��ȡproviderID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderID() {
        return providerID;
    }

    /**
     * ����providerID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderID(String value) {
        this.providerID = value;
    }

    /**
     * ��ȡproviderName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * ����providerName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * ��ȡonlineDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOnlineDate() {
        return onlineDate;
    }

    /**
     * ����onlineDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOnlineDate(XMLGregorianCalendar value) {
        this.onlineDate = value;
    }

    /**
     * ��ȡicoUrl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcoUrl() {
        return icoUrl;
    }

    /**
     * ����icoUrl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcoUrl(String value) {
        this.icoUrl = value;
    }

    /**
     * ��ȡlogoUrl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * ����logoUrl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoUrl(String value) {
        this.logoUrl = value;
    }

}
