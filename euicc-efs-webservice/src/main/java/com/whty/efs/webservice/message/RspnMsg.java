
package com.whty.efs.webservice.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RspnMsg complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RspnMsg"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sts" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rjctCd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rjctInfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="endFlag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RspnMsg", propOrder = {
    "sts",
    "rjctCd",
    "rjctInfo",
    "endFlag"
})
public class RspnMsg {

    @XmlElement(required = true)
    protected String sts;
    @XmlElement(required = true)
    protected String rjctCd;
    @XmlElement(required = true)
    protected String rjctInfo;
    @XmlElement(required = true)
    protected String endFlag;

    /**
     * ��ȡsts���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSts() {
        return sts;
    }

    /**
     * ����sts���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSts(String value) {
        this.sts = value;
    }

    /**
     * ��ȡrjctCd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRjctCd() {
        return rjctCd;
    }

    /**
     * ����rjctCd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRjctCd(String value) {
        this.rjctCd = value;
    }

    /**
     * ��ȡrjctInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRjctInfo() {
        return rjctInfo;
    }

    /**
     * ����rjctInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRjctInfo(String value) {
        this.rjctInfo = value;
    }

    /**
     * ��ȡendFlag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndFlag() {
        return endFlag;
    }

    /**
     * ����endFlag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndFlag(String value) {
        this.endFlag = value;
    }

}
