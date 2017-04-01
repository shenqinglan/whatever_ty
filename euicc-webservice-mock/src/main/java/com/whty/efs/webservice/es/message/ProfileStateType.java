
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ProfileStateType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ProfileStateType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Created"/&gt;
 *     &lt;enumeration value="Disabled"/&gt;
 *     &lt;enumeration value="Enabled"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProfileStateType", namespace = "")
@XmlEnum
public enum ProfileStateType {

    @XmlEnumValue("Created")
    CREATED("Created"),
    @XmlEnumValue("Disabled")
    DISABLED("Disabled"),
    @XmlEnumValue("Enabled")
    ENABLED("Enabled");
    private final String value;

    ProfileStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProfileStateType fromValue(String v) {
        for (ProfileStateType c: ProfileStateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
