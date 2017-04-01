
package com.whty.efs.webservice.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AppQueryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AppQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="seID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appInstalledTag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppQueryRequest", propOrder = {
    "seID",
    "appInstalledTag"
})
public class AppQueryRequest {

    @XmlElement(required = true)
    protected String seID;
    @XmlElement(required = true)
    protected String appInstalledTag;

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
     * ��ȡappInstalledTag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppInstalledTag() {
        return appInstalledTag;
    }

    /**
     * ����appInstalledTag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppInstalledTag(String value) {
        this.appInstalledTag = value;
    }

}
