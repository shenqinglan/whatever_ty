
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>StatusType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="StatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EXECUTED_SUCCESS"/&gt;
 *     &lt;enumeration value="EXECUTED_WITHWARNING"/&gt;
 *     &lt;enumeration value="FAILED"/&gt;
 *     &lt;enumeration value="EXPIRED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatusType", namespace = "")
@XmlEnum
public enum StatusType {

    EXECUTED_SUCCESS,
    EXECUTED_WITHWARNING,
    FAILED,
    EXPIRED;

    public String value() {
        return name();
    }

    public static StatusType fromValue(String v) {
        return valueOf(v);
    }

}
