
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the POL2 rule for a Profile
 * 
 * <p>POL2RuleType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="POL2RuleType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Subject" type="{}POL2RuleSubjectType"/&gt;
 *         &lt;element name="Action" type="{}POL2RuleActionType"/&gt;
 *         &lt;element name="Qualification" type="{}POL2RuleQualificationType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "POL2RuleType", namespace = "", propOrder = {
    "subject",
    "action",
    "qualification"
})
public class POL2RuleType {

    @XmlElement(name = "Subject", required = true)
    @XmlSchemaType(name = "string")
    protected POL2RuleSubjectType subject;
    @XmlElement(name = "Action", required = true)
    @XmlSchemaType(name = "string")
    protected POL2RuleActionType action;
    @XmlElement(name = "Qualification", required = true)
    @XmlSchemaType(name = "string")
    protected POL2RuleQualificationType qualification;

    /**
     * 获取subject属性的值。
     * 
     * @return
     *     possible object is
     *     {@link POL2RuleSubjectType }
     *     
     */
    public POL2RuleSubjectType getSubject() {
        return subject;
    }

    /**
     * 设置subject属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link POL2RuleSubjectType }
     *     
     */
    public void setSubject(POL2RuleSubjectType value) {
        this.subject = value;
    }

    /**
     * 获取action属性的值。
     * 
     * @return
     *     possible object is
     *     {@link POL2RuleActionType }
     *     
     */
    public POL2RuleActionType getAction() {
        return action;
    }

    /**
     * 设置action属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link POL2RuleActionType }
     *     
     */
    public void setAction(POL2RuleActionType value) {
        this.action = value;
    }

    /**
     * 获取qualification属性的值。
     * 
     * @return
     *     possible object is
     *     {@link POL2RuleQualificationType }
     *     
     */
    public POL2RuleQualificationType getQualification() {
        return qualification;
    }

    /**
     * 设置qualification属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link POL2RuleQualificationType }
     *     
     */
    public void setQualification(POL2RuleQualificationType value) {
        this.qualification = value;
    }

}
