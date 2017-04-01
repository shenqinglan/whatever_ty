
package com.whty.efs.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>AppQueryResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取rspnMsg属性的值。
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
     * 设置rspnMsg属性的值。
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
     * 获取appAID属性的值。
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
     * 设置appAID属性的值。
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
     * 获取appVersion属性的值。
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
     * 设置appVersion属性的值。
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
     * 获取seID属性的值。
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
     * 设置seID属性的值。
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
     * 获取appName属性的值。
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
     * 设置appName属性的值。
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
     * 获取appType属性的值。
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
     * 设置appType属性的值。
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
     * 获取appDesc属性的值。
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
     * 设置appDesc属性的值。
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
     * 获取providerID属性的值。
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
     * 设置providerID属性的值。
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
     * 获取providerName属性的值。
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
     * 设置providerName属性的值。
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
     * 获取onlineDate属性的值。
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
     * 设置onlineDate属性的值。
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
     * 获取icoUrl属性的值。
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
     * 设置icoUrl属性的值。
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
     * 获取logoUrl属性的值。
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
     * 设置logoUrl属性的值。
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
