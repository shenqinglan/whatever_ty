
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ES1-MessageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ES1-MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ES1-RegisterEISRequest"/&gt;
 *     &lt;enumeration value="ES1-RegisterEISResponse"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ES1-MessageType", namespace = "")
@XmlEnum
public enum ES1MessageType {

    @XmlEnumValue("ES1-RegisterEISRequest")
    ES_1_REGISTER_EIS_REQUEST("ES1-RegisterEISRequest"),
    @XmlEnumValue("ES1-RegisterEISResponse")
    ES_1_REGISTER_EIS_RESPONSE("ES1-RegisterEISResponse");
    private final String value;

    ES1MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ES1MessageType fromValue(String v) {
        for (ES1MessageType c: ES1MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
