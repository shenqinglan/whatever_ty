
package com.whty.efs.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RspnMsg complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取sts属性的值。
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
     * 设置sts属性的值。
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
     * 获取rjctCd属性的值。
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
     * 设置rjctCd属性的值。
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
     * 获取rjctInfo属性的值。
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
     * 设置rjctInfo属性的值。
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
     * 获取endFlag属性的值。
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
     * 设置endFlag属性的值。
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
